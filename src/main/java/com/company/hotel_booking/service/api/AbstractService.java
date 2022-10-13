package com.company.hotel_booking.service.api;

import com.company.hotel_booking.controller.command.util.Paging;

import java.util.List;

/**
 * General interface
 */
public interface AbstractService<K, T> {
    /**
     * Method finds Dto object by id
     *
     * @param id Dto object id
     * @return Dto object
     */
    T findById(K id);


    /**
     * Method finds all Dto object
     *
     * @return list of all Dto object
     */
    List<T> findAll();

    /**
     * Method saves Dto object
     *
     * @param entity Dto object
     */
    T create(T entity);

    /**
     * Method updates an edited Dto object
     *
     * @param entity Dto object
     */
    T update(T entity);

    /**
     * Method "soft" deletes Dto objects
     *
     * @param id Dto object id to be "soft" deleted
     */
    void delete(K id);

    /**
     * Method gets list of Objects starting from begin position in the table
     *
     * @param paging an instance of a class object Paging
     * @return list of Objects
     */
    List<T> findAllPages(Paging paging);

    /**
     * Method gets number of pages depending on the number of object on the page
     *
     * @return number of pages
     */
    long countRow();
}
