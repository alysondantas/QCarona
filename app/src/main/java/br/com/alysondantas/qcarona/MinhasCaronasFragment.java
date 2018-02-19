package br.com.alysondantas.qcarona;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.alysondantas.qcarona.controller.Controller;


/**
 * A simple {@link Fragment} subclass.
 */
public class MinhasCaronasFragment extends Fragment {

    private ListView listaAmigos;
    private List<String> amigosExib;
    private Controller controller;

    public MinhasCaronasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_minhas_caronas, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaAmigos = getView().findViewById(R.id.listViewAmigos);
        controller = Controller.getInstance();
        amigosExib = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, amigosExib);

        controller.obtemAmigos(this);

        listaAmigos.setAdapter(arrayAdapter);
        listaAmigos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    public void setListaAmigos(ArrayList list){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
        listaAmigos.setAdapter(arrayAdapter);
    }

}
