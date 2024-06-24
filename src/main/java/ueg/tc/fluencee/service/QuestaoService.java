package ueg.tc.fluencee.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ueg.tc.fluencee.dto.QuestaoDTO;
import ueg.tc.fluencee.exception.BusinessException;
import ueg.tc.fluencee.exception.ErrorMessageCode;
import ueg.tc.fluencee.model.*;
import ueg.tc.fluencee.repository.AtividadeRepository;
import ueg.tc.fluencee.repository.OpcaoRepository;
import ueg.tc.fluencee.repository.QuestaoAtividadeRepository;
import ueg.tc.fluencee.repository.QuestaoRepository;
import ueg.tc.fluencee.utils.Utils;

import java.util.ArrayList;

@Service
public class QuestaoService {
    @Autowired
    private QuestaoRepository questaoRepository;

    @Autowired
    AtividadeRepository atividadeRepository;

    @Autowired
    private AtividadeService atividadeService;

    @Autowired
    private QuestaoAtividadeRepository questaoAtividadeRepository;

    @Autowired
    private OpcaoRepository opcaoRepository;

    @Transactional
    public QuestaoDTO criarQuestao(QuestaoDTO questaoDTO, Long idAtividade) {
        if (questaoDTO == null || idAtividade == null) {
            throw new BusinessException(ErrorMessageCode.ERRO_GENERICO_BD);
        }

        return switch (questaoDTO.getTipo()) {
            case 1 -> criarQuestaoDissertativa(questaoDTO, idAtividade);
            case 2 -> criarQuestaoMultiplaEscolha(questaoDTO, idAtividade);
            default -> throw new BusinessException(ErrorMessageCode.ERRO_GENERICO_BD);
        };
    }

    protected QuestaoDTO criarQuestaoDissertativa(QuestaoDTO questaoDTO, Long idAtividade) {
        Usuario usuarioLogado = Utils.getUsuarioLogado();

        Questao questaoDissertativa = new Questao();
        questaoDissertativa.setNome(questaoDTO.getNome());
        questaoDissertativa.setEnunciado(questaoDTO.getEnunciado());
        questaoDissertativa.setTipo(questaoDTO.getTipo());
        questaoDissertativa.setAtivado(Boolean.TRUE);
        questaoDissertativa.setUsuarioCriador(usuarioLogado);

        validarQuestao(questaoDissertativa);

        Opcao opcaoPadrao = new Opcao();
        opcaoPadrao.setOpcaoCorreta(Boolean.TRUE);
        opcaoPadrao.setAtivado(Boolean.TRUE);
        opcaoPadrao.setQuestao(questaoDissertativa);

        questaoDissertativa.setOpcoes(new ArrayList<>());
        questaoDissertativa.getOpcoes().add(opcaoPadrao);

        questaoDissertativa = questaoRepository.save(questaoDissertativa);
        opcaoRepository.save(opcaoPadrao);

        return atividadeService.adicionarQuestao(questaoDissertativa, idAtividade, questaoDTO.getNota());
    }

    protected QuestaoDTO criarQuestaoMultiplaEscolha(QuestaoDTO questaoDTO, Long idAtividade) {
        Usuario usuarioLogado = Utils.getUsuarioLogado();

        Questao questaoMultiplaEscolha = new Questao();
        questaoMultiplaEscolha.setNome(questaoDTO.getNome());
        questaoMultiplaEscolha.setEnunciado(questaoDTO.getEnunciado());
        questaoMultiplaEscolha.setTipo(questaoDTO.getTipo());
        questaoMultiplaEscolha.setAtivado(Boolean.TRUE);
        questaoMultiplaEscolha.setUsuarioCriador(usuarioLogado);

        validarQuestao(questaoMultiplaEscolha);

//        if (questaoDTO.getOpcoes() == null || questaoDTO.getOpcoes().size() < 3 || questaoDTO.getOpcoes().size() > 5) {
//            throw new BusinessException(ErrorMessageCode.QUANTIDADE_OPCOES_INVALIDAS);
//        }
//        questaoMultiplaEscolha.setOpcoes(questaoDTO.getOpcoes());
        int opcoesCorretas = 0;
        for (Opcao opcao : questaoMultiplaEscolha.getOpcoes()) {
            opcao.setAtivado(Boolean.TRUE);
            opcao.setQuestao(questaoMultiplaEscolha);
            if (opcao.getOpcaoCorreta()) {
                opcoesCorretas++;
            }
        }

        if (opcoesCorretas != 1) {
            throw new BusinessException(ErrorMessageCode.OPCAO_CORRETA_INVALIDA);
        }

        questaoMultiplaEscolha = questaoRepository.save(questaoMultiplaEscolha);
        opcaoRepository.saveAll(questaoMultiplaEscolha.getOpcoes());

        return atividadeService.adicionarQuestao(questaoMultiplaEscolha, idAtividade, questaoDTO.getNota());
    }


    public QuestaoDTO editarQuestao(QuestaoDTO questaoDTO, Long idAtividade) {
        if (questaoDTO.getId() == null) {
            throw new BusinessException(ErrorMessageCode.ERRO_GENERICO_BD);
        }

        return switch (questaoDTO.getTipo()) {
            case 1 -> editarQuestaoDissertativa(questaoDTO, idAtividade);
            case 2 -> null;// TODO
            default -> throw new BusinessException(ErrorMessageCode.ERRO_GENERICO_BD);
        };
    }


    protected QuestaoDTO editarQuestaoDissertativa(QuestaoDTO questaoDTO, Long idAtividade){
        Questao questaoBD = questaoRepository.getById(questaoDTO.getId());
        questaoBD.setNome(questaoDTO.getNome());
        questaoBD.setEnunciado(questaoDTO.getEnunciado());

        validarQuestao(questaoBD);
        questaoBD = questaoRepository.save(questaoBD);

        if (questaoDTO.getNota() != null && questaoDTO.getNota() > 0){
            Atividade atividade = atividadeRepository.getById(idAtividade);

            QuestaoAtividade questaoAtividade = questaoAtividadeRepository.findByAtividadeAndQuestao(atividade, questaoBD);

            Double notaUsada = questaoAtividadeRepository.notaTotalAtividade(idAtividade);
            if (notaUsada == null) notaUsada = 0.0;
            notaUsada = notaUsada - questaoAtividade.getNota();
            Double notaDisponivel = atividade.getNotaValor() - notaUsada;

            if (questaoDTO.getNota() > notaDisponivel) {
                throw new BusinessException(ErrorMessageCode.NOTA_QUESTAO_MAIOR);
            }

            questaoAtividade.setNota(questaoDTO.getNota());
            questaoAtividade = questaoAtividadeRepository.save(questaoAtividade);
            return questaoAtividade.toQuestaoDTO();
        } else {
            QuestaoAtividade questaoAtividade = new QuestaoAtividade();
            questaoAtividade.setQuestao(questaoBD);
            return questaoAtividade.toQuestaoDTO();
        }
    }

    private void validarQuestao(Questao questao) {
        if (questao.getId() == null){
            if (questaoRepository.existsByNome(questao.getNome(), Utils.getUsuarioLogado().getId())){
                throw new BusinessException(ErrorMessageCode.NOME_QUESTAO_EXISTE);
            }
        } else {
            if (questaoRepository.existsByNomeDiferent(questao.getNome(), questao.getId(), Utils.getUsuarioLogado().getId())){
                throw new BusinessException(ErrorMessageCode.NOME_QUESTAO_EXISTE);
            }
        }
    }
}
