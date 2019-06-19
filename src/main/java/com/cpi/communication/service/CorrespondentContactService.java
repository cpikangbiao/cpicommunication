package com.cpi.communication.service;

import com.cpi.communication.service.dto.CorrespondentContactDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CorrespondentContact.
 */
public interface CorrespondentContactService {

    /**
     * Save a correspondentContact.
     *
     * @param correspondentContactDTO the entity to save
     * @return the persisted entity
     */
    CorrespondentContactDTO save(CorrespondentContactDTO correspondentContactDTO);

    /**
     * Get all the correspondentContacts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CorrespondentContactDTO> findAll(Pageable pageable);


    /**
     * Get the "id" correspondentContact.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CorrespondentContactDTO> findOne(Long id);

    /**
     * Delete the "id" correspondentContact.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
