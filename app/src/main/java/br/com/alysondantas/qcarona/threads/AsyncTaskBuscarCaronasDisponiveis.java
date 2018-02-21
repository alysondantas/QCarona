package br.com.alysondantas.qcarona.threads;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import br.com.alysondantas.qcarona.QueroCaronaFragment;

/**
 * Created by marco on 15/02/2018.
 */

public class AsyncTaskBuscarCaronasDisponiveis extends AsyncTask<String, Object, String> {
    QueroCaronaFragment frag;

    public AsyncTaskBuscarCaronasDisponiveis(QueroCaronaFragment frag) {
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
        super.onPostExecute(s);
        if(s != null) {
            String[] reuslt = s.split("\\|");
            if (!reuslt[1].equals("")) {
                String[] caronas = reuslt[1].split(";");
                ArrayList<String> array = new ArrayList<>();
                for (String c : caronas){
                    array.add(c);
                }
                frag.setLista(array);
                publishProgress();
            }else {
                Toast.makeText(frag.getContext(), "Não foi encontrado nenhuma carona para essas cidades", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(frag.getContext(), "Não foi encontrado nenhuma carona para essas cidades", Toast.LENGTH_LONG).show();
        }

    }
}
