package pe.com.upao.grupo3.petsnature.services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.com.upao.grupo3.petsnature.exceptions.ContrasenaIncorrectaException;
import pe.com.upao.grupo3.petsnature.exceptions.ContrasenaNoValidaException;
import pe.com.upao.grupo3.petsnature.exceptions.UsuarioNoExisteException;
import pe.com.upao.grupo3.petsnature.exceptions.UsuarioYaExisteException;
import pe.com.upao.grupo3.petsnature.models.Usuario;
import pe.com.upao.grupo3.petsnature.repositories.UsuarioRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private Usuario usuario;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    //Escenario Exitoso de registro de usuario
    @Test
    public void testregistrarUsuario(){
        String correoUsuario="usuario@gmail.com";

        when(usuarioRepository.findByCorreo(correoUsuario)).thenReturn(Optional.empty());
        when(usuario.validarContrasena()).thenReturn(true);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario result= usuarioService.registrarUsuario(usuario);

        assertEquals(usuario,result);
    }

    //Escenario Alternativo 1 de registro de usuario: Correo ya en Uso
    @Test
    public void testregistrarUsuarioA1(){
        String correoUsuario="usuario@gmail.com";

        when(usuarioRepository.findByCorreo(correoUsuario)).thenReturn(Optional.of(usuario));
        when(Optional.of(usuario).get().getCorreo()).thenReturn(correoUsuario);

        assertThrows(UsuarioYaExisteException.class, ()->{
            usuarioService.registrarUsuario(usuario);
        });
    }

    //Escenario Alternativo 2 de registro de usuario: Contraseña no valida
    @Test
    public void testregistrarUsuarioA2(){
        String correoUsuario="usuario@gmail.com";

        when(usuarioRepository.findByCorreo(correoUsuario)).thenReturn(Optional.empty());
        when(usuario.validarContrasena()).thenReturn(false);
        assertThrows(ContrasenaNoValidaException.class, ()->{
            usuarioService.registrarUsuario(usuario);
        });
    }

    //Escenario Exitoso de inicio de sesion
    @Test
    public void testIniciarSesion(){
        String correoUsuario="usuario@gmail.com";
        String contrasenaUsuario="12345";

        when(usuarioRepository.findByCorreo(correoUsuario)).thenReturn(Optional.of(usuario));
        when(usuario.getContrasena()).thenReturn(contrasenaUsuario);

        Usuario result = usuarioService.iniciarSesion(correoUsuario,contrasenaUsuario);
        assertEquals(usuario,result);
    }

    //Escenario Alternativo 1 de inicio de sesion: Credenciales Incorrectas (Correo no valido)
    @Test
    public void testIniciarSesionA1E(){
        String correoUsuario="usuario@gmail.com";
        String contrasenaUsuario="12345";

        when(usuarioRepository.findByCorreo(correoUsuario)).thenReturn(Optional.empty());

        assertThrows(UsuarioNoExisteException.class, ()->{
            usuarioService.iniciarSesion(correoUsuario,contrasenaUsuario);
        });
    }

    //Escenario Alternativo 2 de inicio de sesion: Credenciales Incorrectas (Contraseña no valido)
    @Test
    public void testIniciarSesionA1C(){
        String correoUsuario="usuario@gmail.com";
        String contrasenaUsuario="12345";
        String contrasenaIngresada="12346";

        when(usuarioRepository.findByCorreo(correoUsuario)).thenReturn(Optional.of(usuario));
        when(usuario.getContrasena()).thenReturn(contrasenaUsuario);

        assertThrows(ContrasenaIncorrectaException.class, ()->{
            usuarioService.iniciarSesion(correoUsuario,contrasenaIngresada);
        });
    }

}
