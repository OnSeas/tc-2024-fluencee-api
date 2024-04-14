package ueg.tc.fluencee.validations.usuario;

import ueg.tc.fluencee.dto.UsuarioRequestDTO;


public interface ValidateUsuarioBeforeSave {
    public void validate(UsuarioRequestDTO usuarioRequestDTO);

    public void validate(UsuarioRequestDTO usuarioRequestDTO, Long id);
}
