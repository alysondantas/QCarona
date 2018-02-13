package br.com.alysondantas.qcarona.threads;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import br.com.alysondantas.qcarona.util.Observado;

/**
 * Created by marco on 25/01/2018.
 */

public class ThreadClienteTCP extends Observado implements Runnable{

    private Socket socketCliente;
    private DataOutputStream saidaMens;
    private DataInputStream entradaMens;

    private String ip;
    private int porta;

    private Object ultimaMensagem;

    public ThreadClienteTCP(String ip, int porta) {
        this.ip = ip;
        this.porta = porta;
    }

    @Override
    public void run() {
        configurar();

        while (true) {
            if (entradaMens != null) {
                try {
                    String obj = entradaMens.readUTF();
                    this.ultimaMensagem = obj;
                    Log.d("Thread", "Servidor enviou: " + obj);
                    notificar(obj);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("Cliente", "Nao foi possível permanecer conectado ao servidor.");
                    break;
                }
            }
        }
    }

    public void configurar(){
        try {
            this.socketCliente = new Socket(ip, porta);
            this.entradaMens = new DataInputStream(socketCliente.getInputStream());
            this.saidaMens = new DataOutputStream(socketCliente.getOutputStream());
            Log.d("Servidor", "Conectado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarMensagem(final String mens){
        if(saidaMens != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        saidaMens.writeUTF(mens);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Log.d("Thread CX", "ERRO: Objeto de envio nulo.");
        }

    }

    public String getUltimaMensagem(){
        return (String) this.ultimaMensagem;
    }

    public void desconectar() throws IOException {
        socketCliente.close();
    }
}
