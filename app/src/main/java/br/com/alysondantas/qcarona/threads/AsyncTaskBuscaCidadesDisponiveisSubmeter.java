package br.com.alysondantas.qcarona.threads;

import android.os.AsyncTask;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import br.com.alysondantas.qcarona.DarCaronaFragment;

/**
 * Created by marco on 20/02/2018.
 */

public class AsyncTaskBuscaCidadesDisponiveisSubmeter extends AsyncTask<String, Object, String> {
    private DarCaronaFragment frag;
    public AsyncTaskBuscaCidadesDisponiveisSubmeter(DarCaronaFragment frag) {
        this.frag = frag;
    }

    @Override
    protected String doInBackground(String... strings) {
        String recebido = null;
        try{
            String ip = strings[0];
            String portaS = strings[1];
            int porta = Integer.parseInt(portaS);
            String pack = strings[2];
            Socket rec = new Socket(ip,porta);
            ObjectOutputStream saida = new ObjectOutputStream(rec.getOutputStream());
            saida.writeObject(pack);


            ObjectInputStream entrada = new ObjectInputStream(rec.getInputStream());//recebo o pacote do cliente
            Object object = entrada.readObject();
            if ((object != null) && (object instanceof String)) {
                recebido = (String) object;
            }
            saida.close();//fecha a comunicação com o servidor
            entrada.close();
            rec.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return recebido;
    }

    @Override
    protected void onPostExecute(String s) {
        if(s != null) {
            String[] resultado = s.split("\\|");
            String[] cidades = resultado[1].split(";");
            List<String> cidadesList = new ArrayList<>();
            for (String cidade : cidades) {
                cidadesList.add(cidade);
            }
            frag.adicionarCidadesOrigem(cidadesList);
            frag.adicionarCidadesDestino(cidadesList);
            super.onPostExecute(s);
        }
    }
}
