package br.com.alysondantas.qcarona.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

<<<<<<< HEAD
import br.com.alysondantas.qcarona.Exception.ErroInexperadoException;
import br.com.alysondantas.qcarona.Exception.SenhaIncorretaException;
import br.com.alysondantas.qcarona.Exception.UsuarioNaoCadastradoException;
import br.com.alysondantas.qcarona.model.Protocolo;
import br.com.alysondantas.qcarona.util.Observador;
import br.com.alysondantas.qcarona.util.Helper;
=======
import br.com.alysondantas.qcarona.AreaRestritaActivity;
import br.com.alysondantas.qcarona.threads.AsyncTaskCadastra;
import br.com.alysondantas.qcarona.threads.AsyncTaskLoginWakeup;
import br.com.alysondantas.qcarona.threads.AsyncTaskRealizaLogin;
import br.com.alysondantas.qcarona.threads.ThreadConexaoServidor;
>>>>>>> c5348e22f34babf343e7362e3a357b714147bc08

/**
 * Created by alyso on 25/01/2018.
 */

public class Controller implements Observador{
    private static Controller unicaInstancia;//variavel do controller de unica instancia
<<<<<<< HEAD
    private boolean login;
    private ControllerComunicacao controllerComunicacao;
    private String ip = "192.168.22.102";
=======
    private String ip = "149.56.200.229";
>>>>>>> c5348e22f34babf343e7362e3a357b714147bc08
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
        controllerComunicacao = ControllerComunicacao.getInstance();
        this.login = false;
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


    public void realizarLogin(String email, String senha, Context contexto, ProgressBar progressBar, TextView texto , EditText editTextSenha, EditText editTextUser, Button button) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        Toast toast = Toast.makeText(contexto, "Realizando Login, aguarde.",Toast.LENGTH_SHORT);
        toast.show();
        this.context = contexto;


<<<<<<< HEAD
    public boolean realizarLogin(String email, String senha) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, UsuarioNaoCadastradoException, SenhaIncorretaException, ErroInexperadoException {
=======
>>>>>>> c5348e22f34babf343e7362e3a357b714147bc08
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(senha.getBytes(), 0, senha.length());
        String md5 = new BigInteger(1, m.digest()).toString(16);

<<<<<<< HEAD
        String pack = Protocolo.Solicitacao.FAZER_LOGIN + "|" + email + "|" + senha; //envia 0 para cadastrar o usuario e senha no protocolo
        controllerComunicacao.enviarMensagem(pack);
        Helper.pause(1000);
        String msg = controllerComunicacao.getUltimaMensagem();
        if(msg != null){
            switch (Integer.parseInt(msg.trim())){
                case Protocolo.Notificacao.USUARIO_NAO_CADASTRADO: // <-- Usuario nao cadastrado.
                    throw new UsuarioNaoCadastradoException();
                case Protocolo.Notificacao.SENHA_INCORRETA:
                    throw new SenhaIncorretaException();
                case Protocolo.Notificacao.LOGIN_REALIZADO: // <-- Login realizado com sucesso.
                    return true;
                default:
                    throw new ErroInexperadoException();
            }
        } else {
            throw new ErroInexperadoException();
        }
=======
        String pack = "0|" + email + "|" + md5; //envia 0 para cadastrar o usuario e senha no protocolo
        this.user = email;
        this.senha = md5;

        AsyncTaskRealizaLogin envia = new AsyncTaskRealizaLogin(contexto, progressBar, texto, editTextSenha,editTextUser, button);
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
>>>>>>> c5348e22f34babf343e7362e3a357b714147bc08

    public void cadastra(Context context, String nome, String sobrenome, String email, String senha, String data, String tel, String cep , ProgressBar progressBar, Button button) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(senha.getBytes(), 0, senha.length());
        String md5 = new BigInteger(1, m.digest()).toString(16);

        String pack = "1|" + nome + "|" + sobrenome + "|" + email + "|" + md5 + "|" + data + "|" + tel + "|" + cep;

        AsyncTaskCadastra envia = new AsyncTaskCadastra(context, progressBar, button);
        Log.i("AsyncCadastra", "AsyncTaskCadastra senado chamado Thread: " + Thread.currentThread().getName());
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


    public void setIpServidor(String ip, int porta) {
        this.ip = ip;
    }

    public void conectar() throws IOException {
        controllerComunicacao.conectar(ip, porta);
    }

    public void setLogin(boolean login){
        this.login = login;
    }

    public boolean isLogin() {
        return this.login;
    }

    @Override
    public void update(Object o) {

    }
}

