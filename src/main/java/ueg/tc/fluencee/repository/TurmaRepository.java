package ueg.tc.fluencee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ueg.tc.fluencee.model.Turma;

import java.util.List;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {
    boolean existsByCodigo(String codigo);

    Turma findByCodigo(String codigo);

    boolean existsByNome(String nome);

    @Query("SELECT CASE WHEN COUNT(turma) > 0 THEN true ELSE false END FROM Turma turma WHERE turma.nome LIKE :nome AND turma.id <> :id")
    boolean existsByNomeDiferent(@Param("nome") String nome, @Param("id") Long id);


    @Query("SELECT DISTINCT turma FROM Turma turma " +
            "LEFT JOIN Estudante estudante ON estudante.turma.id = turma.id " +
            "WHERE (turma.professor.id = :usuarioId OR (estudante.estudante.id = :usuarioId AND estudante.ativo = true)) " +
            "AND turma.ativado = true")
    List<Turma> getTurmasByUsuario(@Param("usuarioId") Long usuarioId);

    @Query("SELECT CASE WHEN COUNT(estudante) > 0 THEN true ELSE false END FROM Estudante estudante WHERE estudante.estudante.id = :usuarioId AND estudante.turma.id = :turmaId AND estudante.bloqueado = true")
    boolean isEstudanteBloqueado(@Param("usuarioId") Long usuarioId, @Param("turmaId") Long turmaId);

    @Query("SELECT CASE WHEN COUNT(estudante) > 0 THEN true ELSE false END FROM Estudante estudante WHERE estudante.estudante.id = :usuarioId AND estudante.turma.id = :turmaId")
    boolean estudanteExists(@Param("usuarioId") Long usuarioId, @Param("turmaId") Long turmaId);
}
