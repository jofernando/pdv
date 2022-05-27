package net.originmobi.pdv.service;

import net.originmobi.pdv.model.Fornecedor;
import net.originmobi.pdv.model.PagarTipo;
import net.originmobi.pdv.repository.PagarRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PagarServiceTest {

    @Mock
    FornecedorService fornecedorService;
    @Mock
    PagarParcelaService pagarParcelaService;
    @Mock
    PagarRepository pagarRepository;
    @InjectMocks
    PagarService pagarService;

    Fornecedor fornecedor;
    PagarTipo pagarTipo;

    @BeforeAll
    void setUp() {
        this.fornecedor = Fornecedor.FornecedorBuilder.aFornecedor().withCodigo(1L).build();
        this.pagarTipo = PagarTipo.PagarTipoBuilder.aPagarTipo().withCodigo(1L).withDescricao("descrição").build();
        when(this.fornecedorService.busca(1L)).thenReturn(Optional.of(this.fornecedor));
        when(this.fornecedorService.busca(2L)).thenReturn(Optional.empty());
    }

    @Test
    @DisplayName("Teste para cadastrar pagamento")
    public void cadastrar() {
        assertEquals("Despesa lançada com sucesso", this.pagarService.cadastrar(1L, 154.154, "", LocalDate.now(), this.pagarTipo));
        assertEquals("Despesa lançada com sucesso", this.pagarService.cadastrar(1L, 154.154, "descricao", LocalDate.now(), this.pagarTipo));
        assertEquals("Código do fornecedor inválido", this.pagarService.cadastrar(2L, 154.154, "", LocalDate.now(), this.pagarTipo));
    }


    @AfterAll
    void tearDown() {
    }
}