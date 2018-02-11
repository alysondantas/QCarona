package br.com.alysondantas.qcarona;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button cadastrarbutton;
    private Button entrarbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cadastrarbutton = (Button) findViewById(R.id.cadastrarbutton);
        entrarbutton = (Button) findViewById(R.id.buttonEntrarMain);

        entrarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizaLogin();
            }
        });

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
