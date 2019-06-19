package com.cpi.communication.service.impl;

import com.cpi.communication.service.CorrespondentContactService;
import com.cpi.communication.domain.CorrespondentContact;
import com.cpi.communication.repository.CorrespondentContactRepository;
import com.cpi.communication.service.dto.CorrespondentContactDTO;
import com.cpi.communication.service.mapper.CorrespondentContactMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing CorrespondentContact.
 */
@Service
@Transactional
public class CorrespondentContactServiceImpl implements CorrespondentContactService {

    private final Logger log = LoggerFactory.getLogger(CorrespondentContactServiceImpl.class);

    private final CorrespondentContactRepository correspondentContactRepository;

    private final CorrespondentContactMapper correspondentContactMapper;

    public CorrespondentContactServiceImpl(CorrespondentContactRepository correspondentContactRepository, CorrespondentContactMapper correspondentContactMapper) {
        this.correspondentContactRepository = correspondentContactRepository;
        this.correspondentContactMapper = correspondentContactMapper;
    }

    /**
     * Save a correspondentContact.
     *
     * @param correspondentContactDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CorrespondentContactDTO save(CorrespondentContactDTO correspondentContactDTO) {
        log.debug("Request to save CorrespondentContact : {}", correspondentContactDTO);
        CorrespondentContact correspondentContact = correspondentContactMapper.toEntity(correspondentContactDTO);
        correspondentContact = correspondentContactRepository.save(correspondentContact);
        return correspondentContactMapper.toDto(correspondentContact);
    }

    /**
     * Get all the correspondentContacts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CorrespondentContactDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CorrespondentContacts");
        return correspondentContactRepository.findAll(pageable)
            .map(correspondentContactMapper::toDto);
    }


    /**
     * Get one correspondentContact by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CorrespondentContactDTO> findOne(Long id) {
        log.debug("Request to get CorrespondentContact : {}", id);
        return correspondentContactRepository.findById(id)
            .map(correspondentContactMapper::toDto);
    }

    /**
     * Delete the correspondentContact by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CorrespondentContact : {}", id);
        correspondentContactRepository.deleteById(id);
    }
}
