package ueg.tc.fluencee.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Data
@Table(name="TBL_USUARIO")
public class Usuario {
    @Id
    @SequenceGenerator(name = "musica_sequence", sequenceName = "musica_sequence", allocationSize = 1)
    @Column(name = "id_usuario", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "musica_sequence")
    private Long idUsuario;

    @Column(name = "c_nome", length = 200, nullable = false)
    private String nome;

    @Column(name = "c_email", length = 256, nullable = false, unique = true)
    private String email;

    @Column(name = "c_senha", length = 256, nullable = false)
    private String senha; // Eu preciso criar no banco como varbinary? ou eu coloco aqui? to meio perdido nessa parte

//    @PrePersist
//    private void hashPassword() {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        setSenha(bCryptPasswordEncoder.encode(this.senha));
//    }

    @Column(name = "c_ativado", nullable = false)
    private Boolean ativado;
}
