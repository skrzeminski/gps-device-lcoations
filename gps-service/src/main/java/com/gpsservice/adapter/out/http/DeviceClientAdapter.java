package com.gpsservice.adapter.out.http;

import com.gpsservice.adapter.out.persistance.repository.dto.DeviceInfoDto;
import com.gpsservice.application.port.out.DeviceClientPort;
import com.gpsservice.domain.model.DeviceInfo;
import com.gpsservice.domain.model.DeviceType;
import com.gpsservice.exception.DeviceResolutionRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Optional;

@Repository
public class DeviceClientAdapter implements DeviceClientPort {

    private final WebClient webClient;
    private final Logger logger = LoggerFactory.getLogger(DeviceClientAdapter.class);

    public DeviceClientAdapter(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DeviceInfo> getDevice(String deviceId) {
        try {
            DeviceInfo response = webClient.get()
                    .uri("/{deviceId}", deviceId)
                    .retrieve()
                    .bodyToMono(DeviceInfo.class)
                    .block();

            return Optional.ofNullable(response);

        } catch (WebClientResponseException.NotFound e) {
            logger.warn("Device not found: {}", deviceId);
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Error resolving device for id {}: {}", deviceId, e.getClass().getSimpleName(), e);
            throw new DeviceResolutionRuntimeException("Failed to resolve device: " + deviceId, e);
        }
    }

}
