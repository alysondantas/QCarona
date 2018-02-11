package br.com.alysondantas.qcarona;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

import br.com.alysondantas.qcarona.controller.Controller;

public class CadastrarActivity extends AppCompatActivity {
    private EditText editTextNome;
    private EditText editTextSobrenome;
    private EditText editTextEmail;
    private EditText editTextData;
    private EditText editTextSenha;
    private EditText editTextSenha2;
    private EditText editTextCEP;
    private EditText editTextTel;
    private Button buttonCad;
    private Controller controller;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        editTextNome = findViewById(R.id.editTextCadNome);
        editTextSobrenome = findViewById(R.id.editTextCadSobreNome);
        editTextEmail = findViewById(R.id.editTextCadEmail);
        editTextData = findViewById(R.id.editTextCadData);
        editTextSenha = findViewById(R.id.editTextCadSenha);
        editTextSenha2 = findViewById(R.id.editTextCadSenha2);
        editTextCEP = findViewById(R.id.editTextCadCEP);
        editTextTel = findViewById(R.id.editTextCadTel);
        buttonCad = findViewById(R.id.buttonCadCad);
        controller = Controller.getInstance();
        progressBar = findViewById(R.id.progressBarCad);

        buttonCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });
    }

    public void cadastrar(){
        String senha = editTextSenha.getText().toString();
        String senha2 = editTextSenha2.getText().toString();
        if(!senha.equals(senha2)){
            Toast toast = Toast.makeText(this, "Senhas não conferem.",Toast.LENGTH_SHORT);
            toast.show();
        }else if(senha.equals("") || editTextEmail.getText().toString().equals("") || editTextTel.getText().toString().equals("") || editTextSobrenome.getText().toString().equals("") || editTextNome.getText().toString().equals("") || editTextCEP.getText().toString().equals("") || editTextData.getText().toString().equals("")){
            Toast toast = Toast.makeText(this, "Todos os campos devem estar preenchidos.",Toast.LENGTH_SHORT);
            toast.show();
        }else{
            try {
                controller.cadastra(this,editTextNome.getText().toString(),editTextSobrenome.getText().toString(),editTextEmail.getText().toString(),editTextSenha.getText().toString(),editTextData.getText().toString(),editTextTel.getText().toString(),editTextCEP.getText().toString(), progressBar, buttonCad);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }
}
