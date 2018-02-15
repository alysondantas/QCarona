package br.com.alysondantas.qcarona;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.com.alysondantas.qcarona.controller.Controller;


/**
 * A simple {@link Fragment} subclass.
 */
public class QueroCaronaFragment extends Fragment {

    private Spinner spinnerCidadeOrigem;
    private Spinner spinnerCidadeDestino;
    private Button  buttonAtualizar;
    private Controller controller;

    public QueroCaronaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quero_carona, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        controller = Controller.getInstance();

        spinnerCidadeOrigem = getView().findViewById(R.id.spinner_cidade_origem);
        spinnerCidadeDestino = getView().findViewById(R.id.spinner_cidade_destino);
        buttonAtualizar = getView().findViewById(R.id.btn_atualizar_quero_carona);
        final QueroCaronaFragment q = this;
        buttonAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.atualizarListaCaronas(q);
            }
        });

        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        List<String> nomes = new ArrayList<>();
        nomes.add("---");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, nomes);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCidadeOrigem.setAdapter(spinnerArrayAdapter);
        //super.onViewCreated(view, savedInstanceState);
    }

    public void adicionarCidadesOrigem(List<String> cidades){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, cidades);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCidadeOrigem.setAdapter(spinnerArrayAdapter);
    }


    public void adicionarCidadesDestino(List<String> cidades){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, cidades);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCidadeDestino.setAdapter(spinnerArrayAdapter);
    }

    public void atualizarValores(List<String> cidadesOrigem, List<String> cidadesDestino){
        adicionarCidadesOrigem(cidadesOrigem);
        adicionarCidadesDestino(cidadesDestino);
    }
}
