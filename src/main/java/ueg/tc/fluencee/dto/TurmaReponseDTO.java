package ueg.tc.fluencee.dto;

import lombok.Data;

@Data
public class TurmaReponseDTO {
    private Long id;
    private String nome;
    private String ano;
    private String sala;
    private String codigo;
    private boolean ativado;
    private boolean eProfessor;
    private String professorNome;
}
