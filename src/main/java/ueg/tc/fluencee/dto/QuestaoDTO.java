package ueg.tc.fluencee.dto;

import lombok.Data;
import ueg.tc.fluencee.model.Opcao;
import ueg.tc.fluencee.utils.Utils;

import java.util.List;

@Data
public class QuestaoDTO {
    private Long id;
    private String nome;
    private String enunciado;
    private String gabarito;
    private int tipo;
    private Boolean ativado;
    private UsuarioResponseDTO usuarioCriador;
    private Double nota;
//    private List<Opcao> opcoes;
    private boolean respondida;
}
