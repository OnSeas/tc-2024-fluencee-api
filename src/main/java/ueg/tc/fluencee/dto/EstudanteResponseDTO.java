package ueg.tc.fluencee.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EstudanteResponseDTO {
    private Long id;
    private String nome;
    private UsuarioResponseDTO estudante;
    private TurmaReponseDTO turma;
    private Double nota;
}
