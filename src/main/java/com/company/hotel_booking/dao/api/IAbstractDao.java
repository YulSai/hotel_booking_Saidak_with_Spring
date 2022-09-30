package com.company.hotel_booking.dao.api;

import com.company.hotel_booking.exceptions.DaoException;

import java.util.List;

/**
 * Interface
 */
public interface IAbstractDao<K, T> {

    /**
     * Method finds Entity object in the data source by id
     *
     * @param id Object id
     * @return Entity object
     */
    T findById(K id);

    /**
     * Method finds all Entity object in the data source
     *
     * @return list with all Entity object
     */
    List<T> findAll();

    /**
     * Method is used for saving Entity objects in the data source
     *
     * @param entity Entity object to be saved
     * @return saved entity object
     */
    T save(T entity);


    /**
     * Method is used for saving updating Entity objects in the data source
     *
     * @param entity Entity object to be updated
     * @return updated entity object
     */
    T update(T entity);

    /**
     * Method is used for "soft" deleting Entity objects in the data source
     *
     * @param id Object id to be "soft" deleted
     * @return true/false
     */
    boolean delete(K id);

    /**
     * Method gets list of objects starting from begin position in the table
     *
     * @param limit  number of records from the table
     * @param offset starting position for search in the table
     * @return List of objects
     */
    List<T> findAllPages(int limit, long offset);

    /**
     * Method finds number of rows in the table
     *
     * @return number of records in the table
     */
    Long countRow() throws DaoException;
}