package pe.com.upao.grupo3.petsnature.services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.com.upao.grupo3.petsnature.models.Publicacion;
import pe.com.upao.grupo3.petsnature.repositories.PublicacionRepository;
import pe.com.upao.grupo3.petsnature.serializers.PublicacionSerializer;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PublicacionServiceTest {
    @Mock
    private PublicacionRepository publicacionRepository;

    @Mock
    private Publicacion publicacion;

    @Mock
    private List<PublicacionSerializer> publicaciones;


    @InjectMocks
    private PublicacionService publicacionService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    //Escenario Exitoso de crear Publicacion
    @Test
    public void testCrearPublicacion(){
        when(publicacionRepository.save(publicacion)).thenReturn(publicacion);

        Publicacion result = publicacionService.crearPublicacion(publicacion);

        assertEquals(publicacion,result);
    }

    //Escenario Exitoso de filtrar por categoria
    @Test
    public void testFiltrarPorCategoria(){
        String categoria="Informacion";

        when(publicacionRepository.findAllByCategoria(categoria)).thenReturn(publicaciones);

        List<PublicacionSerializer> result = publicacionService.filtrarporCategoria(categoria);

        assertEquals(publicaciones,result);
    }



}
