package br.com.alysondantas.qcarona.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.regex.Pattern;

/**
 * Created by alyso on 25/01/2018.
 */

public class controller {

    private String ip;
    private int porta;

    public boolean realizarLogin(String email, String senha) throws IOException, ClassNotFoundException {
        //Cria o Socket para buscar o arquivo no servidor
        Socket rec = new Socket(ip, porta);
        String pack = "0|" + email + "|" + senha;

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
        }
    }
}
