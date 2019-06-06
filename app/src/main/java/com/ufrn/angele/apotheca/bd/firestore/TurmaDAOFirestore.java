package com.ufrn.angele.apotheca.bd.firestore;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ufrn.angele.apotheca.bd.TurmaDAO;
import com.ufrn.angele.apotheca.dominio.Turma;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class TurmaDAOFirestore implements TurmaDAO {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private Usuario usuario;
    private Map<String,Object> popularDados(Turma turma){
        Map<String, Object> post = new HashMap<>();
        post.put("id_componente",turma.getId_componente());
        post.put("ano",turma.getAno());
        post.put("codigo_componente",turma.getCodigo_componente());
        post.put("id_turma",turma.getId_turma());
        post.put("nome_componente",turma.getNome_componente());
        post.put("id_discente",turma.getId_discente());
        post.put("chave_usuario",turma.getChave_usuario());
        return post;
    }
    @Override
    public void inserir(Turma tuma) {
        Map<String, Object> post = new HashMap<>();
        post = popularDados(tuma);

        // Add a new document with a generated ID
        db.collection("usuario")
                .document(tuma.getChave_usuario())
                .collection("turma")
                .add(post)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    @Override
    public void atualizar(Turma turma) {

    }

    @Override
    public void deletar(Turma turma) {

    }

    @Override
    public List<Turma> findByIdDiscente(int id_discente) {
        return null;
    }
}
