package com.cpi.communication.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.communication.service.PortService;
import com.cpi.communication.web.rest.errors.BadRequestAlertException;
import com.cpi.communication.web.rest.util.HeaderUtil;
import com.cpi.communication.web.rest.util.PaginationUtil;
import com.cpi.communication.service.dto.PortDTO;
import com.cpi.communication.service.dto.PortCriteria;
import com.cpi.communication.service.PortQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Port.
 */
@RestController
@RequestMapping("/api")
public class PortResource {

    private final Logger log = LoggerFactory.getLogger(PortResource.class);

    private static final String ENTITY_NAME = "port";

    private final PortService portService;

    private final PortQueryService portQueryService;

    public PortResource(PortService portService, PortQueryService portQueryService) {
        this.portService = portService;
        this.portQueryService = portQueryService;
    }

    /**
     * POST  /ports : Create a new port.
     *
     * @param portDTO the portDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new portDTO, or with status 400 (Bad Request) if the port has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ports")
    @Timed
    public ResponseEntity<PortDTO> createPort(@RequestBody PortDTO portDTO) throws URISyntaxException {
        log.debug("REST request to save Port : {}", portDTO);
        if (portDTO.getId() != null) {
            throw new BadRequestAlertException("A new port cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PortDTO result = portService.save(portDTO);
        return ResponseEntity.created(new URI("/api/ports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ports : Updates an existing port.
     *
     * @param portDTO the portDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated portDTO,
     * or with status 400 (Bad Request) if the portDTO is not valid,
     * or with status 500 (Internal Server Error) if the portDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ports")
    @Timed
    public ResponseEntity<PortDTO> updatePort(@RequestBody PortDTO portDTO) throws URISyntaxException {
        log.debug("REST request to update Port : {}", portDTO);
        if (portDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PortDTO result = portService.save(portDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, portDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ports : get all the ports.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of ports in body
     */
    @GetMapping("/ports")
    @Timed
    public ResponseEntity<List<PortDTO>> getAllPorts(PortCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Ports by criteria: {}", criteria);
        Page<PortDTO> page = portQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ports");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ports/:id : get the "id" port.
     *
     * @param id the id of the portDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the portDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ports/{id}")
    @Timed
    public ResponseEntity<PortDTO> getPort(@PathVariable Long id) {
        log.debug("REST request to get Port : {}", id);
        Optional<PortDTO> portDTO = portService.findOne(id);
        return ResponseUtil.wrapOrNotFound(portDTO);
    }

    /**
     * DELETE  /ports/:id : delete the "id" port.
     *
     * @param id the id of the portDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ports/{id}")
    @Timed
    public ResponseEntity<Void> deletePort(@PathVariable Long id) {
        log.debug("REST request to delete Port : {}", id);
        portService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
