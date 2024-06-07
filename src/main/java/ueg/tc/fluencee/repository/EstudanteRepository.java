package ueg.tc.fluencee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ueg.tc.fluencee.model.Estudante;
import ueg.tc.fluencee.model.Turma;
import ueg.tc.fluencee.model.Usuario;

import java.util.List;

@Repository
public interface EstudanteRepository extends JpaRepository<Estudante, Long> {
    List<Estudante> findByTurmaAndAtivo(Turma turma, Boolean ativo);

    Estudante getEstudanteByTurmaAndEstudante(Turma turma, Usuario usuario);
}
