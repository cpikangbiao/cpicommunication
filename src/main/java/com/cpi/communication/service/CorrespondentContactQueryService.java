package com.cpi.communication.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.cpi.communication.domain.CorrespondentContact;
import com.cpi.communication.domain.*; // for static metamodels
import com.cpi.communication.repository.CorrespondentContactRepository;
import com.cpi.communication.service.dto.CorrespondentContactCriteria;
import com.cpi.communication.service.dto.CorrespondentContactDTO;
import com.cpi.communication.service.mapper.CorrespondentContactMapper;

/**
 * Service for executing complex queries for {@link CorrespondentContact} entities in the database.
 * The main input is a {@link CorrespondentContactCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CorrespondentContactDTO} or a {@link Page} of {@link CorrespondentContactDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CorrespondentContactQueryService extends QueryService<CorrespondentContact> {

    private final Logger log = LoggerFactory.getLogger(CorrespondentContactQueryService.class);

    private final CorrespondentContactRepository correspondentContactRepository;

    private final CorrespondentContactMapper correspondentContactMapper;

    public CorrespondentContactQueryService(CorrespondentContactRepository correspondentContactRepository, CorrespondentContactMapper correspondentContactMapper) {
        this.correspondentContactRepository = correspondentContactRepository;
        this.correspondentContactMapper = correspondentContactMapper;
    }

    /**
     * Return a {@link List} of {@link CorrespondentContactDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CorrespondentContactDTO> findByCriteria(CorrespondentContactCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CorrespondentContact> specification = createSpecification(criteria);
        return correspondentContactMapper.toDto(correspondentContactRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CorrespondentContactDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CorrespondentContactDTO> findByCriteria(CorrespondentContactCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CorrespondentContact> specification = createSpecification(criteria);
        return correspondentContactRepository.findAll(specification, page)
            .map(correspondentContactMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CorrespondentContactCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CorrespondentContact> specification = createSpecification(criteria);
        return correspondentContactRepository.count(specification);
    }

    /**
     * Function to convert CorrespondentContactCriteria to a {@link Specification}.
     */
    private Specification<CorrespondentContact> createSpecification(CorrespondentContactCriteria criteria) {
        Specification<CorrespondentContact> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CorrespondentContact_.id));
            }
            if (criteria.getCorrespondentContactName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCorrespondentContactName(), CorrespondentContact_.correspondentContactName));
            }
            if (criteria.geteMail() != null) {
                specification = specification.and(buildStringSpecification(criteria.geteMail(), CorrespondentContact_.eMail));
            }
            if (criteria.getTelephoneOffice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephoneOffice(), CorrespondentContact_.telephoneOffice));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephone(), CorrespondentContact_.telephone));
            }
            if (criteria.getWebSite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebSite(), CorrespondentContact_.webSite));
            }
            if (criteria.getCorrespondentId() != null) {
                specification = specification.and(buildSpecification(criteria.getCorrespondentId(),
                    root -> root.join(CorrespondentContact_.correspondent, JoinType.LEFT).get(Correspondent_.id)));
            }
        }
        return specification;
    }
}
