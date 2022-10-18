package com.company.hotel_booking.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param entity Dto object to be "soft" deleted
     */
    void delete(T entity);

    /**
     * Method gets list of Objects starting from begin position in the table
     *
     * @param pageable an instance of interface Pageable for pagination information
     * @return list of Objects
     */
    Page<T> findAllPages(Pageable pageable);
}
