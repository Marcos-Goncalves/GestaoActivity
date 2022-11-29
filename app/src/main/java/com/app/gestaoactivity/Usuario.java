package com.app.gestaoactivity;

import com.google.firebase.firestore.Exclude;

public class Usuario {
    @Exclude private String id;
    private String nome;

    public Usuario() {
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
}
