package ueg.tc.fluencee.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Data
@Table(name="TBL_ESTUDANTE")
public class Estudante {
    @Id
    @SequenceGenerator(name = "estudante_sequence", sequenceName = "estudante_sequence", allocationSize = 1)
    @Column(name = "id_estudante", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estudante_sequence")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_estudante_id", nullable = false)
    private Usuario estudante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_turma_id", nullable = false)
    private Turma turma;

    @Column(name = "c_bloqueado", nullable = false)
    private boolean bloqueado;

    @Column(name = "c_ativo", nullable = false)
    private boolean ativo;

    public String getNome() {
        return estudante.getNome();
    }
}
