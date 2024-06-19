package Grupo2.PetWellness.entitys.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import Grupo2.PetWellness.entitys.DTOs.UsuarioDTO;
import Grupo2.PetWellness.entitys.models.Usuario;

@Component
public class UsuarioMapper {

    private final ModelMapper modelMapper;

    public UsuarioMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Usuario convertToEntity(UsuarioDTO usuarioDTO) {
        return modelMapper.map(usuarioDTO, Usuario.class);
    }

    public UsuarioDTO convertToDto(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }
}
