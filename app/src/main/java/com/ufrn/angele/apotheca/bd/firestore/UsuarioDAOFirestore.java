package com.ufrn.angele.apotheca.bd.firestore;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.ufrn.angele.apotheca.bd.UsuarioDAO;
import com.ufrn.angele.apotheca.dominio.Usuario;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import static android.support.constraint.Constraints.TAG;
import static com.ufrn.angele.apotheca.bd.firestore.PostagemFirebaseQueryLiveData.LOG_TAG;

public class UsuarioDAOFirestore implements UsuarioDAO {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Usuario usuario = new Usuario();
    private Map<String,Object> popularDados(Usuario usuario){
        Map<String, Object> post = new HashMap<>();
        post.put("id_usuario",usuario.getId_usuario());
        post.put("cpf_cnpj",usuario.getCpf_cnpj());
        post.put("email",usuario.getEmail());
        post.put("login",usuario.getLogin());
        post.put("nome",usuario.getNome());
        post.put("url_foto",usuario.getUrl_foto());

        return post;
    }
    @Override
    public void inserir(Usuario usuario) {
        Map<String, Object> post = new HashMap<>();
        post = popularDados(usuario);

        // Add a new document with a generated ID
        db.collection("usuario")
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
    public void atualizar(Usuario usuario) {

    }

    @Override
    public void deletar(Usuario usuario) {

    }

    @Override
    public Usuario findByCPF(Long cpf) {
        db.collection("usuario")
                .whereEqualTo("cpf_cnpj",cpf)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(LOG_TAG, "Listen failed.", e);
                            return;
                        }
                        for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                            if(doc.getId()!=null){
                                usuario.setId_usuario(doc.getLong("id_usuario").intValue());
                                usuario.setEmail(doc.getString("email"));
                                usuario.setUrl_foto(doc.getString("url_foto"));
                                usuario.setNome(doc.getString("nome"));
                                usuario.setLogin(doc.getString("login"));
                                usuario.setCpf_cnpj(doc.getLong("cpf_cnpj"));
                            }

                        }
                    }
                });
        return usuario;
    }

    @Override
    public Usuario findByIdUsuario(int id_usuario) {
        db.collection("usuario")
                .whereEqualTo("id_usuario",id_usuario)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(LOG_TAG, "Listen failed.", e);
                            return;
                        }
                        for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                            if(doc.getId()!=null){
                                usuario.setId_usuario(doc.getLong("id_usuario").intValue());
                                usuario.setEmail(doc.getString("email"));
                                usuario.setUrl_foto(doc.getString("url_foto"));
                                usuario.setNome(doc.getString("nome"));
                                usuario.setLogin(doc.getString("login"));
                                usuario.setCpf_cnpj(doc.getLong("cpf_cnpj"));
                            }

                        }
                    }
                });
        return usuario;
    }
}
