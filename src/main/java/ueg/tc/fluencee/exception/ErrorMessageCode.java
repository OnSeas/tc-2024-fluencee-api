package ueg.tc.fluencee.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorMessageCode {

    ERRO_GENERICO_BD(HttpStatus.BAD_REQUEST, "Erro na conexão com o banco de dados."),

    CAMPO_OBRIGATORIO_VAZIO(HttpStatus.BAD_REQUEST, "É necessário preencher todos os campos obrigatórios!"),
    CAMPOS_ERRADOS(HttpStatus.BAD_REQUEST,"Campos não preenchidos corretamente, por favor confira suas informações!"),
    PERMISSAO_NEGADA(HttpStatus.UNAUTHORIZED, "Você não tem permissão para realizar essa ação!"),

    // CADASTRO DE USUÁRIO
    USUARIO_NOME_INVALIDO(HttpStatus.BAD_REQUEST, "Nome deve ter entre 3 e 200 caracteres!"),
    EMAIL_REPETIDO(HttpStatus.BAD_REQUEST, "Este email já está cadastrado! Tente outro email se quiser prosseguir com o cadastro."),
    COMPRIMENTO_EMAIL_INVALIDO(HttpStatus.BAD_REQUEST,"O email deve ter no máximo 256 caracteres!"),
    EMAIL_INVALIDO(HttpStatus.BAD_REQUEST,"O email deve seguir o padrão email@email.com!"),
    COMPRIMENTO_SENHA(HttpStatus.BAD_REQUEST,"A senha deve ter entre 8 e 35 caracteres!"),
    SENHA_LETRA_NUMERO(HttpStatus.BAD_REQUEST,"A senha deve ter no mínimo 1 letra e 1 número!"),
    USUARIO_INATIVADO(HttpStatus.BAD_REQUEST, "Este usuário foi excluído!"),
    USUARIO_NAO_ENCONTRADO(HttpStatus.NOT_FOUND, "Usuario não encontrado!"),
    SENHA_ERRADA(HttpStatus.BAD_REQUEST,"Senha antiga incorreta!"),
    EMAIL_ERRADO(HttpStatus.BAD_REQUEST,"Email incorreto!"),
    SENHA_INCORRETA(HttpStatus.BAD_REQUEST,"Senha incorreta!"),


    // LOGIN
    ERRO_GERAR_TOKEN(HttpStatus.UNAUTHORIZED, "Erro ao gerar token para login!"),
    ERRO_VALIDAR_TOKEN(HttpStatus.UNAUTHORIZED, "Erro ao validar token para login!"),
    ERRO_RECUPERAR_USUARIO_LOGIN(HttpStatus.UNAUTHORIZED, "Erro ao recuperar usuário pelo login!"),
    SENHA_EMAIL_ERRADOS(HttpStatus.UNAUTHORIZED, "Usuário não existe ou senha incorreta!"),
    EMAIL_NAO_ENCONTRADO(HttpStatus.NOT_FOUND, "Não existe uma conta associada a este email!"),

    // TURMA
    NOME_TURMA_INVALIDO(HttpStatus.BAD_REQUEST, "Nome da turma deve ter entre 3 e 50 caracteres!"),
    NOME_TURMA_EXISTE(HttpStatus.BAD_REQUEST, "Você já tem uma turma com esse nome!"),
    TURMA_NAO_ENCONTRADA(HttpStatus.NOT_FOUND, "Turma referente ao código não encontrada"),
    TURMA_EXCLUIDA(HttpStatus.BAD_REQUEST, "A turma já foi excluída!"),
    E_PROFESSOR_TURMA(HttpStatus.BAD_REQUEST, "Você não pode entrar em uma turma que você é professor!"),
    ESTUDANTE_BLOQUEADO(HttpStatus.BAD_REQUEST, "Você foi bloqueado e não pode mais entrar nesta turma!");

    final HttpStatus status;
    final String message;

    ErrorMessageCode(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }
}
