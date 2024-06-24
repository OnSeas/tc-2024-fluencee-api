package ueg.tc.fluencee.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ueg.tc.fluencee.dto.EstudanteGrupoDTO;
import ueg.tc.fluencee.dto.RespostaDTO;
import ueg.tc.fluencee.dto.UsuarioResponseDTO;
import ueg.tc.fluencee.exception.BusinessException;
import ueg.tc.fluencee.exception.ErrorMessageCode;
import ueg.tc.fluencee.model.*;
import ueg.tc.fluencee.repository.AtividadeRepository;
import ueg.tc.fluencee.repository.EstudanteGrupoRepository;
import ueg.tc.fluencee.repository.QuestaoRepository;
import ueg.tc.fluencee.repository.RespostaRepository;
import ueg.tc.fluencee.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final ModelMapper mapper = new ModelMapper();

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

    public List<EstudanteGrupoDTO> listarRespostas(Long idAtividade) {
        Map<Long, EstudanteGrupoDTO> estudanteGrupoMap = new HashMap<>();
        List<Resposta> respostas = respostaRepository.findByAtividade(idAtividade);

        if (respostas.isEmpty()) {
            return new ArrayList<>();
        } else {
            for (Resposta resposta : respostas) {
                Long usuarioId = resposta.getEstudanteGrupo().getEstudante().getId();

                EstudanteGrupoDTO estudanteGrupoDTO = estudanteGrupoMap.get(usuarioId);
                if (estudanteGrupoDTO == null) {
                    estudanteGrupoDTO = new EstudanteGrupoDTO();
                    estudanteGrupoDTO.setEstudante(mapper.map(resposta.getEstudanteGrupo().getEstudante().getEstudante(), UsuarioResponseDTO.class));
                    estudanteGrupoDTO.setRespostas(new ArrayList<>());
                    estudanteGrupoMap.put(usuarioId, estudanteGrupoDTO);
                }

                RespostaDTO respostaDTO = mapper.map(resposta, RespostaDTO.class);
                estudanteGrupoDTO.getRespostas().add(respostaDTO);
            }
        }

        return new ArrayList<>(estudanteGrupoMap.values());
    }

}
