package ueg.tc.fluencee.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlterarSenhaDTO {

    private String email;

    private String senhaAntiga;

    private String senhaNova;
}
