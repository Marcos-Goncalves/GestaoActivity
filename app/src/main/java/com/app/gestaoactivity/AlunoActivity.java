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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AlunoActivity extends AppCompatActivity {
    private EditText edNome, edIdade, edEmail;
    private ListView listView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);

        edNome = findViewById(R.id.edNome);
        edIdade = findViewById(R.id.edIdade);
        edEmail = findViewById(R.id.edEmail);

        listView = findViewById(R.id.listView);
    }
    @Override
    protected void onResume() {
        super.onResume();
        listarAlunos();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno aluno = (Aluno) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), EditAlunoActivity.class);
                intent.putExtra("aluno", aluno);
                startActivity(intent);
            }
        });
    }


    public void cadastrarAluno(View view){
        Aluno aluno = new Aluno();
        DocumentReference document = db.collection("aluno").document();
        aluno.setNome(edNome.getText().toString());
        aluno.setIdade(edIdade.getText().toString());
        aluno.setEmail(edEmail.getText().toString());
        aluno.setId(document.getId());
        document.set(aluno).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AlunoActivity.this, "Aluno criado com sucesso!",
                        Toast.LENGTH_SHORT).show();
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
                    ArrayAdapter<Aluno> arrayAdapter = new ArrayAdapter<>(AlunoActivity.this, android.R.layout.simple_list_item_1, lista);
                    listView.setAdapter(arrayAdapter);
                }
            }
        });
    }
}