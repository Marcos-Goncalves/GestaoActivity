package com.app.gestaoactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

public class DisciplinaActivity extends AppCompatActivity {
    private EditText edNome, edCarga;
    private ListView listView;
    private Spinner spProfessor;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String idProfessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina);

        spProfessor = findViewById(R.id.spProfessor);
        edNome = findViewById(R.id.edNome);
        edCarga = findViewById(R.id.edCarga);

        listView = findViewById(R.id.listView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarProfessor();
        listarDisciplina();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Disciplina disciplina = (Disciplina) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), EditDisciplinaActivity.class);
                intent.putExtra("disciplina", disciplina);
                startActivity(intent);
            }
        });
    }

    public void listarProfessor(){
        List<Professor> listaProf = new ArrayList<>();
        db.collection("professor").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    Professor professor;
                    for(QueryDocumentSnapshot document : task.getResult()){
                        professor = document.toObject(Professor.class);
                        listaProf.add(professor);
                    }
                    ArrayAdapter<Professor> arrayAdapter = new ArrayAdapter<>(DisciplinaActivity.this, android.R.layout.simple_spinner_dropdown_item, listaProf);
                    spProfessor.setAdapter(arrayAdapter);
                }
            }
        });

        spProfessor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Professor professor = (Professor) parent.getItemAtPosition(position);
                idProfessor = professor.getId();
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
                    ArrayAdapter<Disciplina> arrayAdapter = new ArrayAdapter<>(DisciplinaActivity.this, android.R.layout.simple_list_item_1, lista);
                    listView.setAdapter(arrayAdapter);
                }
            }
        });
    }

    public void cadastrarDisciplina(View view){
        Disciplina disciplina = new Disciplina();
        DocumentReference document = db.collection("disciplina").document();
        disciplina.setNome(edNome.getText().toString());
        disciplina.setHoras(edCarga.getText().toString());
        disciplina.setIdProfessor(idProfessor);
        disciplina.setId(document.getId());
        document.set(disciplina).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(DisciplinaActivity.this, "Disciplina criada com sucesso!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}