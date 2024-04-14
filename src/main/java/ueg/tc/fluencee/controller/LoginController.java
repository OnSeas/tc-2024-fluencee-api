package ueg.tc.fluencee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ueg.tc.fluencee.dto.LoginDTO;
import ueg.tc.fluencee.exception.BusinessException;
import ueg.tc.fluencee.exception.ErrorMessageCode;
import ueg.tc.fluencee.repository.UsuarioRepository;
import ueg.tc.fluencee.validations.usuario.ValidateEmailUsuario;
import ueg.tc.fluencee.validations.usuario.ValidateSenhaUsuario;

@RestController
@RequestMapping("fluencee/")
public class LoginController {
    @Autowired
    private UsuarioRepository usuarioRepository;

//    @PostMapping("/login")
//    public String login(@RequestBody LoginDTO loginDTO){
//        ValidateEmailUsuario.validarEmail(loginDTO.getLogin());
//        ValidateSenhaUsuario.validarSenha(loginDTO.getSenha());
//        if (usuarioRepository.findByLogin(loginDTO.getLogin()) == null){
//            throw new BusinessException(ErrorMessageCode.EMAIL_NAO_ENCONTRADO);
//        }
//
//        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.getLogin(), loginDTO.getSenha());
//        // TODO autenticar
//    }
}
