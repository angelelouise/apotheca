package com.ufrn.angele.apotheca.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.ufrn.angele.apotheca.R;
import com.ufrn.angele.apotheca.bd.firestore.PostagemDAOFirestore;
import com.ufrn.angele.apotheca.dominio.Postagem;
import com.ufrn.angele.apotheca.dominio.Turma;
import com.ufrn.angele.apotheca.dominio.Usuario;
import com.ufrn.angele.apotheca.outros.Constants;
import com.ufrn.angele.apotheca.viewmodel.PostagemViewModel;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static android.support.constraint.Constraints.TAG;

public class CadastrarPostActivity extends AppCompatActivity {

    private ArrayList<Turma> mTurmas;
    private ArrayList<String> mTipos;

    private PostagemViewModel postagemViewModel;
    private PostagemDAOFirestore postagemDAOFirestore = new PostagemDAOFirestore();
    private Postagem p;
    private Usuario mUser;
    Map<String, Object> post = new HashMap<>();
    private int mSelecionada =-1;
    private int tipo_activity =-1;
    private List<Uri> uris = new ArrayList<>();;
    public static final String DATE_FORMAT = "dd.MM.yyyy 'às' HH:mm:ss";
    StringBuilder builder = new StringBuilder();
    private boolean editar =false;
    private Postagem mPostagem;
    private static class ViewHolder{
        TextView mTituloView, mDescricaoView, mTagView, mUris;
        Spinner mTipo,mTurma;
        ImageButton mAddAnexo;
        Button mPublicar;
    }
    private ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_post);
        postagemViewModel = new PostagemViewModel(getApplication());
        Intent intent = getIntent();
        editar = intent.getBooleanExtra("editar",false);
        if(!editar){
            mTurmas = (ArrayList<Turma>) intent.getSerializableExtra(Constants.INTENT_TURMA);
            mSelecionada =  intent.getIntExtra("selecionada",-1); //pega posição da turma selecionada na lista de turma
            mUser = (Usuario) intent.getSerializableExtra(Constants.INTENT_USER);
        }else{
            mPostagem = (Postagem) intent.getSerializableExtra(Constants.INTENT_POSTAGEM);
            mUser = (Usuario) intent.getSerializableExtra(Constants.INTENT_USER);
            Turma aux = new Turma();
            aux.setId_componente(mPostagem.getId_componente());
            aux.setNome_componente(mPostagem.getComponente());
            mTurmas = new ArrayList<>();
            mTurmas.add(aux);
        }


//        mTipos = new ArrayList<>();
//        mTipos.add(TIPO0);
//        mTipos.add(TIPO1);

        mViewHolder.mTituloView = findViewById(R.id.tituloText);
        mViewHolder.mDescricaoView = findViewById(R.id.descricaoText);
        mViewHolder.mTagView = findViewById(R.id.tagAutoCompleteTextView);
        mViewHolder.mPublicar = findViewById(R.id.publicar_button);
        mViewHolder.mAddAnexo = findViewById(R.id.anexoButton);
        mViewHolder.mTurma = findViewById(R.id.spinnerTurma);
        mViewHolder.mUris = findViewById(R.id.uris);
        // Creating adapter for spinner
        ArrayAdapter<Turma> turmaDataAdapter = new ArrayAdapter<Turma>(this, android.R.layout.simple_spinner_item, mTurmas);
        //ArrayAdapter<String> tipoDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mTipos);
        // Drop down layout style - list view with radio button
        turmaDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        tipoDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        mViewHolder.mTurma.setAdapter(turmaDataAdapter);
        if(mSelecionada > 0){
            mViewHolder.mTurma.setSelection(mSelecionada);
        }
        if(editar){
            mViewHolder.mTituloView.setText(mPostagem.getTitulo());
            mViewHolder.mDescricaoView.setText(mPostagem.getDescricao());
            //mViewHolder.mTagView.setText(mPostagem.getTitulo());
        }
//        mViewHolder.mTipo.setAdapter(tipoDataAdapter);
        mViewHolder.mAddAnexo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(CadastrarPostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    selectFile();
                }else{
                    ActivityCompat.requestPermissions(CadastrarPostActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });

        mViewHolder.mPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Turma t = (Turma) mViewHolder.mTurma.getSelectedItem();
                String turma = mViewHolder.mTurma.getSelectedItem().toString();
//                String tipo = mViewHolder.mTipo.getSelectedItem().toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date today = Calendar.getInstance().getTime();
                if(mViewHolder.mTituloView.getText().toString().isEmpty() || mViewHolder.mDescricaoView.getText().toString().isEmpty()){
                    Toast toast = Toast.makeText(getApplicationContext(),"Por favor, defina um título e uma descrição para sua postagem",Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    p = new Postagem(mViewHolder.mTituloView.getText().toString(), t.getNome_componente() , dateFormat.format(today));
                    p.setDescricao(mViewHolder.mDescricaoView.getText().toString());
                    p.setAtivo(true);
                    p.setId_componente(t.getId_componente());
                    p.setComponente(turma);
                    p.setUrl_autor(mUser.getUrl_foto());
//                if(tipo == TIPO0){
//                    p.setTipo_postagem(0);
//                }else{
//                    p.setTipo_postagem(1);
//                }
                    p.setId_autor(mUser.getId_usuario());

                    //cadastrar tag
                    if(editar){
                        FirebaseFirestore
                                .getInstance()
                                .collection("postagem")
                                .document(mPostagem.getId_postagem())
                                .set(postagemDAOFirestore.popularDados(p),SetOptions.merge() )
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (!uris.isEmpty()){
                                            uploadFiles(uris,mPostagem.getId_postagem());
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                    }
                                });
                    }else {
                        FirebaseFirestore
                                .getInstance()
                                .collection("postagem")
                                .add(postagemDAOFirestore.popularDados(p))
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                        if (!uris.isEmpty()){
                                            uploadFiles(uris,documentReference.getId());
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                    }
                                });
                    }

                    //postagemViewModel.inserir(p);

                    finish();
                }

            }
        });


    }

    private List<String> uploadFiles(List<Uri> uris, String Idpost) {
        String filename;
        final List<String> urls = new ArrayList<>();
        for (Uri u: uris) {
            filename = System.currentTimeMillis()+"";
            FirebaseStorage
                    .getInstance()
                    .getReference()
                    .child(Idpost)
                    .child(filename)
                    .putFile(u)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //urls.add(taskSnapshot.getStorage().getDownloadUrl().getResult().toString());
                        }
                    });
            Map<String, Object> file = new HashMap<>();
            file.put("filename",filename);
            FirebaseFirestore
                    .getInstance()
                    .collection("postagem")
                    .document(Idpost)
                    .collection("anexo")
                    .add(file)
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
        return urls;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectFile();
        }else{
            Toast.makeText(CadastrarPostActivity.this, "Por favor, conceda a permissão", Toast.LENGTH_LONG).show();
        }
    }

    private void selectFile() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(intent,86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==86 && resultCode==RESULT_OK && data!=null){

            uris.add(data.getData());
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Path p = Paths.get(String.valueOf(data.getData()));
                String file = p.getFileName().toString();
                builder.append(file + "\n");
            }else{
                builder.append(data.getData() + "\n");
            }
//          String aux = data.getData().getPath().substring(data.getData().getPath().lastIndexOf("\\")+1);

            mViewHolder.mUris.setText(builder.toString());

        }
    }
}
