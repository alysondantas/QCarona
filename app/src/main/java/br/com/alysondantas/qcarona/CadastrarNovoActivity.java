package br.com.alysondantas.qcarona;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

import br.com.alysondantas.qcarona.controller.Controller;

public class CadastrarNovoActivity extends AppCompatActivity {
    private EditText editText;
    private TextView textView;
    private Button button;
    private int cont;
    private String nome = "";
    private String sobrenome = "";
    private String email = "";
    private String senha = "";
    private String senha2 = "";
    private String tel = "";
    private String cep = "";
    private String data = "";
    private ProgressBar progressBar;
    private Controller controller;
    private int progresso = 12;
    private int decrementa = -12;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_novo);

        editText = findViewById(R.id.editTextCadNovo);
        textView = findViewById(R.id.textViewBemvindoCad);
        button = findViewById(R.id.buttonCadNovo);
        progressBar = findViewById(R.id.progressBarCadNovo);
        controller = Controller.getInstance();
        cont = 0;
        button2 = findViewById(R.id.buttonCadNovo2);
        button2.setEnabled(false);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPreview();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickNext();
            }
        });
    }

    public void clickNext(){
        switch (cont){
            case 0:
            if(!editText.getText().toString().equals("")){
                cont++;
                nome = editText.getText().toString();
                editText.setText(sobrenome);
                textView.setText(R.string.qualsobrenome);
                progressBar.incrementProgressBy(progresso);
                button2.setEnabled(true);
            }else{
                Toast toast = Toast.makeText(this, "Inserir um nome valido.",Toast.LENGTH_SHORT);
                toast.show();
            }
            break;
            case 1:
                if(!editText.getText().toString().equals("")){
                    cont++;
                    sobrenome = editText.getText().toString();
                    editText.setText(email);
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
                    textView.setText(R.string.digaemail);
                    progressBar.incrementProgressBy(progresso);
                }else{
                    Toast toast = Toast.makeText(this, "Inserir um sobrenome valido.",Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 2:
                if(!editText.getText().toString().equals("")){
                    cont++;
                    email = editText.getText().toString();
                    editText.setText(senha);
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    textView.setText(R.string.digasenha);
                    progressBar.incrementProgressBy(progresso);
                }else {
                    Toast toast = Toast.makeText(this, "Inserir um email valido.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 3:
                if(!editText.getText().toString().equals("")){
                    cont++;
                    senha = editText.getText().toString();
                    editText.setText(senha2);
                    textView.setText(R.string.senhanovamente);
                    progressBar.incrementProgressBy(progresso);
                }else {
                    Toast toast = Toast.makeText(this, "Inserir um email valido.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 4:
                if(!editText.getText().toString().equals("")){
                    if(editText.getText().toString().equals(senha)){
                        cont++;
                        senha2 = editText.getText().toString();
                        editText.setText(tel);
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                        textView.setText(R.string.digatel);
                        progressBar.incrementProgressBy(progresso);
                    }else{
                        Toast toast = Toast.makeText(this, "A senha não confere.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }else {
                    Toast toast = Toast.makeText(this, "Inserir uma senha valida.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 5:
                if(!editText.getText().toString().equals("")){
                    cont++;
                    tel = editText.getText().toString();
                    editText.setText(cep);
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    textView.setText(R.string.digacep);
                    progressBar.incrementProgressBy(progresso);
                }else {
                    Toast toast = Toast.makeText(this, "Inserir um numero de telefone valido.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 6:
                if(!editText.getText().toString().equals("")){
                    cont++;
                    cep = editText.getText().toString();
                    editText.setText(data);
                    editText.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_NORMAL);
                    textView.setText(R.string.digadata);
                    button.setText(R.string.cadastrar);
                    progressBar.incrementProgressBy(progresso);
                }else {
                    Toast toast = Toast.makeText(this, "Inserir um cep valido.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case 7:
                if(!editText.getText().toString().equals("")){
                    data = editText.getText().toString();
                    editText.setText(nome);
                    cont = 0;
                    button2.setEnabled(false);
                    button.setEnabled(false);
                    textView.setText(R.string.aguardar);
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                    progressBar.incrementProgressBy(progresso);
                    try{
                        progressBar.setProgress(0);
                        controller.cadastra(this,nome,sobrenome,email,senha,data,tel,cep,progressBar,button,textView);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast toast = Toast.makeText(this, "Inserir uma data valida.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
        }
    }

    public void clickPreview(){
        switch (cont){
            case 0:
                button2.setEnabled(false);
                break;
            case 1:
                button2.setEnabled(false);
                cont--;
                textView.setText(R.string.bemvindocad);
                editText.setText(nome);
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                progressBar.incrementProgressBy(decrementa);
                break;
            case 2:
                cont--;
                editText.setText(sobrenome);
                textView.setText(R.string.qualsobrenome);
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                progressBar.incrementProgressBy(decrementa);
                break;
            case 3:
                cont--;
                editText.setText(email);
                textView.setText(R.string.digaemail);
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
                progressBar.incrementProgressBy(decrementa);
                break;
            case 4:
                cont--;
                editText.setText(senha);
                textView.setText(R.string.digasenha);
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                progressBar.incrementProgressBy(decrementa);
                break;
            case 5:
                cont--;
                editText.setText(senha2);
                textView.setText(R.string.senhanovamente);
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                progressBar.incrementProgressBy(decrementa);
                break;
            case 6:
                cont--;
                editText.setText(tel);
                textView.setText(R.string.digatel);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                progressBar.incrementProgressBy(decrementa);
                break;
            case 7:
                cont--;
                editText.setText(cep);
                textView.setText(R.string.digacep);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                progressBar.incrementProgressBy(decrementa);
                break;
        }
    }
}
