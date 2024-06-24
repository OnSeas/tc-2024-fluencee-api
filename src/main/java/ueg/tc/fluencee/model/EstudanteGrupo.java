package ueg.tc.fluencee.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Data
@Table(name="TBL_ESTUDANTE_GRUPO")
public class EstudanteGrupo {
    @Id
    @SequenceGenerator(name = "estudante_grupo_sequence", sequenceName = "estudante_grupo_sequence", allocationSize = 1)
    @Column(name = "id_estudante_grupo", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estudante_grupo_sequence")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_estudante_id", nullable = false)
    private Estudante estudante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_grupo_id", nullable = false)
    private Grupo grupo;

    @Column(name = "id_tutor", nullable = false)
    private Boolean tutor;

    @Column(name = "id_pronto", nullable = false)
    private Boolean pronto;
}
