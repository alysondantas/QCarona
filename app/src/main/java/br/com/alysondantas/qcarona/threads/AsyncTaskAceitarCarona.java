package br.com.alysondantas.qcarona.threads;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.com.alysondantas.qcarona.QueroCaronaFragment;
import br.com.alysondantas.qcarona.model.Protocolo;

/**
 * Created by marco on 21/02/2018.
 */

public class AsyncTaskAceitarCarona extends AsyncTask<String, Object, String> {

    private QueroCaronaFragment fragment;

    public AsyncTaskAceitarCarona(QueroCaronaFragment context) {

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
            //saida.flush();

            ObjectInputStream entrada = new ObjectInputStream(rec.getInputStream());//recebo o pacote do cliente
            Object object = entrada.readObject();
            publishProgress();
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
        if (s != null) {
            String[] result = s.split("\\|");
            if (result[0].equals(Protocolo.Notificacao.RESPOSTA_CONFIRMAR_CARONA+"")) {
                Toast.makeText(fragment.getContext(), "Reserva de carona realizada com sucesso", Toast.LENGTH_LONG).show();
            } else if (result[1].trim().equals("ERRO")) {
                Toast.makeText(fragment.getContext(), "Erro ao reservar.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(fragment.getContext(), "Houve um erro.", Toast.LENGTH_LONG).show();
        }
        super.onPostExecute(s);
    }
}
