package ueg.tc.fluencee.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorMessageCode {

    // CADASTRO DE USUÁRIO
    CAMPO_OBRIGATORIO_VAZIO(HttpStatus.BAD_REQUEST, "É necessário preencher todos os campos obrigatórios!"),
    USUARIO_NOME_INVALIDO(HttpStatus.BAD_REQUEST, "Nome deve ter entre 3 e 200 caracteres!"),
    EMAIL_REPETIDO(HttpStatus.BAD_REQUEST, "Este email já está cadastrado! Tente outro email se quiser prosseguir com o cadastro."),
    COMPRIMENTO_EMAIL_INVALIDO(HttpStatus.BAD_REQUEST,"O email deve ter no máximo 256 caracteres!"),
    EMAIL_INVALIDO(HttpStatus.BAD_REQUEST,"O email deve seguir o padrão email@email.com!"),
    COMPRIMENTO_SENHA(HttpStatus.BAD_REQUEST,"A senha deve ter entre 8 e 35 caracteres!"),
    SENHA_LETRA_NUMERO(HttpStatus.BAD_REQUEST,"A senha deve ter no mínimo 1 letra e 1 número!"),
    USUARIO_INATIVADO(HttpStatus.BAD_REQUEST, "Este usuário foi excluído!"),
    USUARIO_NAO_ENCONTRADO(HttpStatus.NOT_FOUND, "Usuario não encontrado!"),
    SENHA_ERRADA(HttpStatus.BAD_REQUEST,"A senha antiga não confere com sua senha."),
    EMAIL_ERRADO(HttpStatus.BAD_REQUEST,"O email que você digitou não confere com o email da sua conta."),


    // LOGIN
    EMAIL_NAO_ENCONTRADO(HttpStatus.BAD_REQUEST, "O email não foi encontrado. Tente criar uma conta se você ainda não tem uma!");

    final HttpStatus status;
    final String message;

    ErrorMessageCode(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }
}
