package com.oplever.sioe.service;

import com.oplever.sioe.domain.Personal2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Personal2.
 */
public interface Personal2Service {

    /**
     * Save a personal2.
     *
     * @param personal2 the entity to save
     * @return the persisted entity
     */
    Personal2 save(Personal2 personal2);

    /**
     *  Get all the personal2S.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Personal2> findAll(Pageable pageable);

    /**
     *  Get the "id" personal2.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Personal2 findOne(Long id);

    /**
     *  Delete the "id" personal2.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
