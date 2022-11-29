package com.app.gestaoactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EditMatriculaActivity extends AppCompatActivity {
    private Matricula matricula;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Spinner alunos, disciplinas;
    private String idAluno, idDisciplina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_matricula);

        matricula = (Matricula) getIntent().getSerializableExtra("matricula");
        alunos = findViewById(R.id.spAluno);
        disciplinas = findViewById(R.id.spDisciplina);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarAlunos();
        listarDisciplina();
    }

    public void listarAlunos(){
        List<Aluno> lista = new ArrayList<>();
        db.collection("aluno").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    Aluno aluno;
                    for(QueryDocumentSnapshot document : task.getResult()){
                        aluno = document.toObject(Aluno.class);
                        lista.add(aluno);
                    }
                    ArrayAdapter<Aluno> arrayAdapter = new ArrayAdapter<>(EditMatriculaActivity.this, android.R.layout.simple_spinner_dropdown_item, lista);
                    alunos.setAdapter(arrayAdapter);
                }
            }
        });

        alunos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Aluno aluno = (Aluno) parent.getItemAtPosition(position);
                idAluno = aluno.getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void listarDisciplina() {
        List<Disciplina> lista = new ArrayList<>();
        db.collection("disciplina").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Disciplina disciplina;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        disciplina = document.toObject(Disciplina.class);
                        lista.add(disciplina);
                    }
                    ArrayAdapter<Disciplina> arrayAdapter = new ArrayAdapter<>(EditMatriculaActivity.this, android.R.layout.simple_spinner_dropdown_item, lista);
                    disciplinas.setAdapter(arrayAdapter);
                }
            }
        });

        disciplinas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Disciplina disciplina = (Disciplina) parent.getItemAtPosition(position);
                idDisciplina = disciplina.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void editMatricula(View view){
        DocumentReference document = db.collection("matricula").document(matricula.getId());
        document.update(
                        "idAluno", idAluno,
                        "idDisciplina", idDisciplina
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditMatriculaActivity.this, "Matrícula atualizada com sucesso!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}