package ueg.tc.fluencee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ueg.tc.fluencee.dto.AtividadeResponseDTO;
import ueg.tc.fluencee.dto.QuestaoDTO;
import ueg.tc.fluencee.model.Atividade;
import ueg.tc.fluencee.model.Turma;
import ueg.tc.fluencee.service.AtividadeService;

import java.util.List;

@RestController
@RequestMapping(path = "fluencee/atividade")
public class AtividadeController {

    @Autowired
    AtividadeService atividadeService;

    @PostMapping("/criarAtividade/{idTurma}")
    public ResponseEntity<AtividadeResponseDTO> criarAtividade(@RequestBody Atividade atividade, @PathVariable Long idTurma) {
        return ResponseEntity.ok(atividadeService.criarAtividade(idTurma, atividade));
    }

    @PostMapping("/listarAtividades")
    public ResponseEntity<List<AtividadeResponseDTO>> listarAtividades(@RequestBody Turma turma) {
        return ResponseEntity.ok(atividadeService.listarAtividades(turma));
    }

    @PutMapping("/editarAtividade")
    public ResponseEntity<AtividadeResponseDTO> editarAtividade(@RequestBody Atividade atividade) {
        return ResponseEntity.ok(atividadeService.editarAtividade(atividade));
    }

    @PutMapping("/excluirAtividade")
    public ResponseEntity<AtividadeResponseDTO> excluirAtividade(@RequestBody Atividade atividade) {
        return ResponseEntity.ok(atividadeService.excluirAtividade(atividade));
    }

    @GetMapping("/listarQuestoes/{idAtividade}")
    public ResponseEntity<List<QuestaoDTO>> listarQuestoes(@PathVariable Long idAtividade) {
        return ResponseEntity.ok(atividadeService.listarQuestoes(idAtividade));
    }

    @DeleteMapping("/removerQuestao/{idAtividade}/{idQuestao}")
    public ResponseEntity<QuestaoDTO> removerQuestao(@PathVariable Long idAtividade, @PathVariable Long idQuestao) {
        return ResponseEntity.ok(atividadeService.removerQuestao(idAtividade, idQuestao));
    }

    @PutMapping("/iniciarAtividade/{idAtividade}")
    public ResponseEntity<AtividadeResponseDTO> iniciarAtividade(@PathVariable Long idAtividade) {
        return ResponseEntity.ok(atividadeService.iniciarAtividade(idAtividade));
    }
}
