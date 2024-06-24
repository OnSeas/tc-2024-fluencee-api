package ueg.tc.fluencee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ueg.tc.fluencee.model.Questao;

@Repository
public interface QuestaoRepository extends JpaRepository<Questao, Long> {
    @Query("SELECT CASE WHEN COUNT(questao) > 0 THEN true ELSE false END FROM Questao questao WHERE questao.nome LIKE :nome AND questao.id <> :id AND questao.usuarioCriador.id = :idUsuario")
    boolean existsByNomeDiferent(@Param("nome") String nome, @Param("id") Long id, @Param("idUsuario") Long idUsuario);

    @Query("SELECT CASE WHEN COUNT(questao) > 0 THEN true ELSE false END FROM Questao questao WHERE questao.nome LIKE :nome AND questao.usuarioCriador.id = :idUsuario")
    boolean existsByNome(@Param("nome") String nome, @Param("idUsuario") Long idUsuario);
}
