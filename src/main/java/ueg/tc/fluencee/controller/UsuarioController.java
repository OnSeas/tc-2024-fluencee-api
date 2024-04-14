package ueg.tc.fluencee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ueg.tc.fluencee.dto.AlterarSenhaDTO;
import ueg.tc.fluencee.dto.UsuarioRequestDTO;
import ueg.tc.fluencee.dto.UsuarioResponseDTO;
import ueg.tc.fluencee.service.UsuarioService;

@RestController
@RequestMapping(path = "fluencee/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(path = "{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> getUsuario(@PathVariable("idUsuario") Long id){
        return ResponseEntity.ok(usuarioService.getUsuarioById(id));
    }

//    @GetMapping(path = "")
//    public ResponseEntity<List<UsuarioResponseDTO>> listarTudo(){
//        return ResponseEntity.ok(usuarioService.listarTudo());
//    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> incluir(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        return ResponseEntity.ok(usuarioService.inserir(usuarioRequestDTO));
    }

    @PutMapping(path = "{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> alterar(@PathVariable("idUsuario") Long id, @RequestBody UsuarioRequestDTO usuarioRequestDTO){
        return ResponseEntity.ok(usuarioService.alterarNome(id, usuarioRequestDTO));
    }

    @PutMapping(path = "/trocarsenha/{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> trocarSenha(@PathVariable("idUsuario") Long id, @RequestBody AlterarSenhaDTO alterarSenhaDTO){
        return ResponseEntity.ok(usuarioService.trocarSenha(id, alterarSenhaDTO));
    }

    @PutMapping(path = "desativar/{idUsuario}")
    public ResponseEntity<UsuarioResponseDTO> desativar(@PathVariable("idUsuario") Long id){
        return ResponseEntity.ok(usuarioService.desativar(id));
    }
}
