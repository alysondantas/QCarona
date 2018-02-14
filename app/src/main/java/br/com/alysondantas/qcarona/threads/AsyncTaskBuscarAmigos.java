package br.com.alysondantas.qcarona.threads;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import br.com.alysondantas.qcarona.InicioFragment;

/**
 * Created by marco on 13/02/2018.
 */

public class AsyncTaskBuscarAmigos extends AsyncTask<String, Object, String> {

    private InicioFragment fragment;
    private ArrayList<String> listTemp;

    public AsyncTaskBuscarAmigos(InicioFragment frag) {
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
            String nome = info[2].trim();
            String sobrenome = info[3].trim();
            ArrayList array = new ArrayList();
            array.add(nome + " " + sobrenome);
            this.listTemp = array;
            publishProgress();
        }
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        fragment.setLista(listTemp);
        //super.onProgressUpdate(values);
    }
}
