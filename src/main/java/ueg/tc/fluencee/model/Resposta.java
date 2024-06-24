package ueg.tc.fluencee.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Data
@Table(name="TBL_RESPOSTA")
public class Resposta {
    @Id
    @SequenceGenerator(name = "resposta_sequence", sequenceName = "resposta_sequence", allocationSize = 1)
    @Column(name = "id_resposta", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resposta_sequence")
    private Long id;

    @Column(name = "c_resposta", length = 150, nullable = true)
    private String resposta;

    @ManyToOne
    @JoinColumn(name = "c_opcao", nullable = false)
    private Opcao opcao;

    @Column(name = "c_nota", nullable = true)
    private Double nota;

    @Column(name = "c_comentario", length = 100, nullable = true)
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "c_estudante_grupo", nullable = false)
    private EstudanteGrupo estudanteGrupo;
}
