package com.app.gestaoactivity;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Professor implements Serializable {
    @Exclude private String id;
    private String nome;
    private String idade;
    private String graduacao;

    public Professor() {
    }

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

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getGraduacao() {
        return graduacao;
    }

    public void setGraduacao(String graduacao) {
        this.graduacao = graduacao;
    }

    @Override
    public String toString() {
        return nome + " - " + graduacao;
    }
}
