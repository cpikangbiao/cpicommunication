package com.cpi.communication.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.communication.service.CorrespondentContactService;
import com.cpi.communication.web.rest.errors.BadRequestAlertException;
import com.cpi.communication.web.rest.util.HeaderUtil;
import com.cpi.communication.web.rest.util.PaginationUtil;
import com.cpi.communication.service.dto.CorrespondentContactDTO;
import com.cpi.communication.service.dto.CorrespondentContactCriteria;
import com.cpi.communication.service.CorrespondentContactQueryService;
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
 * REST controller for managing CorrespondentContact.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentContactResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentContactResource.class);

    private static final String ENTITY_NAME = "correspondentContact";

    private final CorrespondentContactService correspondentContactService;

    private final CorrespondentContactQueryService correspondentContactQueryService;

    public CorrespondentContactResource(CorrespondentContactService correspondentContactService, CorrespondentContactQueryService correspondentContactQueryService) {
        this.correspondentContactService = correspondentContactService;
        this.correspondentContactQueryService = correspondentContactQueryService;
    }

    /**
     * POST  /correspondent-contacts : Create a new correspondentContact.
     *
     * @param correspondentContactDTO the correspondentContactDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new correspondentContactDTO, or with status 400 (Bad Request) if the correspondentContact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/correspondent-contacts")
    @Timed
    public ResponseEntity<CorrespondentContactDTO> createCorrespondentContact(@Valid @RequestBody CorrespondentContactDTO correspondentContactDTO) throws URISyntaxException {
        log.debug("REST request to save CorrespondentContact : {}", correspondentContactDTO);
        if (correspondentContactDTO.getId() != null) {
            throw new BadRequestAlertException("A new correspondentContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorrespondentContactDTO result = correspondentContactService.save(correspondentContactDTO);
        return ResponseEntity.created(new URI("/api/correspondent-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /correspondent-contacts : Updates an existing correspondentContact.
     *
     * @param correspondentContactDTO the correspondentContactDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated correspondentContactDTO,
     * or with status 400 (Bad Request) if the correspondentContactDTO is not valid,
     * or with status 500 (Internal Server Error) if the correspondentContactDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/correspondent-contacts")
    @Timed
    public ResponseEntity<CorrespondentContactDTO> updateCorrespondentContact(@Valid @RequestBody CorrespondentContactDTO correspondentContactDTO) throws URISyntaxException {
        log.debug("REST request to update CorrespondentContact : {}", correspondentContactDTO);
        if (correspondentContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CorrespondentContactDTO result = correspondentContactService.save(correspondentContactDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, correspondentContactDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /correspondent-contacts : get all the correspondentContacts.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of correspondentContacts in body
     */
    @GetMapping("/correspondent-contacts")
    @Timed
    public ResponseEntity<List<CorrespondentContactDTO>> getAllCorrespondentContacts(CorrespondentContactCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CorrespondentContacts by criteria: {}", criteria);
        Page<CorrespondentContactDTO> page = correspondentContactQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/correspondent-contacts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /correspondent-contacts/:id : get the "id" correspondentContact.
     *
     * @param id the id of the correspondentContactDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the correspondentContactDTO, or with status 404 (Not Found)
     */
    @GetMapping("/correspondent-contacts/{id}")
    @Timed
    public ResponseEntity<CorrespondentContactDTO> getCorrespondentContact(@PathVariable Long id) {
        log.debug("REST request to get CorrespondentContact : {}", id);
        Optional<CorrespondentContactDTO> correspondentContactDTO = correspondentContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(correspondentContactDTO);
    }

    /**
     * DELETE  /correspondent-contacts/:id : delete the "id" correspondentContact.
     *
     * @param id the id of the correspondentContactDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/correspondent-contacts/{id}")
    @Timed
    public ResponseEntity<Void> deleteCorrespondentContact(@PathVariable Long id) {
        log.debug("REST request to delete CorrespondentContact : {}", id);
        correspondentContactService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
