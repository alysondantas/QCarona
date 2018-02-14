package br.com.alysondantas.qcarona.threads;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.com.alysondantas.qcarona.controller.Controller;
import br.com.alysondantas.qcarona.model.Protocolo;

/**
 * Created by marco on 14/02/2018.
 */

public class AsyncTaskSolicitacaoAmizade extends AsyncTask<String, Object, String> {
    Context context;

    public AsyncTaskSolicitacaoAmizade(Context context) {
        this.context = context;
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
        if (s != null && !s.equals("")) {
            String info[] = s.split("\\|");
            if (info[0].equals(Protocolo.Notificacao.SOLICITACAO_AMIZ_ENVIADA + "")) {
                Toast.makeText(context, "A solicitação de amizade foi enviada", Toast.LENGTH_SHORT).show();
            }
            if (info[0].equals(Protocolo.Notificacao.JA_EXISTE_SOLICITACAO_AMIZ + "")) {
                Toast.makeText(context, "Já existe uma solicitação de amizade. Tenha esperança, o usuario vai aceitar.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, "Desculpe, houve algum problema. Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
        }
    }

}
