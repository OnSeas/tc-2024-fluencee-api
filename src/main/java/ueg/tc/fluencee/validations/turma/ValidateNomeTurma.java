package ueg.tc.fluencee.validations.turma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ueg.tc.fluencee.configuration.security.TokenService;
import ueg.tc.fluencee.dto.TurmaRequestDTO;
import ueg.tc.fluencee.exception.BusinessException;
import ueg.tc.fluencee.exception.ErrorMessageCode;
import ueg.tc.fluencee.model.Usuario;
import ueg.tc.fluencee.repository.TurmaRepository;
import ueg.tc.fluencee.utils.Utils;

@Component
public class ValidateNomeTurma implements IValidarTurmaBeforeSave{

    @Autowired
    private TurmaRepository turmaRepository;

    @Override
    public void validate(TurmaRequestDTO turmaRequestDTO) {
        validarNome(turmaRequestDTO.getNome());

        Usuario usuario = Utils.getUsuarioLogado();

        if (turmaRepository.existsByNomeAndProfessor(turmaRequestDTO.getNome(), usuario)){
            throw new BusinessException(ErrorMessageCode.NOME_TURMA_EXISTE);
        }
    }

    @Override
    public void validate(TurmaRequestDTO turmaRequestDTO, Long id) {
        validarNome(turmaRequestDTO.getNome());

        Usuario usuario = Utils.getUsuarioLogado();

        if (turmaRepository.existsByNomeDiferent(turmaRequestDTO.getNome(), id, usuario.getId())){
            throw new BusinessException(ErrorMessageCode.NOME_TURMA_EXISTE);
        }
    }

    public static void validarNome(String nome){
        if(nome == null || nome.isBlank()){
            throw new BusinessException(ErrorMessageCode.CAMPO_OBRIGATORIO_VAZIO);
        } else if (nome.length() < 3 || nome.length() > 50) {
            throw new BusinessException(ErrorMessageCode.NOME_TURMA_INVALIDO);
        }
    }
}
