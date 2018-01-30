package br.com.alysondantas.qcarona.threads;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by alyso on 26/01/2018.
 */

public class ThreadConexaoServidor extends Thread {
    private String pack;
    private String ip;
    private int porta;
    private boolean running = false;
    private PrintWriter mOut;

    public ThreadConexaoServidor(String pack, String ip, int porta){
        this.pack = pack;
        this.ip = ip;
        this.porta = porta;
    }

    @Override
    public void run(){
        try{
            //Cria o Socket para buscar o arquivo no servidor
            Socket rec = new Socket(ip,porta);

            //Enviando o nome do arquivo a ser baixado do servidor
            ObjectOutputStream saida = new ObjectOutputStream(rec.getOutputStream());
            saida.writeObject(pack);
            saida.flush();

            ObjectInputStream entrada = new ObjectInputStream(rec.getInputStream());//recebo o pacote do cliente
            String recebido = (String) entrada.readObject();
            saida.close();//fecha a comunicação com o servidor
            entrada.close();
            rec.close();

            if(recebido.equals("3")){

            }else if(recebido.equals("jacadastrado")){

            }

        }catch(Exception e){

        }
    }
}
