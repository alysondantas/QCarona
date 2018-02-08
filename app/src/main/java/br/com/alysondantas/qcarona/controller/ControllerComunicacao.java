package br.com.alysondantas.qcarona.controller;

import android.util.Log;

import br.com.alysondantas.qcarona.util.*;
import java.io.IOException;
import br.com.alysondantas.qcarona.threads.*;

/**
 * Created by marcos on 25/01/2018.
 */

public class ControllerComunicacao extends Observado implements Observador{

    private ThreadClienteTCP servidor;
    private static ControllerComunicacao controllerComunicacao;

    private ControllerComunicacao() {
    }

    public void conectar(String ip, int porta) throws IOException {
        this.servidor = new ThreadClienteTCP(ip, porta);
        this.servidor.addObservador(this);
        new Thread(servidor).start();
    }

    public void enviarMensagem(String msg) throws IOException {
        servidor.enviarMensagem(msg);
    }

    public static ControllerComunicacao getInstance() {
        if (controllerComunicacao == null) {
            controllerComunicacao = new ControllerComunicacao();
        }
        return controllerComunicacao;
    }


    @Override
    public void update(Object o) {
        Log.d("Servidor", (String)o);
        notificar(o);
    }
}
