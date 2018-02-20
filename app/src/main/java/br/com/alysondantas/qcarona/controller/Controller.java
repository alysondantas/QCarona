package br.com.alysondantas.qcarona.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.com.alysondantas.qcarona.AreaRestritaActivity;
import br.com.alysondantas.qcarona.InicioFragment;
import br.com.alysondantas.qcarona.MinhasCaronasFragment;
import br.com.alysondantas.qcarona.QueroCaronaFragment;
import br.com.alysondantas.qcarona.model.Protocolo;
import br.com.alysondantas.qcarona.model.Usuario;
import br.com.alysondantas.qcarona.threads.AsyncTaskBuscarAmigos;
import br.com.alysondantas.qcarona.threads.AsyncTaskBuscarCaronasDisponiveis;
import br.com.alysondantas.qcarona.threads.AsyncTaskCadastra;
import br.com.alysondantas.qcarona.threads.AsyncTaskDesFazAmigo;
import br.com.alysondantas.qcarona.threads.AsyncTaskEditarPerfil;
import br.com.alysondantas.qcarona.threads.AsyncTaskLoginWakeup;
import br.com.alysondantas.qcarona.threads.AsyncTaskObtemAmigos;
import br.com.alysondantas.qcarona.threads.AsyncTaskObtemPerfil;
import br.com.alysondantas.qcarona.threads.AsyncTaskObtemPerfilAmigo;
import br.com.alysondantas.qcarona.threads.AsyncTaskRealizaLogin;
import br.com.alysondantas.qcarona.threads.AsyncTaskSolicitacaoAmizade;
import br.com.alysondantas.qcarona.threads.ThreadConexaoServidor;

/**
 * Created by alyso on 25/01/2018.
 */

public class Controller {
    private static Controller unicaInstancia;//variavel do controller de unica instancia
    //private String ip = "149.56.200.229";
    private String ip = "192.168.0.107";
    private int porta = 1099;
    private Context context;
    private final static String ARQUIVO_PREFERENCIA = "Login";
    private SharedPreferences sharedPreferences;
    private String user;
    private String senha;
    private int id = 0;
    private int idamigo = 0;
    private Usuario userAux;

    /**
     * Contrutor privado por ser singleton
     */
    private Controller(){
        userAux = new Usuario();
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

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }

    public Usuario getUserAux() {
        return userAux;
    }
    public void setUserAux(Usuario userAux){
        this.userAux = userAux;
    }

    public void realizarLogin(String email, String senha, Context contexto, ProgressBar progressBar, TextView texto , EditText editTextSenha, EditText editTextUser, Button button) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        Toast toast = Toast.makeText(contexto, "Realizando Login, aguarde.",Toast.LENGTH_SHORT);
        toast.show();
        this.context = contexto;


        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(senha.getBytes(), 0, senha.length());
        String md5 = new BigInteger(1, m.digest()).toString(16);

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

    public void cadastra(Context context, String nome, String sobrenome, String email, String senha, String data, String tel, String cep , ProgressBar progressBar, Button button, TextView textView) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(senha.getBytes(), 0, senha.length());
        String md5 = new BigInteger(1, m.digest()).toString(16);

        String pack = "1|" + nome + "|" + sobrenome + "|" + email + "|" + md5 + "|" + data + "|" + tel + "|" + cep;

        AsyncTaskCadastra envia = new AsyncTaskCadastra(context, progressBar, button, textView);
        Log.i("AsyncCadastra", "AsyncTaskCadastra senado chamado Thread: " + Thread.currentThread().getName());
        String[] parametros = new String[3];
        parametros[0] = ip;
        parametros[1] = porta+"";
        parametros[2] = pack;
        envia.execute(parametros);
    }

    public void editar(Context context, String nome, String sobrenome, String email, String senha, String data, String tel, ProgressBar progressBar, Button button, TextView textView) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(senha.getBytes(), 0, senha.length());
        String md5 = new BigInteger(1, m.digest()).toString(16);

        String pack = "5|" + nome + "|" + sobrenome + "|" + email + "|" + md5 + "|" + data + "|" + tel + "|" + id;

        AsyncTaskEditarPerfil envia = new AsyncTaskEditarPerfil(context, progressBar, button, textView);
        Log.i("AsyncEditar", "AsyncTaskEditar senado chamado Thread: " + Thread.currentThread().getName());
        String[] parametros = new String[3];
        parametros[0] = ip;
        parametros[1] = porta+"";
        parametros[2] = pack;
        envia.execute(parametros);

    }

    public void obtemPerfil(Context context, int id){
        String pack = "6|" + id;
        AsyncTaskObtemPerfil envia = new AsyncTaskObtemPerfil(context);
        Log.i("AsyncTaskObtemPerfil", "AsyncTaskObtemPerfil senado chamado Thread: " + Thread.currentThread().getName());
        String[] parametros = new String[3];
        parametros[0] = ip;
        parametros[1] = porta+"";
        parametros[2] = pack;
        envia.execute(parametros);
    }

    public void obtemPerfilAmigo(Context context, int id, MinhasCaronasFragment fragment){
        String pack = "6|" + id;
        AsyncTaskObtemPerfilAmigo envia = new AsyncTaskObtemPerfilAmigo(context, fragment);
        Log.i("AsyncTaskObtemPerfAmigo", "AsyncTaskObtemPerfilAmigo senado chamado Thread: " + Thread.currentThread().getName());
        String[] parametros = new String[3];
        parametros[0] = ip;
        parametros[1] = porta+"";
        parametros[2] = pack;
        envia.execute(parametros);
    }

    public void obtemAmigos(MinhasCaronasFragment frag){
        String pack = Protocolo.Solicitacao.BUSCAR_AMIGOS+"|"+id;
        AsyncTaskObtemAmigos busca = new AsyncTaskObtemAmigos(frag);
        String[] parametros = new String[3];
        parametros[0] = ip;
        parametros[1] = porta+"";
        parametros[2] = pack;
        busca.execute(parametros);
    }

    public void buscarAmigos(InicioFragment frag, String email){
        String pack = Protocolo.Solicitacao.BUSCAR_USUARIO_EMAIL+"|"+email;
        AsyncTaskBuscarAmigos busca = new AsyncTaskBuscarAmigos(frag);
        String[] parametros = new String[3];
        parametros[0] = ip;
        parametros[1] = porta+"";
        parametros[2] = pack;
        busca.execute(parametros);
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

    public void enviarSolicitacaoAmizade(Context context, int idUsuario) {
        String pack = Protocolo.Solicitacao.SOLICITAR_AMIZADE+"|"+getUsuario()+"|"+idUsuario;
        AsyncTaskSolicitacaoAmizade solicitacao = new AsyncTaskSolicitacaoAmizade(context);
        String[] parametros = new String[3];
        parametros[0] = ip;
        parametros[1] = porta+"";
        parametros[2] = pack;
        solicitacao.execute(parametros);
    }

    public void desfazAmizade(Context context, int idAmigo){
        String pack = Protocolo.Solicitacao.DESFAZ_AMIGO+"|"+id+"|"+idAmigo;
        AsyncTaskDesFazAmigo solicitacao = new AsyncTaskDesFazAmigo(context);
        String[] parametros = new String[3];
        parametros[0] = ip;
        parametros[1] = porta+"";
        parametros[2] = pack;
        solicitacao.execute(parametros);
    }

    public void atualizarListaCaronas(QueroCaronaFragment frag) {
        String pack = Protocolo.Solicitacao.CARONAS_DISPONIVEIS+"|"+getId();
        AsyncTaskBuscarCaronasDisponiveis solicitacao = new AsyncTaskBuscarCaronasDisponiveis(frag);
        String[] parametros = new String[3];
        parametros[0] = ip;
        parametros[1] = porta+"";
        parametros[2] = pack;
        solicitacao.execute(parametros);
    }

    public void setAmizade(int idAmigo){
        this.idamigo = idAmigo;
    }
    public int getIdamigo(){
        return idamigo;
    }
}