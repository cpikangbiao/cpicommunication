package com.cpi.communication.web.rest;


import com.cpi.communication.domain.Correspondent;
import com.cpi.communication.repository.PortRepository;
import com.cpi.communication.service.CorrespondentListService;
import com.cpi.communication.service.CountryListService;
import com.cpi.communication.service.PortListService;
import com.cpi.communication.service.bean.CorrespondentBean;
import com.cpi.communication.service.bean.list.CountryListBean;
import com.cpi.communication.service.bean.list.PortListBean;
import com.cpi.communication.service.utility.CorrespondentBookUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing Correspondent.
 */
@RestController
@RequestMapping("/api/web")
public class CorrespondentWebResource {

    private final Logger log = LoggerFactory.getLogger(CorrespondentWebResource.class);

    private static final String ENTITY_NAME = "correspondent";

    @Autowired
    private CountryListService countryListService;

    @Autowired
    private PortListService portListService;

    @Autowired
    private CorrespondentListService correspondentListService;

    @GetMapping("/country/list")
    public ResponseEntity<List<CountryListBean>> getWebCountryList() {
        log.debug("REST request to getWebCountryList " );

        return new ResponseEntity<>(countryListService.findCountryListBean(), HttpStatus.OK);
    }

    @GetMapping("/port/{countryId}/list")
    public ResponseEntity<List<PortListBean>> getWebPortList(@PathVariable("countryId") Long countryId) {
        log.debug("REST request to getWebCountryList " );

        return new ResponseEntity<>(portListService.findPortListBeanByCountryId(countryId), HttpStatus.OK);
    }

    @GetMapping("/correspondent/{portId}/list")
    public ResponseEntity<List<CorrespondentBean>> getWebCorrespondentList(@PathVariable("portId") Long portId) {
        log.debug("REST request to getWebCountryList " );

        return new ResponseEntity<>(correspondentListService.findCorrespondentBean(portId), HttpStatus.OK);
    }
}
