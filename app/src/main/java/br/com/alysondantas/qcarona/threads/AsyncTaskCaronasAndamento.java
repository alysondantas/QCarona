package br.com.alysondantas.qcarona.threads;

import android.os.AsyncTask;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import br.com.alysondantas.qcarona.MinhasCaronasFragment;

/**
 * Created by marco on 21/02/2018.
 */

public class AsyncTaskCaronasAndamento extends AsyncTask<String, Object, String> {
   private MinhasCaronasFragment fragment;

    public AsyncTaskCaronasAndamento(MinhasCaronasFragment minhasCaronasFragment) {
        this.fragment = minhasCaronasFragment;
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
        String[] result  = s.split("\\|");
        String[] caronas = result[1].split(";");
        ArrayList<String> array = new ArrayList<>();
        for (String car : caronas){
            array.add(car);
        }
        fragment.setListaCaronas(array);
        super.onPostExecute(s);
    }
}
