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
import com.ufrn.angele.apotheca.bd.VotoDAO;
import com.ufrn.angele.apotheca.dominio.Voto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import static android.support.constraint.Constraints.TAG;
import static com.ufrn.angele.apotheca.bd.firestore.PostagemFirebaseQueryLiveData.LOG_TAG;

public class VotoDAOFirestore implements VotoDAO {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Voto> listPV = new ArrayList<>();
    private List<Voto> listPN = new ArrayList<>();
    private List<Voto> listCV = new ArrayList<>();
    private List<Voto> listCN = new ArrayList<>();
    private int countPV=0;
    private int countPN=0;
    private int countCV=0;
    private int countCN=0;
    private Map<String,Object> popularDados(Voto voto){
        Map<String, Object> post = new HashMap<>();
        post.put("id_comentario",voto.getId_comentario());
        post.put("id_postagem",voto.getId_postagem());
        post.put("data_cadastro",voto.getData_cadastro());
        post.put("id_autor",voto.getId_autor());
        post.put("voto",voto.isVoto());
        post.put("negativacao",voto.isNegativacao());

        return post;
    }
    @Override
    public void inserir(Voto voto) {
        Map<String, Object> post = new HashMap<>();
        post = popularDados(voto);

        // Add a new document with a generated ID
        db.collection("postagem")
                .document(voto.getId_postagem())
                .collection("comentario")
                .document(voto.getId_comentario())
                .collection("voto")
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
    public void inserirPostagem(Voto voto) {
        Map<String, Object> post = new HashMap<>();
        post = popularDados(voto);

        // Add a new document with a generated ID
        db.collection("postagem")
                .document(voto.getId_postagem())
                .collection("voto")
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
    public void atualizar(Voto voto) {

    }

    @Override
    public void deletar(Voto voto) {

    }

    @Override
    public int countVotosPostagem(String id_postagem) {


        db.collection("postagem")
                .document(id_postagem)
                .collection("voto")
                .whereEqualTo("voto",true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(LOG_TAG, "Listen failed.", e);
                            return;
                        }
                        int count=0;
                        for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                            if(doc.getId()!=null){
                                count++;
                                Voto p = new Voto(0,
                                        doc.getString("id_postagem"),
                                        doc.getString("id_comentario"),
                                        doc.getBoolean("voto"),
                                        doc.getBoolean("negativacao"),
                                        doc.getLong("id_autor").intValue(),
                                        doc.getString("data_cadastro"));

                                listPV.add(p);
                            }

                        }
                        countPV=count;
                    }
                });
        return countPV;
    }

    @Override
    public int countNegativacoesPostagem(String id_postagem) {

        db.collection("postagem")
                .document(id_postagem)
                .collection("voto")
                .whereEqualTo("negativacao",true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(LOG_TAG, "Listen failed.", e);
                            return;
                        }
                        int count =0;
                        for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                            if(doc.getId()!=null){
                                count++;
                                Log.d("Consulta postagem",doc.getId());
                                Voto p = new Voto(0,
                                        doc.getString("id_postagem"),
                                        doc.getString("id_comentario"),
                                        doc.getBoolean("voto"),
                                        doc.getBoolean("negativacao"),
                                        doc.getLong("id_autor").intValue(),
                                        doc.getString("data_cadastro"));

                                listPN.add(p);
                            }

                        }
                        countPN =count;
                    }
                });
        return countPN;
    }

    @Override
    public int countVotosComentario(String id_postagem, String id_comentario) {

        db.collection("postagem")
                .document(id_postagem)
                .collection("comentario")
                .document(id_comentario)
                .collection("voto")
                .whereEqualTo("voto",true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(LOG_TAG, "Listen failed.", e);
                            return;
                        }
                        int count =0;
                        for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                            if(doc.getId()!=null){
                                count++;
                                Voto p = new Voto(0,
                                        doc.getString("id_postagem"),
                                        doc.getString("id_comentario"),
                                        doc.getBoolean("voto"),
                                        doc.getBoolean("negativacao"),
                                        doc.getLong("id_autor").intValue(),
                                        doc.getString("data_cadastro"));

                                listCV.add(p);
                            }

                        }
                        countCV=count;
                    }
                });
        return countCV;
    }

    @Override
    public int countNegativacoesComentario(String id_postagem, String id_comentario) {

        db.collection("postagem")
                .document(id_postagem)
                .collection("comentario")
                .document(id_comentario)
                .collection("voto")
                .whereEqualTo("negativacao",true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(LOG_TAG, "Listen failed.", e);
                            return;
                        }
                        int count=0;
                        for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                            if(doc.getId()!=null){
                                count++;
                                Voto p = new Voto(0,
                                        doc.getString("id_postagem"),
                                        doc.getString("id_comentario"),
                                        doc.getBoolean("voto"),
                                        doc.getBoolean("negativacao"),
                                        doc.getLong("id_autor").intValue(),
                                        doc.getString("data_cadastro"));

                                listCN.add(p);

                            }

                        }
                        countCN=count;
                    }
                });
        return countCN;
    }
}
