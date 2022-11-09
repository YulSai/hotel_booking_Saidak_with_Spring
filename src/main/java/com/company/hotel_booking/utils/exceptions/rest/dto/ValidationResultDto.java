package com.company.hotel_booking.utils.exceptions.rest.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class ValidationResultDto extends ErrorDto{
    private static final String DEFAULT_ERROR_MESSAGE = "default.error.message";
    private Map<String, List<String>> messages;

    public ValidationResultDto() {
        super(DEFAULT_ERROR_MESSAGE);
    }

    public ValidationResultDto(Map<String, List<String>> messages) {
        this();
        this.messages = messages;
    }

}
