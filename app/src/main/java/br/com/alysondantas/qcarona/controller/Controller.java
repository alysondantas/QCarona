package br.com.alysondantas.qcarona.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.com.alysondantas.qcarona.AreaRestritaActivity;
import br.com.alysondantas.qcarona.threads.AsyncTaskLoginWakeup;
import br.com.alysondantas.qcarona.threads.AsyncTaskRealizaLogin;
import br.com.alysondantas.qcarona.threads.ThreadConexaoServidor;

/**
 * Created by alyso on 25/01/2018.
 */

public class Controller {
    private static Controller unicaInstancia;//variavel do controller de unica instancia
    private String ip = "192.168.22.105";
    private int porta = 1099;
    private Context context;
    private final static String ARQUIVO_PREFERENCIA = "Login";
    private SharedPreferences sharedPreferences;
    private String user;
    private String senha;

    /**
     * Contrutor privado por ser singleton
     */
    private Controller(){
    }

    /**
     * controla o instanciamento de objetos Controller
     *
     * @return unicaInstancia
     */
    public static synchronized Controller getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new Controller();
        }
        return unicaInstancia;
    }

    /**
     * reseta o objeto Controller ja instanciado
     */
    public static void zerarSingleton() {
        unicaInstancia = null;
    }


    public void realizarLogin(String email, String senha, Context contexto, ProgressBar progressBar, TextView texto , EditText editTextSenha, EditText editTextUser) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        Toast toast = Toast.makeText(contexto, "Realizando Login, aguarde.",Toast.LENGTH_SHORT);
        toast.show();
        this.context = contexto;


        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(senha.getBytes(), 0, senha.length());
        String md5 = new BigInteger(1, m.digest()).toString(16);

        String pack = "0|" + email + "|" + md5; //envia 0 para cadastrar o usuario e senha no protocolo
        this.user = email;
        this.senha = md5;

        AsyncTaskRealizaLogin envia = new AsyncTaskRealizaLogin(contexto, progressBar, texto, editTextSenha,editTextUser);
        Log.i("AsyncRealizaLogin", "AsyncTask senado chamado Thread: " + Thread.currentThread().getName());
        String[] parametros = new String[3];
        parametros[0] = ip;
        parametros[1] = porta+"";
        parametros[2] = pack;
        envia.execute(parametros);

        /*ThreadConexaoServidor thread = new ThreadConexaoServidor(pack, ip, porta);
        thread.start();
        toast = Toast.makeText(contexto, "Conexão iniciada, aguarde." + pack,Toast.LENGTH_SHORT);
        toast.show();*/

    }

    public void realizaLoginWakeup(String email, String senha, Context contexto) throws NoSuchAlgorithmException {
        Toast toast = Toast.makeText(contexto, "Realizando Login novamente, Bem vindo, aguarde.",Toast.LENGTH_SHORT);
        toast.show();
        this.context = contexto;

        String pack = "0|" + email + "|" + senha; //envia 0 para cadastrar o usuario e senha no protocolo
        this.user = email;
        this.senha = senha;

        AsyncTaskLoginWakeup envia = new AsyncTaskLoginWakeup(contexto);
        Log.i("AsyncLoginWakeup", "AsyncLoginWakeup senado chamado Thread: " + Thread.currentThread().getName());
        String[] parametros = new String[3];
        parametros[0] = ip;
        parametros[1] = porta+"";
        parametros[2] = pack;
        envia.execute(parametros);
    }

    public Context getAtualContext(){
        return this.context;
    }
    public String getArquivoPreferencia(){
        return ARQUIVO_PREFERENCIA;
    }
    public void setSharedPrefecencs(SharedPreferences sharedPreferences ){
        this.sharedPreferences = sharedPreferences;
    }
    public SharedPreferences getSharedPreferences(){
        return this.sharedPreferences;
    }
    public String getUsuario(){
        return user;
    }
    public String getSenha(){
        return senha;
    }


    public void setIpServidor(String ip, int porta){
        this.ip = ip;
    }
}

