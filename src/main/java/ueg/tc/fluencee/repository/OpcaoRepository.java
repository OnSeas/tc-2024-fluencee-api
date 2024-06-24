package ueg.tc.fluencee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ueg.tc.fluencee.model.Opcao;
import ueg.tc.fluencee.model.Questao;

public interface OpcaoRepository extends JpaRepository<Opcao, Long> {
    Opcao findByQuestao(Questao questao);
}
