package com.cpi.communication.web.rest;

import com.cpi.communication.service.CorrespondentContactService;
import com.cpi.communication.web.rest.errors.BadRequestAlertException;
import com.cpi.communication.service.dto.CorrespondentContactDTO;
import com.cpi.communication.service.dto.CorrespondentContactCriteria;
import com.cpi.communication.service.CorrespondentContactQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.cpi.communication.domain.CorrespondentContact}.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentContactResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentContactResource.class);

    private static final String ENTITY_NAME = "cpicommunicationCorrespondentContact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CorrespondentContactService correspondentContactService;

    private final CorrespondentContactQueryService correspondentContactQueryService;

    public CorrespondentContactResource(CorrespondentContactService correspondentContactService, CorrespondentContactQueryService correspondentContactQueryService) {
        this.correspondentContactService = correspondentContactService;
        this.correspondentContactQueryService = correspondentContactQueryService;
    }

    /**
     * {@code POST  /correspondent-contacts} : Create a new correspondentContact.
     *
     * @param correspondentContactDTO the correspondentContactDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new correspondentContactDTO, or with status {@code 400 (Bad Request)} if the correspondentContact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/correspondent-contacts")
    public ResponseEntity<CorrespondentContactDTO> createCorrespondentContact(@Valid @RequestBody CorrespondentContactDTO correspondentContactDTO) throws URISyntaxException {
        log.debug("REST request to save CorrespondentContact : {}", correspondentContactDTO);
        if (correspondentContactDTO.getId() != null) {
            throw new BadRequestAlertException("A new correspondentContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorrespondentContactDTO result = correspondentContactService.save(correspondentContactDTO);
        return ResponseEntity.created(new URI("/api/correspondent-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /correspondent-contacts} : Updates an existing correspondentContact.
     *
     * @param correspondentContactDTO the correspondentContactDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated correspondentContactDTO,
     * or with status {@code 400 (Bad Request)} if the correspondentContactDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the correspondentContactDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/correspondent-contacts")
    public ResponseEntity<CorrespondentContactDTO> updateCorrespondentContact(@Valid @RequestBody CorrespondentContactDTO correspondentContactDTO) throws URISyntaxException {
        log.debug("REST request to update CorrespondentContact : {}", correspondentContactDTO);
        if (correspondentContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CorrespondentContactDTO result = correspondentContactService.save(correspondentContactDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, correspondentContactDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /correspondent-contacts} : get all the correspondentContacts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of correspondentContacts in body.
     */
    @GetMapping("/correspondent-contacts")
    public ResponseEntity<List<CorrespondentContactDTO>> getAllCorrespondentContacts(CorrespondentContactCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get CorrespondentContacts by criteria: {}", criteria);
        Page<CorrespondentContactDTO> page = correspondentContactQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /correspondent-contacts/count} : count all the correspondentContacts.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/correspondent-contacts/count")
    public ResponseEntity<Long> countCorrespondentContacts(CorrespondentContactCriteria criteria) {
        log.debug("REST request to count CorrespondentContacts by criteria: {}", criteria);
        return ResponseEntity.ok().body(correspondentContactQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /correspondent-contacts/:id} : get the "id" correspondentContact.
     *
     * @param id the id of the correspondentContactDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the correspondentContactDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/correspondent-contacts/{id}")
    public ResponseEntity<CorrespondentContactDTO> getCorrespondentContact(@PathVariable Long id) {
        log.debug("REST request to get CorrespondentContact : {}", id);
        Optional<CorrespondentContactDTO> correspondentContactDTO = correspondentContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(correspondentContactDTO);
    }

    /**
     * {@code DELETE  /correspondent-contacts/:id} : delete the "id" correspondentContact.
     *
     * @param id the id of the correspondentContactDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/correspondent-contacts/{id}")
    public ResponseEntity<Void> deleteCorrespondentContact(@PathVariable Long id) {
        log.debug("REST request to delete CorrespondentContact : {}", id);
        correspondentContactService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
