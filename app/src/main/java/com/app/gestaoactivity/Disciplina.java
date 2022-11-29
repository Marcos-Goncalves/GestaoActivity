package com.app.gestaoactivity;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Disciplina implements Serializable {
    @Exclude private String id;
    private String nome;
    private String horas;
    private String idProfessor;

    public Disciplina() {
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

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(String idProfessor) {
        this.idProfessor = idProfessor;
    }

    @Override
    public String toString() {
        return nome + " - " + horas + " horas";
    }
}
