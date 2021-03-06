package br.com.alysondantas.qcarona.threads;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.regex.Pattern;

import br.com.alysondantas.qcarona.MinhasCaronasFragment;
import br.com.alysondantas.qcarona.controller.Controller;
import br.com.alysondantas.qcarona.model.Usuario;

/**
 * Created by alyso on 20/02/2018.
 */

public class AsyncTaskObtemPerfilAmigo extends AsyncTask<String, Void, String> {
    private Context context;
    private Controller controller;
    private MinhasCaronasFragment fragment;

    public AsyncTaskObtemPerfilAmigo(Context context, MinhasCaronasFragment fragment) {
        this.context = context;
        controller = Controller.getInstance();
        this.fragment = fragment;
    }

    @Override
    protected void onPreExecute(){
        Log.i("AsyncTask", "Exibindo ProgressDialog na tela Thread: " + Thread.currentThread().getName());
    }

    @Override
    protected String doInBackground(String... params) {
        String recebido = null;
        try{
            String ip = params[0];
            String portaS = params[1];
            int porta = Integer.parseInt(portaS);
            publishProgress();
            String pack = params[2];
            Socket rec = new Socket(ip,porta);
            //Enviando o nome do arquivo a ser baixado do servidor
            ObjectOutputStream saida = new ObjectOutputStream(rec.getOutputStream());
            saida.writeObject(pack);
            publishProgress();
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
            publishProgress();

        }catch(Exception e){
            e.printStackTrace();
        }
        return recebido;
    }

    @Override
    protected void onPostExecute(String result){
        if(result!=null) {
            result = result.trim();
            Log.i("AsyncRealizandoLogin", "Exibindo resultado Thread: " + result + " || " + Thread.currentThread().getName());
            if(result.equals("100")){
                Toast toast = Toast.makeText(context, "Usuário ou senha invalido, necessario login novamente.",Toast.LENGTH_SHORT);
                toast.show();
            }else{
                String informacoes[] = result.split(Pattern.quote("|"));
                if(informacoes[0].equals("102")){
                    //Toast toast = Toast.makeText(context, "Perfil Obtido com sucesso." + result,Toast.LENGTH_SHORT);
                    //toast.show();
                    String nome = informacoes[1];
                    String sobrenome = informacoes[2];
                    String data = informacoes[3];
                    String email = informacoes[4];
                    String numero = informacoes[5];
                    String qualificacao = informacoes[6];
                    String id = informacoes[7];
                    Usuario user = new Usuario();
                    user.setNome(nome);
                    user.setSobreNome(sobrenome);
                    user.setData(data);
                    user.setEmail(email);
                    user.setId(id);
                    user.setQualificacao(qualificacao);
                    user.setNumero(numero);
                    controller.setUserAux(user);
                    fragment.mudaParaAmigo();


                }else{
                    Toast toast = Toast.makeText(context, "Erro para realizar login.",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }else{
            Log.i("AsyncRealizandoLogin", "Erro ao baixar a informação " + Thread.currentThread().getName());
        }
        Log.i("AsyncRealizandoLogin", "Tirando ProgressDialog da tela Thread: " + Thread.currentThread().getName());

    }
}
