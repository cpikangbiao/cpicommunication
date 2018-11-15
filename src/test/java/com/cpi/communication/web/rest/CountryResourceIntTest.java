package com.cpi.communication.web.rest;

import com.cpi.communication.CpicommunicationApp;

import com.cpi.communication.config.SecurityBeanOverrideConfiguration;

import com.cpi.communication.domain.Country;
import com.cpi.communication.repository.CountryRepository;
import com.cpi.communication.service.CountryService;
import com.cpi.communication.service.dto.CountryDTO;
import com.cpi.communication.service.mapper.CountryMapper;
import com.cpi.communication.web.rest.errors.ExceptionTranslator;
import com.cpi.communication.service.dto.CountryCriteria;
import com.cpi.communication.service.CountryQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.cpi.communication.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CountryResource REST controller.
 *
 * @see CountryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, CpicommunicationApp.class})
public class CountryResourceIntTest {

    private static final String DEFAULT_COUNTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_NAME_ABBR = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME_ABBR = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_NAME_CHINESE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME_CHINESE = "BBBBBBBBBB";

    private static final String DEFAULT_DIAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DIAL_CODE = "BBBBBBBBBB";

    @Autowired
    private CountryRepository countryRepository;


    @Autowired
    private CountryMapper countryMapper;
    

    @Autowired
    private CountryService countryService;

    @Autowired
    private CountryQueryService countryQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCountryMockMvc;

    private Country country;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CountryResource countryResource = new CountryResource(countryService, countryQueryService);
        this.restCountryMockMvc = MockMvcBuilders.standaloneSetup(countryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Country createEntity(EntityManager em) {
        Country country = new Country()
            .countryName(DEFAULT_COUNTRY_NAME)
            .countryNameAbbr(DEFAULT_COUNTRY_NAME_ABBR)
            .countryNameChinese(DEFAULT_COUNTRY_NAME_CHINESE)
            .dialCode(DEFAULT_DIAL_CODE);
        return country;
    }

    @Before
    public void initTest() {
        country = createEntity(em);
    }

    @Test
    @Transactional
    public void createCountry() throws Exception {
        int databaseSizeBeforeCreate = countryRepository.findAll().size();

        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);
        restCountryMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isCreated());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeCreate + 1);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testCountry.getCountryNameAbbr()).isEqualTo(DEFAULT_COUNTRY_NAME_ABBR);
        assertThat(testCountry.getCountryNameChinese()).isEqualTo(DEFAULT_COUNTRY_NAME_CHINESE);
        assertThat(testCountry.getDialCode()).isEqualTo(DEFAULT_DIAL_CODE);
    }

    @Test
    @Transactional
    public void createCountryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countryRepository.findAll().size();

        // Create the Country with an existing ID
        country.setId(1L);
        CountryDTO countryDTO = countryMapper.toDto(country);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountryMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCountryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setCountryName(null);

        // Create the Country, which fails.
        CountryDTO countryDTO = countryMapper.toDto(country);

        restCountryMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryNameAbbrIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setCountryNameAbbr(null);

        // Create the Country, which fails.
        CountryDTO countryDTO = countryMapper.toDto(country);

        restCountryMockMvc.perform(post("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCountries() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList
        restCountryMockMvc.perform(get("/api/countries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(country.getId().intValue())))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME.toString())))
            .andExpect(jsonPath("$.[*].countryNameAbbr").value(hasItem(DEFAULT_COUNTRY_NAME_ABBR.toString())))
            .andExpect(jsonPath("$.[*].countryNameChinese").value(hasItem(DEFAULT_COUNTRY_NAME_CHINESE.toString())))
            .andExpect(jsonPath("$.[*].dialCode").value(hasItem(DEFAULT_DIAL_CODE.toString())));
    }
    

    @Test
    @Transactional
    public void getCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get the country
        restCountryMockMvc.perform(get("/api/countries/{id}", country.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(country.getId().intValue()))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME.toString()))
            .andExpect(jsonPath("$.countryNameAbbr").value(DEFAULT_COUNTRY_NAME_ABBR.toString()))
            .andExpect(jsonPath("$.countryNameChinese").value(DEFAULT_COUNTRY_NAME_CHINESE.toString()))
            .andExpect(jsonPath("$.dialCode").value(DEFAULT_DIAL_CODE.toString()));
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryNameIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryName equals to DEFAULT_COUNTRY_NAME
        defaultCountryShouldBeFound("countryName.equals=" + DEFAULT_COUNTRY_NAME);

        // Get all the countryList where countryName equals to UPDATED_COUNTRY_NAME
        defaultCountryShouldNotBeFound("countryName.equals=" + UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryNameIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryName in DEFAULT_COUNTRY_NAME or UPDATED_COUNTRY_NAME
        defaultCountryShouldBeFound("countryName.in=" + DEFAULT_COUNTRY_NAME + "," + UPDATED_COUNTRY_NAME);

        // Get all the countryList where countryName equals to UPDATED_COUNTRY_NAME
        defaultCountryShouldNotBeFound("countryName.in=" + UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryName is not null
        defaultCountryShouldBeFound("countryName.specified=true");

        // Get all the countryList where countryName is null
        defaultCountryShouldNotBeFound("countryName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryNameAbbrIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryNameAbbr equals to DEFAULT_COUNTRY_NAME_ABBR
        defaultCountryShouldBeFound("countryNameAbbr.equals=" + DEFAULT_COUNTRY_NAME_ABBR);

        // Get all the countryList where countryNameAbbr equals to UPDATED_COUNTRY_NAME_ABBR
        defaultCountryShouldNotBeFound("countryNameAbbr.equals=" + UPDATED_COUNTRY_NAME_ABBR);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryNameAbbrIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryNameAbbr in DEFAULT_COUNTRY_NAME_ABBR or UPDATED_COUNTRY_NAME_ABBR
        defaultCountryShouldBeFound("countryNameAbbr.in=" + DEFAULT_COUNTRY_NAME_ABBR + "," + UPDATED_COUNTRY_NAME_ABBR);

        // Get all the countryList where countryNameAbbr equals to UPDATED_COUNTRY_NAME_ABBR
        defaultCountryShouldNotBeFound("countryNameAbbr.in=" + UPDATED_COUNTRY_NAME_ABBR);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryNameAbbrIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryNameAbbr is not null
        defaultCountryShouldBeFound("countryNameAbbr.specified=true");

        // Get all the countryList where countryNameAbbr is null
        defaultCountryShouldNotBeFound("countryNameAbbr.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryNameChineseIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryNameChinese equals to DEFAULT_COUNTRY_NAME_CHINESE
        defaultCountryShouldBeFound("countryNameChinese.equals=" + DEFAULT_COUNTRY_NAME_CHINESE);

        // Get all the countryList where countryNameChinese equals to UPDATED_COUNTRY_NAME_CHINESE
        defaultCountryShouldNotBeFound("countryNameChinese.equals=" + UPDATED_COUNTRY_NAME_CHINESE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryNameChineseIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryNameChinese in DEFAULT_COUNTRY_NAME_CHINESE or UPDATED_COUNTRY_NAME_CHINESE
        defaultCountryShouldBeFound("countryNameChinese.in=" + DEFAULT_COUNTRY_NAME_CHINESE + "," + UPDATED_COUNTRY_NAME_CHINESE);

        // Get all the countryList where countryNameChinese equals to UPDATED_COUNTRY_NAME_CHINESE
        defaultCountryShouldNotBeFound("countryNameChinese.in=" + UPDATED_COUNTRY_NAME_CHINESE);
    }

    @Test
    @Transactional
    public void getAllCountriesByCountryNameChineseIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where countryNameChinese is not null
        defaultCountryShouldBeFound("countryNameChinese.specified=true");

        // Get all the countryList where countryNameChinese is null
        defaultCountryShouldNotBeFound("countryNameChinese.specified=false");
    }

    @Test
    @Transactional
    public void getAllCountriesByDialCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where dialCode equals to DEFAULT_DIAL_CODE
        defaultCountryShouldBeFound("dialCode.equals=" + DEFAULT_DIAL_CODE);

        // Get all the countryList where dialCode equals to UPDATED_DIAL_CODE
        defaultCountryShouldNotBeFound("dialCode.equals=" + UPDATED_DIAL_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByDialCodeIsInShouldWork() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where dialCode in DEFAULT_DIAL_CODE or UPDATED_DIAL_CODE
        defaultCountryShouldBeFound("dialCode.in=" + DEFAULT_DIAL_CODE + "," + UPDATED_DIAL_CODE);

        // Get all the countryList where dialCode equals to UPDATED_DIAL_CODE
        defaultCountryShouldNotBeFound("dialCode.in=" + UPDATED_DIAL_CODE);
    }

    @Test
    @Transactional
    public void getAllCountriesByDialCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList where dialCode is not null
        defaultCountryShouldBeFound("dialCode.specified=true");

        // Get all the countryList where dialCode is null
        defaultCountryShouldNotBeFound("dialCode.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCountryShouldBeFound(String filter) throws Exception {
        restCountryMockMvc.perform(get("/api/countries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(country.getId().intValue())))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME.toString())))
            .andExpect(jsonPath("$.[*].countryNameAbbr").value(hasItem(DEFAULT_COUNTRY_NAME_ABBR.toString())))
            .andExpect(jsonPath("$.[*].countryNameChinese").value(hasItem(DEFAULT_COUNTRY_NAME_CHINESE.toString())))
            .andExpect(jsonPath("$.[*].dialCode").value(hasItem(DEFAULT_DIAL_CODE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCountryShouldNotBeFound(String filter) throws Exception {
        restCountryMockMvc.perform(get("/api/countries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingCountry() throws Exception {
        // Get the country
        restCountryMockMvc.perform(get("/api/countries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Update the country
        Country updatedCountry = countryRepository.findById(country.getId()).get();
        // Disconnect from session so that the updates on updatedCountry are not directly saved in db
        em.detach(updatedCountry);
        updatedCountry
            .countryName(UPDATED_COUNTRY_NAME)
            .countryNameAbbr(UPDATED_COUNTRY_NAME_ABBR)
            .countryNameChinese(UPDATED_COUNTRY_NAME_CHINESE)
            .dialCode(UPDATED_DIAL_CODE);
        CountryDTO countryDTO = countryMapper.toDto(updatedCountry);

        restCountryMockMvc.perform(put("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isOk());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testCountry.getCountryNameAbbr()).isEqualTo(UPDATED_COUNTRY_NAME_ABBR);
        assertThat(testCountry.getCountryNameChinese()).isEqualTo(UPDATED_COUNTRY_NAME_CHINESE);
        assertThat(testCountry.getDialCode()).isEqualTo(UPDATED_DIAL_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Create the Country
        CountryDTO countryDTO = countryMapper.toDto(country);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCountryMockMvc.perform(put("/api/countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeDelete = countryRepository.findAll().size();

        // Get the country
        restCountryMockMvc.perform(delete("/api/countries/{id}", country.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Country.class);
        Country country1 = new Country();
        country1.setId(1L);
        Country country2 = new Country();
        country2.setId(country1.getId());
        assertThat(country1).isEqualTo(country2);
        country2.setId(2L);
        assertThat(country1).isNotEqualTo(country2);
        country1.setId(null);
        assertThat(country1).isNotEqualTo(country2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryDTO.class);
        CountryDTO countryDTO1 = new CountryDTO();
        countryDTO1.setId(1L);
        CountryDTO countryDTO2 = new CountryDTO();
        assertThat(countryDTO1).isNotEqualTo(countryDTO2);
        countryDTO2.setId(countryDTO1.getId());
        assertThat(countryDTO1).isEqualTo(countryDTO2);
        countryDTO2.setId(2L);
        assertThat(countryDTO1).isNotEqualTo(countryDTO2);
        countryDTO1.setId(null);
        assertThat(countryDTO1).isNotEqualTo(countryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(countryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(countryMapper.fromId(null)).isNull();
    }
}
