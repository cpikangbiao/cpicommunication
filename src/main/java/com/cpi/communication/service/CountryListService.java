package com.cpi.communication.service;

import com.cpi.communication.domain.Country;
import com.cpi.communication.domain.Country_;
import com.cpi.communication.repository.CountryRepository;
import com.cpi.communication.service.bean.list.CountryListBean;
import com.cpi.communication.service.dto.CountryCriteria;
import com.cpi.communication.service.dto.CountryDTO;
import com.cpi.communication.service.mapper.CountryMapper;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for executing complex queries for Country entities in the database.
 * The main input is a {@link CountryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CountryDTO} or a {@link Page} of {@link CountryDTO} which fulfills the criteria.
 */
@Service
@Transactional
public class CountryListService extends QueryService<Country> {

    private final Logger log = LoggerFactory.getLogger(CountryListService.class);

    private final CountryRepository countryRepository;

    private final CountryMapper countryMapper;

    public CountryListService(CountryRepository countryRepository, CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }

    @Transactional(readOnly = true)
    public List<CountryListBean> findCountryListBean() {
        log.debug("find by findCountryListBean ");
        List<CountryListBean> countryListBeans = new ArrayList<>();
        List<Country> countries = countryRepository.findAllByOrderByCountryName();

        for (Country country : countries) {
            CountryListBean countryListBean = new CountryListBean(
                country.getId(),
                new StringBuilder()
                    .append(country.getCountryNameAbbr())
                    .append(" ")
                    .append(country.getCountryName())
                    .append(" GMT +")
                    .append(country.getDialCode()).toString()
            );
            countryListBeans.add(countryListBean);
        }

        return countryListBeans;
    }


}
