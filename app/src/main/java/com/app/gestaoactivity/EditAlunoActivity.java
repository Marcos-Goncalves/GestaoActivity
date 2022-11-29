package com.app.gestaoactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditAlunoActivity extends AppCompatActivity {
    private Aluno aluno;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_aluno);

        aluno = (Aluno) getIntent().getSerializableExtra("aluno");
        ((TextView)findViewById(R.id.edNome)).setText(aluno.getNome());
        ((TextView)findViewById(R.id.edIdade)).setText(aluno.getIdade());
        ((TextView)findViewById(R.id.edEmail)).setText(aluno.getEmail());
    }

    public void editAluno(View view){
        String nome = ((TextView)findViewById(R.id.edNome)).getText().toString();
        String idade = ((TextView)findViewById(R.id.edIdade)).getText().toString();
        String email = ((TextView)findViewById(R.id.edEmail)).getText().toString();

        DocumentReference document = db.collection("aluno").document(aluno.getId());
        document.update(
                        "nome", nome,
                        "idade", idade,
                        "email", email
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditAlunoActivity.this, "Aluno atualizado com sucesso!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}