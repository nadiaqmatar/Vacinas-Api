package com.github.nadia.vacinasapi.service;

import com.github.nadia.vacinasapi.builder.VacinaBuilder;
import com.github.nadia.vacinasapi.domain.entity.Vacina;
import com.github.nadia.vacinasapi.domain.exception.NotFoundException;
import com.github.nadia.vacinasapi.domain.exception.ServiceException;
import com.github.nadia.vacinasapi.domain.repository.VacinaRepository;
import com.github.nadia.vacinasapi.domain.service.VacinaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VacinaServiceTest {

    private final Long VALID_ID = 1L;
    private final Long INVALID_ID = 2l;

    @Mock
    private VacinaRepository vacinaRepository;

    @InjectMocks
    private VacinaService vacinaService;

    @Test
    void whenListaDeVacinasRequisitada_thenShownThis(){ //então mostre isso
        //condições inicias do test
        Vacina vacina1 = VacinaBuilder.builder().build().toVacina();
        Vacina vacina2 = VacinaBuilder.builder().build().toVacina();
        Vacina vacina3 = VacinaBuilder.builder().build().toVacina();

        List<Vacina> vacinas = Arrays.asList(vacina1,vacina2,vacina3);

        //Estabelece o comportamento do mock
        when(vacinaRepository.findAll()).thenReturn(vacinas);

        //Realizar o teste
        var listaResposta = vacinaService.buscarTodos();
        assertEquals(vacinas, listaResposta);
    }

    @Test
    void whenSalvarVacinaRequisitada_thenVacinaShouldBeShown(){ //então a vacina deve ser mostrada
        //Condições iniciais
        Vacina vacina = VacinaBuilder.builder().build().toVacina();

        //Estabelecer o comportamento dos mocks
        when(vacinaRepository.save(vacina)).thenReturn(vacina);

        //Realizar teste
        var vacinaResposta = vacinaService.salvar(vacina);
        assertEquals(vacina, vacinaResposta);
    }

    @Test
    void whenValidVacinaIdInformado_thenVacinaShouldBeShown(){
        //Condições iniciais
        Vacina vacina = VacinaBuilder.builder().build().toVacina();

        //Estabelecer o comportamento dos mocks
        when(vacinaRepository.findById(VALID_ID)).thenReturn(Optional.of(vacina));

        //Realizar teste
        var vacinaResposta = vacinaService.buscarPorId(VALID_ID);
        assertEquals(vacina, vacinaResposta);
    }
    @Test
    void whenInvalidVacinaIdInformado_thenAnErrorShouldBeShown() {
        //Estabelecer o comportamento dos mocks
        when(vacinaRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        //Realizar teste
        //AssertThrows -> Usado para quando se espera determinado erro. Ex: asserTrows(TipoDeErro.class, ()-> classe.metodoTestado)
        assertThrows(NotFoundException.class,()-> vacinaService.buscarPorId(INVALID_ID));
    }
    @Test
    void whenVacinaAndValidIdInformadoToUpdate_thenAnVacinaShouldBeShown() {
        //Condições iniciais
        var vacina = VacinaBuilder.builder().build().toVacina();
        var vacinaParaAtualizar = VacinaBuilder.builder().build().toVacina();

        //Estabelecer o comportamento dos mocks
        when(vacinaRepository.findById(VALID_ID)).thenReturn(Optional.of(vacinaParaAtualizar));
        when(vacinaRepository.save(vacina)).thenReturn(vacina);

        //Realizar teste
        var vacinaResposta = vacinaService.atualizar(VALID_ID, vacina);
        assertEquals(vacina, vacinaResposta);
    }

    @Test
    void whenVacinaAndInvalidIdInformadoToUpdate_thenAnErrorShouldBeShown() {
        //Condições iniciais
        var vacina = VacinaBuilder.builder().build().toVacina();
        var vacinaParaAtualizar = VacinaBuilder.builder().build().toVacina();

        //Estabelecer o comportamento dos mocks
        when(vacinaRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        //Realizar teste
        assertThrows(ServiceException.class, ()-> vacinaService.atualizar(INVALID_ID, vacina));

    }

    @Test
    void whenDeletarIsRequestedWithValidId_thenAVacinaShouldBeExcluded() {
        //Condições iniciais
        var vacina = VacinaBuilder.builder().build().toVacina();
        vacina.setId(VALID_ID);

        //Estabelecer o comportamento dos mocks
        when(vacinaRepository.findById(VALID_ID)).thenReturn(Optional.of(vacina));
        doNothing().when(vacinaRepository).deleteById(VALID_ID);  //doNothing comportamento de uma classe que é void - tipo delete

        //Realizar teste
        vacinaService.deletar(vacina.getId());
        verify(vacinaRepository, times(1)).findById(vacina.getId());
        verify(vacinaRepository, times(1)).deleteById(vacina.getId());
    }

    @Test
    void whenDeletarIsRequestedWithValidId_thenAnErrorShouldBeShown() {
        //Estabelecer o comportamento dos mocks
        when(vacinaRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        //Realizar teste
        assertThrows(ServiceException.class,()-> vacinaService.deletar(INVALID_ID));

    }
}
