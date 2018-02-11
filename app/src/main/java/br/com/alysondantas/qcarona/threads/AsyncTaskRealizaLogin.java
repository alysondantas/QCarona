package br.com.alysondantas.qcarona.threads;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.com.alysondantas.qcarona.EntrarActivity;
import br.com.alysondantas.qcarona.MainActivity;
import br.com.alysondantas.qcarona.controller.Controller;

/**
 * Created by alyso on 11/02/2018.
 */

public class AsyncTaskRealizaLogin extends AsyncTask<String, Void, String> {


    public AsyncTaskRealizaLogin() {
    }

    @Override
    protected void onPreExecute(){
        Log.i("AsyncTask", "Exibindo ProgressDialog na tela Thread: " + Thread.currentThread().getName());

    }

    @Override
    protected String doInBackground(String... params) {
        String recebido = null;
        try{
            String ip = params[0];
            String portaS = params[1];
            int porta = Integer.parseInt(portaS);
            String pack = params[2];
            Socket rec = new Socket(ip,porta);
            //Enviando o nome do arquivo a ser baixado do servidor
            ObjectOutputStream saida = new ObjectOutputStream(rec.getOutputStream());
            saida.writeObject(pack);
            //saida.flush();

            ObjectInputStream entrada = new ObjectInputStream(rec.getInputStream());//recebo o pacote do cliente
            Object object = entrada.readObject();

            if ((object != null) && (object instanceof String)) {
                recebido = (String) object;
            }
            saida.close();//fecha a comunicação com o servidor
            entrada.close();
            rec.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return recebido;
    }

    @Override
    protected void onPostExecute(String result){
        if(result!=null) {
            Log.i("AsyncRealizandoLogin", "Exibindo resultado Thread: " + result + " || " + Thread.currentThread().getName());
        }else{
            Log.i("AsyncRealizandoLogin", "Erro ao baixar a informação " + Thread.currentThread().getName());
        }
        Log.i("AsyncRealizandoLogin", "Tirando ProgressDialog da tela Thread: " + Thread.currentThread().getName());

    }

}
