package ru.pivovarov.t1.ConsumerService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is5xxServerError()) {
            if (response.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                throw new ServiceUnavailableException("Service is unavailable");
            }
            throw new HttpServerErrorException(response.getStatusCode(), response.getStatusText());
        } else if (response.getStatusCode().is4xxClientError()) {
            if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new NotFoundException(response.getStatusCode().toString());
            }
            if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                throw new BadRequestException(response.getStatusCode().toString());
            }
            throw new HttpClientErrorException(response.getStatusCode(), response.getStatusText());
        }
    }
}
