package com.oplever.sioe.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oplever.sioe.domain.Personal2;
import com.oplever.sioe.service.Personal2Service;
import com.oplever.sioe.web.rest.util.HeaderUtil;
import com.oplever.sioe.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Personal2.
 */
@RestController
@RequestMapping("/api")
public class Personal2Resource {

    private final Logger log = LoggerFactory.getLogger(Personal2Resource.class);

    private static final String ENTITY_NAME = "personal2";
        
    private final Personal2Service personal2Service;

    public Personal2Resource(Personal2Service personal2Service) {
        this.personal2Service = personal2Service;
    }

    /**
     * POST  /personal-2-s : Create a new personal2.
     *
     * @param personal2 the personal2 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personal2, or with status 400 (Bad Request) if the personal2 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personal-2-s")
    @Timed
    public ResponseEntity<Personal2> createPersonal2(@Valid @RequestBody Personal2 personal2) throws URISyntaxException {
        log.debug("REST request to save Personal2 : {}", personal2);
        if (personal2.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new personal2 cannot already have an ID")).body(null);
        }
        Personal2 result = personal2Service.save(personal2);
        return ResponseEntity.created(new URI("/api/personal-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personal-2-s : Updates an existing personal2.
     *
     * @param personal2 the personal2 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personal2,
     * or with status 400 (Bad Request) if the personal2 is not valid,
     * or with status 500 (Internal Server Error) if the personal2 couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personal-2-s")
    @Timed
    public ResponseEntity<Personal2> updatePersonal2(@Valid @RequestBody Personal2 personal2) throws URISyntaxException {
        log.debug("REST request to update Personal2 : {}", personal2);
        if (personal2.getId() == null) {
            return createPersonal2(personal2);
        }
        Personal2 result = personal2Service.save(personal2);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personal2.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personal-2-s : get all the personal2S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personal2S in body
     */
    @GetMapping("/personal-2-s")
    @Timed
    public ResponseEntity<List<Personal2>> getAllPersonal2S(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Personal2S");
        Page<Personal2> page = personal2Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/personal-2-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /personal-2-s/:id : get the "id" personal2.
     *
     * @param id the id of the personal2 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personal2, or with status 404 (Not Found)
     */
    @GetMapping("/personal-2-s/{id}")
    @Timed
    public ResponseEntity<Personal2> getPersonal2(@PathVariable Long id) {
        log.debug("REST request to get Personal2 : {}", id);
        Personal2 personal2 = personal2Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personal2));
    }

    /**
     * DELETE  /personal-2-s/:id : delete the "id" personal2.
     *
     * @param id the id of the personal2 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personal-2-s/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonal2(@PathVariable Long id) {
        log.debug("REST request to delete Personal2 : {}", id);
        personal2Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
