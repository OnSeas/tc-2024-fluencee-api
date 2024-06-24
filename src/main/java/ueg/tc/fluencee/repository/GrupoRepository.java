package ueg.tc.fluencee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ueg.tc.fluencee.model.Atividade;
import ueg.tc.fluencee.model.Grupo;

import java.util.List;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    List<Grupo> findByAtividade(Atividade atividade);
}
