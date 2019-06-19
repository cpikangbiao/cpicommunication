package com.cpi.communication.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.cpi.communication.domain.Correspondent;
import com.cpi.communication.domain.*; // for static metamodels
import com.cpi.communication.repository.CorrespondentRepository;
import com.cpi.communication.service.dto.CorrespondentCriteria;

import com.cpi.communication.service.dto.CorrespondentDTO;
import com.cpi.communication.service.mapper.CorrespondentMapper;

/**
 * Service for executing complex queries for Correspondent entities in the database.
 * The main input is a {@link CorrespondentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CorrespondentDTO} or a {@link Page} of {@link CorrespondentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CorrespondentQueryService extends QueryService<Correspondent> {

    private final Logger log = LoggerFactory.getLogger(CorrespondentQueryService.class);

    private final CorrespondentRepository correspondentRepository;

    private final CorrespondentMapper correspondentMapper;

    public CorrespondentQueryService(CorrespondentRepository correspondentRepository, CorrespondentMapper correspondentMapper) {
        this.correspondentRepository = correspondentRepository;
        this.correspondentMapper = correspondentMapper;
    }

    /**
     * Return a {@link List} of {@link CorrespondentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CorrespondentDTO> findByCriteria(CorrespondentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Correspondent> specification = createSpecification(criteria);
        return correspondentMapper.toDto(correspondentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CorrespondentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CorrespondentDTO> findByCriteria(CorrespondentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Correspondent> specification = createSpecification(criteria);
        return correspondentRepository.findAll(specification, page)
            .map(correspondentMapper::toDto);
    }

    /**
     * Function to convert CorrespondentCriteria to a {@link Specification}
     */
    private Specification<Correspondent> createSpecification(CorrespondentCriteria criteria) {
        Specification<Correspondent> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Correspondent_.id));
            }
            if (criteria.getCorrespondentName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCorrespondentName(), Correspondent_.correspondentName));
            }
            if (criteria.getFaxNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFaxNumber(), Correspondent_.faxNumber));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Correspondent_.address));
            }
            if (criteria.getTelephoneOffice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephoneOffice(), Correspondent_.telephoneOffice));
            }
            if (criteria.getTelephoneAlternate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephoneAlternate(), Correspondent_.telephoneAlternate));
            }
            if (criteria.getWebSite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebSite(), Correspondent_.webSite));
            }
            if (criteria.getPortId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPortId(), Correspondent_.port, Port_.id));
            }
        }
        return specification;
    }

}
