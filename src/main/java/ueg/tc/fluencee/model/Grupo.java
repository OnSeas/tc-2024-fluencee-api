package ueg.tc.fluencee.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Entity
@Data
@Table(name="TBL_GRUPO")
public class Grupo {
    @Id
    @SequenceGenerator(name = "grupo_sequence", sequenceName = "grupo_sequence", allocationSize = 1)
    @Column(name = "id_grupo", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grupo_sequence")
    private Long id;

    @Column(name = "c_nota_atividade")
    private Double notaAtividade;

    @Column(name = "c_data_Conclusao")
    private Date dataConclusao;

    @ManyToOne
    @JoinColumn(name = "c_atividade_id", nullable = true)
    private Atividade atividade;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_grupo_id", nullable = true)
    private List<EstudanteGrupo> estudanteGrupo;
}
