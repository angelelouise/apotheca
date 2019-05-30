package com.ufrn.angele.apotheca.bd.firestore;

import android.arch.lifecycle.LiveData;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.ufrn.angele.apotheca.dominio.Comentario;

import java.util.ArrayList;
import java.util.List;

public class ComentarioFirebaseQueryLiveData extends LiveData<List<Comentario>>  {
    public static final String LOG_TAG = "FirebaseQueryLiveData";

    private final Query query;
    private final MyValueEventListener listener = new MyValueEventListener();

    private ListenerRegistration registration;

    public ComentarioFirebaseQueryLiveData(Query query) {
        this.query = query;
    }

    public ComentarioFirebaseQueryLiveData(CollectionReference ref) {
        this.query = ref;
    }

//    public ComentarioFirebaseQueryLiveData(Task<QuerySnapshot> task) {
//        this.task = task;
//    }
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

            List<Comentario> comentarios = new ArrayList<>();

            for(DocumentSnapshot doc : value.getDocuments()){
                if(doc.getId()!=null){
                    Comentario p = new Comentario(doc.getId(),
                            doc.getString("id_postagem"),
                            doc.getLong("id_autor").intValue(),
                            doc.getString("data_cadastro"),
                            doc.getBoolean("escolhido"),
                            doc.getString("titulo"));

                    comentarios.add(p);
                }

            }

            setValue(comentarios);
            Log.d(LOG_TAG, "Comentarios retornados");
        }
    }
}
