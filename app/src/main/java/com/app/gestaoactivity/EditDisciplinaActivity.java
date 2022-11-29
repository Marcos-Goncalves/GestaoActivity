package com.app.gestaoactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class EditDisciplinaActivity extends AppCompatActivity {
    private Disciplina disciplina;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Spinner spProfessor;
    private String idProfessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_disciplina);

        disciplina = (Disciplina) getIntent().getSerializableExtra("disciplina");
        ((TextView)findViewById(R.id.edNome)).setText(disciplina.getNome());
        ((TextView)findViewById(R.id.edCarga)).setText(disciplina.getHoras());

        spProfessor = findViewById(R.id.spProfessor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarProfessor();
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
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter(EditDisciplinaActivity.this, android.R.layout.simple_spinner_dropdown_item, listaProf);
                    spProfessor.setAdapter(arrayAdapter);
                    //spProfessor.setSelection(arrayAdapter.getPosition(disciplina.getIdProfessor()));
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

    public void editDisciplina(View view){
        String nome = ((TextView)findViewById(R.id.edNome)).getText().toString();
        String carga = ((TextView)findViewById(R.id.edCarga)).getText().toString();

        DocumentReference document = db.collection("disciplina").document(disciplina.getId());
        document.update(
                        "nome", nome,
                        "horas", carga,
                        "idProfessor", idProfessor
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditDisciplinaActivity.this, "Disciplina atualizada com sucesso!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}