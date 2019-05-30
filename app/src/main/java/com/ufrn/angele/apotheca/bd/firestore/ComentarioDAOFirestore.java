package com.ufrn.angele.apotheca.bd.firestore;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.ufrn.angele.apotheca.bd.ComentarioDAO;
import com.ufrn.angele.apotheca.dominio.Comentario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class ComentarioDAOFirestore implements ComentarioDAO {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Map<String,Object> popularDados(Comentario comentario){
        Map<String, Object> post = new HashMap<>();
        post.put("titulo",comentario.getTitulo());
        post.put("id_autor",comentario.getId_autor());
        post.put("id_postagem",comentario.getId_postagem());
        post.put("data_cadastro",comentario.getData_cadastro());
        post.put("escolhido",comentario.isEscolhido());

        return post;
    }
    @Override
    public void inserir(Comentario comentario) {
        Map<String, Object> post = new HashMap<>();
        post = popularDados(comentario);

        // Add a new document with a generated ID
        db.collection("postagem")
                .document(comentario.getId_postagem())
                .collection("comentario")
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
    public void atualizar(Comentario comentario) {

    }

    @Override
    public void deletar(Comentario comentario) {

    }

    @Override
    public LiveData<List<Comentario>> findById(String id) {
        return new ComentarioFirebaseQueryLiveData(db
                .collection("postagem")
                .document(id)
                .collection("comentario"));
    }

    @Override
    public LiveData<List<Comentario>> findByUsuario(Long id_autor) {
        return null;
    }
}
