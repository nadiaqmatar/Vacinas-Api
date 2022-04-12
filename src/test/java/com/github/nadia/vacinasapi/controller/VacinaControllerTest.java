package com.github.nadia.vacinasapi.controller;

import com.github.nadia.vacinasapi.api.DTO.request.VacinaRequest;
import com.github.nadia.vacinasapi.api.DTO.request.VacinaUpdateRequest;
import com.github.nadia.vacinasapi.api.DTO.response.VacinaResponse;
import com.github.nadia.vacinasapi.api.controller.VacinaController;
import com.github.nadia.vacinasapi.builder.VacinaBuilder;
import com.github.nadia.vacinasapi.builder.VacinaRequestBuilder;
import com.github.nadia.vacinasapi.builder.VacinaResponseBuilder;
import com.github.nadia.vacinasapi.builder.VacinaUpdateRequestBuilder;
import com.github.nadia.vacinasapi.core.mapper.VacinaMapper;
import com.github.nadia.vacinasapi.domain.entity.Vacina;
import com.github.nadia.vacinasapi.domain.exception.NotFoundException;
import com.github.nadia.vacinasapi.domain.exception.ServiceException;
import com.github.nadia.vacinasapi.domain.service.VacinaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.nadia.vacinasapi.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//mockito - permite elaborar os testes
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class VacinaControllerTest {

    private static final String VACINA_API_URL_PATH = "/api/v1/vacinas";
    private final Long VALID_ID = 1L;
    private final Long INVALID_ID = 2l;
    private final String INVALID_USER_MAIL = "invalid@teste.com";

    private MockMvc mockMvc;

    @Mock //pega a dependencia
    private VacinaService vacinaService;

    @Mock
    private VacinaMapper vacinaMapper;

    @InjectMocks //injeta a dependencia
    private VacinaController vacinaController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vacinaController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenGETListVacinasIsCalled_ThenAnListOfVacinasIsReturned() throws Exception {
        //Configurações iniciais
        Vacina vacina = VacinaBuilder.builder().build().toVacina();
        VacinaResponse vacinaResponse = VacinaResponseBuilder.builder().build().toVacinaResponse();

        //Estabelecendo comportamento dos mocks
        when(vacinaService.buscarTodos()).thenReturn(Collections.singletonList(vacina)); //cria lista de um unico objeto
        when(vacinaMapper.toVacinaResponseList(Collections.singletonList(vacina))).thenReturn(Collections.singletonList(vacinaResponse));

        //Realizando test via mockMVC

        mockMvc.perform(get(VACINA_API_URL_PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is((vacinaResponse.getId().intValue()))))
                .andExpect(jsonPath("$[0].nome", is(vacinaResponse.getNome())))
                .andExpect(jsonPath("$[0].email", is(vacinaResponse.getEmail())))
                .andExpect(jsonPath("$[0].dataAplicacao", is(parse(vacinaResponse.getDataAplicacao()))));
    }

    private List<Integer> parse(LocalDate data) {
        List<Integer> dataList = new ArrayList<>();
        dataList.add(data.getYear());
        dataList.add(data.getMonthValue());
        dataList.add(data.getDayOfMonth());

        return dataList;
    }

    @Test
        //retornar um id valido
    void whenGETVacinaWithValidIdIsCalled_ThenAVacinaIsReturned() throws Exception {
        //Configurações iniciais
        Vacina vacina = VacinaBuilder.builder().build().toVacina();
        VacinaResponse vacinaResponse = VacinaResponseBuilder.builder().build().toVacinaResponse();

        //Estabelecendo comportamento dos mocks //dois tipos de retorno
        when(vacinaService.buscarPorId(VALID_ID)).thenReturn(vacina); //
        when(vacinaMapper.toVacinaResponse(vacina)).thenReturn(vacinaResponse);

        //Realizando test via mockMVC - realizando a requisição get

        mockMvc.perform(get(VACINA_API_URL_PATH + "/" + VALID_ID.toString()).contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vacinaResponse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((vacinaResponse.getId().intValue()))))
                .andExpect(jsonPath("$.nome", is(vacinaResponse.getNome())))
                .andExpect(jsonPath("$.email", is(vacinaResponse.getEmail())))
                .andExpect(jsonPath("$.dataAplicacao", is(parse(vacinaResponse.getDataAplicacao()))));
    }

    @Test
    void whenGETVacinaWithInvalidIdIsCalled_ThenAnErrorIsReturned() throws Exception {
        //Estabelecendo comportamento dos mocks
        doThrow(NotFoundException.class).when(vacinaService).buscarPorId(INVALID_ID);

        //Realizando test via mockMVC

        mockMvc.perform(get(VACINA_API_URL_PATH + "/" + INVALID_ID.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPOSTVacinaIdIsCalled_ThenAVacinaIsReturned() throws Exception {
        //Configurações iniciais
        Vacina vacina = VacinaBuilder.builder().build().toVacina();
        VacinaResponse vacinaResponse = VacinaResponseBuilder.builder().build().toVacinaResponse();
        VacinaRequest vacinaRequest = VacinaRequestBuilder.builder().build().toVacinaRequest();

        //Estabelecendo comportamento dos mocks
        when(vacinaMapper.toVacinaEntity(vacinaRequest)).thenReturn(vacina);
        when(vacinaMapper.toVacinaResponse(vacina)).thenReturn(vacinaResponse);
        when(vacinaService.salvar(vacina)).thenReturn(vacina);

        //Realizando test via mockMVC - realizando a requisição get

        mockMvc.perform(post(VACINA_API_URL_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vacinaResponse)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is((vacinaResponse.getId().intValue()))))
                .andExpect(jsonPath("$.nome", is(vacinaResponse.getNome())))
                .andExpect(jsonPath("$.email", is(vacinaResponse.getEmail())))
                .andExpect(jsonPath("$.dataAplicacao", is(parse(vacinaResponse.getDataAplicacao()))));
    }

    @Test
    void whenPOSTInvalidVacinaIsCalled_ThenAnErrorIsReturned() throws Exception {
        //Configurações iniciais
        VacinaRequest vacinaRequest = VacinaRequestBuilder.builder().build().toVacinaRequest();
        vacinaRequest.setEmail(INVALID_USER_MAIL);

        //Estabelecendo comportamento dos mocks - chamada de erro
        doThrow(ServiceException.class).when(vacinaMapper).toVacinaEntity(vacinaRequest);

        //Realizando test via mockMVC

        mockMvc.perform(post(VACINA_API_URL_PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPUTVacinaIsCalled_ThenAVacinaIsReturned() throws Exception {
        //Configurações iniciais
        Vacina vacina = VacinaBuilder.builder().build().toVacina();
        VacinaResponse vacinaResponse = VacinaResponseBuilder.builder().build().toVacinaResponse();
        VacinaUpdateRequest vacinaUpdateRequest = VacinaUpdateRequestBuilder.builder().build().toVacinaUpdateRequest();
        vacina.setNome(vacinaUpdateRequest.getNome());
        vacinaResponse.setNome(vacinaUpdateRequest.getNome());

        //Estabelecendo comportamento dos mocks
        when(vacinaMapper.toVacinaEntity(vacinaUpdateRequest)).thenReturn(vacina);
        when(vacinaMapper.toVacinaResponse(vacina)).thenReturn(vacinaResponse);
        when(vacinaService.atualizar(VALID_ID, vacina)).thenReturn(vacina);

        //Realizando test via mockMVC - realizando a requisição get

        mockMvc.perform(put(VACINA_API_URL_PATH+"/"+VALID_ID.toString()).contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vacinaResponse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((vacinaResponse.getId().intValue()))))
                .andExpect(jsonPath("$.nome", is(vacinaResponse.getNome())))
                .andExpect(jsonPath("$.email", is(vacinaResponse.getEmail())))
                .andExpect(jsonPath("$.dataAplicacao", is(parse(vacinaResponse.getDataAplicacao()))));
    }

    @Test
    void whenPUTInvalidVacinaIdIsCalled_ThenAnErrorIsReturned() throws Exception {
        //Configurações iniciais
        Vacina vacina = VacinaBuilder.builder().build().toVacina();
        VacinaUpdateRequest vacinaUpdateRequest = VacinaUpdateRequestBuilder.builder().build().toVacinaUpdateRequest();
        vacina.setNome(vacinaUpdateRequest.getNome());


        //Estabelecendo comportamento dos mocks - chamada de erro
        //Estabelecendo comportamento dos mocks
        when(vacinaMapper.toVacinaEntity(vacinaUpdateRequest)).thenReturn(vacina);
        doThrow(ServiceException.class).when(vacinaService).atualizar(INVALID_ID,vacina);

        //Realizando test via mockMVC
        mockMvc.perform(put(VACINA_API_URL_PATH+"/"+VALID_ID.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenDELETECalledWithValidId_ThenStatusNoContentIsReturned() throws Exception{
        //Estabelecendo comportamento dos mocks
        doNothing().when(vacinaService).deletar(VALID_ID);

        //Realizando test via mockMVC
        mockMvc.perform(delete(VACINA_API_URL_PATH+"/"+VALID_ID.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETECalledWithInvalidId_ThenAnErrorIsReturned() throws Exception{
        //Estabelecendo comportamento dos mocks
        doThrow(ServiceException.class).when(vacinaService).deletar(INVALID_ID);

        //Realizando test via mockMVC
        mockMvc.perform(delete(VACINA_API_URL_PATH+"/"+INVALID_ID.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}







