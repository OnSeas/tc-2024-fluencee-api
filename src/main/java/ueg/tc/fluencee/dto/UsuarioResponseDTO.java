package ueg.tc.fluencee.dto;

import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long idUsuario;

    private String nome;

    private String email;

    private Boolean ativado;
}
