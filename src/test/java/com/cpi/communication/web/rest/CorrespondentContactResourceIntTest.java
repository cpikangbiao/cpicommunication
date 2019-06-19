package com.cpi.communication.web.rest;

import com.cpi.communication.CpicommunicationApp;

import com.cpi.communication.config.SecurityBeanOverrideConfiguration;

import com.cpi.communication.domain.CorrespondentContact;
import com.cpi.communication.domain.Correspondent;
import com.cpi.communication.repository.CorrespondentContactRepository;
import com.cpi.communication.service.CorrespondentContactService;
import com.cpi.communication.service.dto.CorrespondentContactDTO;
import com.cpi.communication.service.mapper.CorrespondentContactMapper;
import com.cpi.communication.web.rest.errors.ExceptionTranslator;
import com.cpi.communication.service.dto.CorrespondentContactCriteria;
import com.cpi.communication.service.CorrespondentContactQueryService;

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
 * Test class for the CorrespondentContactResource REST controller.
 *
 * @see CorrespondentContactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, CpicommunicationApp.class})
public class CorrespondentContactResourceIntTest {

    private static final String DEFAULT_CORRESPONDENT_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CORRESPONDENT_CONTACT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_E_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_E_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_OFFICE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_OFFICE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_SITE = "AAAAAAAAAA";
    private static final String UPDATED_WEB_SITE = "BBBBBBBBBB";

    @Autowired
    private CorrespondentContactRepository correspondentContactRepository;


    @Autowired
    private CorrespondentContactMapper correspondentContactMapper;
    

    @Autowired
    private CorrespondentContactService correspondentContactService;

    @Autowired
    private CorrespondentContactQueryService correspondentContactQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCorrespondentContactMockMvc;

    private CorrespondentContact correspondentContact;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CorrespondentContactResource correspondentContactResource = new CorrespondentContactResource(correspondentContactService, correspondentContactQueryService);
        this.restCorrespondentContactMockMvc = MockMvcBuilders.standaloneSetup(correspondentContactResource)
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
    public static CorrespondentContact createEntity(EntityManager em) {
        CorrespondentContact correspondentContact = new CorrespondentContact()
            .correspondentContactName(DEFAULT_CORRESPONDENT_CONTACT_NAME)
            .eMail(DEFAULT_E_MAIL)
            .telephoneOffice(DEFAULT_TELEPHONE_OFFICE)
            .telephone(DEFAULT_TELEPHONE)
            .webSite(DEFAULT_WEB_SITE);
        return correspondentContact;
    }

    @Before
    public void initTest() {
        correspondentContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorrespondentContact() throws Exception {
        int databaseSizeBeforeCreate = correspondentContactRepository.findAll().size();

        // Create the CorrespondentContact
        CorrespondentContactDTO correspondentContactDTO = correspondentContactMapper.toDto(correspondentContact);
        restCorrespondentContactMockMvc.perform(post("/api/correspondent-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentContactDTO)))
            .andExpect(status().isCreated());

        // Validate the CorrespondentContact in the database
        List<CorrespondentContact> correspondentContactList = correspondentContactRepository.findAll();
        assertThat(correspondentContactList).hasSize(databaseSizeBeforeCreate + 1);
        CorrespondentContact testCorrespondentContact = correspondentContactList.get(correspondentContactList.size() - 1);
        assertThat(testCorrespondentContact.getCorrespondentContactName()).isEqualTo(DEFAULT_CORRESPONDENT_CONTACT_NAME);
        assertThat(testCorrespondentContact.geteMail()).isEqualTo(DEFAULT_E_MAIL);
        assertThat(testCorrespondentContact.getTelephoneOffice()).isEqualTo(DEFAULT_TELEPHONE_OFFICE);
        assertThat(testCorrespondentContact.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testCorrespondentContact.getWebSite()).isEqualTo(DEFAULT_WEB_SITE);
    }

    @Test
    @Transactional
    public void createCorrespondentContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = correspondentContactRepository.findAll().size();

        // Create the CorrespondentContact with an existing ID
        correspondentContact.setId(1L);
        CorrespondentContactDTO correspondentContactDTO = correspondentContactMapper.toDto(correspondentContact);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorrespondentContactMockMvc.perform(post("/api/correspondent-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CorrespondentContact in the database
        List<CorrespondentContact> correspondentContactList = correspondentContactRepository.findAll();
        assertThat(correspondentContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCorrespondentContactNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = correspondentContactRepository.findAll().size();
        // set the field null
        correspondentContact.setCorrespondentContactName(null);

        // Create the CorrespondentContact, which fails.
        CorrespondentContactDTO correspondentContactDTO = correspondentContactMapper.toDto(correspondentContact);

        restCorrespondentContactMockMvc.perform(post("/api/correspondent-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentContactDTO)))
            .andExpect(status().isBadRequest());

        List<CorrespondentContact> correspondentContactList = correspondentContactRepository.findAll();
        assertThat(correspondentContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkeMailIsRequired() throws Exception {
        int databaseSizeBeforeTest = correspondentContactRepository.findAll().size();
        // set the field null
        correspondentContact.seteMail(null);

        // Create the CorrespondentContact, which fails.
        CorrespondentContactDTO correspondentContactDTO = correspondentContactMapper.toDto(correspondentContact);

        restCorrespondentContactMockMvc.perform(post("/api/correspondent-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentContactDTO)))
            .andExpect(status().isBadRequest());

        List<CorrespondentContact> correspondentContactList = correspondentContactRepository.findAll();
        assertThat(correspondentContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCorrespondentContacts() throws Exception {
        // Initialize the database
        correspondentContactRepository.saveAndFlush(correspondentContact);

        // Get all the correspondentContactList
        restCorrespondentContactMockMvc.perform(get("/api/correspondent-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondentContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].correspondentContactName").value(hasItem(DEFAULT_CORRESPONDENT_CONTACT_NAME.toString())))
            .andExpect(jsonPath("$.[*].eMail").value(hasItem(DEFAULT_E_MAIL.toString())))
            .andExpect(jsonPath("$.[*].telephoneOffice").value(hasItem(DEFAULT_TELEPHONE_OFFICE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].webSite").value(hasItem(DEFAULT_WEB_SITE.toString())));
    }
    

    @Test
    @Transactional
    public void getCorrespondentContact() throws Exception {
        // Initialize the database
        correspondentContactRepository.saveAndFlush(correspondentContact);

        // Get the correspondentContact
        restCorrespondentContactMockMvc.perform(get("/api/correspondent-contacts/{id}", correspondentContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(correspondentContact.getId().intValue()))
            .andExpect(jsonPath("$.correspondentContactName").value(DEFAULT_CORRESPONDENT_CONTACT_NAME.toString()))
            .andExpect(jsonPath("$.eMail").value(DEFAULT_E_MAIL.toString()))
            .andExpect(jsonPath("$.telephoneOffice").value(DEFAULT_TELEPHONE_OFFICE.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.webSite").value(DEFAULT_WEB_SITE.toString()));
    }

    @Test
    @Transactional
    public void getAllCorrespondentContactsByCorrespondentContactNameIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentContactRepository.saveAndFlush(correspondentContact);

        // Get all the correspondentContactList where correspondentContactName equals to DEFAULT_CORRESPONDENT_CONTACT_NAME
        defaultCorrespondentContactShouldBeFound("correspondentContactName.equals=" + DEFAULT_CORRESPONDENT_CONTACT_NAME);

        // Get all the correspondentContactList where correspondentContactName equals to UPDATED_CORRESPONDENT_CONTACT_NAME
        defaultCorrespondentContactShouldNotBeFound("correspondentContactName.equals=" + UPDATED_CORRESPONDENT_CONTACT_NAME);
    }

    @Test
    @Transactional
    public void getAllCorrespondentContactsByCorrespondentContactNameIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentContactRepository.saveAndFlush(correspondentContact);

        // Get all the correspondentContactList where correspondentContactName in DEFAULT_CORRESPONDENT_CONTACT_NAME or UPDATED_CORRESPONDENT_CONTACT_NAME
        defaultCorrespondentContactShouldBeFound("correspondentContactName.in=" + DEFAULT_CORRESPONDENT_CONTACT_NAME + "," + UPDATED_CORRESPONDENT_CONTACT_NAME);

        // Get all the correspondentContactList where correspondentContactName equals to UPDATED_CORRESPONDENT_CONTACT_NAME
        defaultCorrespondentContactShouldNotBeFound("correspondentContactName.in=" + UPDATED_CORRESPONDENT_CONTACT_NAME);
    }

    @Test
    @Transactional
    public void getAllCorrespondentContactsByCorrespondentContactNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentContactRepository.saveAndFlush(correspondentContact);

        // Get all the correspondentContactList where correspondentContactName is not null
        defaultCorrespondentContactShouldBeFound("correspondentContactName.specified=true");

        // Get all the correspondentContactList where correspondentContactName is null
        defaultCorrespondentContactShouldNotBeFound("correspondentContactName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentContactsByeMailIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentContactRepository.saveAndFlush(correspondentContact);

        // Get all the correspondentContactList where eMail equals to DEFAULT_E_MAIL
        defaultCorrespondentContactShouldBeFound("eMail.equals=" + DEFAULT_E_MAIL);

        // Get all the correspondentContactList where eMail equals to UPDATED_E_MAIL
        defaultCorrespondentContactShouldNotBeFound("eMail.equals=" + UPDATED_E_MAIL);
    }

    @Test
    @Transactional
    public void getAllCorrespondentContactsByeMailIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentContactRepository.saveAndFlush(correspondentContact);

        // Get all the correspondentContactList where eMail in DEFAULT_E_MAIL or UPDATED_E_MAIL
        defaultCorrespondentContactShouldBeFound("eMail.in=" + DEFAULT_E_MAIL + "," + UPDATED_E_MAIL);

        // Get all the correspondentContactList where eMail equals to UPDATED_E_MAIL
        defaultCorrespondentContactShouldNotBeFound("eMail.in=" + UPDATED_E_MAIL);
    }

    @Test
    @Transactional
    public void getAllCorrespondentContactsByeMailIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentContactRepository.saveAndFlush(correspondentContact);

        // Get all the correspondentContactList where eMail is not null
        defaultCorrespondentContactShouldBeFound("eMail.specified=true");

        // Get all the correspondentContactList where eMail is null
        defaultCorrespondentContactShouldNotBeFound("eMail.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentContactsByTelephoneOfficeIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentContactRepository.saveAndFlush(correspondentContact);

        // Get all the correspondentContactList where telephoneOffice equals to DEFAULT_TELEPHONE_OFFICE
        defaultCorrespondentContactShouldBeFound("telephoneOffice.equals=" + DEFAULT_TELEPHONE_OFFICE);

        // Get all the correspondentContactList where telephoneOffice equals to UPDATED_TELEPHONE_OFFICE
        defaultCorrespondentContactShouldNotBeFound("telephoneOffice.equals=" + UPDATED_TELEPHONE_OFFICE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentContactsByTelephoneOfficeIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentContactRepository.saveAndFlush(correspondentContact);

        // Get all the correspondentContactList where telephoneOffice in DEFAULT_TELEPHONE_OFFICE or UPDATED_TELEPHONE_OFFICE
        defaultCorrespondentContactShouldBeFound("telephoneOffice.in=" + DEFAULT_TELEPHONE_OFFICE + "," + UPDATED_TELEPHONE_OFFICE);

        // Get all the correspondentContactList where telephoneOffice equals to UPDATED_TELEPHONE_OFFICE
        defaultCorrespondentContactShouldNotBeFound("telephoneOffice.in=" + UPDATED_TELEPHONE_OFFICE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentContactsByTelephoneOfficeIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentContactRepository.saveAndFlush(correspondentContact);

        // Get all the correspondentContactList where telephoneOffice is not null
        defaultCorrespondentContactShouldBeFound("telephoneOffice.specified=true");

        // Get all the correspondentContactList where telephoneOffice is null
        defaultCorrespondentContactShouldNotBeFound("telephoneOffice.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentContactsByTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentContactRepository.saveAndFlush(correspondentContact);

        // Get all the correspondentContactList where telephone equals to DEFAULT_TELEPHONE
        defaultCorrespondentContactShouldBeFound("telephone.equals=" + DEFAULT_TELEPHONE);

        // Get all the correspondentContactList where telephone equals to UPDATED_TELEPHONE
        defaultCorrespondentContactShouldNotBeFound("telephone.equals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentContactsByTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentContactRepository.saveAndFlush(correspondentContact);

        // Get all the correspondentContactList where telephone in DEFAULT_TELEPHONE or UPDATED_TELEPHONE
        defaultCorrespondentContactShouldBeFound("telephone.in=" + DEFAULT_TELEPHONE + "," + UPDATED_TELEPHONE);

        // Get all the correspondentContactList where telephone equals to UPDATED_TELEPHONE
        defaultCorrespondentContactShouldNotBeFound("telephone.in=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentContactsByTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentContactRepository.saveAndFlush(correspondentContact);

        // Get all the correspondentContactList where telephone is not null
        defaultCorrespondentContactShouldBeFound("telephone.specified=true");

        // Get all the correspondentContactList where telephone is null
        defaultCorrespondentContactShouldNotBeFound("telephone.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentContactsByWebSiteIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentContactRepository.saveAndFlush(correspondentContact);

        // Get all the correspondentContactList where webSite equals to DEFAULT_WEB_SITE
        defaultCorrespondentContactShouldBeFound("webSite.equals=" + DEFAULT_WEB_SITE);

        // Get all the correspondentContactList where webSite equals to UPDATED_WEB_SITE
        defaultCorrespondentContactShouldNotBeFound("webSite.equals=" + UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentContactsByWebSiteIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentContactRepository.saveAndFlush(correspondentContact);

        // Get all the correspondentContactList where webSite in DEFAULT_WEB_SITE or UPDATED_WEB_SITE
        defaultCorrespondentContactShouldBeFound("webSite.in=" + DEFAULT_WEB_SITE + "," + UPDATED_WEB_SITE);

        // Get all the correspondentContactList where webSite equals to UPDATED_WEB_SITE
        defaultCorrespondentContactShouldNotBeFound("webSite.in=" + UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentContactsByWebSiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentContactRepository.saveAndFlush(correspondentContact);

        // Get all the correspondentContactList where webSite is not null
        defaultCorrespondentContactShouldBeFound("webSite.specified=true");

        // Get all the correspondentContactList where webSite is null
        defaultCorrespondentContactShouldNotBeFound("webSite.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentContactsByCorrespondentIsEqualToSomething() throws Exception {
        // Initialize the database
        Correspondent correspondent = CorrespondentResourceIntTest.createEntity(em);
        em.persist(correspondent);
        em.flush();
        correspondentContact.setCorrespondent(correspondent);
        correspondentContactRepository.saveAndFlush(correspondentContact);
        Long correspondentId = correspondent.getId();

        // Get all the correspondentContactList where correspondent equals to correspondentId
        defaultCorrespondentContactShouldBeFound("correspondentId.equals=" + correspondentId);

        // Get all the correspondentContactList where correspondent equals to correspondentId + 1
        defaultCorrespondentContactShouldNotBeFound("correspondentId.equals=" + (correspondentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCorrespondentContactShouldBeFound(String filter) throws Exception {
        restCorrespondentContactMockMvc.perform(get("/api/correspondent-contacts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondentContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].correspondentContactName").value(hasItem(DEFAULT_CORRESPONDENT_CONTACT_NAME.toString())))
            .andExpect(jsonPath("$.[*].eMail").value(hasItem(DEFAULT_E_MAIL.toString())))
            .andExpect(jsonPath("$.[*].telephoneOffice").value(hasItem(DEFAULT_TELEPHONE_OFFICE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].webSite").value(hasItem(DEFAULT_WEB_SITE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCorrespondentContactShouldNotBeFound(String filter) throws Exception {
        restCorrespondentContactMockMvc.perform(get("/api/correspondent-contacts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingCorrespondentContact() throws Exception {
        // Get the correspondentContact
        restCorrespondentContactMockMvc.perform(get("/api/correspondent-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorrespondentContact() throws Exception {
        // Initialize the database
        correspondentContactRepository.saveAndFlush(correspondentContact);

        int databaseSizeBeforeUpdate = correspondentContactRepository.findAll().size();

        // Update the correspondentContact
        CorrespondentContact updatedCorrespondentContact = correspondentContactRepository.findById(correspondentContact.getId()).get();
        // Disconnect from session so that the updates on updatedCorrespondentContact are not directly saved in db
        em.detach(updatedCorrespondentContact);
        updatedCorrespondentContact
            .correspondentContactName(UPDATED_CORRESPONDENT_CONTACT_NAME)
            .eMail(UPDATED_E_MAIL)
            .telephoneOffice(UPDATED_TELEPHONE_OFFICE)
            .telephone(UPDATED_TELEPHONE)
            .webSite(UPDATED_WEB_SITE);
        CorrespondentContactDTO correspondentContactDTO = correspondentContactMapper.toDto(updatedCorrespondentContact);

        restCorrespondentContactMockMvc.perform(put("/api/correspondent-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentContactDTO)))
            .andExpect(status().isOk());

        // Validate the CorrespondentContact in the database
        List<CorrespondentContact> correspondentContactList = correspondentContactRepository.findAll();
        assertThat(correspondentContactList).hasSize(databaseSizeBeforeUpdate);
        CorrespondentContact testCorrespondentContact = correspondentContactList.get(correspondentContactList.size() - 1);
        assertThat(testCorrespondentContact.getCorrespondentContactName()).isEqualTo(UPDATED_CORRESPONDENT_CONTACT_NAME);
        assertThat(testCorrespondentContact.geteMail()).isEqualTo(UPDATED_E_MAIL);
        assertThat(testCorrespondentContact.getTelephoneOffice()).isEqualTo(UPDATED_TELEPHONE_OFFICE);
        assertThat(testCorrespondentContact.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testCorrespondentContact.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    public void updateNonExistingCorrespondentContact() throws Exception {
        int databaseSizeBeforeUpdate = correspondentContactRepository.findAll().size();

        // Create the CorrespondentContact
        CorrespondentContactDTO correspondentContactDTO = correspondentContactMapper.toDto(correspondentContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCorrespondentContactMockMvc.perform(put("/api/correspondent-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CorrespondentContact in the database
        List<CorrespondentContact> correspondentContactList = correspondentContactRepository.findAll();
        assertThat(correspondentContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCorrespondentContact() throws Exception {
        // Initialize the database
        correspondentContactRepository.saveAndFlush(correspondentContact);

        int databaseSizeBeforeDelete = correspondentContactRepository.findAll().size();

        // Get the correspondentContact
        restCorrespondentContactMockMvc.perform(delete("/api/correspondent-contacts/{id}", correspondentContact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorrespondentContact> correspondentContactList = correspondentContactRepository.findAll();
        assertThat(correspondentContactList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrespondentContact.class);
        CorrespondentContact correspondentContact1 = new CorrespondentContact();
        correspondentContact1.setId(1L);
        CorrespondentContact correspondentContact2 = new CorrespondentContact();
        correspondentContact2.setId(correspondentContact1.getId());
        assertThat(correspondentContact1).isEqualTo(correspondentContact2);
        correspondentContact2.setId(2L);
        assertThat(correspondentContact1).isNotEqualTo(correspondentContact2);
        correspondentContact1.setId(null);
        assertThat(correspondentContact1).isNotEqualTo(correspondentContact2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrespondentContactDTO.class);
        CorrespondentContactDTO correspondentContactDTO1 = new CorrespondentContactDTO();
        correspondentContactDTO1.setId(1L);
        CorrespondentContactDTO correspondentContactDTO2 = new CorrespondentContactDTO();
        assertThat(correspondentContactDTO1).isNotEqualTo(correspondentContactDTO2);
        correspondentContactDTO2.setId(correspondentContactDTO1.getId());
        assertThat(correspondentContactDTO1).isEqualTo(correspondentContactDTO2);
        correspondentContactDTO2.setId(2L);
        assertThat(correspondentContactDTO1).isNotEqualTo(correspondentContactDTO2);
        correspondentContactDTO1.setId(null);
        assertThat(correspondentContactDTO1).isNotEqualTo(correspondentContactDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(correspondentContactMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(correspondentContactMapper.fromId(null)).isNull();
    }
}
