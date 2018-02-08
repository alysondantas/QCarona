package br.com.alysondantas.qcarona;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.alysondantas.qcarona.controller.Controller;

public class ConfigServidor extends AppCompatActivity {

    private EditText editTextIPServidor;
    private Button   buttonConectar;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_servidor);

        controller = Controller.getInstance();
        editTextIPServidor = findViewById(R.id.ip_servidor);
        buttonConectar = findViewById(R.id.btn_conectar);
    }

    public void conectarServidor(View view){
        String ip = editTextIPServidor.getText().toString().trim();
        controller.setIpServidor(ip, 1099);
        Toast.makeText(getApplicationContext(),"O servidor principal foi mudado para " + ip + ".",Toast.LENGTH_SHORT).show();
    }
}
