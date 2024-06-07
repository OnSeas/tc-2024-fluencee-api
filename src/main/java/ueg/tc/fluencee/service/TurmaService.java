package ueg.tc.fluencee.service;

import org.aspectj.weaver.StandardAnnotation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ueg.tc.fluencee.configuration.security.TokenService;
import ueg.tc.fluencee.dto.TurmaReponseDTO;
import ueg.tc.fluencee.dto.TurmaRequestDTO;
import ueg.tc.fluencee.dto.UsuarioRequestDTO;
import ueg.tc.fluencee.dto.UsuarioResponseDTO;
import ueg.tc.fluencee.exception.BusinessException;
import ueg.tc.fluencee.exception.ErrorMessageCode;
import ueg.tc.fluencee.model.Estudante;
import ueg.tc.fluencee.model.Turma;
import ueg.tc.fluencee.model.Usuario;
import ueg.tc.fluencee.repository.EstudanteRepository;
import ueg.tc.fluencee.repository.TurmaRepository;
import ueg.tc.fluencee.utils.Utils;
import ueg.tc.fluencee.validations.turma.IValidarTurmaBeforeSave;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private List<IValidarTurmaBeforeSave> validacoesTurma;

    private final ModelMapper mapper = new ModelMapper();


    public TurmaReponseDTO criarTurma(TurmaRequestDTO turmaRequestDTO) {
        Usuario usuarioLogado = tokenService.getUsuarioLogado();

        // Validar antes de criar
        validacoesTurma.forEach(validacaoTurma -> validacaoTurma.validate(turmaRequestDTO));

        // Inicialização de Turma
        Turma turma = mapper.map(turmaRequestDTO, Turma.class);
        turma.setAtivado(Boolean.TRUE);
        turma.setCodigo(gerarCodigoTurma());
        turma.setProfessor(usuarioLogado);

        return mapper.map(turmaRepository.save(turma), TurmaReponseDTO.class);
    }

    public TurmaReponseDTO alterarTurma(TurmaRequestDTO turmaRequestDTO, Long idTurma) {
        Usuario usuarioLogado = tokenService.getUsuarioLogado();

        // Validações para alterar turma
        if (!turmaRepository.existsById(idTurma)){
            throw new BusinessException(ErrorMessageCode.TURMA_NAO_ENCONTRADA);
        }

        Turma turmaBD = turmaRepository.getReferenceById(idTurma);

        if (!Objects.equals(usuarioLogado.getId(), turmaBD.getProfessor().getId())){
            throw new BusinessException(ErrorMessageCode.PERMISSAO_NEGADA);
        }

        validacoesTurma.forEach(validacao -> validacao.validate(turmaRequestDTO, idTurma));

        Turma turmaAlterar = mapper.map(turmaRequestDTO, Turma.class);

        // Alterações turma
        if (turmaAlterar.getNome() != null){
            turmaBD.setNome(turmaAlterar.getNome());
        }
        if (turmaAlterar.getAno() != null){
            turmaBD.setAno(turmaAlterar.getAno());
        }
        if(turmaAlterar.getSala() != null){
            turmaBD.setSala(turmaAlterar.getSala());
        }

        return mapper.map(turmaRepository.save(turmaBD), TurmaReponseDTO.class);
    }

    public TurmaReponseDTO excluirTurma(Long idTurma) {
        Usuario usuarioLogado = tokenService.getUsuarioLogado();

        // Validações para alterar turma
        if (!turmaRepository.existsById(idTurma)){
            throw new BusinessException(ErrorMessageCode.TURMA_NAO_ENCONTRADA);
        }

        Turma turmaBD = turmaRepository.getReferenceById(idTurma);

        if (!Objects.equals(usuarioLogado.getId(), turmaBD.getProfessor().getId())){
            throw new BusinessException(ErrorMessageCode.PERMISSAO_NEGADA);
        }

        if (turmaBD.getAtivado().equals(Boolean.FALSE)){
            throw new BusinessException(ErrorMessageCode.TURMA_EXCLUIDA);
        }

        turmaBD.setAtivado(Boolean.FALSE);
        turmaBD.setNome(turmaBD.getNome().concat(turmaBD.getId().toString()));
        return mapper.map(turmaRepository.save(turmaBD), TurmaReponseDTO.class);
    }

    public List<TurmaReponseDTO> listarTurmas(){
        Usuario usuarioLogado = tokenService.getUsuarioLogado();

        try{
            List<Turma> turmasUsuario = new ArrayList<>(turmaRepository.getTurmasByUsuario(usuarioLogado.getId()));

            return turmasUsuario
                    .stream()
                    .map(t -> mapper.map(t, TurmaReponseDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(ErrorMessageCode.ERRO_GENERICO_BD);
        }
    }


    public TurmaReponseDTO entrarTurma(String codigo){
        Usuario usuario = tokenService.getUsuarioLogado();

        Turma turma = turmaRepository.findByCodigo(codigo);
        if (turma == null){
            throw new BusinessException(ErrorMessageCode.TURMA_NAO_ENCONTRADA);
        } else if (turma.getProfessor().getId().equals(usuario.getId())) {
            throw new BusinessException(ErrorMessageCode.E_PROFESSOR_TURMA);
        } else if (turmaRepository.isEstudanteBloqueado(usuario.getId(), turma.getId())) {
            throw new BusinessException(ErrorMessageCode.ESTUDANTE_BLOQUEADO);
        }

        Estudante estudante;

        if(turmaRepository.estudanteExists(usuario.getId(), turma.getId())) {
            estudante = estudanteRepository.getEstudanteByTurmaAndEstudante(turma, usuario);
            estudante.setAtivo(Boolean.TRUE);
        } else {
            estudante = new Estudante();
            estudante.setTurma(turma);
            estudante.setEstudante(usuario);
            estudante.setAtivo(Boolean.TRUE);
            estudante.setBloqueado(Boolean.FALSE);
        }

        estudanteRepository.save(estudante);
        return mapper.map(estudante.getTurma(), TurmaReponseDTO.class);
    }


    // Funções internas
    private String gerarCodigoTurma(){
        String caracteresPermitidos = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();

        StringBuilder codigoBuilder = new StringBuilder(15);

        for (int i = 0; i < 15; i++) {
            int posicaoAleatoria = random.nextInt(caracteresPermitidos.length());
            char caractereAleatorio = caracteresPermitidos.charAt(posicaoAleatoria);
            codigoBuilder.append(caractereAleatorio);
        }

        String codigo = codigoBuilder.toString();

        if (turmaRepository.existsByCodigo(codigo)){ // Enquanto o código existir vai gerando um novo.
            codigo = gerarCodigoTurma();
        }

        return codigo;
    }
}
