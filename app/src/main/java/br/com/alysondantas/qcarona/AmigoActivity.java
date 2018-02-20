package br.com.alysondantas.qcarona;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import br.com.alysondantas.qcarona.controller.Controller;

import static android.widget.Toast.LENGTH_SHORT;

public class AmigoActivity extends AppCompatActivity {

    private Controller controller;
    private String nome = "";
    private String sobrenome = "";
    private String email = "";
    private String tel = "";
    private String data = "";
    private int id;
    private TextView textViewNomeAmigo;
    private TextView textViewSobreNomeAmigo;
    private TextView textViewIdadeAmigo;
    private TextView textViewEmailAmigo;
    private TextView textViewNumeroAmigo;
    private TextView textViewQualificacaoAmigo;
    private RatingBar ratingBar;
    private TextView textViewidAmigo;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigo);

        controller = Controller.getInstance();
        controller.obtemPerfil(this,controller.getIdamigo());

        textViewNomeAmigo = findViewById(R.id.textViewNomeAmigo);
        textViewSobreNomeAmigo = findViewById(R.id.textViewSobrenomeAmigo);
        textViewIdadeAmigo = findViewById(R.id.textViewDataAmigo);
        textViewEmailAmigo = findViewById(R.id.textViewemailAmigo);
        textViewNumeroAmigo = findViewById(R.id.textViewTelAmigo);
        textViewQualificacaoAmigo = findViewById(R.id.textViewQualificAmigo);
        textViewidAmigo = findViewById(R.id.textViewidAmigo);
        ratingBar = findViewById(R.id.ratingbarAmigo);
        button = findViewById(R.id.buttonDesfazAmigo);
        ratingBar.setEnabled(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desfazerAmizade();
            }
        });

        setPerfil();
        colocaEstrelas();
    }

    public void setPerfil(){
        nome = controller.getUserAux().getNome();
        textViewNomeAmigo.setText(nome);
        sobrenome = controller.getUserAux().getSobreNome();
        textViewSobreNomeAmigo.setText(sobrenome);
        data = controller.getUserAux().getData();
        textViewIdadeAmigo.setText(data);
        email = controller.getUserAux().getEmail();
        textViewEmailAmigo.setText(email);
        tel = controller.getUserAux().getNumero();
        textViewNumeroAmigo.setText(tel);
        textViewQualificacaoAmigo.setText(controller.getUserAux().getQualificacao());
        textViewidAmigo.setText("O id do amigo é: " + controller.getUserAux().getId());

    }

    public void colocaEstrelas(){
        String valorS = controller.getUserAux().getQualificacao() + "f";
        valorS = valorS.trim();
        float valor = Float.parseFloat(valorS);
        ratingBar.setRating(valor);
    }

    public void desfazerAmizade(){
        new AlertDialog.Builder(this)
                .setTitle("Desfazer de amizade")
                .setMessage("Você deseja desfazer a amizade com " + nome + "?")
                .setPositiveButton("sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                controller.desfazAmizade(AmigoActivity.this, controller.getIdamigo());

                            }
                        })
                .setNegativeButton("não", null)
                .show();
    }
}
