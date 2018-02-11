package br.com.alysondantas.qcarona.threads;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class AsyncTaskRealizaLogin extends AsyncTask<String, Object, String> {
    private ProgressBar progressBar;
    private TextView texto;
    private int total = 0;
    private static int PROGRESSO = 25;
    private EditText editTextSenha;
    private EditText editTextUser;
    private Context context;

    public AsyncTaskRealizaLogin(Context context, ProgressBar progressBar, TextView texto , EditText editTextSenha, EditText editTextUser) {
        this.progressBar = progressBar;
        this.texto = texto;
        this.editTextSenha = editTextSenha;
        this.editTextUser = editTextUser;
        this.context = context;
    }

    @Override
    protected void onPreExecute(){
        texto.setText("0%");
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
        texto.setText(total + "%");

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result){
        if(result!=null) {
            Log.i("AsyncRealizandoLogin", "Exibindo resultado Thread: " + result + " || " + Thread.currentThread().getName());
            if(result.equals("100")){
                progressBar.setProgress(0);
                editTextUser.setEnabled(true);
                editTextSenha.setEnabled(true);
                Toast toast = Toast.makeText(context, "Usuário ou senha invalido.",Toast.LENGTH_SHORT);
                toast.show();
            }
        }else{
            Log.i("AsyncRealizandoLogin", "Erro ao baixar a informação " + Thread.currentThread().getName());
        }
        Log.i("AsyncRealizandoLogin", "Tirando ProgressDialog da tela Thread: " + Thread.currentThread().getName());

    }



}
