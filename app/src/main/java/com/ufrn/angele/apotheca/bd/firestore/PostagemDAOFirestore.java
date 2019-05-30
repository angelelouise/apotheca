package com.ufrn.angele.apotheca.bd.firestore;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ufrn.angele.apotheca.bd.PostagemDAO;
import com.ufrn.angele.apotheca.dominio.Postagem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class PostagemDAOFirestore implements PostagemDAO {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Map<String,Object> popularDados(Postagem postagem){
        Map<String, Object> post = new HashMap<>();
        post.put("descricao",postagem.getDescricao());
        post.put("titulo",postagem.getTitulo());
        post.put("id_turma",postagem.getId_componente());
        //post.put("id_postagem",postagem.getId_postagem());
        post.put("id_usuario",postagem.getId_autor());
        post.put("turma",postagem.getTurma());
        post.put("data_cadastro",postagem.getData_cadastro());
        post.put("ativo",postagem.isAtivo());

        return post;
    }
    public void inserir(Postagem postagem){
        Map<String, Object> post = new HashMap<>();
        post = popularDados(postagem);

        // Add a new document with a generated ID
        db.collection("postagem")
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
    public void atualizar(Postagem postagem) {

    }

    @Override
    public void deletar(Postagem postagem) {

    }

    @Override
    public LiveData<Postagem> findById(Long id) {
        return null;
    }

    @Override
    public LiveData<List<Postagem>> findByUsuario(Long id_usuario) {
        return null;
    }

    @Override
    public LiveData<List<Postagem>> findByTurma(Long id_turma) {
        return null;
    }

    @Override
    public LiveData<List<Postagem>> buscarTodas(int id) {
//        return new PostagemFirebaseQueryLiveData(db.collection("postagem").whereGreaterThanOrEqualTo(documentId(), 0));

        return new PostagemFirebaseQueryLiveData(db.collection("postagem").whereEqualTo("id_turma",id));
    }
}