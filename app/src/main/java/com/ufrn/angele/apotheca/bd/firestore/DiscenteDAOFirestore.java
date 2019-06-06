package com.ufrn.angele.apotheca.bd.firestore;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ufrn.angele.apotheca.bd.DiscenteDAO;
import com.ufrn.angele.apotheca.dominio.Discente;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class DiscenteDAOFirestore implements DiscenteDAO {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private Usuario usuario;
    private Map<String,Object> popularDados(Discente discente){
        Map<String, Object> post = new HashMap<>();
        post.put("id_usuario",discente.getId_usuario());
        post.put("cpf",discente.getCpf());
        post.put("id_discente",discente.getId_discente());
        post.put("id_tipo_discente",discente.getId_tipo_discente());
        post.put("matricula",discente.getMatricula());
        post.put("nome_curso",discente.getNome_curso());
        post.put("tipo_vinculo",discente.getTipo_vinculo());
        post.put("id_curso",discente.getId_curso());
        post.put("chave_usuario",discente.getChave_usuario());
        return post;
    }
    @Override
    public void inserir(Discente discente) {
        Map<String, Object> post = new HashMap<>();
        post = popularDados(discente);

        // Add a new document with a generated ID
        db.collection("usuario")
                .document(discente.getChave_usuario())
                .collection("discente")
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
    public void atualizar(Discente discente) {

    }

    @Override
    public void deletar(Discente discente) {

    }

    @Override
    public List<Discente> findByCPF(String cpf) {
        return null;
    }

    @Override
    public List<Discente> buscarTodos() {
        return null;
    }

    @Override
    public List<Discente> findByIdUsuario(int id_usuario) {
        return null;
    }
}
