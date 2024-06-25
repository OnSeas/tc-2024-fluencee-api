package ueg.tc.fluencee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ueg.tc.fluencee.dto.EstudanteGrupoDTO;
import ueg.tc.fluencee.model.Resposta;
import ueg.tc.fluencee.service.RespostaService;

import java.util.List;

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

    @GetMapping("/listarRespostas{idAtividade}")
    public ResponseEntity<List<EstudanteGrupoDTO>> listarRespostas(@PathVariable Long idAtividade) {
        List<EstudanteGrupoDTO> estudanteGrupoDTOS = respostaService.listarRespostas(idAtividade);
        System.out.println(estudanteGrupoDTOS);
        return ResponseEntity.ok(estudanteGrupoDTOS);
    }

    @PutMapping("/corrigir/{idResposta}")
    public ResponseEntity<String> corrigirQuestao(@PathVariable Long idResposta, @RequestBody Resposta resposta) {
        return ResponseEntity.ok(respostaService.corrigir(idResposta, resposta));
    }
}
