package ueg.tc.fluencee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ueg.tc.fluencee.model.Atividade;
import ueg.tc.fluencee.model.Turma;

import java.util.List;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    @Query("SELECT CASE WHEN COUNT(atividade) > 0 THEN true ELSE false END FROM Atividade atividade WHERE atividade.nome LIKE :nome AND atividade.id <> :id AND atividade.turma.professor.id = :idUsuario")
    boolean existsByNomeDiferent(@Param("nome") String nome, @Param("id") Long id, @Param("idUsuario") Long idUsuario);

    @Query("SELECT CASE WHEN COUNT(atividade) > 0 THEN true ELSE false END FROM Atividade atividade WHERE atividade.nome LIKE :nome AND atividade.turma.professor.id = :idUsuario")
    boolean existsByNome(@Param("nome") String nome, @Param("idUsuario") Long idUsuario);

    List<Atividade> findByTurmaAndAtivadoOrderByNome(Turma turma, Boolean ativado);
}
