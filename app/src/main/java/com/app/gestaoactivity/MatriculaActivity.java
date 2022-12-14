package com.app.gestaoactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MatriculaActivity extends AppCompatActivity {
    private Spinner alunos, disciplinas;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String idAluno, idDisciplina;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matricula);

        alunos = findViewById(R.id.spAluno);
        disciplinas = findViewById(R.id.spDisciplina);

        listView = findViewById(R.id.listViewMatricula);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarAlunos();
        listarDisciplina();
        listarMatricula();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Matricula matricula = (Matricula) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), EditMatriculaActivity.class);
                intent.putExtra("matricula", matricula);
                startActivity(intent);
            }
        });
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
                    ArrayAdapter<Aluno> arrayAdapter = new ArrayAdapter<>(MatriculaActivity.this, android.R.layout.simple_spinner_dropdown_item, lista);
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

    public void listarDisciplina(){
        List<Disciplina> lista = new ArrayList<>();
        db.collection("disciplina").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    Disciplina disciplina;
                    for(QueryDocumentSnapshot document : task.getResult()){
                        disciplina = document.toObject(Disciplina.class);
                        lista.add(disciplina);
                    }
                    ArrayAdapter<Disciplina> arrayAdapter = new ArrayAdapter<>(MatriculaActivity.this, android.R.layout.simple_spinner_dropdown_item, lista);
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

    public void listarMatricula(){
        List<Matricula> lista = new ArrayList<>();
        db.collection("matricula").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    Matricula matricula;
                    for(QueryDocumentSnapshot document : task.getResult()){
                        matricula = document.toObject(Matricula.class);
                        lista.add(matricula);
                    }
                    ArrayAdapter<Matricula> arrayAdapter = new ArrayAdapter<>(MatriculaActivity.this, android.R.layout.simple_list_item_1, lista);
                    listView.setAdapter(arrayAdapter);
                }
            }
        });
    }

    public void cadastrarMatricula(View view){
        Matricula matricula = new Matricula();
        DocumentReference document = db.collection("matricula").document();
        matricula.setIdAluno(idAluno);
        matricula.setIdDisciplina(idDisciplina);
        matricula.setId(document.getId());
        document.set(matricula).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(MatriculaActivity.this, "Matr??cula realizada com sucesso!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}