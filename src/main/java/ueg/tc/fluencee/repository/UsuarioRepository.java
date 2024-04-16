package ueg.tc.fluencee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import ueg.tc.fluencee.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(usuario) > 0 THEN true ELSE false END FROM Usuario usuario WHERE usuario.email LIKE :email AND usuario.id <> :id")
    boolean existsByEmailDiferent(@Param("email") String email, @Param("id") Long id);

    @Query("SELECT usuario FROM Usuario usuario WHERE usuario.email = :email")
    UserDetails findByLogin(String email);

    Usuario findByEmail(String email);
}
