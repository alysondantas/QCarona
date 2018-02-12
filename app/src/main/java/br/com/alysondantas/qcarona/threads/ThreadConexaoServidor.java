package br.com.alysondantas.qcarona.threads;

import android.content.Context;
import android.widget.Toast;

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
            /*Toast toast2 = Toast.makeText(contexto, "ThreadConexão OK.",Toast.LENGTH_SHORT);
            toast2.show();
            if(rec != null){
                Toast toast = Toast.makeText(contexto, "Socket instanciado.",Toast.LENGTH_SHORT);
                toast.show();
            }*/

            //Enviando o nome do arquivo a ser baixado do servidor
            ObjectOutputStream saida = new ObjectOutputStream(rec.getOutputStream());
            saida.writeObject(pack);
            //saida.flush();

            ObjectInputStream entrada = new ObjectInputStream(rec.getInputStream());//recebo o pacote do cliente
            Object object = entrada.readObject();
            String recebido = null;
            if ((object != null) && (object instanceof String)) {
                recebido = (String) object;
            }
            saida.close();//fecha a comunicação com o servidor
            entrada.close();
            rec.close();

            if(recebido.equals("3")){

            }else if(recebido.equals("jacadastrado")){
                //Toast toast = Toast.makeText(contexto, "erro, aguarde.",Toast.LENGTH_SHORT);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
