package net.originmobi.pdv.service;

import net.originmobi.pdv.model.Caixa;
import net.originmobi.pdv.model.Usuario;
import net.originmobi.pdv.repository.CaixaRepository;
import net.originmobi.pdv.singleton.Aplicacao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CaixaServiceTest {
    @Mock
    Aplicacao aplicacao;
    @Mock
    UsuarioService usuarioService;
    @Mock
    CaixaRepository caixaRepository;
    @InjectMocks
    CaixaService caixaService;

    Usuario usuarioLogado;
    Caixa caixaAberto;
    Caixa caixaFechado;

    @BeforeEach
    void setUp() {
        this.usuarioLogado = Usuario.UsuarioBuilder.anUsuario().withUser("usuarioLogado").withSenha("senha", true).build();
        this.caixaAberto = Caixa.CaixaBuilder.aCaixa().withCodigo(1L).build();
        this.caixaFechado = Caixa.CaixaBuilder.aCaixa().withCodigo(2L).withData_fechamento(new Timestamp(new Date().getTime())).build();
        when(this.aplicacao.getUsuarioAtual()).thenReturn(this.usuarioLogado.getUser());
        when(this.usuarioService.buscaUsuario(this.usuarioLogado.getUser())).thenReturn(this.usuarioLogado);
        when(this.caixaRepository.findById(this.caixaAberto.getCodigo())).thenReturn(Optional.of(this.caixaAberto));
        when(this.caixaRepository.findById(this.caixaFechado.getCodigo())).thenReturn(Optional.of(this.caixaFechado));
    }

    @Test
    void fechaCaixa() {
        try (MockedStatic<Aplicacao> aplicacao = Mockito.mockStatic(Aplicacao.class)) {
            aplicacao.when(Aplicacao::getInstancia).thenReturn(this.aplicacao);
            assertEquals("Favor, informe a senha", this.caixaService.fechaCaixa(1L, ""));
            assertEquals("Senha incorreta, favor verifique", this.caixaService.fechaCaixa(1L, "123456"));
            assertEquals("Caixa fechado com sucesso", this.caixaService.fechaCaixa(1L, "senha"));
            Exception exception = assertThrows(RuntimeException.class, () -> this.caixaService.fechaCaixa(2L, "senha"), "não lançou exceção");
            assertEquals("Caixa já esta fechado", exception.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
    }
}