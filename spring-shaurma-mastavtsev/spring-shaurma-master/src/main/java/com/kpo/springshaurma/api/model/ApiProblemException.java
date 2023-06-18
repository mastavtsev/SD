package com.kpo.springshaurma.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApiProblemException extends RuntimeException {

    private final HttpStatus status;

    private final Exception previous;

    private final Map<String, String> detailsMap;

    public ApiProblemException(
            HttpStatus status, String message, Exception previous, Map<String, String> detailsMap) {
        super(message);
        this.status = status;
        this.previous = previous;
        this.detailsMap = detailsMap;
    }
}