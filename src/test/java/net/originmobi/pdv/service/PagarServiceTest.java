package net.originmobi.pdv.service;

import net.originmobi.pdv.model.Fornecedor;
import net.originmobi.pdv.model.PagarTipo;
import net.originmobi.pdv.repository.PagarRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    @TestFactory
    @DisplayName("Teste para cadastrar pagamento")
    Stream<DynamicTest> teste() {
        List<String> observacoes = Arrays.asList("", "descricao");
        List<Long> codigos = Arrays.asList(1L, 2L);
        return observacoes.stream().flatMap(
            obs -> (
                codigos.stream().map(
                    codigo -> DynamicTest.dynamicTest("observacao: " + obs + ", código: " + codigo,
                            () -> assertEquals("Despesa lançada com sucesso", this.pagarService.cadastrar(1L, 154.154, obs, LocalDate.now(), this.pagarTipo))
                    )
                )
            )
        );
    }


    @AfterAll
    void tearDown() {
    }
}