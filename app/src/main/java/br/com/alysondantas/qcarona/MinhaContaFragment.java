package br.com.alysondantas.qcarona;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

import br.com.alysondantas.qcarona.controller.Controller;


public class MinhaContaFragment extends Fragment {
    private TextView textViewIdMinhaconta;
    private TextView textViewQualificacaoMinhaconta;
    private TextView textViewNomeMinhaConta;
    private TextView textViewSobreNomeMinhaconta;
    private TextView textViewIdadeMinhaConta;
    private TextView textViewEmailMinhaConta;
    private TextView textViewNumeroMinhaConta;
    private EditText editTextMinhaConta;
    private Button buttonNaoMinhaconta;
    private Button buttonSimMinhaconta;
    private TextView textViewDialogoMnhaconta;
    private RatingBar ratingBar;
    private boolean selecionado;
    private int cont = 0;
    private String nome = "";
    private String sobrenome = "";
    private String email = "";
    private String senha = "";
    private String senha2 = "";
    private String tel = "";
    private String data = "";
    private ProgressBar progressBar;
    private Controller controller;

    public MinhaContaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_minha_conta, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ratingBar = getView().findViewById(R.id.ratingbarMinhaconta);
        ratingBar.setEnabled(false);
        float valorFixo = 2.5f;
        ratingBar.setRating(valorFixo);

        selecionado = false;
        textViewIdMinhaconta = getView().findViewById(R.id.textViewIdMinhaconta);
        textViewQualificacaoMinhaconta = getView().findViewById(R.id.textViewQualificacaoMinhaconta);
        textViewNomeMinhaConta = getView().findViewById(R.id.textViewNomeMinhaConta);
        textViewSobreNomeMinhaconta = getView().findViewById(R.id.textViewSobreNomeMinhaconta);
        textViewIdadeMinhaConta = getView().findViewById(R.id.textViewIdadeMinhaConta);
        textViewEmailMinhaConta = getView().findViewById(R.id.textViewEmailMinhaConta);
        textViewNumeroMinhaConta = getView().findViewById(R.id.textViewNumeroMinhaConta);
        editTextMinhaConta = getView().findViewById(R.id.editTextMinhaConta);
        buttonNaoMinhaconta = getView().findViewById(R.id.buttonNaoMinhaconta);
        buttonSimMinhaconta = getView().findViewById(R.id.buttonSimMinhaconta);
        textViewDialogoMnhaconta = getView().findViewById(R.id.textViewDialogoMnhaconta);
        progressBar = getView().findViewById(R.id.progressBarEditar);
        controller = Controller.getInstance();

        buttonSimMinhaconta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickNext();
            }
        });

        buttonNaoMinhaconta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPreview();
            }
        });
    }

    public void clickNext(){
        if(!selecionado){
            switch (cont){
                case 0:
                    if(!editTextMinhaConta.getText().toString().equals("")){
                        cont++;
                        nome = editTextMinhaConta.getText().toString();
                        editTextMinhaConta.setText(sobrenome);
                        textViewDialogoMnhaconta.setText(R.string.editarsobrenome);
                        buttonNaoMinhaconta.setText(R.string.anterior);
                    }else{
                        Toast toast = Toast.makeText(getView().getContext(), "Inserir um nome valido.",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    break;
                case 1:
                    if(!editTextMinhaConta.getText().toString().equals("")){
                        cont++;
                        sobrenome = editTextMinhaConta.getText().toString();
                        editTextMinhaConta.setText(email);
                        editTextMinhaConta.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
                        textViewDialogoMnhaconta.setText(R.string.editaremail);
                    }else{
                        Toast toast = Toast.makeText(getView().getContext(), "Inserir um sobrenome valido.",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    break;
                case 2:
                    if(!editTextMinhaConta.getText().toString().equals("")){
                        cont++;
                        email = editTextMinhaConta.getText().toString();
                        editTextMinhaConta.setText(senha);
                        editTextMinhaConta.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        textViewDialogoMnhaconta.setText(R.string.editarsenha1);
                    }else {
                        Toast toast = Toast.makeText(getView().getContext(), "Inserir um email valido.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    break;
                case 3:
                    if(!editTextMinhaConta.getText().toString().equals("")){
                        cont++;
                        senha = editTextMinhaConta.getText().toString();
                        editTextMinhaConta.setText(senha2);
                        textViewDialogoMnhaconta.setText(R.string.editarsenha2);
                    }else {
                        Toast toast = Toast.makeText(getView().getContext(), "Inserir um email valido.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    break;
                case 4:
                    if(!editTextMinhaConta.getText().toString().equals("")){
                        if(editTextMinhaConta.getText().toString().equals(senha)){
                            cont++;
                            senha2 = editTextMinhaConta.getText().toString();
                            editTextMinhaConta.setText(tel);
                            editTextMinhaConta.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                            textViewDialogoMnhaconta.setText(R.string.editartel);
                        }else{
                            Toast toast = Toast.makeText(getView().getContext(), "A senha não confere.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }else {
                        Toast toast = Toast.makeText(getView().getContext(), "Inserir uma senha valida.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    break;
                case 5:
                    if(!editTextMinhaConta.getText().toString().equals("")){
                        cont++;
                        tel = editTextMinhaConta.getText().toString();
                        editTextMinhaConta.setText(data);
                        buttonSimMinhaconta.setText(R.string.cadastrar);
                        editTextMinhaConta.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        textViewDialogoMnhaconta.setText(R.string.editardata);
                    }else {
                        Toast toast = Toast.makeText(getView().getContext(), "Inserir um numero de telefone valido.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    break;
                case 6:
                    if(!editTextMinhaConta.getText().toString().equals("")){
                        data = editTextMinhaConta.getText().toString();
                        editTextMinhaConta.setText(nome);
                        cont = 0;
                        buttonSimMinhaconta.setEnabled(false);
                        selecionado = false;
                        buttonNaoMinhaconta.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                        textViewDialogoMnhaconta.setText(R.string.aguardar);
                        editTextMinhaConta.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                        try{
                            controller.editar(getView().getContext(),nome,sobrenome,email,senha,data,tel,progressBar,buttonSimMinhaconta,textViewDialogoMnhaconta);
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                    }else {
                        Toast toast = Toast.makeText(getView().getContext(), "Inserir uma data valida.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    break;
            }
        }else{
            selecionado = true;
            buttonNaoMinhaconta.setText(R.string.cancelar);
            buttonSimMinhaconta.setText(R.string.proximo);
            buttonNaoMinhaconta.setVisibility(View.VISIBLE);
            textViewDialogoMnhaconta.setText(R.string.editarnome);
        }
    }

    public void clickPreview(){
        switch (cont){
            case 0:
                buttonNaoMinhaconta.setVisibility(View.INVISIBLE);
                break;
            case 1:
                buttonNaoMinhaconta.setVisibility(View.INVISIBLE);
                editTextMinhaConta.setVisibility(View.INVISIBLE);
                cont--;
                textViewDialogoMnhaconta.setText(R.string.dialogominhaconta);
                editTextMinhaConta.setText(nome);
                editTextMinhaConta.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                break;
            case 2:
                cont--;
                editTextMinhaConta.setText(sobrenome);
                textViewIdMinhaconta.setText(R.string.editarsobrenome);
                editTextMinhaConta.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                break;
            case 3:
                cont--;
                editTextMinhaConta.setText(email);
                textViewIdMinhaconta.setText(R.string.editaremail);
                editTextMinhaConta.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
                break;
            case 4:
                cont--;
                editTextMinhaConta.setText(senha);
                textViewIdMinhaconta.setText(R.string.editarsenha1);
                editTextMinhaConta.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case 5:
                cont--;
                editTextMinhaConta.setText(senha2);
                textViewIdMinhaconta.setText(R.string.editarsenha2);
                editTextMinhaConta.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case 6:
                cont--;
                editTextMinhaConta.setText(tel);
                textViewIdMinhaconta.setText(R.string.editartel);
                editTextMinhaConta.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                break;
        }
    }

}
