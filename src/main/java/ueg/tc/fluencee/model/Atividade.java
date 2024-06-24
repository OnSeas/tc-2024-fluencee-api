package ueg.tc.fluencee.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Entity
@Data
@Table(name="TBL_ATIVIDADE")
public class Atividade {

    // Tipo de atividade
    public static final int INDIVIDUAL = 1;
    public static final int DUPLA = 2;
    public static final int GRUPO = 3;

    // Status atividade
    public static final int CRIADA = 1;
    public static final int INICIADA = 2;
    public static final int FINALIZADA = 3;
    public static final int CORRIGIDA = 4;

    @Id
    @SequenceGenerator(name = "atividade_sequence", sequenceName = "atividade_sequence", allocationSize = 1)
    @Column(name = "id_atividade", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "atividade_sequence")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_turma_id", nullable = false)
    private Turma turma;

    @Column(name = "c_nome", length = 40, nullable = false)
    private String nome;

    @Column(name = "c_enunciado", length = 500, nullable = true)
    private String enunciado;

    @Column(name = "c_ativado", nullable = false)
    private Boolean ativado;

    @Column(name = "c_tipo_atividade", nullable = false)
    private int tipoAtividade;

    @Column(name = "c_nota_valor", nullable = false)
    private double notaValor;

    @Column(name = "c_quantidade_alunos", nullable = true)
    private int quantidadeAlunos;

    @Column(name = "c_status", nullable = false)
    private int status;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_atividade_id", nullable = true)
    private List<QuestaoAtividade> questoes;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_atividade_id", nullable = true)
    private List<Grupo> grupos;
}
