package com.app.gestaoactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MenuActivity extends AppCompatActivity {
    private FirebaseUser user;
    private TextView edNome;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        user = FirebaseAuth.getInstance().getCurrentUser();
        edNome = findViewById(R.id.textViewMenu);
    }
    @Override
    protected void onStart() {
        super.onStart();

        DocumentReference document = db.collection("usuario").document(user.getUid());
        document.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                edNome.setText("Olá, " + value.getString("nome") + "!");
            }
        });
    }

    public void menuDisciplina(View view){
        Intent intent = new Intent(getApplicationContext(), DisciplinaActivity.class);
        startActivity(intent);
    }

    public void menuMatricula(View view){
        Intent intent = new Intent(getApplicationContext(), MatriculaActivity.class);
        startActivity(intent);
    }

    public void menuProfessor(View view){
        Intent intent = new Intent(getApplicationContext(), ProfessorActivity.class);
        startActivity(intent);
    }

    public void menuAluno(View view){
        Intent intent = new Intent(getApplicationContext(), AlunoActivity.class);
        startActivity(intent);
    }

    public void sairUsuario(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}