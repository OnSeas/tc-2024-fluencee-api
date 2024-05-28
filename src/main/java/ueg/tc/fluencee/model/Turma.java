package ueg.tc.fluencee.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Data
@Table(name="TBL_TURMA")
public class Turma {
    @Id
    @SequenceGenerator(name = "turma_sequence", sequenceName = "turma_sequence", allocationSize = 1)
    @Column(name = "id_turma", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "turma_sequence")
    private Long id;

    // NOME da turma
    @Column(name = "c_nome", length = 50, nullable = false)
    private String nome;

    // SALA da turma
    @Column(name = "c_sala", length = 40, nullable = true)
    private String sala;

    // ANO da turma
    @Column(name = "c_ano", length = 40, nullable = true)
    private String ano;

    @Column(name = "c_ativado", nullable = false)
    private Boolean ativado;

    // CODIGO usado para entrar na turma
    @Column(name = "c_codigo", length = 15, nullable = false, unique = true)
    private String codigo;

    // CRIADOR da turma
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_professor_id", nullable = false)
    private Usuario professor;
}
