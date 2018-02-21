package br.com.alysondantas.qcarona;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.alysondantas.qcarona.controller.Controller;


/**
 * A simple {@link Fragment} subclass.
 */
public class QueroCaronaFragment extends Fragment {

    private Spinner spinnerCidadeOrigem;
    private Spinner spinnerCidadeDestino;
    private Button  buttonVerifica;
    private Controller controller;
    private ListView listViewCaronas;

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
        buttonVerifica = getView().findViewById(R.id.btn_verificar_caronas);
        listViewCaronas = getView().findViewById(R.id.listViewCaronasDisponiveis);
        final QueroCaronaFragment t = this;
        buttonVerifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.verificarCaronaDisponivel(t, (String)spinnerCidadeOrigem.getSelectedItem(), (String)spinnerCidadeDestino.getSelectedItem());
            }
        });

        ArrayList usuariosExib = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, usuariosExib);
        listViewCaronas.setAdapter(arrayAdapter);
        listViewCaronas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // pega o o item selecionado com os dados da pessoa
                new AlertDialog.Builder(getContext())
                        .setTitle("Carona Ofertada")
                        .setMessage("Você deseja confirmar que quer essa carona?")
                        .setPositiveButton("sim",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(getContext(),"Enviando confirmação de carona", Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("não", null)
                        .show();

            }

        });

        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        List<String> nomes = new ArrayList<>();
        nomes.add("---");
        ArrayAdapter<String> arrayyAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, nomes);
        ArrayAdapter<String> spinnerArrayAdapter = arrayyAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCidadeOrigem.setAdapter(spinnerArrayAdapter);
        //super.onViewCreated(view, savedInstanceState);

        controller.atualizarCidadesDisponiveis(this);
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

    public void setLista(ArrayList list){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
        listViewCaronas.setAdapter(arrayAdapter);
    }
}
