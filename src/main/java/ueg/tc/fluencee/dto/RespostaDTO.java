package ueg.tc.fluencee.dto;

import lombok.Data;

@Data
public class RespostaDTO {
    Long id;
    String resposta;
    Double nota;
    String nomeQuestao;
    String enunciadoQuestao;
}
