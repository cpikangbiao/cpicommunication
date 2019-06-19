package com.cpi.communication.service;

import com.cpi.communication.service.dto.PortDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.cpi.communication.domain.Port}.
 */
public interface PortService {

    /**
     * Save a port.
     *
     * @param portDTO the entity to save.
     * @return the persisted entity.
     */
    PortDTO save(PortDTO portDTO);

    /**
     * Get all the ports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PortDTO> findAll(Pageable pageable);


    /**
     * Get the "id" port.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PortDTO> findOne(Long id);

    /**
     * Delete the "id" port.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
