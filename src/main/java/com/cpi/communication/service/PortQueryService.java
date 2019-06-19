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

import com.cpi.communication.domain.Port;
import com.cpi.communication.domain.*; // for static metamodels
import com.cpi.communication.repository.PortRepository;
import com.cpi.communication.service.dto.PortCriteria;
import com.cpi.communication.service.dto.PortDTO;
import com.cpi.communication.service.mapper.PortMapper;

/**
 * Service for executing complex queries for {@link Port} entities in the database.
 * The main input is a {@link PortCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PortDTO} or a {@link Page} of {@link PortDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PortQueryService extends QueryService<Port> {

    private final Logger log = LoggerFactory.getLogger(PortQueryService.class);

    private final PortRepository portRepository;

    private final PortMapper portMapper;

    public PortQueryService(PortRepository portRepository, PortMapper portMapper) {
        this.portRepository = portRepository;
        this.portMapper = portMapper;
    }

    /**
     * Return a {@link List} of {@link PortDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PortDTO> findByCriteria(PortCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Port> specification = createSpecification(criteria);
        return portMapper.toDto(portRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PortDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PortDTO> findByCriteria(PortCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Port> specification = createSpecification(criteria);
        return portRepository.findAll(specification, page)
            .map(portMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PortCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Port> specification = createSpecification(criteria);
        return portRepository.count(specification);
    }

    /**
     * Function to convert PortCriteria to a {@link Specification}.
     */
    private Specification<Port> createSpecification(PortCriteria criteria) {
        Specification<Port> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Port_.id));
            }
            if (criteria.getPortCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPortCode(), Port_.portCode));
            }
            if (criteria.getPortName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPortName(), Port_.portName));
            }
            if (criteria.getPortNameChinese() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPortNameChinese(), Port_.portNameChinese));
            }
            if (criteria.getCountryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCountryId(),
                    root -> root.join(Port_.country, JoinType.LEFT).get(Country_.id)));
            }
        }
        return specification;
    }
}
