package ueg.tc.fluencee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ueg.tc.fluencee.model.*;

import java.util.List;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {

    @Query("SELECT CASE WHEN COUNT(resposta) > 0 THEN true ELSE false END FROM Resposta resposta WHERE resposta.opcao.questao.id = :idQuestao AND resposta.estudanteGrupo.estudante.estudante.id = :idUsuario")
    boolean findByUsuarioAndQuestaoAtividade(Long idUsuario, Long idQuestao);

    @Query("SELECT resposta FROM Resposta resposta LEFT JOIN QuestaoAtividade questaoAtividade ON resposta.opcao.questao.id = questaoAtividade.questao.id WHERE questaoAtividade.atividade.id = :idAtividade")
    List<Resposta> findByAtividade(Long idAtividade);
}
