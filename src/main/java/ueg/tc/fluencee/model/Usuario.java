package ueg.tc.fluencee.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;

@NoArgsConstructor
@Entity
@Data
@Table(name="TBL_USUARIO")
public class Usuario implements UserDetails {
    @Id
    @SequenceGenerator(name = "usuario_sequence", sequenceName = "usuario_sequence", allocationSize = 1)
    @Column(name = "id_usuario", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_sequence")
    private Long id;

    @Column(name = "c_nome", length = 200, nullable = false)
    private String nome;

    @Column(name = "c_email", length = 256, nullable = false, unique = true)
    private String email;

    @Column(name = "c_senha", length = 256, nullable = false)
    private String senha;

    @Column(name = "c_ativado", nullable = false)
    private Boolean ativado;

    // USER DETAILS
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.ativado;
    }
}
