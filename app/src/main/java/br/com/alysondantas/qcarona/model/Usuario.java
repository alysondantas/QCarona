package br.com.alysondantas.qcarona.model;

/**
 * Created by alyso on 15/02/2018.
 */

public class Usuario {
    private String id;
    private String nome;
    private String sobreNome;
    private String email;
    private String numero;
    private String data;
    private String qualificacao;

    public Usuario(){
        id = "";
        nome = "";
        sobreNome = "";
        email = "";
        numero = "";
        data = "";
        qualificacao = "";
    }

    //get e sets
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getQualificacao() {
        return qualificacao;
    }

    public void setQualificacao(String qualificacao) {
        this.qualificacao = qualificacao;
    }

}
