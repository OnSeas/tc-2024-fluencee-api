package ueg.tc.fluencee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ueg.tc.fluencee.model.Turma;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {
    boolean existsByCodigo(String codigo);

    boolean existsByNome(String nome);

    @Query("SELECT CASE WHEN COUNT(turma) > 0 THEN true ELSE false END FROM Turma turma WHERE turma.nome LIKE :nome AND turma.id <> :id")
    boolean existsByNomeDiferent(@Param("nome") String nome, @Param("id") Long id);
}
