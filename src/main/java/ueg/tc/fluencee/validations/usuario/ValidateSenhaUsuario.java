package ueg.tc.fluencee.validations.usuario;

import org.springframework.stereotype.Component;
import ueg.tc.fluencee.dto.UsuarioRequestDTO;
import ueg.tc.fluencee.exception.BusinessException;
import ueg.tc.fluencee.exception.ErrorMessageCode;

@Component
public class ValidateSenhaUsuario implements ValidateUsuarioBeforeSave{
    @Override
    public void validate(UsuarioRequestDTO usuarioRequestDTO) {

    }

    @Override
    public void validate(UsuarioRequestDTO usuarioRequestDTO, Long id) {

    }

    public static void validarSenha(String senha){
        if(senha == null || senha.isBlank()){
            throw new BusinessException(ErrorMessageCode.CAMPO_OBRIGATORIO_VAZIO);
        } else if (senha.length() < 8 || senha.length() > 35) {
            throw new BusinessException(ErrorMessageCode.COMPRIMENTO_SENHA);
        } else if (!senha.matches("^(?=.*[a-zA-Z])(?=.*\\d).*$")) {
            throw new BusinessException(ErrorMessageCode.SENHA_LETRA_NUMERO);
        }
    }
}
