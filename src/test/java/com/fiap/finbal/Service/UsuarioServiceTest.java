package com.fiap.finbal.Service;

import com.fiap.finbal.Model.Usuario;
import com.fiap.finbal.Repository.UsuarioRepository;
import com.fiap.finbal.Service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioAna;
    private Usuario usuarioBruno;
    private Usuario usuarioCarla;

    @BeforeEach
    void setUp() {
        usuarioAna = new Usuario();
        usuarioAna.setId(1L);
        usuarioAna.setNome("Ana");
        usuarioAna.setSobrenome("Silva");
        usuarioAna.setEmail("ana.silva@teste.com");
        usuarioAna.setCpf("111.222.333-44");
        usuarioAna.setSenha("hash123");
        usuarioAna.setDataCriacao(LocalDate.of(2025, 10, 3));

        usuarioBruno = new Usuario();
        usuarioBruno.setId(2L);
        usuarioBruno.setNome("Bruno");
        usuarioBruno.setSobrenome("Costa");
        usuarioBruno.setEmail("bruno.costa@teste.com");

        usuarioCarla = new Usuario();
        usuarioCarla.setId(3L);
        usuarioCarla.setNome("Carla");
        usuarioCarla.setSobrenome("Mendes");
    }

    @Test
    @DisplayName("Deve criar e salvar um novo usuário com sucesso")
    void deveCriarUsuarioComSucesso() {
        when(repository.save(any(Usuario.class))).thenReturn(usuarioAna);

        Usuario usuarioCriado = usuarioService.criarUsuario(usuarioAna);

        assertNotNull(usuarioCriado, "O usuário criado não deve ser nulo");
        assertEquals("Ana", usuarioCriado.getNome(), "O nome do usuário deve corresponder ao nome fornecido");

        verify(repository, times(1)).save(usuarioAna);
    }

    @Test
    @DisplayName("Deve listar todos os 3 usuários existentes")
    void deveListarTodosOsUsuarios() {

        List<Usuario> listaUsuarios = Arrays.asList(usuarioAna, usuarioBruno, usuarioCarla);
        when(repository.findAll()).thenReturn(listaUsuarios);
        List<Usuario> usuariosEncontrados = usuarioService.listarUsuarios();
        assertNotNull(usuariosEncontrados, "A lista não deve ser nula");
        assertEquals(3, usuariosEncontrados.size(), "Devem ser encontrados 3 usuários");
        assertEquals("Bruno", usuariosEncontrados.get(1).getNome());

        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve encontrar um usuário ao buscar pelo ID")
    void deveEncontrarUsuarioPorId() {

        when(repository.findById(1L)).thenReturn(Optional.of(usuarioAna));
        Optional<Usuario> usuarioEncontrado = usuarioService.obterUsuarioPorId(1L);

        assertTrue(usuarioEncontrado.isPresent(), "O Optional deve conter o usuário");
        assertEquals(1L, usuarioEncontrado.get().getId());
        assertEquals("Ana", usuarioEncontrado.get().getNome());

        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve atualizar o sobrenome e email de um usuário existente")
    void deveAtualizarUsuario() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuarioAna));
        Usuario dadosAtualizados = new Usuario();
        dadosAtualizados.setNome("Ana Carolina");
        dadosAtualizados.setSobrenome("Oliveira");
        dadosAtualizados.setEmail("ana.oliveira@teste.com");
        when(repository.save(any(Usuario.class))).thenReturn(usuarioAna); // Simula o save da Ana ATUALIZADA
        Usuario resultado = usuarioService.atualizarUsuario(1L, dadosAtualizados);

        assertNotNull(resultado, "O usuário retornado não deve ser nulo");
        assertEquals("Ana Carolina", resultado.getNome(), "O nome deve ser atualizado");
        assertEquals("Oliveira", resultado.getSobrenome(), "O sobrenome deve ser atualizado");
        assertEquals("ana.oliveira@teste.com", resultado.getEmail(), "O email deve ser atualizado");

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(usuarioAna);
    }

    @Test
    @DisplayName("Deve lançar RuntimeException ao tentar atualizar um usuário que não existe")
    void naoDeveAtualizarUsuarioInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            usuarioService.atualizarUsuario(99L, new Usuario());
        }, "Deve lançar RuntimeException quando o usuário não for encontrado");
        verify(repository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve deletar um usuário pelo ID se ele existir")
    void deveDeletarUsuarioPorId() {
        Long idParaDeletar = 2L;
        when(repository.findById(idParaDeletar)).thenReturn(Optional.of(usuarioBruno));

        usuarioService.deletarUsuario(idParaDeletar);

        verify(repository, times(1)).deleteById(idParaDeletar);
    }
}