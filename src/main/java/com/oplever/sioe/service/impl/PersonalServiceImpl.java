package com.oplever.sioe.service.impl;

import com.oplever.sioe.service.PersonalService;
import com.oplever.sioe.domain.Personal;
import com.oplever.sioe.repository.PersonalRepository;
import com.oplever.sioe.service.dto.PersonalDTO;
import com.oplever.sioe.service.mapper.PersonalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Personal.
 */
@Service
@Transactional
public class PersonalServiceImpl implements PersonalService{

    private final Logger log = LoggerFactory.getLogger(PersonalServiceImpl.class);
    
    private final PersonalRepository personalRepository;

    private final PersonalMapper personalMapper;

    public PersonalServiceImpl(PersonalRepository personalRepository, PersonalMapper personalMapper) {
        this.personalRepository = personalRepository;
        this.personalMapper = personalMapper;
    }

    /**
     * Save a personal.
     *
     * @param personalDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PersonalDTO save(PersonalDTO personalDTO) {
        log.debug("Request to save Personal : {}", personalDTO);
        Personal personal = personalMapper.personalDTOToPersonal(personalDTO);
        personal = personalRepository.save(personal);
        PersonalDTO result = personalMapper.personalToPersonalDTO(personal);
        return result;
    }

    /**
     *  Get all the personals.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PersonalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Personals");
        Page<Personal> result = personalRepository.findAll(pageable);
        return result.map(personal -> personalMapper.personalToPersonalDTO(personal));
    }

    /**
     *  Get one personal by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PersonalDTO findOne(Long id) {
        log.debug("Request to get Personal : {}", id);
        Personal personal = personalRepository.findOne(id);
        PersonalDTO personalDTO = personalMapper.personalToPersonalDTO(personal);
        return personalDTO;
    }

    /**
     *  Delete the  personal by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Personal : {}", id);
        personalRepository.delete(id);
    }
}
