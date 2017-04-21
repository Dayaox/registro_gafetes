package com.oplever.sioe.web.rest;

import com.oplever.sioe.RegistroGafetesApp;

import com.oplever.sioe.domain.Personal2;
import com.oplever.sioe.repository.Personal2Repository;
import com.oplever.sioe.service.Personal2Service;
import com.oplever.sioe.web.rest.errors.ExceptionTranslator;

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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Personal2Resource REST controller.
 *
 * @see Personal2Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RegistroGafetesApp.class)
public class Personal2ResourceIntTest {

    private static final String DEFAULT_DISTRITO = "AAAAAAAAAA";
    private static final String UPDATED_DISTRITO = "BBBBBBBBBB";

    private static final String DEFAULT_MUNICIPIO = "AAAAAAAAAA";
    private static final String UPDATED_MUNICIPIO = "BBBBBBBBBB";

    private static final String DEFAULT_CARGO = "AAAAAAAAAA";
    private static final String UPDATED_CARGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIPO = 1;
    private static final Integer UPDATED_TIPO = 2;

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private Personal2Repository personal2Repository;

    @Autowired
    private Personal2Service personal2Service;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonal2MockMvc;

    private Personal2 personal2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Personal2Resource personal2Resource = new Personal2Resource(personal2Service);
        this.restPersonal2MockMvc = MockMvcBuilders.standaloneSetup(personal2Resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personal2 createEntity(EntityManager em) {
        Personal2 personal2 = new Personal2()
            .distrito(DEFAULT_DISTRITO)
            .municipio(DEFAULT_MUNICIPIO)
            .cargo(DEFAULT_CARGO)
            .nombre(DEFAULT_NOMBRE)
            .tipo(DEFAULT_TIPO)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE);
        return personal2;
    }

    @Before
    public void initTest() {
        personal2 = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonal2() throws Exception {
        int databaseSizeBeforeCreate = personal2Repository.findAll().size();

        // Create the Personal2
        restPersonal2MockMvc.perform(post("/api/personal-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personal2)))
            .andExpect(status().isCreated());

        // Validate the Personal2 in the database
        List<Personal2> personal2List = personal2Repository.findAll();
        assertThat(personal2List).hasSize(databaseSizeBeforeCreate + 1);
        Personal2 testPersonal2 = personal2List.get(personal2List.size() - 1);
        assertThat(testPersonal2.getDistrito()).isEqualTo(DEFAULT_DISTRITO);
        assertThat(testPersonal2.getMunicipio()).isEqualTo(DEFAULT_MUNICIPIO);
        assertThat(testPersonal2.getCargo()).isEqualTo(DEFAULT_CARGO);
        assertThat(testPersonal2.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPersonal2.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testPersonal2.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testPersonal2.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPersonal2WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personal2Repository.findAll().size();

        // Create the Personal2 with an existing ID
        personal2.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonal2MockMvc.perform(post("/api/personal-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personal2)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Personal2> personal2List = personal2Repository.findAll();
        assertThat(personal2List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDistritoIsRequired() throws Exception {
        int databaseSizeBeforeTest = personal2Repository.findAll().size();
        // set the field null
        personal2.setDistrito(null);

        // Create the Personal2, which fails.

        restPersonal2MockMvc.perform(post("/api/personal-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personal2)))
            .andExpect(status().isBadRequest());

        List<Personal2> personal2List = personal2Repository.findAll();
        assertThat(personal2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMunicipioIsRequired() throws Exception {
        int databaseSizeBeforeTest = personal2Repository.findAll().size();
        // set the field null
        personal2.setMunicipio(null);

        // Create the Personal2, which fails.

        restPersonal2MockMvc.perform(post("/api/personal-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personal2)))
            .andExpect(status().isBadRequest());

        List<Personal2> personal2List = personal2Repository.findAll();
        assertThat(personal2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCargoIsRequired() throws Exception {
        int databaseSizeBeforeTest = personal2Repository.findAll().size();
        // set the field null
        personal2.setCargo(null);

        // Create the Personal2, which fails.

        restPersonal2MockMvc.perform(post("/api/personal-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personal2)))
            .andExpect(status().isBadRequest());

        List<Personal2> personal2List = personal2Repository.findAll();
        assertThat(personal2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = personal2Repository.findAll().size();
        // set the field null
        personal2.setNombre(null);

        // Create the Personal2, which fails.

        restPersonal2MockMvc.perform(post("/api/personal-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personal2)))
            .andExpect(status().isBadRequest());

        List<Personal2> personal2List = personal2Repository.findAll();
        assertThat(personal2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonal2S() throws Exception {
        // Initialize the database
        personal2Repository.saveAndFlush(personal2);

        // Get all the personal2List
        restPersonal2MockMvc.perform(get("/api/personal-2-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personal2.getId().intValue())))
            .andExpect(jsonPath("$.[*].distrito").value(hasItem(DEFAULT_DISTRITO.toString())))
            .andExpect(jsonPath("$.[*].municipio").value(hasItem(DEFAULT_MUNICIPIO.toString())))
            .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))));
    }

    @Test
    @Transactional
    public void getPersonal2() throws Exception {
        // Initialize the database
        personal2Repository.saveAndFlush(personal2);

        // Get the personal2
        restPersonal2MockMvc.perform(get("/api/personal-2-s/{id}", personal2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personal2.getId().intValue()))
            .andExpect(jsonPath("$.distrito").value(DEFAULT_DISTRITO.toString()))
            .andExpect(jsonPath("$.municipio").value(DEFAULT_MUNICIPIO.toString()))
            .andExpect(jsonPath("$.cargo").value(DEFAULT_CARGO.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingPersonal2() throws Exception {
        // Get the personal2
        restPersonal2MockMvc.perform(get("/api/personal-2-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonal2() throws Exception {
        // Initialize the database
        personal2Service.save(personal2);

        int databaseSizeBeforeUpdate = personal2Repository.findAll().size();

        // Update the personal2
        Personal2 updatedPersonal2 = personal2Repository.findOne(personal2.getId());
        updatedPersonal2
            .distrito(UPDATED_DISTRITO)
            .municipio(UPDATED_MUNICIPIO)
            .cargo(UPDATED_CARGO)
            .nombre(UPDATED_NOMBRE)
            .tipo(UPDATED_TIPO)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE);

        restPersonal2MockMvc.perform(put("/api/personal-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonal2)))
            .andExpect(status().isOk());

        // Validate the Personal2 in the database
        List<Personal2> personal2List = personal2Repository.findAll();
        assertThat(personal2List).hasSize(databaseSizeBeforeUpdate);
        Personal2 testPersonal2 = personal2List.get(personal2List.size() - 1);
        assertThat(testPersonal2.getDistrito()).isEqualTo(UPDATED_DISTRITO);
        assertThat(testPersonal2.getMunicipio()).isEqualTo(UPDATED_MUNICIPIO);
        assertThat(testPersonal2.getCargo()).isEqualTo(UPDATED_CARGO);
        assertThat(testPersonal2.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPersonal2.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testPersonal2.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testPersonal2.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonal2() throws Exception {
        int databaseSizeBeforeUpdate = personal2Repository.findAll().size();

        // Create the Personal2

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonal2MockMvc.perform(put("/api/personal-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personal2)))
            .andExpect(status().isCreated());

        // Validate the Personal2 in the database
        List<Personal2> personal2List = personal2Repository.findAll();
        assertThat(personal2List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonal2() throws Exception {
        // Initialize the database
        personal2Service.save(personal2);

        int databaseSizeBeforeDelete = personal2Repository.findAll().size();

        // Get the personal2
        restPersonal2MockMvc.perform(delete("/api/personal-2-s/{id}", personal2.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Personal2> personal2List = personal2Repository.findAll();
        assertThat(personal2List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Personal2.class);
    }
}
