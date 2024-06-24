package ueg.tc.fluencee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ueg.tc.fluencee.dto.EstudanteResponseDTO;
import ueg.tc.fluencee.dto.TurmaReponseDTO;
import ueg.tc.fluencee.dto.TurmaRequestDTO;
import ueg.tc.fluencee.model.Estudante;
import ueg.tc.fluencee.service.TurmaService;

import java.util.List;

@RestController
@RequestMapping(path = "fluencee/turma")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @PostMapping("/criar")
    public ResponseEntity<TurmaReponseDTO> criarTurma(@RequestBody TurmaRequestDTO turmaRequestDTO) {
        return ResponseEntity.ok(turmaService.criarTurma(turmaRequestDTO));
    }

    @PutMapping("/editar/{idTurma}")
    public ResponseEntity<TurmaReponseDTO> alterarTurma(@RequestBody TurmaRequestDTO turmaRequestDTO, @PathVariable Long idTurma) {
        return ResponseEntity.ok(turmaService.alterarTurma(turmaRequestDTO, idTurma));
    }

    @PutMapping("excluir/{idTurma}")
    public ResponseEntity<TurmaReponseDTO> excluirTurma(@PathVariable Long idTurma) {
        return ResponseEntity.ok(turmaService.excluirTurma(idTurma));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<TurmaReponseDTO>> listarTurmasUsuario(){
        return ResponseEntity.ok(turmaService.listarTurmas());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<TurmaReponseDTO> buscarTurma(@PathVariable Long id){
        return ResponseEntity.ok(turmaService.buscarTurma(id));
    }

    @PostMapping("/entrarTurma")
    public ResponseEntity<TurmaReponseDTO> entrarTurma(@RequestBody String codigo) {
        return ResponseEntity.ok(turmaService.entrarTurma(codigo));
    }

    @GetMapping("/listarEstudantes/{idTurma}")
    public ResponseEntity<List<EstudanteResponseDTO>> listarEstudantes(@PathVariable Long idTurma){
        return ResponseEntity.ok(turmaService.listarEstudantes(idTurma));
    }

    @PutMapping("/removerEstudante")
    public ResponseEntity<EstudanteResponseDTO> removerEstudante(@RequestBody Estudante estudante){
        return ResponseEntity.ok(turmaService.removerEstudante(estudante));
    }
}
