package br.com.alysondantas.qcarona;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.alysondantas.qcarona.controller.Controller;

public class AreaRestritaActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Controller controller;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new InicioFragment()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new QueroCaronaFragment()).commit();
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new MinhasCaronasFragment()).commit();
                    return true;
                case R.id.navigation_minhaconta:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new MinhaContaFragment()).commit();
                    return true;
                case R.id.navigation_darcarona:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new DarCaronaFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_restrita);
        controller = Controller.getInstance();
        controller.obtemPerfil(this,controller.getId());

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(savedInstanceState == null){
            //adiciona fragmento inicial
            getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new InicioFragment()).commit();
        }
    }

    public void openFragment()
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new DarCaronaFragment()).commit();
    }

}
