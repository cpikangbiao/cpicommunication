package com.cpi.communication.web.rest;

import com.cpi.communication.CpicommunicationApp;

import com.cpi.communication.config.SecurityBeanOverrideConfiguration;

import com.cpi.communication.domain.Correspondent;
import com.cpi.communication.domain.Port;
import com.cpi.communication.repository.CorrespondentRepository;
import com.cpi.communication.service.CorrespondentService;
import com.cpi.communication.service.dto.CorrespondentDTO;
import com.cpi.communication.service.mapper.CorrespondentMapper;
import com.cpi.communication.web.rest.errors.ExceptionTranslator;
import com.cpi.communication.service.dto.CorrespondentCriteria;
import com.cpi.communication.service.CorrespondentQueryService;

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
 * Test class for the CorrespondentResource REST controller.
 *
 * @see CorrespondentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, CpicommunicationApp.class})
public class CorrespondentResourceIntTest {

    private static final String DEFAULT_CORRESPONDENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CORRESPONDENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FAX_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FAX_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_OFFICE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_OFFICE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_ALTERNATE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_ALTERNATE = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_SITE = "AAAAAAAAAA";
    private static final String UPDATED_WEB_SITE = "BBBBBBBBBB";

    @Autowired
    private CorrespondentRepository correspondentRepository;


    @Autowired
    private CorrespondentMapper correspondentMapper;
    

    @Autowired
    private CorrespondentService correspondentService;

    @Autowired
    private CorrespondentQueryService correspondentQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCorrespondentMockMvc;

    private Correspondent correspondent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CorrespondentResource correspondentResource = new CorrespondentResource(correspondentService, correspondentQueryService);
        this.restCorrespondentMockMvc = MockMvcBuilders.standaloneSetup(correspondentResource)
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
    public static Correspondent createEntity(EntityManager em) {
        Correspondent correspondent = new Correspondent()
            .correspondentName(DEFAULT_CORRESPONDENT_NAME)
            .faxNumber(DEFAULT_FAX_NUMBER)
            .address(DEFAULT_ADDRESS)
            .telephoneOffice(DEFAULT_TELEPHONE_OFFICE)
            .telephoneAlternate(DEFAULT_TELEPHONE_ALTERNATE)
            .webSite(DEFAULT_WEB_SITE);
        return correspondent;
    }

    @Before
    public void initTest() {
        correspondent = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorrespondent() throws Exception {
        int databaseSizeBeforeCreate = correspondentRepository.findAll().size();

        // Create the Correspondent
        CorrespondentDTO correspondentDTO = correspondentMapper.toDto(correspondent);
        restCorrespondentMockMvc.perform(post("/api/correspondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentDTO)))
            .andExpect(status().isCreated());

        // Validate the Correspondent in the database
        List<Correspondent> correspondentList = correspondentRepository.findAll();
        assertThat(correspondentList).hasSize(databaseSizeBeforeCreate + 1);
        Correspondent testCorrespondent = correspondentList.get(correspondentList.size() - 1);
        assertThat(testCorrespondent.getCorrespondentName()).isEqualTo(DEFAULT_CORRESPONDENT_NAME);
        assertThat(testCorrespondent.getFaxNumber()).isEqualTo(DEFAULT_FAX_NUMBER);
        assertThat(testCorrespondent.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCorrespondent.getTelephoneOffice()).isEqualTo(DEFAULT_TELEPHONE_OFFICE);
        assertThat(testCorrespondent.getTelephoneAlternate()).isEqualTo(DEFAULT_TELEPHONE_ALTERNATE);
        assertThat(testCorrespondent.getWebSite()).isEqualTo(DEFAULT_WEB_SITE);
    }

    @Test
    @Transactional
    public void createCorrespondentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = correspondentRepository.findAll().size();

        // Create the Correspondent with an existing ID
        correspondent.setId(1L);
        CorrespondentDTO correspondentDTO = correspondentMapper.toDto(correspondent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorrespondentMockMvc.perform(post("/api/correspondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Correspondent in the database
        List<Correspondent> correspondentList = correspondentRepository.findAll();
        assertThat(correspondentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCorrespondentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = correspondentRepository.findAll().size();
        // set the field null
        correspondent.setCorrespondentName(null);

        // Create the Correspondent, which fails.
        CorrespondentDTO correspondentDTO = correspondentMapper.toDto(correspondent);

        restCorrespondentMockMvc.perform(post("/api/correspondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentDTO)))
            .andExpect(status().isBadRequest());

        List<Correspondent> correspondentList = correspondentRepository.findAll();
        assertThat(correspondentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFaxNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = correspondentRepository.findAll().size();
        // set the field null
        correspondent.setFaxNumber(null);

        // Create the Correspondent, which fails.
        CorrespondentDTO correspondentDTO = correspondentMapper.toDto(correspondent);

        restCorrespondentMockMvc.perform(post("/api/correspondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentDTO)))
            .andExpect(status().isBadRequest());

        List<Correspondent> correspondentList = correspondentRepository.findAll();
        assertThat(correspondentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCorrespondents() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList
        restCorrespondentMockMvc.perform(get("/api/correspondents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondent.getId().intValue())))
            .andExpect(jsonPath("$.[*].correspondentName").value(hasItem(DEFAULT_CORRESPONDENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].faxNumber").value(hasItem(DEFAULT_FAX_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].telephoneOffice").value(hasItem(DEFAULT_TELEPHONE_OFFICE.toString())))
            .andExpect(jsonPath("$.[*].telephoneAlternate").value(hasItem(DEFAULT_TELEPHONE_ALTERNATE.toString())))
            .andExpect(jsonPath("$.[*].webSite").value(hasItem(DEFAULT_WEB_SITE.toString())));
    }
    

    @Test
    @Transactional
    public void getCorrespondent() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get the correspondent
        restCorrespondentMockMvc.perform(get("/api/correspondents/{id}", correspondent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(correspondent.getId().intValue()))
            .andExpect(jsonPath("$.correspondentName").value(DEFAULT_CORRESPONDENT_NAME.toString()))
            .andExpect(jsonPath("$.faxNumber").value(DEFAULT_FAX_NUMBER.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.telephoneOffice").value(DEFAULT_TELEPHONE_OFFICE.toString()))
            .andExpect(jsonPath("$.telephoneAlternate").value(DEFAULT_TELEPHONE_ALTERNATE.toString()))
            .andExpect(jsonPath("$.webSite").value(DEFAULT_WEB_SITE.toString()));
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByCorrespondentNameIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where correspondentName equals to DEFAULT_CORRESPONDENT_NAME
        defaultCorrespondentShouldBeFound("correspondentName.equals=" + DEFAULT_CORRESPONDENT_NAME);

        // Get all the correspondentList where correspondentName equals to UPDATED_CORRESPONDENT_NAME
        defaultCorrespondentShouldNotBeFound("correspondentName.equals=" + UPDATED_CORRESPONDENT_NAME);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByCorrespondentNameIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where correspondentName in DEFAULT_CORRESPONDENT_NAME or UPDATED_CORRESPONDENT_NAME
        defaultCorrespondentShouldBeFound("correspondentName.in=" + DEFAULT_CORRESPONDENT_NAME + "," + UPDATED_CORRESPONDENT_NAME);

        // Get all the correspondentList where correspondentName equals to UPDATED_CORRESPONDENT_NAME
        defaultCorrespondentShouldNotBeFound("correspondentName.in=" + UPDATED_CORRESPONDENT_NAME);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByCorrespondentNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where correspondentName is not null
        defaultCorrespondentShouldBeFound("correspondentName.specified=true");

        // Get all the correspondentList where correspondentName is null
        defaultCorrespondentShouldNotBeFound("correspondentName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByFaxNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where faxNumber equals to DEFAULT_FAX_NUMBER
        defaultCorrespondentShouldBeFound("faxNumber.equals=" + DEFAULT_FAX_NUMBER);

        // Get all the correspondentList where faxNumber equals to UPDATED_FAX_NUMBER
        defaultCorrespondentShouldNotBeFound("faxNumber.equals=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByFaxNumberIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where faxNumber in DEFAULT_FAX_NUMBER or UPDATED_FAX_NUMBER
        defaultCorrespondentShouldBeFound("faxNumber.in=" + DEFAULT_FAX_NUMBER + "," + UPDATED_FAX_NUMBER);

        // Get all the correspondentList where faxNumber equals to UPDATED_FAX_NUMBER
        defaultCorrespondentShouldNotBeFound("faxNumber.in=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByFaxNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where faxNumber is not null
        defaultCorrespondentShouldBeFound("faxNumber.specified=true");

        // Get all the correspondentList where faxNumber is null
        defaultCorrespondentShouldNotBeFound("faxNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where address equals to DEFAULT_ADDRESS
        defaultCorrespondentShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the correspondentList where address equals to UPDATED_ADDRESS
        defaultCorrespondentShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultCorrespondentShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the correspondentList where address equals to UPDATED_ADDRESS
        defaultCorrespondentShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where address is not null
        defaultCorrespondentShouldBeFound("address.specified=true");

        // Get all the correspondentList where address is null
        defaultCorrespondentShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByTelephoneOfficeIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where telephoneOffice equals to DEFAULT_TELEPHONE_OFFICE
        defaultCorrespondentShouldBeFound("telephoneOffice.equals=" + DEFAULT_TELEPHONE_OFFICE);

        // Get all the correspondentList where telephoneOffice equals to UPDATED_TELEPHONE_OFFICE
        defaultCorrespondentShouldNotBeFound("telephoneOffice.equals=" + UPDATED_TELEPHONE_OFFICE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByTelephoneOfficeIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where telephoneOffice in DEFAULT_TELEPHONE_OFFICE or UPDATED_TELEPHONE_OFFICE
        defaultCorrespondentShouldBeFound("telephoneOffice.in=" + DEFAULT_TELEPHONE_OFFICE + "," + UPDATED_TELEPHONE_OFFICE);

        // Get all the correspondentList where telephoneOffice equals to UPDATED_TELEPHONE_OFFICE
        defaultCorrespondentShouldNotBeFound("telephoneOffice.in=" + UPDATED_TELEPHONE_OFFICE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByTelephoneOfficeIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where telephoneOffice is not null
        defaultCorrespondentShouldBeFound("telephoneOffice.specified=true");

        // Get all the correspondentList where telephoneOffice is null
        defaultCorrespondentShouldNotBeFound("telephoneOffice.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByTelephoneAlternateIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where telephoneAlternate equals to DEFAULT_TELEPHONE_ALTERNATE
        defaultCorrespondentShouldBeFound("telephoneAlternate.equals=" + DEFAULT_TELEPHONE_ALTERNATE);

        // Get all the correspondentList where telephoneAlternate equals to UPDATED_TELEPHONE_ALTERNATE
        defaultCorrespondentShouldNotBeFound("telephoneAlternate.equals=" + UPDATED_TELEPHONE_ALTERNATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByTelephoneAlternateIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where telephoneAlternate in DEFAULT_TELEPHONE_ALTERNATE or UPDATED_TELEPHONE_ALTERNATE
        defaultCorrespondentShouldBeFound("telephoneAlternate.in=" + DEFAULT_TELEPHONE_ALTERNATE + "," + UPDATED_TELEPHONE_ALTERNATE);

        // Get all the correspondentList where telephoneAlternate equals to UPDATED_TELEPHONE_ALTERNATE
        defaultCorrespondentShouldNotBeFound("telephoneAlternate.in=" + UPDATED_TELEPHONE_ALTERNATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByTelephoneAlternateIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where telephoneAlternate is not null
        defaultCorrespondentShouldBeFound("telephoneAlternate.specified=true");

        // Get all the correspondentList where telephoneAlternate is null
        defaultCorrespondentShouldNotBeFound("telephoneAlternate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByWebSiteIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where webSite equals to DEFAULT_WEB_SITE
        defaultCorrespondentShouldBeFound("webSite.equals=" + DEFAULT_WEB_SITE);

        // Get all the correspondentList where webSite equals to UPDATED_WEB_SITE
        defaultCorrespondentShouldNotBeFound("webSite.equals=" + UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByWebSiteIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where webSite in DEFAULT_WEB_SITE or UPDATED_WEB_SITE
        defaultCorrespondentShouldBeFound("webSite.in=" + DEFAULT_WEB_SITE + "," + UPDATED_WEB_SITE);

        // Get all the correspondentList where webSite equals to UPDATED_WEB_SITE
        defaultCorrespondentShouldNotBeFound("webSite.in=" + UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByWebSiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where webSite is not null
        defaultCorrespondentShouldBeFound("webSite.specified=true");

        // Get all the correspondentList where webSite is null
        defaultCorrespondentShouldNotBeFound("webSite.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByPortIsEqualToSomething() throws Exception {
        // Initialize the database
        Port port = PortResourceIntTest.createEntity(em);
        em.persist(port);
        em.flush();
        correspondent.setPort(port);
        correspondentRepository.saveAndFlush(correspondent);
        Long portId = port.getId();

        // Get all the correspondentList where port equals to portId
        defaultCorrespondentShouldBeFound("portId.equals=" + portId);

        // Get all the correspondentList where port equals to portId + 1
        defaultCorrespondentShouldNotBeFound("portId.equals=" + (portId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCorrespondentShouldBeFound(String filter) throws Exception {
        restCorrespondentMockMvc.perform(get("/api/correspondents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondent.getId().intValue())))
            .andExpect(jsonPath("$.[*].correspondentName").value(hasItem(DEFAULT_CORRESPONDENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].faxNumber").value(hasItem(DEFAULT_FAX_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].telephoneOffice").value(hasItem(DEFAULT_TELEPHONE_OFFICE.toString())))
            .andExpect(jsonPath("$.[*].telephoneAlternate").value(hasItem(DEFAULT_TELEPHONE_ALTERNATE.toString())))
            .andExpect(jsonPath("$.[*].webSite").value(hasItem(DEFAULT_WEB_SITE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCorrespondentShouldNotBeFound(String filter) throws Exception {
        restCorrespondentMockMvc.perform(get("/api/correspondents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingCorrespondent() throws Exception {
        // Get the correspondent
        restCorrespondentMockMvc.perform(get("/api/correspondents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorrespondent() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        int databaseSizeBeforeUpdate = correspondentRepository.findAll().size();

        // Update the correspondent
        Correspondent updatedCorrespondent = correspondentRepository.findById(correspondent.getId()).get();
        // Disconnect from session so that the updates on updatedCorrespondent are not directly saved in db
        em.detach(updatedCorrespondent);
        updatedCorrespondent
            .correspondentName(UPDATED_CORRESPONDENT_NAME)
            .faxNumber(UPDATED_FAX_NUMBER)
            .address(UPDATED_ADDRESS)
            .telephoneOffice(UPDATED_TELEPHONE_OFFICE)
            .telephoneAlternate(UPDATED_TELEPHONE_ALTERNATE)
            .webSite(UPDATED_WEB_SITE);
        CorrespondentDTO correspondentDTO = correspondentMapper.toDto(updatedCorrespondent);

        restCorrespondentMockMvc.perform(put("/api/correspondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentDTO)))
            .andExpect(status().isOk());

        // Validate the Correspondent in the database
        List<Correspondent> correspondentList = correspondentRepository.findAll();
        assertThat(correspondentList).hasSize(databaseSizeBeforeUpdate);
        Correspondent testCorrespondent = correspondentList.get(correspondentList.size() - 1);
        assertThat(testCorrespondent.getCorrespondentName()).isEqualTo(UPDATED_CORRESPONDENT_NAME);
        assertThat(testCorrespondent.getFaxNumber()).isEqualTo(UPDATED_FAX_NUMBER);
        assertThat(testCorrespondent.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCorrespondent.getTelephoneOffice()).isEqualTo(UPDATED_TELEPHONE_OFFICE);
        assertThat(testCorrespondent.getTelephoneAlternate()).isEqualTo(UPDATED_TELEPHONE_ALTERNATE);
        assertThat(testCorrespondent.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    public void updateNonExistingCorrespondent() throws Exception {
        int databaseSizeBeforeUpdate = correspondentRepository.findAll().size();

        // Create the Correspondent
        CorrespondentDTO correspondentDTO = correspondentMapper.toDto(correspondent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCorrespondentMockMvc.perform(put("/api/correspondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Correspondent in the database
        List<Correspondent> correspondentList = correspondentRepository.findAll();
        assertThat(correspondentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCorrespondent() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        int databaseSizeBeforeDelete = correspondentRepository.findAll().size();

        // Get the correspondent
        restCorrespondentMockMvc.perform(delete("/api/correspondents/{id}", correspondent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Correspondent> correspondentList = correspondentRepository.findAll();
        assertThat(correspondentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Correspondent.class);
        Correspondent correspondent1 = new Correspondent();
        correspondent1.setId(1L);
        Correspondent correspondent2 = new Correspondent();
        correspondent2.setId(correspondent1.getId());
        assertThat(correspondent1).isEqualTo(correspondent2);
        correspondent2.setId(2L);
        assertThat(correspondent1).isNotEqualTo(correspondent2);
        correspondent1.setId(null);
        assertThat(correspondent1).isNotEqualTo(correspondent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrespondentDTO.class);
        CorrespondentDTO correspondentDTO1 = new CorrespondentDTO();
        correspondentDTO1.setId(1L);
        CorrespondentDTO correspondentDTO2 = new CorrespondentDTO();
        assertThat(correspondentDTO1).isNotEqualTo(correspondentDTO2);
        correspondentDTO2.setId(correspondentDTO1.getId());
        assertThat(correspondentDTO1).isEqualTo(correspondentDTO2);
        correspondentDTO2.setId(2L);
        assertThat(correspondentDTO1).isNotEqualTo(correspondentDTO2);
        correspondentDTO1.setId(null);
        assertThat(correspondentDTO1).isNotEqualTo(correspondentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(correspondentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(correspondentMapper.fromId(null)).isNull();
    }
}
