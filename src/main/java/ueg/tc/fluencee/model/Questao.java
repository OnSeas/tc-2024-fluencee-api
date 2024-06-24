package ueg.tc.fluencee.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Entity
@Data
@Table(name="TBL_QUESTAO")
public class Questao {
    @Id
    @SequenceGenerator(name = "questao_sequence", sequenceName = "questao_sequence", allocationSize = 1)
    @Column(name = "id_questao", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questao_sequence")
    private Long id;

    // Tipo de questao
    static final int DISSERTATIVA = 1;
    static final int MULTIPLA_ESCOLHA = 2;
    static final int PRONUNCIA = 3;

    @Column(name = "c_nome", length = 40, nullable = false)
    private String nome;

    @Column(name = "c_enunciado", length = 100, nullable = false)
    private String enunciado;

    @Column(name = "c_gabarito", length = 200, nullable = true)
    private String gabarito;

    @Column(name = "c_tipo", nullable = false)
    private int tipo;

    @Column(name = "c_ativado", nullable = false)
    private Boolean ativado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_usuario_criador_id", nullable = false)
    private Usuario usuarioCriador;

    @OneToMany
    @JoinColumn(name = "c_questao_id")
    private List<Opcao> opcoes;
}
