package ru.pivovarov.t1.ConsumerService.exception;

public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException() {
    }

    public ServiceUnavailableException(String message) {
        super(message);
    }
}
