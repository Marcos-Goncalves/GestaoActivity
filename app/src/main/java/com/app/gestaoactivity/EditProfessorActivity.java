package com.app.gestaoactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfessorActivity extends AppCompatActivity {
    private Professor professor;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_professor);

        professor = (Professor) getIntent().getSerializableExtra("professor");
        ((TextView)findViewById(R.id.edNome)).setText(professor.getNome());
        ((TextView)findViewById(R.id.edIdade)).setText(professor.getIdade());
        ((TextView)findViewById(R.id.edGraduacao)).setText(professor.getGraduacao());
    }

    public void editProfessor(View view){
        String nome = ((TextView)findViewById(R.id.edNome)).getText().toString();
        String idade = ((TextView)findViewById(R.id.edIdade)).getText().toString();
        String graduacao = ((TextView)findViewById(R.id.edGraduacao)).getText().toString();

        DocumentReference document = db.collection("professor").document(professor.getId());
        document.update(
                        "nome", nome,
                        "idade", idade,
                        "graduacao", graduacao
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditProfessorActivity.this, "Professor atualizado com sucesso!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}