package br.com.alysondantas.qcarona.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import br.com.alysondantas.qcarona.threads.ThreadConexaoServidor;

/**
 * Created by alyso on 25/01/2018.
 */

public class Controller {
    private static Controller unicaInstancia;//variavel do controller de unica instancia
    private String ip = "192.168.22.102";
    private int porta = 1099;

    /**
     * Contrutor privado por ser singleton
     */
    private Controller(){
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




    public void realizarLogin(String email, String senha) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(senha.getBytes(), 0, senha.length());
        String md5 = new BigInteger(1, m.digest()).toString(16);

        String pack = "0|" + email + "|" + senha; //envia 0 para cadastrar o usuario e senha no protocolo
        ThreadConexaoServidor thread = new ThreadConexaoServidor(pack, ip, porta);
        thread.start();

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
}

