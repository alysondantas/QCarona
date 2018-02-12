package br.com.alysondantas.qcarona.threads;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.regex.Pattern;

import br.com.alysondantas.qcarona.AreaRestritaActivity;
import br.com.alysondantas.qcarona.EntrarActivity;
import br.com.alysondantas.qcarona.controller.Controller;

/**
 * Created by alyso on 11/02/2018.
 */

public class AsyncTaskCadastra extends AsyncTask<String, Object, String> {
    private ProgressBar progressBar;
    private int total = 0;
    private Button button;
    private static int PROGRESSO = 25;
    private Context context;
    private Controller controller;

    public AsyncTaskCadastra(Context context, ProgressBar progressBar, Button button) {
        this.progressBar = progressBar;
        this.context = context;
        controller = Controller.getInstance();
        this.button = button;
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
    protected void onProgressUpdate(Object... values) {
        total += PROGRESSO;
        progressBar.incrementProgressBy(PROGRESSO);

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result){
        if(result!=null) {
            Log.i("AsyncRealizandoCadastro", "Exibindo resultado Thread: " + result + " || " + Thread.currentThread().getName());

                if(result.equals("103")){
                    Toast toast = Toast.makeText(context, "Cadastro realizado com sucesso.",Toast.LENGTH_SHORT);
                    toast.show();

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            Intent intent = new Intent(context, EntrarActivity.class);
                            context.startActivity(intent);
                        }
                    });

                }else if(result.equals("100")){
                    Toast toast = Toast.makeText(context, "Erro para realizar cadastro, usuário já cadastrado.",Toast.LENGTH_SHORT);
                    toast.show();
                    progressBar.setProgress(0);
                    button.setEnabled(true);
                }else{
                    progressBar.setProgress(0);
                    button.setEnabled(true);
                    Toast toast = Toast.makeText(context, "Erro no servidor.",Toast.LENGTH_SHORT);
                    toast.show();
                }

        }else{
            Log.i("AsyncRealizandoLogin", "Erro ao baixar a informação " + Thread.currentThread().getName());
        }
        Log.i("AsyncRealizandoLogin", "Tirando ProgressDialog da tela Thread: " + Thread.currentThread().getName());

    }



}
