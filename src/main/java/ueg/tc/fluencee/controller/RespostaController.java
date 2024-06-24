package ueg.tc.fluencee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ueg.tc.fluencee.model.Resposta;
import ueg.tc.fluencee.service.RespostaService;

@RestController
@RequestMapping(path = "fluencee/resposta")
public class RespostaController {

    @Autowired
    private RespostaService respostaService;

    @PostMapping("/responder/{idQuestao}/{idAtividade}")
    public ResponseEntity<String> responderQuestao(@PathVariable Long idQuestao, @PathVariable Long idAtividade, @RequestBody Resposta resposta) {
        respostaService.responderQuestao(resposta, idQuestao, idAtividade);
        return ResponseEntity.ok("Resposta cadastrada");
    }

}
