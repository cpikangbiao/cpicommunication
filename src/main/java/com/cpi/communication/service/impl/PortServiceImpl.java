package com.cpi.communication.service.impl;

import com.cpi.communication.service.PortService;
import com.cpi.communication.domain.Port;
import com.cpi.communication.repository.PortRepository;
import com.cpi.communication.service.dto.PortDTO;
import com.cpi.communication.service.mapper.PortMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Port}.
 */
@Service
@Transactional
public class PortServiceImpl implements PortService {

    private final Logger log = LoggerFactory.getLogger(PortServiceImpl.class);

    private final PortRepository portRepository;

    private final PortMapper portMapper;

    public PortServiceImpl(PortRepository portRepository, PortMapper portMapper) {
        this.portRepository = portRepository;
        this.portMapper = portMapper;
    }

    /**
     * Save a port.
     *
     * @param portDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PortDTO save(PortDTO portDTO) {
        log.debug("Request to save Port : {}", portDTO);
        Port port = portMapper.toEntity(portDTO);
        port = portRepository.save(port);
        return portMapper.toDto(port);
    }

    /**
     * Get all the ports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PortDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ports");
        return portRepository.findAll(pageable)
            .map(portMapper::toDto);
    }


    /**
     * Get one port by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PortDTO> findOne(Long id) {
        log.debug("Request to get Port : {}", id);
        return portRepository.findById(id)
            .map(portMapper::toDto);
    }

    /**
     * Delete the port by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Port : {}", id);
        portRepository.deleteById(id);
    }
}
