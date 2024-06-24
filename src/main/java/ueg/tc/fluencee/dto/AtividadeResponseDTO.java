package ueg.tc.fluencee.dto;

import lombok.Data;

import java.util.List;

@Data
public class AtividadeResponseDTO {
    private Long id;
    private TurmaReponseDTO turma;
    private String nome;
    private String enunciado;
    private int tipoAtividade;
    private double notaValor;
    private int status;
    private int quantidadeAlunos;
    private List<QuestaoDTO> questoes;
}
