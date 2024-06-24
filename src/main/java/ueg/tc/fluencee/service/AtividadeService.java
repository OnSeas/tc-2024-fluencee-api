package ueg.tc.fluencee.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ueg.tc.fluencee.configuration.security.TokenService;
import ueg.tc.fluencee.dto.AtividadeResponseDTO;
import ueg.tc.fluencee.dto.QuestaoDTO;
import ueg.tc.fluencee.exception.BusinessException;
import ueg.tc.fluencee.exception.ErrorMessageCode;
import ueg.tc.fluencee.model.*;
import ueg.tc.fluencee.repository.*;
import ueg.tc.fluencee.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtividadeService {
    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private QuestaoAtividadeRepository questaoAtividadeRepository;

    @Autowired
    private TokenService tokenService;

    private final ModelMapper mapper = new ModelMapper();
    @Autowired
    private QuestaoRepository questaoRepository;

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private EstudanteGrupoRepository estudanteGrupoRepository;

    public AtividadeResponseDTO criarAtividade(Long TurmaId, Atividade atividade) {
        Usuario usuarioLogado = tokenService.getUsuarioLogado();

        // Inicialização de Atividade
        atividade.setAtivado(Boolean.TRUE);
        atividade.setTurma(turmaRepository.getById(TurmaId));
        atividade.setStatus(Atividade.CRIADA);

        //Validações
        validarAtividade(atividade);

        return mapper.map(atividadeRepository.save(atividade), AtividadeResponseDTO.class);
    }

    public List<AtividadeResponseDTO> listarAtividades(Turma turma) {
        List<Atividade> atividadesTurma = atividadeRepository.findByTurmaAndAtivadoOrderByNome(turma, true);
        return atividadesTurma
                .stream()
                .map(atividade -> mapper.map(atividade, AtividadeResponseDTO.class))
                .collect(Collectors.toList());
    }

    public AtividadeResponseDTO editarAtividade(Atividade atividade) {
        Usuario usuarioLogado = tokenService.getUsuarioLogado();

        //Validações
        validarAtividade(atividade);

        Atividade atividadeBD = atividadeRepository.getById(atividade.getId());

        atividadeBD.setNome(atividade.getNome());
        atividadeBD.setEnunciado(atividade.getEnunciado());
        atividadeBD.setNotaValor(atividade.getNotaValor());

        return mapper.map(atividadeRepository.save(atividadeBD), AtividadeResponseDTO.class);
    }

    public AtividadeResponseDTO excluirAtividade(Atividade atividade) {
        Usuario usuarioLogado = tokenService.getUsuarioLogado();

        //Validações
        validarAtividade(atividade);

        Atividade atividadeBD = atividadeRepository.getById(atividade.getId());

        atividadeBD.setNome(atividade.getId().toString());
        atividadeBD.setEnunciado(atividade.getId().toString());
        atividadeBD.setNotaValor(atividade.getId());
        atividadeBD.setAtivado(Boolean.FALSE);

        return mapper.map(atividadeRepository.save(atividadeBD), AtividadeResponseDTO.class);
    }

    public QuestaoDTO adicionarQuestao(Questao questao, Long idAtividade, Double nota) {
        Atividade atividade = atividadeRepository.getById(idAtividade);

        QuestaoAtividade questaoAtividade = new QuestaoAtividade();
        questaoAtividade.setAtividade(atividade);
        questaoAtividade.setQuestao(questao);
        questaoAtividade.setNota(nota);

        Double notaUsada = questaoAtividadeRepository.notaTotalAtividade(idAtividade);
        if (notaUsada == null) notaUsada = 0.0;
        Double notaDisponivel = atividade.getNotaValor() - notaUsada;

        if (questaoAtividade.getNota() > notaDisponivel) {
            throw new BusinessException(ErrorMessageCode.NOTA_QUESTAO_MAIOR);
        }

        questaoAtividadeRepository.save(questaoAtividade);

        return questaoAtividade.toQuestaoDTO();
    }

    public List<QuestaoDTO> listarQuestoes(Long idAtividade) {
        List<QuestaoAtividade> questaoAtividadeList = questaoAtividadeRepository.findByAtividadeAndAtivado(idAtividade);
        return questaoAtividadeList
                .stream()
                .map(QuestaoAtividade::toQuestaoDTO)
                .collect(Collectors.toList());
    }

    public QuestaoDTO removerQuestao(Long idAtividade, Long idQuestao) {
        Atividade atividade = atividadeRepository.getById(idAtividade);
        Questao questao = questaoRepository.getById(idQuestao);

        QuestaoAtividade questaoAtividade = questaoAtividadeRepository.findByAtividadeAndQuestao(atividade, questao);
        try {
            questaoAtividadeRepository.delete(questaoAtividade);
            return mapper.map(questao, QuestaoDTO.class);
        } catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(ErrorMessageCode.EXCLUIR_QUESTAO);
        }
    }

    @Transactional
    public AtividadeResponseDTO iniciarAtividade(Long idAtividade) {
        Atividade atividade = atividadeRepository.getById(idAtividade);

        Double notaTotalAtividade = questaoAtividadeRepository.notaTotalAtividade(atividade.getId());
        if (notaTotalAtividade == null) notaTotalAtividade = 0.0;
        if (atividade.getNotaValor() != notaTotalAtividade){
            throw new BusinessException(ErrorMessageCode.NOTA_DIFERENTE);
        }

        criarGrupos(atividade);

        atividade.setStatus(Atividade.INICIADA);
        atividade = atividadeRepository.save(atividade);

        return mapper.map(atividade, AtividadeResponseDTO.class);
    }

    private void criarGrupos(Atividade atividade){
        if(atividade.getTipoAtividade() == Atividade.INDIVIDUAL){
            criarGrupoIndividual(atividade);
        }
        // else if ... aqui colocar futuramente em dupla e em grupo
    }

    private void criarGrupoIndividual(Atividade atividade){
        try{
            List<Estudante> estudantes = turmaService.listarEstudantesModel(atividade.getTurma().getId());

            for (Estudante estudante : estudantes){ // Cria um grupo e um estudante grupo para cada estudante
                Grupo grupo = new Grupo();
                grupo.setAtividade(atividade);

                grupoRepository.save(grupo);

                EstudanteGrupo estudanteGrupo = new EstudanteGrupo();
                estudanteGrupo.setEstudante(estudante);
                estudanteGrupo.setGrupo(grupo);
                estudanteGrupo.setTutor(Boolean.FALSE);
                estudanteGrupo.setPronto(Boolean.FALSE);

                estudanteGrupoRepository.save(estudanteGrupo);
            }

        } catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(ErrorMessageCode.INICIAR_ATIVIDADE_GRUPOS);
        }

    }

    private void validarAtividade(Atividade atividade){
        Usuario usuarioLogado = Utils.getUsuarioLogado();

        if (atividade.getId() != null){
            if (atividadeRepository.existsByNomeDiferent(atividade.getNome(), atividade.getId(), usuarioLogado.getId())){
                throw new BusinessException(ErrorMessageCode.NOME_ATIVIDADE_EXISTE);
            }
        } else {
            if (atividadeRepository.existsByNome(atividade.getNome(), usuarioLogado.getId())){
                throw new BusinessException(ErrorMessageCode.NOME_ATIVIDADE_EXISTE);
            }
        }
        // Validar tamanho e nullability do nome + nota > 0 (atualmente valida só no frontend).
    }
}
