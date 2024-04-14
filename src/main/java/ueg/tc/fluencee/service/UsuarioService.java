package ueg.tc.fluencee.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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
import java.util.Optional;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
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
        usuario = usuarioRepository.save(usuario);

        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    public UsuarioResponseDTO getUsuarioById(Long id){
        UsuarioResponseDTO usuarioResponseDTO;
        usuarioResponseDTO = mapper.map(usuarioRepository.getReferenceById(id), UsuarioResponseDTO.class);

        if(usuarioResponseDTO.getAtivado() == Boolean.FALSE){
            throw new BusinessException(ErrorMessageCode.USUARIO_INATIVADO);
        }

        if(usuarioResponseDTO == null){
            throw new BusinessException(ErrorMessageCode.USUARIO_NAO_ENCONTRADO);
        }

        return usuarioResponseDTO;
    }

    public UsuarioResponseDTO alterarNome(Long id, UsuarioRequestDTO usuarioRequestDTO){
        Usuario usuarioBD = usuarioRepository.getReferenceById(id);

        if(usuarioBD.getAtivado() == Boolean.FALSE){
            throw new BusinessException(ErrorMessageCode.USUARIO_INATIVADO);
        }

        ValidateNomeUsuario.validarNome(usuarioRequestDTO.getNome());
        usuarioBD.setNome(usuarioRequestDTO.getNome());
        usuarioRepository.save(usuarioBD);

        return mapper.map(usuarioBD, UsuarioResponseDTO.class);
    }

    public UsuarioResponseDTO trocarSenha(Long id, AlterarSenhaDTO alterarSenhaDTO){
        Usuario usuarioBD = usuarioRepository.getReferenceById(id);

        if(usuarioBD.getAtivado() == Boolean.FALSE){
            throw new BusinessException(ErrorMessageCode.USUARIO_INATIVADO);
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (!alterarSenhaDTO.getEmail().equals(usuarioBD.getEmail())){
            throw new BusinessException(ErrorMessageCode.EMAIL_ERRADO);
        } else if(!bCryptPasswordEncoder.matches(alterarSenhaDTO.getSenhaAntiga(), usuarioBD.getSenha())) { // TODO arrumar senha SHA-256
            throw new BusinessException(ErrorMessageCode.SENHA_ERRADA);
        }

        ValidateSenhaUsuario.validarSenha(alterarSenhaDTO.getSenhaNova());
        String novaSenha = bCryptPasswordEncoder.encode(alterarSenhaDTO.getSenhaNova()); // TODO arrumar senha SHA-256
        usuarioBD.setSenha(novaSenha);
        usuarioRepository.save(usuarioBD);

        return mapper.map(usuarioBD, UsuarioResponseDTO.class);
    }

    public UsuarioResponseDTO desativar(Long id){
        Optional<Usuario> usuarioBD = usuarioRepository.findById(id);

        if (usuarioBD.isPresent()){
            Usuario usuario = usuarioBD.get();
            if(usuario.getAtivado() == Boolean.FALSE){
                throw new BusinessException(ErrorMessageCode.USUARIO_INATIVADO);
            } else {
                usuario.setAtivado(Boolean.FALSE);
                usuarioRepository.save(usuario);
            }
        } else throw new BusinessException(ErrorMessageCode.USUARIO_NAO_ENCONTRADO);

        return mapper.map(usuarioBD, UsuarioResponseDTO.class);
    }
}
