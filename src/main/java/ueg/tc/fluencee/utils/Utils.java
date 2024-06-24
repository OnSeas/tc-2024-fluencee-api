package ueg.tc.fluencee.utils;

import org.springframework.stereotype.Component;
import ueg.tc.fluencee.configuration.security.TokenService;
import ueg.tc.fluencee.model.Usuario;
import ueg.tc.fluencee.repository.RespostaRepository;

@Component
public class Utils {
    public static Usuario getUsuarioLogado(){
        TokenService tokenService = SpringContext.getBean(TokenService.class);
        return tokenService.getUsuarioLogado();
    }


    public static boolean usuarioRespondeuQuestao(Long idQuestao){
        try {
            Usuario usuario = getUsuarioLogado();
            RespostaRepository respostaRepository = SpringContext.getBean(RespostaRepository.class);
            boolean respondeu = respostaRepository.findByUsuarioAndQuestaoAtividade(usuario.getId(), idQuestao);

            if(respondeu){
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
