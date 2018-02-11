package br.com.alysondantas.qcarona.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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




    public void realizarLogin(String email, String senha, Context contexto) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        Toast toast = Toast.makeText(contexto, "Realizando Login, aguarde.",Toast.LENGTH_SHORT);
        toast.show();
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(senha.getBytes(), 0, senha.length());
        String md5 = new BigInteger(1, m.digest()).toString(16);

        String pack = "0|" + email + "|" + md5; //envia 0 para cadastrar o usuario e senha no protocolo

        ThreadConexaoServidor thread = new ThreadConexaoServidor(pack, ip, porta);
        thread.start();
        toast = Toast.makeText(contexto, "Conexão iniciada, aguarde." + pack,Toast.LENGTH_SHORT);
        toast.show();

    }

    public void realizarLogin2(String email, String senha, Context contexto) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        Toast toast = Toast.makeText(contexto, "Realizando Login, aguarde.",Toast.LENGTH_SHORT);
        toast.show();
        this.context = contexto;


        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(senha.getBytes(), 0, senha.length());
        String md5 = new BigInteger(1, m.digest()).toString(16);

        String pack = "0|" + email + "|" + md5; //envia 0 para cadastrar o usuario e senha no protocolo

        AsyncTaskRealizaLogin envia = new AsyncTaskRealizaLogin();
        Log.i("AsyncTask", "AsyncTask senado chamado Thread: " + Thread.currentThread().getName());
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

    public Context getAtualContext(){
        return this.context;
    }


    public void setIpServidor(String ip, int porta){
        this.ip = ip;
    }
}

