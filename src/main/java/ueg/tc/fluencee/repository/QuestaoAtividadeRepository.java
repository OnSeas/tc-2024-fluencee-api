package ueg.tc.fluencee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ueg.tc.fluencee.model.Atividade;
import ueg.tc.fluencee.model.Questao;
import ueg.tc.fluencee.model.QuestaoAtividade;

import java.util.List;

@Repository
public interface QuestaoAtividadeRepository extends JpaRepository<QuestaoAtividade, Long> {

    @Query("SELECT questaoAtividade FROM QuestaoAtividade questaoAtividade WHERE questaoAtividade.atividade.id = :idAtividade AND questaoAtividade.questao.ativado = true")
    List<QuestaoAtividade> findByAtividadeAndAtivado(@Param("idAtividade") Long idAtividade);

    @Query("SELECT SUM(questao.nota) FROM QuestaoAtividade questao WHERE questao.atividade.id = :idAtividade AND questao.questao.ativado = true")
    Double notaTotalAtividade(@Param("idAtividade") Long idAtividade);

    QuestaoAtividade findByAtividadeAndQuestao(Atividade atividade, Questao questao);
}
