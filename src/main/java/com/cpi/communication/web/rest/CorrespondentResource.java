package com.cpi.communication.web.rest;

import com.cpi.communication.service.CorrespondentService;
import com.cpi.communication.web.rest.errors.BadRequestAlertException;
import com.cpi.communication.service.dto.CorrespondentDTO;
import com.cpi.communication.service.dto.CorrespondentCriteria;
import com.cpi.communication.service.CorrespondentQueryService;

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
 * REST controller for managing {@link com.cpi.communication.domain.Correspondent}.
 */
@RestController
@RequestMapping("/api")
public class CorrespondentResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentResource.class);

    private static final String ENTITY_NAME = "cpicommunicationCorrespondent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CorrespondentService correspondentService;

    private final CorrespondentQueryService correspondentQueryService;

    public CorrespondentResource(CorrespondentService correspondentService, CorrespondentQueryService correspondentQueryService) {
        this.correspondentService = correspondentService;
        this.correspondentQueryService = correspondentQueryService;
    }

    /**
     * {@code POST  /correspondents} : Create a new correspondent.
     *
     * @param correspondentDTO the correspondentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new correspondentDTO, or with status {@code 400 (Bad Request)} if the correspondent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/correspondents")
    public ResponseEntity<CorrespondentDTO> createCorrespondent(@Valid @RequestBody CorrespondentDTO correspondentDTO) throws URISyntaxException {
        log.debug("REST request to save Correspondent : {}", correspondentDTO);
        if (correspondentDTO.getId() != null) {
            throw new BadRequestAlertException("A new correspondent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorrespondentDTO result = correspondentService.save(correspondentDTO);
        return ResponseEntity.created(new URI("/api/correspondents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /correspondents} : Updates an existing correspondent.
     *
     * @param correspondentDTO the correspondentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated correspondentDTO,
     * or with status {@code 400 (Bad Request)} if the correspondentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the correspondentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/correspondents")
    public ResponseEntity<CorrespondentDTO> updateCorrespondent(@Valid @RequestBody CorrespondentDTO correspondentDTO) throws URISyntaxException {
        log.debug("REST request to update Correspondent : {}", correspondentDTO);
        if (correspondentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CorrespondentDTO result = correspondentService.save(correspondentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, correspondentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /correspondents} : get all the correspondents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of correspondents in body.
     */
    @GetMapping("/correspondents")
    public ResponseEntity<List<CorrespondentDTO>> getAllCorrespondents(CorrespondentCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Correspondents by criteria: {}", criteria);
        Page<CorrespondentDTO> page = correspondentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /correspondents/count} : count all the correspondents.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/correspondents/count")
    public ResponseEntity<Long> countCorrespondents(CorrespondentCriteria criteria) {
        log.debug("REST request to count Correspondents by criteria: {}", criteria);
        return ResponseEntity.ok().body(correspondentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /correspondents/:id} : get the "id" correspondent.
     *
     * @param id the id of the correspondentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the correspondentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/correspondents/{id}")
    public ResponseEntity<CorrespondentDTO> getCorrespondent(@PathVariable Long id) {
        log.debug("REST request to get Correspondent : {}", id);
        Optional<CorrespondentDTO> correspondentDTO = correspondentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(correspondentDTO);
    }

    /**
     * {@code DELETE  /correspondents/:id} : delete the "id" correspondent.
     *
     * @param id the id of the correspondentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/correspondents/{id}")
    public ResponseEntity<Void> deleteCorrespondent(@PathVariable Long id) {
        log.debug("REST request to delete Correspondent : {}", id);
        correspondentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
