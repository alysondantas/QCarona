package br.com.alysondantas.qcarona;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.security.NoSuchAlgorithmException;

import br.com.alysondantas.qcarona.controller.Controller;

public class MainActivity extends AppCompatActivity {

    private Button cadastrarbutton;
    private Button entrarbutton;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cadastrarbutton = (Button) findViewById(R.id.cadastrarbutton);
        entrarbutton = (Button) findViewById(R.id.buttonCadCad);

        entrarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizaLogin();
            }
        });

        controller = Controller.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences(controller.getArquivoPreferencia(),0);
        controller.setSharedPrefecencs(sharedPreferences);
        if(sharedPreferences.contains("user")){
            String user = sharedPreferences.getString("user","erro");
            String senha = sharedPreferences.getString("senha","erro");
            try {
                controller.realizaLoginWakeup(user,senha,this);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        cadastrarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CadastrarActivity.class);
                startActivity(intent);

            }
        });
    }


    public void realizaLogin(){
        Intent intent = new Intent(MainActivity.this, EntrarActivity.class);
        startActivity(intent);
    }
}
