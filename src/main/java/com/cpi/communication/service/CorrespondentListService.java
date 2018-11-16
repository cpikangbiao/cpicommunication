package com.cpi.communication.service;

import com.cpi.communication.domain.*;
import com.cpi.communication.repository.CorrespondentContactRepository;
import com.cpi.communication.repository.CorrespondentRepository;
import com.cpi.communication.repository.PortRepository;
import com.cpi.communication.service.bean.CorrespondentBean;
import com.cpi.communication.service.bean.CorrespondentContactBean;
import com.cpi.communication.service.bean.list.CountryListBean;
import com.cpi.communication.service.dto.CorrespondentCriteria;
import com.cpi.communication.service.dto.CorrespondentDTO;
import com.cpi.communication.service.mapper.CorrespondentMapper;
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
 * Service for executing complex queries for Correspondent entities in the database.
 * The main input is a {@link CorrespondentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CorrespondentDTO} or a {@link Page} of {@link CorrespondentDTO} which fulfills the criteria.
 */
@Service
@Transactional
public class CorrespondentListService extends QueryService<Correspondent> {

    private final Logger log = LoggerFactory.getLogger(CorrespondentListService.class);

    private final CorrespondentRepository correspondentRepository;

    private final CorrespondentMapper correspondentMapper;

    public CorrespondentListService(CorrespondentRepository correspondentRepository, CorrespondentMapper correspondentMapper) {
        this.correspondentRepository = correspondentRepository;
        this.correspondentMapper = correspondentMapper;
    }

    @Autowired
    private PortRepository portRepository;

    @Autowired
    private CorrespondentContactRepository correspondentContactRepository;

    @Transactional(readOnly = true)
    public List<CorrespondentBean> findCorrespondentBean(Long portId) {
        log.debug("find by findCorrespondentBean by id : {} ", portId );
        List<CorrespondentBean> correspondentBeans = new ArrayList<>();

        Port port = portRepository.getOne(portId);
        if (port != null) {
            List<Correspondent> correspondents = correspondentRepository.findAllByPortOrderByCorrespondentName(port);
            for (Correspondent correspondent : correspondents) {
                CorrespondentBean correspondentBean = new CorrespondentBean();
                correspondentBean.init(
                    correspondent.getCorrespondentName(),
                    correspondent.getFaxNumber(),
                    correspondent.getAddress(),
                    correspondent.getTelephoneOffice(),
                    correspondent.getTelephoneAlternate(),
                    correspondent.getWebSite()
                );

                List<CorrespondentContact> correspondentContacts = correspondentContactRepository.findAllByCorrespondentOrderByCorrespondentContactName(correspondent);
                for (CorrespondentContact correspondentContact : correspondentContacts) {
                    CorrespondentContactBean correspondentContactBean = new CorrespondentContactBean();
                    correspondentContactBean.init(
                        correspondentContact.getCorrespondentContactName(),
                        correspondentContact.geteMail(),
                        correspondentContact.getTelephoneOffice(),
                        correspondentContact.getTelephone(),
                        correspondentContact.getWebSite()
                    );
                    correspondentBean.addCorrespondentContactBean(correspondentContactBean);
                }
                correspondentBeans.add(correspondentBean);
            }

        }


        return correspondentBeans;
    }


}
