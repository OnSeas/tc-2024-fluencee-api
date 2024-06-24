package ueg.tc.fluencee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ueg.tc.fluencee.dto.QuestaoDTO;
import ueg.tc.fluencee.service.QuestaoService;

@RestController
@RequestMapping(path = "fluencee/questao")
public class QuestaoController {
    @Autowired
    private QuestaoService questaoService;

    @PostMapping("/criarQuestao/{idAtividade}")
    public ResponseEntity<QuestaoDTO> criarQuestao(@RequestBody QuestaoDTO questaoDTO, @PathVariable Long idAtividade) {
        return ResponseEntity.ok(questaoService.criarQuestao(questaoDTO, idAtividade));
    }

    @PutMapping("editarQuestao/{idAtividade}")
    public ResponseEntity<QuestaoDTO> editarQuestao(@RequestBody QuestaoDTO questaoDTO, @PathVariable Long idAtividade) {
        return ResponseEntity.ok(questaoService.editarQuestao(questaoDTO, idAtividade));
    }
}
