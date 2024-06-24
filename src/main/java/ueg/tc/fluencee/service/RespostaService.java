package ueg.tc.fluencee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ueg.tc.fluencee.exception.BusinessException;
import ueg.tc.fluencee.exception.ErrorMessageCode;
import ueg.tc.fluencee.model.*;
import ueg.tc.fluencee.repository.AtividadeRepository;
import ueg.tc.fluencee.repository.EstudanteGrupoRepository;
import ueg.tc.fluencee.repository.QuestaoRepository;
import ueg.tc.fluencee.repository.RespostaRepository;
import ueg.tc.fluencee.utils.Utils;

@Service
public class RespostaService {
    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    QuestaoRepository questaoRepository;

    @Autowired
    AtividadeRepository atividadeRepository;

    @Autowired
    EstudanteGrupoRepository estudanteGrupoRepository;

    public void responderQuestao(Resposta resposta, Long idQuestao, Long idAtividade){
        // Tem que organizar para responder outros tipos de questões.

        try {
            Questao questao = questaoRepository.getById(idQuestao);
            Opcao opcao = questao.getOpcoes().get(0);
            resposta.setOpcao(opcao);

            EstudanteGrupo estudanteGrupo = estudanteGrupoRepository.findByUsuarioAndAtividade(Utils.getUsuarioLogado().getId(), idAtividade);
            resposta.setEstudanteGrupo(estudanteGrupo);

            respostaRepository.save(resposta);
        } catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(ErrorMessageCode.ERRO_GENERICO_BD);
        }
    }
}
