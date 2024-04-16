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

    @GetMapping
    public ResponseEntity<UsuarioResponseDTO> getUsuario(){
        return ResponseEntity.ok(usuarioService.getUsuarioById());
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> incluir(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        return ResponseEntity.ok(usuarioService.inserir(usuarioRequestDTO));
    }

    @PutMapping("/alterarNome")
    public ResponseEntity<UsuarioResponseDTO> alterar(@RequestBody UsuarioRequestDTO usuarioRequestDTO){
        return ResponseEntity.ok(usuarioService.alterarNome(usuarioRequestDTO));
    }

    @PutMapping(path = "/trocarSenha")
    public ResponseEntity<UsuarioResponseDTO> trocarSenha(@RequestBody AlterarSenhaDTO alterarSenhaDTO){
        return ResponseEntity.ok(usuarioService.trocarSenha(alterarSenhaDTO));
    }

    @PutMapping(path = "/desativar")
    public ResponseEntity<UsuarioResponseDTO> desativar(){
        return ResponseEntity.ok(usuarioService.desativar());
    }
}
