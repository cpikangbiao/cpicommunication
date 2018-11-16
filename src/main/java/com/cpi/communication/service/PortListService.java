package com.cpi.communication.service;

import com.cpi.communication.domain.Country;
import com.cpi.communication.domain.Country_;
import com.cpi.communication.domain.Port;
import com.cpi.communication.domain.Port_;
import com.cpi.communication.repository.CountryRepository;
import com.cpi.communication.repository.PortRepository;
import com.cpi.communication.service.bean.list.CountryListBean;
import com.cpi.communication.service.bean.list.PortListBean;
import com.cpi.communication.service.dto.PortCriteria;
import com.cpi.communication.service.dto.PortDTO;
import com.cpi.communication.service.mapper.PortMapper;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for executing complex queries for Port entities in the database.
 * The main input is a {@link PortCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PortDTO} or a {@link Page} of {@link PortDTO} which fulfills the criteria.
 */
@Service
@Transactional
public class PortListService extends QueryService<Port> {

    private final Logger log = LoggerFactory.getLogger(PortListService.class);

    private final PortRepository portRepository;

    private final PortMapper portMapper;

    public PortListService(PortRepository portRepository, PortMapper portMapper) {
        this.portRepository = portRepository;
        this.portMapper = portMapper;
    }

    @Autowired
    private CountryRepository countryRepository;

    @Transactional(readOnly = true)
    public List<PortListBean> findPortListBeanByCountryId(Long countryId) {
        log.debug("find by findPortListBeanByCountryId id : {} ", countryId);
        List<PortListBean> portListBeans = new ArrayList<>();
        Country country  = countryRepository.getOne(countryId);

        if (country != null) {
            List<Port> ports = portRepository.findAllByCountryOrderByPortName(country);

            for (Port port : ports) {
                PortListBean portListBean = new PortListBean(
                    port.getId(),
                    new StringBuilder()
                        .append(port.getPortName()).toString()
                );
                portListBeans.add(portListBean);
            }
        }

        return portListBeans;
    }
}
