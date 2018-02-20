package br.com.alysondantas.qcarona.threads;

import android.os.AsyncTask;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import br.com.alysondantas.qcarona.InicioFragment;
import br.com.alysondantas.qcarona.MinhasCaronasFragment;

/**
 * Created by alyso on 19/02/2018.
 */

public class AsyncTaskObtemAmigos  extends AsyncTask<String, Object, String> {

    private MinhasCaronasFragment fragment;
    private ArrayList<String> listTemp;

    public AsyncTaskObtemAmigos(MinhasCaronasFragment frag){
        this.fragment = frag;
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
            //Enviando o nome do arquivo a ser baixado do servidor
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
        String[] info = s.split("\\|");
        if(!info[1].equals("ERRO")){
            ArrayList array = new ArrayList();
            String[] amigos = info[1].split("&");
            for (int i = 0; i< amigos.length ; i++){
                String[] ind = amigos[i].split("/");
                String id = ind[0].trim();
                String nome = ind[1].trim();
                String email = ind[2].trim();
                array.add(id + ": " + nome + " " + email);
            }
            this.listTemp = array;
            publishProgress();
        }
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        if (listTemp !=null) {
            fragment.setListaAmigos(listTemp);
        }
        //super.onProgressUpdate(values);
    }
}
