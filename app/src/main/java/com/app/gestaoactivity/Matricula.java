package com.app.gestaoactivity;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Matricula implements Serializable {
    @Exclude private String id;
    private String idAluno;
    private String idDisciplina;

    public Matricula() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(String idAluno) {
        this.idAluno = idAluno;
    }

    public String getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(String idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    @Override
    public String toString() {
        return "Número de matrícula: " + id;
    }
}
