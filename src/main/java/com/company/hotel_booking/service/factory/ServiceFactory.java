package com.company.hotel_booking.service.factory;

import com.company.hotel_booking.dao.api.*;
import com.company.hotel_booking.dao.factory.DaoFactory;
import com.company.hotel_booking.service.api.*;
import com.company.hotel_booking.service.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Class with methods for creating a list relationships between a class and a
 * Service object
 */
public class ServiceFactory {
    private static ServiceFactory INSTANCE;
    private final Map<Class<?>, Object> map;

    /**
     * Method creates a list relationships between a class and a Service object
     */
    private ServiceFactory() {
        map = new HashMap<>();
        map.put(IUserService.class, new UserServiceImpl(DaoFactory.getINSTANCE().getDao(IUserDao.class)));
        map.put(IRoomService.class, new RoomServiceImpl(DaoFactory.getINSTANCE().getDao(IRoomDao.class)));
        map.put(IReservationInfoService.class,
                new ReservationInfoServiceImpl(DaoFactory.getINSTANCE().getDao(IReservationInfoDao.class)));
        map.put(IReservationService.class,
                new ReservationServiceImpl(DaoFactory.getINSTANCE().getDao(IReservationDao.class),
                        DaoFactory.getINSTANCE().getDao(IRoomDao.class)));
    }

    /**
     * Method gets an instance of the class object
     *
     * @return an instance of the class object
     */
    public static ServiceFactory getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new ServiceFactory();
        }
        return INSTANCE;
    }

    /**
     * Method gets service
     *
     * @param clazz object Class
     * @param <T>   general type
     * @return service
     */
    @SuppressWarnings("unchecked")
    public <T> T getService(Class<?> clazz) {
        T service = (T) map.get(clazz);
        if (service == null) {
            throw new RuntimeException("Class " + clazz + "is not constructed");
        }
        return service;
    }
}