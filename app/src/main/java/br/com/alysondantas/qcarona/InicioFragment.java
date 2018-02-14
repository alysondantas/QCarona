package br.com.alysondantas.qcarona;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import br.com.alysondantas.qcarona.controller.Controller;


/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment {

    Controller controller;

    private ListView lista;
    private List<String> usuariosExib;
    private EditText editTextEmail;
    private Button botaoPesq;

    public InicioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lista = getView().findViewById(R.id.listView);
        editTextEmail = (EditText) getView().findViewById(R.id.editTextEmail);

        controller = Controller.getInstance();
        usuariosExib = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, usuariosExib);
        lista.setAdapter(arrayAdapter);
        botaoPesq = getView().findViewById(R.id.btn_buscar_amigo);

        botaoPesq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListenerBotaoPesq(view);
            }
        });
    }

    private void onClickListenerBotaoPesq(View view){
        controller.buscarAmigos(this, editTextEmail.getText().toString().trim());
    }

    public void setLista(ArrayList list){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
        lista.setAdapter(arrayAdapter);
    }
}
