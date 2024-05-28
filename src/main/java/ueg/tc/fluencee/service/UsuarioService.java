package ueg.tc.fluencee.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ueg.tc.fluencee.configuration.security.TokenService;
import ueg.tc.fluencee.dto.AlterarSenhaDTO;
import ueg.tc.fluencee.dto.UsuarioRequestDTO;
import ueg.tc.fluencee.dto.UsuarioResponseDTO;
import ueg.tc.fluencee.exception.BusinessException;
import ueg.tc.fluencee.exception.ErrorMessageCode;
import ueg.tc.fluencee.model.Usuario;
import ueg.tc.fluencee.repository.UsuarioRepository;
import ueg.tc.fluencee.validations.usuario.ValidateNomeUsuario;
import ueg.tc.fluencee.validations.usuario.ValidateSenhaUsuario;
import ueg.tc.fluencee.validations.usuario.ValidateUsuarioBeforeSave;

import java.util.List;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    private List<ValidateUsuarioBeforeSave> validateUsuarioBeforeSave;

//    public List<UsuarioResponseDTO> listarTudo() {
//        List<Usuario> usuarioList = usuarioRepository.findAll();
//        return usuarioList
//                .stream()
//                .map(m -> mapper.map(m, UsuarioResponseDTO.class))
//                .collect(Collectors.toList());
//    }

    public UsuarioResponseDTO inserir(UsuarioRequestDTO usuarioRequestDTO) {
        // Validar antes de salvar
        validateUsuarioBeforeSave.forEach(v -> v.validate(usuarioRequestDTO));

        // Inicialização de Usuário
        Usuario usuario = mapper.map(usuarioRequestDTO, Usuario.class);
        usuario.setAtivado(Boolean.TRUE);

        usuario.setSenha(new BCryptPasswordEncoder().encode(usuarioRequestDTO.getSenha()));
        usuario = usuarioRepository.save(usuario);

        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    public UsuarioResponseDTO getUsuarioById(){
        UsuarioResponseDTO usuarioResponseDTO;
        usuarioResponseDTO = mapper.map(tokenService.getUsuarioLogado(), UsuarioResponseDTO.class);

        if(usuarioResponseDTO.getAtivado() == Boolean.FALSE){
            throw new BusinessException(ErrorMessageCode.USUARIO_INATIVADO);
        }

        if(usuarioResponseDTO == null){
            throw new BusinessException(ErrorMessageCode.USUARIO_NAO_ENCONTRADO);
        }

        return usuarioResponseDTO;
    }

    public UsuarioResponseDTO alterarNome(UsuarioRequestDTO usuarioRequestDTO){
        Usuario usuarioBD = tokenService.getUsuarioLogado();

        if(usuarioBD.getAtivado() == Boolean.FALSE){
            throw new BusinessException(ErrorMessageCode.USUARIO_INATIVADO);
        }

        ValidateNomeUsuario.validarNome(usuarioRequestDTO.getNome());
        usuarioBD.setNome(usuarioRequestDTO.getNome());
        usuarioRepository.save(usuarioBD);

        return mapper.map(usuarioBD, UsuarioResponseDTO.class);
    }

    public UsuarioResponseDTO trocarSenha(AlterarSenhaDTO alterarSenhaDTO){
        Usuario usuarioBD = tokenService.getUsuarioLogado();

        if(usuarioBD.getAtivado() == Boolean.FALSE){
            throw new BusinessException(ErrorMessageCode.USUARIO_INATIVADO);
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if(!bCryptPasswordEncoder.matches(alterarSenhaDTO.getSenhaAntiga(), usuarioBD.getSenha())) {
            throw new BusinessException(ErrorMessageCode.SENHA_ERRADA);
        }

        ValidateSenhaUsuario.validarSenha(alterarSenhaDTO.getSenhaNova());
        String novaSenha = bCryptPasswordEncoder.encode(alterarSenhaDTO.getSenhaNova());
        usuarioBD.setSenha(novaSenha);
        usuarioRepository.save(usuarioBD);

        return mapper.map(usuarioBD, UsuarioResponseDTO.class);
    }

    public UsuarioResponseDTO desativar(){
        Usuario usuario = tokenService.getUsuarioLogado();

        if (usuario != null){
            if(usuario.getAtivado() == Boolean.FALSE){
                throw new BusinessException(ErrorMessageCode.USUARIO_INATIVADO);
            } else {
                usuario.setAtivado(Boolean.FALSE);
                // Limpando os dados do usuário
                usuario.setEmail(usuario.getId().toString());
                usuario.setNome(usuario.getId().toString());
                usuarioRepository.save(usuario);
            }
        } else throw new BusinessException(ErrorMessageCode.USUARIO_NAO_ENCONTRADO);

        return mapper.map(usuario, UsuarioResponseDTO.class);
    }
}
