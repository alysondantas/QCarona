package br.com.alysondantas.qcarona.model;

/**
 * Created by marco on 10/02/2018.
 */

public abstract class Protocolo {

    public abstract class Solicitacao{
        public static final int FAZER_LOGIN = 0;
        public static final int CADASTRA_USUARIO = 1;
    }

    public abstract class Notificacao {
        public static final int USUARIO_NAO_CADASTRADO = 100;
        public static final int SENHA_INCORRETA = 101;
        public static final int LOGIN_REALIZADO = 102;
        public static final int USUARIO_CADASTRADO = 103;
    }
}
