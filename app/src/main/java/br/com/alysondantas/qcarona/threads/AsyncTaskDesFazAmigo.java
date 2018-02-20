package br.com.alysondantas.qcarona.threads;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.regex.Pattern;

import br.com.alysondantas.qcarona.controller.Controller;
import br.com.alysondantas.qcarona.model.Protocolo;
import br.com.alysondantas.qcarona.model.Usuario;

/**
 * Created by alyso on 20/02/2018.
 */

public class AsyncTaskDesFazAmigo extends AsyncTask<String, Void, String> {

    private Context context;
    private Controller controller;

    public AsyncTaskDesFazAmigo(Context context) {
        this.context = context;
        controller = Controller.getInstance();
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
            publishProgress();
            String pack = params[2];
            Socket rec = new Socket(ip,porta);
            //Enviando o nome do arquivo a ser baixado do servidor
            ObjectOutputStream saida = new ObjectOutputStream(rec.getOutputStream());
            saida.writeObject(pack);
            publishProgress();
            //saida.flush();

            ObjectInputStream entrada = new ObjectInputStream(rec.getInputStream());//recebo o pacote do cliente
            Object object = entrada.readObject();
            publishProgress();
            if ((object != null) && (object instanceof String)) {
                recebido = (String) object;
            }
            saida.close();//fecha a comunicação com o servidor
            entrada.close();
            rec.close();
            publishProgress();

        }catch(Exception e){
            e.printStackTrace();
        }
        return recebido;
    }

    @Override
    protected void onPostExecute(String result){
        if(result!=null) {
            result = result.trim();
            Log.i("AsyncRealizandoLogin", "Exibindo resultado Thread: " + result + " || " + Thread.currentThread().getName());

                String informacoes[] = result.split(Pattern.quote("|"));
                if(informacoes[0].equals(Protocolo.Notificacao.OPERACAO_CONCLUIDA + "")){
                    Toast toast = Toast.makeText(context, "Já era amizade...",Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    Toast toast = Toast.makeText(context, "Erro ao desfazer amizade.",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        Log.i("AsyncRealizandoLogin", "Tirando ProgressDialog da tela Thread: " + Thread.currentThread().getName());

    }
}
