package br.com.alysondantas.qcarona;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import br.com.alysondantas.qcarona.controller.Controller;

public class EntrarActivity extends AppCompatActivity {
    private Controller controller;
    private String senha;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);
        controller = Controller.getInstance();
    }


    public void realizaLogin(){
        try {
            controller.realizarLogin(email,senha);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
