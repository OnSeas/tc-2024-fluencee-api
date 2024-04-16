package ueg.tc.fluencee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ueg.tc.fluencee.configuration.security.TokenService;
import ueg.tc.fluencee.dto.LoginDTO;
import ueg.tc.fluencee.dto.TokenDTO;
import ueg.tc.fluencee.dto.UsuarioRequestDTO;
import ueg.tc.fluencee.exception.BusinessException;
import ueg.tc.fluencee.exception.ErrorMessageCode;
import ueg.tc.fluencee.model.Usuario;
import ueg.tc.fluencee.repository.UsuarioRepository;
import ueg.tc.fluencee.service.UsuarioService;
import ueg.tc.fluencee.validations.usuario.ValidateEmailUsuario;
import ueg.tc.fluencee.validations.usuario.ValidateSenhaUsuario;

@RestController
@RequestMapping("fluencee")
public class LoginController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
        ValidateEmailUsuario.validarEmail(loginDTO.getLogin());
        ValidateSenhaUsuario.validarSenha(loginDTO.getSenha());
        if (usuarioRepository.findByLogin(loginDTO.getLogin()) == null){
            throw new BusinessException(ErrorMessageCode.EMAIL_NAO_ENCONTRADO);
        } else {
            if(!usuarioRepository.findByLogin(loginDTO.getLogin()).isEnabled()){
                throw new BusinessException(ErrorMessageCode.USUARIO_INATIVADO);
            }
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.getLogin(), loginDTO.getSenha());
        var authentication = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((Usuario) authentication.getPrincipal());
        TokenDTO tokenDTO = new TokenDTO(token);
        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody UsuarioRequestDTO usuarioRequestDTO){
        return ResponseEntity.ok(usuarioService.inserir(usuarioRequestDTO));
    }
}
