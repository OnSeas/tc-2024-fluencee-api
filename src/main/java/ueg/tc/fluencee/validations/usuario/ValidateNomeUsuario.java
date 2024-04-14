package ueg.tc.fluencee.validations.usuario;

import org.springframework.stereotype.Component;
import ueg.tc.fluencee.dto.UsuarioRequestDTO;
import ueg.tc.fluencee.exception.BusinessException;
import ueg.tc.fluencee.exception.ErrorMessageCode;

@Component
public class ValidateNomeUsuario implements ValidateUsuarioBeforeSave{
    @Override
    public void validate(UsuarioRequestDTO usuarioRequestDTO) {
        validarNome(usuarioRequestDTO.getNome());
    }

    @Override
    public void validate(UsuarioRequestDTO usuarioRequestDTO, Long id) {
        validarNome(usuarioRequestDTO.getNome());
    }

    public static void validarNome(String nome){
        if(nome == null || nome.isBlank()){
            throw new BusinessException(ErrorMessageCode.CAMPO_OBRIGATORIO_VAZIO);
        } else if (nome.length() < 3 || nome.length() > 200) {
            throw new BusinessException(ErrorMessageCode.USUARIO_NOME_INVALIDO);
        }
    }

}
