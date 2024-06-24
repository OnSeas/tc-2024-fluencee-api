package ueg.tc.fluencee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ueg.tc.fluencee.model.EstudanteGrupo;

public interface EstudanteGrupoRepository extends JpaRepository<EstudanteGrupo, Long> {

    @Query("SELECT estudanteGrupo FROM EstudanteGrupo estudanteGrupo WHERE estudanteGrupo.estudante.estudante.id = :usuario AND estudanteGrupo.grupo.atividade.id = :atividade")
    EstudanteGrupo findByUsuarioAndAtividade(Long usuario, Long atividade);
}
