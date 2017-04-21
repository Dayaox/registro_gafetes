package com.oplever.sioe.service.impl;

import com.oplever.sioe.service.Personal2Service;
import com.oplever.sioe.domain.Personal2;
import com.oplever.sioe.repository.Personal2Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Personal2.
 */
@Service
@Transactional
public class Personal2ServiceImpl implements Personal2Service{

    private final Logger log = LoggerFactory.getLogger(Personal2ServiceImpl.class);
    
    private final Personal2Repository personal2Repository;

    public Personal2ServiceImpl(Personal2Repository personal2Repository) {
        this.personal2Repository = personal2Repository;
    }

    /**
     * Save a personal2.
     *
     * @param personal2 the entity to save
     * @return the persisted entity
     */
    @Override
    public Personal2 save(Personal2 personal2) {
        log.debug("Request to save Personal2 : {}", personal2);
        Personal2 result = personal2Repository.save(personal2);
        return result;
    }

    /**
     *  Get all the personal2S.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Personal2> findAll(Pageable pageable) {
        log.debug("Request to get all Personal2S");
        Page<Personal2> result = personal2Repository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personal2 by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Personal2 findOne(Long id) {
        log.debug("Request to get Personal2 : {}", id);
        Personal2 personal2 = personal2Repository.findOne(id);
        return personal2;
    }

    /**
     *  Delete the  personal2 by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Personal2 : {}", id);
        personal2Repository.delete(id);
    }
}
