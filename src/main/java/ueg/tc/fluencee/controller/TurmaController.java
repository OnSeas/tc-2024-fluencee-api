package ueg.tc.fluencee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ueg.tc.fluencee.dto.TurmaReponseDTO;
import ueg.tc.fluencee.dto.TurmaRequestDTO;
import ueg.tc.fluencee.service.TurmaService;

import java.util.List;

@RestController
@RequestMapping(path = "fluencee/turma")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @PostMapping
    public ResponseEntity<TurmaReponseDTO> criarTurma(@RequestBody TurmaRequestDTO turmaRequestDTO) {
        return ResponseEntity.ok(turmaService.criarTurma(turmaRequestDTO));
    }

    @PutMapping("{idTurma}")
    public ResponseEntity<TurmaReponseDTO> alterarTurma(@RequestBody TurmaRequestDTO turmaRequestDTO, @PathVariable Long idTurma) {
        return ResponseEntity.ok(turmaService.alterarTurma(turmaRequestDTO, idTurma));
    }

    @PutMapping("excluir/{idTurma}")
    public ResponseEntity<TurmaReponseDTO> excluirTurma(@PathVariable Long idTurma) {
        return ResponseEntity.ok(turmaService.excluirTurma(idTurma));
    }

//    @PutMapping("/listar")
//    public ResponseEntity<List<TurmaReponseDTO>> listarTurmasUsuario(){
//
//    }
}
