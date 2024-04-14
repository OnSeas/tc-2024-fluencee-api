package ueg.tc.fluencee.validations.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ueg.tc.fluencee.dto.UsuarioRequestDTO;
import ueg.tc.fluencee.exception.BusinessException;
import ueg.tc.fluencee.exception.ErrorMessageCode;
import ueg.tc.fluencee.repository.UsuarioRepository;

@Component
public class ValidateEmailUsuario implements ValidateUsuarioBeforeSave{
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validate(UsuarioRequestDTO usuarioRequestDTO) {
        validarEmail(usuarioRequestDTO.getEmail());
        if(usuarioRepository.existsByEmail(usuarioRequestDTO.getEmail())){
            throw new BusinessException(ErrorMessageCode.EMAIL_REPETIDO);
        }
    }

    @Override
    public void validate(UsuarioRequestDTO usuarioRequestDTO, Long id) {
        validarEmail(usuarioRequestDTO.getEmail());
        if(usuarioRepository.existsByEmailDiferent(usuarioRequestDTO.getEmail(), id)){
            throw new BusinessException(ErrorMessageCode.EMAIL_REPETIDO);
        }
    }

    public static void validarEmail(String email){
        if(email == null || email.isBlank()){
            throw new BusinessException(ErrorMessageCode.CAMPO_OBRIGATORIO_VAZIO);
        } else if (email.length() > 256){
            throw new BusinessException(ErrorMessageCode.COMPRIMENTO_EMAIL_INVALIDO);
        } else if (!email.contains("@") || !email.contains(".") || email.indexOf("@") > email.indexOf(".")) {
            throw new BusinessException(ErrorMessageCode.EMAIL_INVALIDO);
        }
    }
}
