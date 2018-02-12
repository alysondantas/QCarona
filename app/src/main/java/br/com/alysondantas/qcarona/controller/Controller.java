package br.com.alysondantas.qcarona.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.com.alysondantas.qcarona.Exception.ErroInexperadoException;
import br.com.alysondantas.qcarona.Exception.SenhaIncorretaException;
import br.com.alysondantas.qcarona.Exception.UsuarioNaoCadastradoException;
import br.com.alysondantas.qcarona.model.Protocolo;
import br.com.alysondantas.qcarona.util.Observador;
import br.com.alysondantas.qcarona.util.Helper;

/**
 * Created by alyso on 25/01/2018.
 */

public class Controller implements Observador{
    private static Controller unicaInstancia;//variavel do controller de unica instancia
    private boolean login;
    private ControllerComunicacao controllerComunicacao;
    private String ip = "192.168.22.102";
    private int porta = 1099;

    /**
     * Contrutor privado por ser singleton
     */
    private Controller(){
        controllerComunicacao = ControllerComunicacao.getInstance();
        this.login = false;
    }

    /**
     * controla o instanciamento de objetos Controller
     *
     * @return unicaInstancia
     */
    public static synchronized Controller getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new Controller();
        }
        return unicaInstancia;
    }

    /**
     * reseta o objeto Controller ja instanciado
     */
    public static void zerarSingleton() {
        unicaInstancia = null;
    }




    public boolean realizarLogin(String email, String senha) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, UsuarioNaoCadastradoException, SenhaIncorretaException, ErroInexperadoException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(senha.getBytes(), 0, senha.length());
        String md5 = new BigInteger(1, m.digest()).toString(16);

        String pack = Protocolo.Solicitacao.FAZER_LOGIN + "|" + email + "|" + senha; //envia 0 para cadastrar o usuario e senha no protocolo
        controllerComunicacao.enviarMensagem(pack);
        Helper.pause(1000);
        String msg = controllerComunicacao.getUltimaMensagem();
        if(msg != null){
            switch (Integer.parseInt(msg.trim())){
                case Protocolo.Notificacao.USUARIO_NAO_CADASTRADO: // <-- Usuario nao cadastrado.
                    throw new UsuarioNaoCadastradoException();
                case Protocolo.Notificacao.SENHA_INCORRETA:
                    throw new SenhaIncorretaException();
                case Protocolo.Notificacao.LOGIN_REALIZADO: // <-- Login realizado com sucesso.
                    return true;
                default:
                    throw new ErroInexperadoException();
            }
        } else {
            throw new ErroInexperadoException();
        }

        /*//Cria o Socket para buscar o arquivo no servidor
        Socket rec = new Socket(ip, porta);

        //Enviando o nome do arquivo a ser baixado do servidor
        ObjectOutputStream saida = new ObjectOutputStream(rec.getOutputStream());
        saida.writeObject(pack);
        saida.flush();


        ObjectInputStream entrada = new ObjectInputStream(rec.getInputStream());
        String recebido = (String) entrada.readObject();//obtem o que foi recebido
        String[] informacoes = recebido.split(Pattern.quote("|"));//fragmenta as informações
        if(informacoes[0].equals("3")) {//se for 0
            return true;
        }else{
            return false;
        }*/
    }

    public void setIpServidor(String ip, int porta) {
        this.ip = ip;
    }

    public void conectar() throws IOException {
        controllerComunicacao.conectar(ip, porta);
    }

    public void setLogin(boolean login){
        this.login = login;
    }

    public boolean isLogin() {
        return this.login;
    }

    @Override
    public void update(Object o) {

    }
}

