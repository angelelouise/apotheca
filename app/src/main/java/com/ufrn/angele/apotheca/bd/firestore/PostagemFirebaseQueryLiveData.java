package com.ufrn.angele.apotheca.bd.firestore;

import android.arch.lifecycle.LiveData;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.ufrn.angele.apotheca.dominio.Postagem;

import java.util.ArrayList;
import java.util.List;


public class PostagemFirebaseQueryLiveData  extends LiveData<List<Postagem>> {
    public static final String LOG_TAG = "FirebaseQueryLiveData";

    private final Query query;
    private final MyValueEventListener listener = new MyValueEventListener();
    private List<Integer>ids = new ArrayList<>();
    private ListenerRegistration registration;

    public PostagemFirebaseQueryLiveData(Query query, List<Integer>ids) {
        this.query = query;
        this.ids=ids;
    }

    public PostagemFirebaseQueryLiveData(CollectionReference ref, List<Integer>ids) {
        this.query = ref;
        this.ids=ids;
    }

    @Override
    protected void onActive() {
        Log.d(LOG_TAG, "onActive");
        registration = query.addSnapshotListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(LOG_TAG, "onInactive");
        registration.remove();
    }

    private class MyValueEventListener implements EventListener<QuerySnapshot> {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onEvent(@Nullable QuerySnapshot value,
                            @Nullable FirebaseFirestoreException e) {

            if (e != null) {
                Log.w(LOG_TAG, "Listen failed.", e);
                return;
            }

            List<Postagem> postagens = new ArrayList<>();

            for(DocumentSnapshot doc : value.getDocuments()){
                if(doc.getId()!=null){
                    if(ids.contains(doc.getLong("id_componente").intValue()) ){
                        Postagem p = new Postagem(doc.getString("titulo"),doc.getString("componente"),doc.getString("data_cadastro"));
                        p.setId_autor(doc.getLong("id_usuario").intValue());
                        p.setId_postagem(doc.getId());
                        p.setId_componente(doc.getLong("id_componente").intValue());
                        p.setDescricao(doc.getString("descricao"));
                        p.setAtivo(doc.getBoolean("ativo"));
                        p.setUrl_autor(doc.getString("url_autor"));
                        postagens.add(p);
                    }
                }

            }

            setValue(postagens);
            Log.d(LOG_TAG, "Postagem retornadas");
        }
    }
}
