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

public class ProfessorActivity extends AppCompatActivity {
    private EditText edNome, edIdade, edGraduacao;
    private ListView listView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

        edNome = findViewById(R.id.edNome);
        edIdade = findViewById(R.id.edIdade);
        edGraduacao = findViewById(R.id.edGraduacao);

        listView = findViewById(R.id.listView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarProfessor();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Professor professor = (Professor) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), EditProfessorActivity.class);
                intent.putExtra("professor", professor);
                startActivity(intent);
            }
        });
    }

    public void cadastrarProfessor(View view){
        Professor professor = new Professor();
        DocumentReference document = db.collection("professor").document();
        professor.setNome(edNome.getText().toString());
        professor.setIdade(edIdade.getText().toString());
        professor.setGraduacao(edGraduacao.getText().toString());
        professor.setId(document.getId());
        document.set(professor).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ProfessorActivity.this, "Professor criado com sucesso!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void listarProfessor(){
        List<Professor> lista = new ArrayList<>();
        db.collection("professor").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    Professor professor;
                    for(QueryDocumentSnapshot document : task.getResult()){
                        professor = document.toObject(Professor.class);
                        lista.add(professor);
                    }
                    ArrayAdapter<Professor> arrayAdapter = new ArrayAdapter<>(ProfessorActivity.this, android.R.layout.simple_list_item_1, lista);
                    listView.setAdapter(arrayAdapter);
                }
            }
        });
    }
}