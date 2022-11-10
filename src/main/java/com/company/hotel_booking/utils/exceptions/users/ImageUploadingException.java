package com.company.hotel_booking.utils.exceptions.users;

import com.company.hotel_booking.utils.exceptions.AppException;

/**
 * Class of errors coming when loading images
 */
public class ImageUploadingException extends AppException {
    public ImageUploadingException(String message, Throwable cause) {
        super(message, cause);
    }
}