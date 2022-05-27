package net.originmobi.pdv.service;

import net.originmobi.pdv.model.GrupoUsuario;
import net.originmobi.pdv.model.Pessoa;
import net.originmobi.pdv.model.Usuario;
import net.originmobi.pdv.repository.UsuarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarios;
    @Mock
    private GrupoUsuarioService grupos;
    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioComUserUtilizado;
    private Usuario usuarioComPessoaJaVinculada;
    private Usuario usuarioComUserNaoUtilizadoComPessoaNaoVinculada;
    private Usuario usuarioJaCadastrado;
    private Usuario usuarioComGrupos;
    private Usuario usuarioSemGrupo;
    private GrupoUsuario grupo;

    @BeforeEach
    public void setUp() {
        this.usuarioComUserUtilizado = Usuario.UsuarioBuilder.anUsuario().withUser("usuarioComUserUtilizado").withSenha("senha").withPessoa(new Pessoa(1L)).build();
        this.usuarioComPessoaJaVinculada = Usuario.UsuarioBuilder.anUsuario().withUser("usuarioComPessoaJaVinculada").withSenha("senha").withPessoa(new Pessoa(2L)).build();
        this.usuarioComUserNaoUtilizadoComPessoaNaoVinculada = Usuario.UsuarioBuilder.anUsuario().withUser("usuarioComUserNaoUtilizadoComPessoaNaoVinculada").withSenha("senha").withPessoa(new Pessoa(3L)).build();
        this.usuarioJaCadastrado = Usuario.UsuarioBuilder.anUsuario().withCodigo(1L).withSenha("senha").build();

        this.grupo = new GrupoUsuario(1L);
        List<GrupoUsuario> grupos = new ArrayList<>();
        grupos.add(this.grupo);
        GrupoUsuario grupo = new GrupoUsuario(2L);
        grupos.add(grupo);
        this.usuarioComGrupos = Usuario.UsuarioBuilder.anUsuario().withCodigo(2L).withGrupousuario(grupos).build();
        this.usuarioSemGrupo = Usuario.UsuarioBuilder.anUsuario().withCodigo(3L).withGrupousuario(new ArrayList<>()).build();

        when(this.usuarios.findByUserEquals("usuarioComUserUtilizado")).thenReturn(this.usuarioComUserUtilizado);
        when(this.usuarios.findByUserEquals("usuarioComPessoaJaVinculada")).thenReturn(null);
        when(this.usuarios.findByUserEquals("usuarioComUserNaoUtilizadoComPessoaNaoVinculada")).thenReturn(null);

        when(this.usuarios.findByCodigoIn(2L)).thenReturn(this.usuarioComGrupos);
        when(this.usuarios.findByCodigoIn(3L)).thenReturn(this.usuarioSemGrupo);

        when(this.grupos.buscaGrupo(1L)).thenReturn(this.grupo);
        when(this.grupos.buscaGrupo(2L)).thenReturn(grupo);

        when(this.grupos.buscaGrupos(this.usuarioComGrupos)).thenReturn(grupos);
        when(this.grupos.buscaGrupos(this.usuarioSemGrupo)).thenReturn(new ArrayList<>());

        when(this.usuarios.findByPessoaCodigoEquals(1L)).thenReturn(this.usuarioComUserUtilizado);
        when(this.usuarios.findByPessoaCodigoEquals(2L)).thenReturn(this.usuarioComPessoaJaVinculada);
        when(this.usuarios.findByPessoaCodigoEquals(3L)).thenReturn(null);
    }

    @DisplayName("Testes para cadastrar usuário")
    @Test
    public void cadastrar() {
        assertAll("cadastro de usuários",
            () -> assertEquals("Usuário já existe", this.usuarioService.cadastrar(this.usuarioComUserUtilizado)),
            () -> assertEquals("Pessoa já vinculada a outro usuário", this.usuarioService.cadastrar(this.usuarioComPessoaJaVinculada)),
            () -> assertEquals("Usuário salvo com sucesso", this.usuarioService.cadastrar(this.usuarioComUserNaoUtilizadoComPessoaNaoVinculada)),
            () -> assertEquals("Usuário atualizado com sucesso", this.usuarioService.cadastrar(this.usuarioJaCadastrado))
        );
    }

    @DisplayName("Testes para adicionar grupo ao usuário informado")
    @Test
    public void addGrupo() {
        assertAll("adicionar grupo ao usuário",
            () -> assertEquals("ja existe", this.usuarioService.addGrupo(this.usuarioComGrupos.getCodigo(), this.grupo.getCodigo())),
            () -> assertEquals("ok", this.usuarioService.addGrupo(this.usuarioSemGrupo.getCodigo(), this.grupo.getCodigo()))
        );
    }

    @DisplayName("Testes para remover grupo informado do usuário informado")
    @Test
    public void removerGrupo() {
        String msg = this.usuarioService.removeGrupo(this.usuarioComGrupos.getCodigo(), 2L);
        assertAll("remover grupo do usário",
            () -> assertEquals("ok", this.usuarioService.removeGrupo(this.usuarioSemGrupo.getCodigo(), this.grupo.getCodigo())),
            () -> assertEquals("ok", msg),
            // verifica se o grupo foi removido
            () -> assertEquals(this.grupo.getCodigo(), this.usuarioComGrupos.getGrupoUsuario().get(0).getCodigo()),
            () -> assertEquals(1, this.usuarioComGrupos.getGrupoUsuario().size())
        );
    }

    @AfterEach
    public void tearDown() {
    }
}