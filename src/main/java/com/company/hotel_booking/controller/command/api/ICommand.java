package com.company.hotel_booking.controller.command.api;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Interface commands
 */
public interface ICommand {

    /**
     * Method processes the request and returns a response
     *
     * @param req passed request
     * @return response
     */
    String execute(HttpServletRequest req);
}