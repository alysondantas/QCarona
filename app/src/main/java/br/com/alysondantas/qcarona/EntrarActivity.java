package br.com.alysondantas.qcarona;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import br.com.alysondantas.qcarona.controller.Controller;

public class EntrarActivity extends AppCompatActivity {
    private Controller controller;
    private String senha;
    private String email;
    private EditText editTextUser;
    private EditText editTextSenha;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);
        controller = Controller.getInstance();
        editTextSenha = (EditText) findViewById(R.id.editTextSenhaLogin);
        editTextUser = (EditText) findViewById(R.id.editTextEmailLogin);
        buttonLogin = (Button) findViewById(R.id.buttonEntrarLogin);



        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizaLogin();
            }
        });
    }


    public void realizaLogin(){
        try {
            email = editTextUser.getText().toString();
            senha = editTextSenha.getText().toString();
            Context contexto = getApplicationContext();
            controller.realizarLogin(email,senha,contexto);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
