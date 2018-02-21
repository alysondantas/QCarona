package br.com.alysondantas.qcarona;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.List;

import br.com.alysondantas.qcarona.controller.Controller;


/**
 * A simple {@link Fragment} subclass.
 */
public class DarCaronaFragment extends Fragment {

    private Spinner cidadeOrigem;
    private Spinner cidadeDestino;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button buttonSubmeter;

    private Controller controller;

    public DarCaronaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dar_carona, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        cidadeOrigem = getView().findViewById(R.id.spinnerCidadeOrigemSub);
        cidadeDestino = getView().findViewById(R.id.spinner2CidadeDestinoSub);
        datePicker = getView().findViewById(R.id.datePicker2);
        timePicker = getView().findViewById(R.id.timePicker2);
        buttonSubmeter = getView().findViewById(R.id.btn_submeter_carona);
        final DarCaronaFragment t = this;
        buttonSubmeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String data = datePicker.getDayOfMonth()+"/"+datePicker.getMonth()+"/"+datePicker.getYear();
                final String hora = timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute();
                new AlertDialog.Builder(getContext())
                        .setTitle("Ofertar carona")
                        .setMessage("Você deseja ofertar essa carona para dia " + data + " às " + hora+"?")
                        .setPositiveButton("sim",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        controller.submeterCaronaDisponivel(t,(String)cidadeOrigem.getSelectedItem(),(String)cidadeDestino.getSelectedItem(),data,hora);
                                        Toast.makeText(getContext(),"Enviando submissão de carona", Toast.LENGTH_LONG).show();
                                    }
                                })
                        .setNegativeButton("não", null)
                        .show();
            }
        });
        controller = Controller.getInstance();
        controller.atualizarCidadesDisponiveisSubmeter(this);
        super.onViewCreated(view, savedInstanceState);
    }

    public void adicionarCidadesOrigem(List<String> cidades){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, cidades);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cidadeOrigem.setAdapter(spinnerArrayAdapter);
    }


    public void adicionarCidadesDestino(List<String> cidades){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, cidades);
        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cidadeDestino.setAdapter(spinnerArrayAdapter);
    }
}
