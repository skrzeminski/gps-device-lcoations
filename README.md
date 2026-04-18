# Enigma — Device Tracking System

A microservices-based system for registering devices and tracking their GPS locations in real time. Built with **Spring Boot**, **Apache Kafka**, and **PostgreSQL**.

---

## Architecture

```
┌──────────────────────────┐        Kafka: device-registered       ┌─────────────────────┐
│ device-registration-     │ ────────────────────────────────────► │   device-service    │
│ service  (port 8081)     │                                        │   (port 8082)       │
│                          │                                        │   PostgreSQL        │
└──────────────────────────┘                                        └─────────────────────┘
                                                                             ▲
┌──────────────────────────┐        Kafka: gps-locations           ┌────────┴────────────┐
│ gps-registration-        │ ────────────────────────────────────► │   gps-service       │
│ service  (port 8083)     │                                        │   (port 8084)       │
│                          │                                        │   PostgreSQL        │
└──────────────────────────┘                                        └─────────────────────┘
```

### Services

| Service | Port | Role |
|---|---|---|
| `device-registration-service` | `8081` | Accepts device registration requests, publishes event to Kafka |
| `device-service` | `8082` | Consumes Kafka events, persists devices, exposes query API |
| `gps-registration-service` | `8083` | Accepts GPS location updates, publishes event to Kafka |
| `gps-service` | `8084` | Consumes Kafka events, persists locations, exposes query API |

### Kafka Topics

| Topic | Producer | Consumer |
|---|---|---|
| `device-registered` | `device-registration-service` | `device-service` |
| `gps-locations` | `gps-registration-service` | `gps-service` |

---

## Infrastructure

Start all required infrastructure with Docker Compose:

```bash
docker-compose up -d
```

This starts:
- **Zookeeper** — port `2181`
- **Kafka** — port `9092`
- **Kafka UI** — port `8080` → [http://localhost:8080](http://localhost:8080)
- **PostgreSQL** — port `5432`, database `testdb`, user `sa`, password `sa`

---

## Running the Services

Start each service independently (in separate terminals or as background processes):

```bash
# Terminal 1
cd device-registration-service && ./mvnw spring-boot:run

# Terminal 2
cd device-service && ./mvnw spring-boot:run

# Terminal 3
cd gps-registration-service && ./mvnw spring-boot:run

# Terminal 4
cd gps-service && ./mvnw spring-boot:run
```

---

## API Reference

### 1. Register a Device

Registers a new device. Publishes a `DeviceRegisteredEvent` to the `device-registered` Kafka topic.

```
POST http://localhost:8081/api/v1/devices
Content-Type: application/json
```

**Request body**

```json
{
  "deviceId": "3fa85f64-5717-4562-b3fc-2c963f66afa2",
  "name": "iPhone 15",
  "type": "ANDROID"
}
```

| Field | Type | Required | Description |
|---|---|---|---|
| `deviceId` | `string (UUID)` | ✅ | Unique device identifier |
| `name` | `string` | ✅ | Human-readable device name |
| `type` | `string` | ✅ | One of: `ANDROID`, `IOS`, `IOT` |

**Response** `201 Created`

```json
{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa2"
}
```

---

### 2. Get All Devices

Returns all registered devices stored in the database.

```
GET http://localhost:8082/api/v1/devices
```

**Response** `200 OK`

```json
[
  {
    "deviceId": "3fa85f64-5717-4562-b3fc-2c963f66afa2",
    "name": "iPhone 15",
    "type": "ANDROID"
  }
]
```

---

### 3. Get Device by ID

Returns a single device by its UUID.

```
GET http://localhost:8082/api/v1/devices/{deviceId}
```

| Path parameter | Description |
|---|---|
| `deviceId` | UUID of the device |

**Response** `200 OK`

```json
{
  "deviceId": "3fa85f64-5717-4562-b3fc-2c963f66afa2",
  "name": "iPhone 15",
  "type": "ANDROID"
}
```

**Error** `404 Not Found` — device does not exist.

---

### 4. Submit GPS Location

Records a new GPS location for a device. Publishes an event to the `gps-locations` Kafka topic.

```
POST http://localhost:8083/api/v1/devices/{deviceId}/locations
Content-Type: application/json
```

| Path parameter | Description |
|---|---|
| `deviceId` | UUID of the device |

**Request body**

```json
{
  "deviceId": "3fa85f64-5717-4562-b3fc-2c963f66afa2",
  "lat": 52.2297,
  "lon": 21.0122,
  "timestamp": "2026-04-18T12:46:56Z"
}
```

| Field | Type | Required | Validation |
|---|---|---|---|
| `deviceId` | `UUID` | ✅ | Must not be null |
| `lat` | `double` | ✅ | `-90.0` to `90.0` |
| `lon` | `double` | ✅ | `-180.0` to `180.0` |
| `timestamp` | `ISO 8601 (UTC)` | ✅ | Must be past or present |

**Response** `201 Created` (empty body)

**Validation errors thrown by `gps-service`** on consumption:

| Code | Reason |
|---|---|
| `INVALID_LATITUDE` | Latitude out of `[-90, 90]` range |
| `INVALID_LONGITUDE` | Longitude out of `[-180, 180]` range |
| `FUTURE_TIMESTAMP` | Timestamp is in the future |
| `DUPLICATE_EVENT` | Same timestamp as the previous location |
| `OUT_OF_ORDER` | Timestamp is older than the previous location |

> **Note:** The `gps-service` also validates that the device exists by calling `device-service` at `http://localhost:8082/api/v1/devices/{deviceId}` before saving the location.

---

### 5. Get All Locations for a Device

Returns the full location history for a given device.

```
GET http://localhost:8084/api/v1/devices/{deviceId}/locations
```

| Path parameter | Description |
|---|---|
| `deviceId` | UUID of the device |

**Response** `200 OK`

```json
[
  {
    "deviceId": "3fa85f64-5717-4562-b3fc-2c963f66afa2",
    "latitude": 52.2297,
    "longitude": 21.0122,
    "timestamp": "2026-04-18T12:46:56Z"
  }
]
```

---

### 6. Get Latest Location for a Device

Returns only the most recent location for a given device.

```
GET http://localhost:8084/api/v1/devices/{deviceId}/locations/latest
```

| Path parameter | Description |
|---|---|
| `deviceId` | UUID of the device |

**Response** `200 OK`

```json
{
  "deviceId": "3fa85f64-5717-4562-b3fc-2c963f66afa2",
  "latitude": 52.2297,
  "longitude": 21.0122,
  "timestamp": "2026-04-18T12:46:56Z"
}
```

**Error** `404 Not Found` — no location recorded for this device.

---

## Example Flow (curl)

```bash
# 1. Register a device
curl -X POST http://localhost:8081/api/v1/devices \
  -H "Content-Type: application/json" \
  -d '{
    "deviceId": "3fa85f64-5717-4562-b3fc-2c963f66afa2",
    "name": "iPhone 15",
    "type": "IOS"
  }'

# 2. List all devices
curl http://localhost:8082/api/v1/devices

# 3. Get a specific device
curl http://localhost:8082/api/v1/devices/3fa85f64-5717-4562-b3fc-2c963f66afa2

# 4. Submit a GPS location
curl -X POST http://localhost:8083/api/v1/devices/3fa85f64-5717-4562-b3fc-2c963f66afa2/locations \
  -H "Content-Type: application/json" \
  -d '{
    "deviceId": "3fa85f64-5717-4562-b3fc-2c963f66afa2",
    "lat": 52.2297,
    "lon": 21.0122,
    "timestamp": "2026-04-18T12:46:56Z"
  }'

# 5. Get full location history
curl http://localhost:8084/api/v1/devices/3fa85f64-5717-4562-b3fc-2c963f66afa2/locations

# 6. Get latest location
curl http://localhost:8084/api/v1/devices/3fa85f64-5717-4562-b3fc-2c963f66afa2/locations/latest
```

---

## Tech Stack

- **Java 21+** / **Spring Boot**
- **Apache Kafka** — async event-driven communication between services
- **PostgreSQL** — persistent storage for devices and locations
- **Spring Data JPA / Hibernate** — ORM (`ddl-auto=create-drop`, recreates schema on startup)
- **Spring Cache** — caching layer in `device-service` and `gps-service`
- **Spring WebClient** — `gps-service` calls `device-service` over HTTP to validate device existence
- **Docker Compose** — local infrastructure (Kafka, Zookeeper, Kafka UI, PostgreSQL)

---

## Notes

- Database schema is set to `create-drop` — data is **lost on every restart**. Change `spring.jpa.hibernate.ddl-auto` to `validate` or `update` for persistent data.
- Kafka uses `lz4` compression on the producer side.
- `device-service` uses batch Kafka listener; `gps-service` uses single-message listener.
- All `deviceId` values are UUIDs.
- Timestamps must be ISO 8601 in UTC.
