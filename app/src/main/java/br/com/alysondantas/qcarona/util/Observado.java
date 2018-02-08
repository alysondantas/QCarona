package br.com.alysondantas.qcarona.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 29/01/2018.
 */

public class Observado {
    private List<Observador> observadores;

    public Observado() {
        this.observadores = new ArrayList<>();
    }

    public void addObservador(Observador obs){
        observadores.add(obs);
    }

    public void notificar(Object obj){
        for (Observador observador: observadores) {
            observador.update(obj);
        }
    }
}
