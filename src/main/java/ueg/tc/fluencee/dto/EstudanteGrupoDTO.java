package ueg.tc.fluencee.dto;

import lombok.Data;

import java.util.List;

@Data
public class EstudanteGrupoDTO {
    UsuarioResponseDTO estudante;
    List<RespostaDTO> respostas;
}
