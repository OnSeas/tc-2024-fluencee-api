package ueg.tc.fluencee.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Data
@Table(name="TBL_OPCAO")
public class Opcao {
    @Id
    @SequenceGenerator(name = "opcao_sequence", sequenceName = "opcao_sequence", allocationSize = 1)
    @Column(name = "id_opcao", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "opcao_sequence")
    private Long id;

    @Column(name = "c_texto_opcao", length = 150, nullable = true)
    private String textoOpcao;

    @Column(name = "c_opcao_correta", nullable = false)
    private Boolean opcaoCorreta;

    @Column(name = "c_ativado", nullable = false)
    private Boolean ativado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "c_questao_id", nullable = false)
    private Questao questao;
}
