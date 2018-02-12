package br.com.alysondantas.qcarona.threads;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.regex.Pattern;

import br.com.alysondantas.qcarona.AreaRestritaActivity;
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
    private Controller controller;
    private Button button;

    public AsyncTaskRealizaLogin(Context context, ProgressBar progressBar, TextView texto , EditText editTextSenha, EditText editTextUser, Button button) {
        this.progressBar = progressBar;
        this.texto = texto;
        this.editTextSenha = editTextSenha;
        this.editTextUser = editTextUser;
        this.context = context;
        controller = Controller.getInstance();
        this.button = button;
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
                button.setEnabled(true);
                Toast toast = Toast.makeText(context, "Usuário ou senha invalido.",Toast.LENGTH_SHORT);
                toast.show();
            }else{
                String informacoes[] = result.split(Pattern.quote("|"));
                if(informacoes[0].equals("102")){
                    Toast toast = Toast.makeText(context, "Login realizado com sucesso.",Toast.LENGTH_SHORT);
                    toast.show();
                    SharedPreferences.Editor editor = controller.getSharedPreferences().edit();
                    editor.putString("user",controller.getUsuario());
                    editor.putString("senha",controller.getSenha());
                    editor.commit();
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            Intent intent = new Intent(context, AreaRestritaActivity.class);
                            context.startActivity(intent);
                        }
                    });

                }else{
                    Toast toast = Toast.makeText(context, "Erro para realizar login.",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }else{
            Log.i("AsyncRealizandoLogin", "Erro ao baixar a informação " + Thread.currentThread().getName());
        }
        Log.i("AsyncRealizandoLogin", "Tirando ProgressDialog da tela Thread: " + Thread.currentThread().getName());

    }



}
