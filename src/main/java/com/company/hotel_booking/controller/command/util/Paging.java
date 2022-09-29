package com.company.hotel_booking.controller.command.util;

import lombok.Data;

/**
 * Class describing the object Paging
 */
@Data
public class Paging {
    private final int limit;
    private final long offset;
    private final long page;
}
