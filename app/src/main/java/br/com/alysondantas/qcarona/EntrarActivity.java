package br.com.alysondantas.qcarona;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
<<<<<<< HEAD
import android.widget.Toast;
=======
import android.widget.ProgressBar;
import android.widget.TextView;
>>>>>>> c5348e22f34babf343e7362e3a357b714147bc08

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import br.com.alysondantas.qcarona.Exception.ErroInexperadoException;
import br.com.alysondantas.qcarona.Exception.SenhaIncorretaException;
import br.com.alysondantas.qcarona.Exception.UsuarioNaoCadastradoException;
import br.com.alysondantas.qcarona.controller.Controller;

public class EntrarActivity extends AppCompatActivity {
    private Controller controller;
    private String senha;
    private String email;
    private EditText editTextUser;
    private EditText editTextSenha;
    private Button buttonLogin;
    private ProgressBar progress;
    private TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);
        controller = Controller.getInstance();
        editTextSenha = (EditText) findViewById(R.id.editTextSenhaLogin);
        editTextUser = (EditText) findViewById(R.id.editTextEmailLogin);
        buttonLogin = (Button) findViewById(R.id.buttonEntrarLogin);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        texto = (TextView) findViewById(R.id.textViewProg);


        Log.i("AsyncTask", "Elementos de tela criados e atribuidos Thread: " + Thread.currentThread().getName());
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("AsyncTask", "Botão Clicado Thread: " + Thread.currentThread().getName());
                realizaLogin();
            }
        });



        /*buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizaLogin();
            }
        });*/
    }

    /*private void chamarAsyncTask(String endereco){
        TarefaDownload download = new TarefaDownload();
        Log.i("AsyncTask", "AsyncTask senado chamado Thread: " + Thread.currentThread().getName());
        download.execute(endereco);
    }*/


    public void realizaLogin(){
        try {
            email = editTextUser.getText().toString();
            senha = editTextSenha.getText().toString();
            editTextUser.setEnabled(false);
            editTextSenha.setEnabled(false);
            buttonLogin.setEnabled(false);
            controller.realizarLogin(email,senha,this, progress,texto, editTextSenha,editTextUser,buttonLogin);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SenhaIncorretaException e) {
            e.printStackTrace();
        } catch (UsuarioNaoCadastradoException e) {
            e.printStackTrace();
        } catch (ErroInexperadoException e) {
            e.printStackTrace();
        }
    }

    public void acessoliberado(){
        Intent intent = new Intent(this, AreaRestritaActivity.class);
        startActivity(intent);
    }
}
