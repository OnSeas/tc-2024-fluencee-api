package ueg.tc.fluencee.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import ueg.tc.fluencee.dto.QuestaoDTO;
import ueg.tc.fluencee.dto.UsuarioResponseDTO;
import ueg.tc.fluencee.utils.Utils;

@NoArgsConstructor
@Entity
@Data
@Table(name="TBL_QUESTAO_ATIVIDADE")
public class QuestaoAtividade {
    @Id
    @SequenceGenerator(name = "questao_atividade_sequence", sequenceName = "questao_atividade_sequence", allocationSize = 1)
    @Column(name = "id_questao_atividade", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questao_atividade_sequence")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_atividade_id", nullable = false)
    private Atividade atividade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_questao_id", nullable = false)
    private Questao questao;

    @Column(name = "c_nota", nullable = false)
    private Double nota;

    public QuestaoDTO toQuestaoDTO() {
        ModelMapper mapper = new ModelMapper();

        QuestaoDTO questaoDTO = new QuestaoDTO();
        questaoDTO.setId(questao.getId());
        questaoDTO.setNome(questao.getNome());
        questaoDTO.setEnunciado(questao.getEnunciado());
        questaoDTO.setGabarito(questao.getGabarito());
        questaoDTO.setTipo(questao.getTipo());
        questaoDTO.setUsuarioCriador(mapper.map(questao.getUsuarioCriador(), UsuarioResponseDTO.class));
        questaoDTO.setAtivado(questao.getAtivado());
        questaoDTO.setNota(this.nota);
        questaoDTO.setRespondida(Utils.usuarioRespondeuQuestao(questao.getId()));

        return questaoDTO;
    }
}