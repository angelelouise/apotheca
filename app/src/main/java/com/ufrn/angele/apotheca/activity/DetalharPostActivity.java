package com.ufrn.angele.apotheca.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.ufrn.angele.apotheca.R;
import com.ufrn.angele.apotheca.adapters.ComentarioAdapter;
import com.ufrn.angele.apotheca.adapters.ComentarioAdapterListener;
import com.ufrn.angele.apotheca.dominio.Comentario;
import com.ufrn.angele.apotheca.dominio.Postagem;
import com.ufrn.angele.apotheca.dominio.Usuario;
import com.ufrn.angele.apotheca.dominio.Voto;
import com.ufrn.angele.apotheca.outros.CircleTransform;
import com.ufrn.angele.apotheca.outros.Constants;
import com.ufrn.angele.apotheca.viewmodel.ComentarioViewModel;
import com.ufrn.angele.apotheca.viewmodel.UsuarioViewModel;
import com.ufrn.angele.apotheca.viewmodel.VotoViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.ufrn.angele.apotheca.bd.firestore.PostagemFirebaseQueryLiveData.LOG_TAG;

public class DetalharPostActivity extends AppCompatActivity {
    private Postagem mPostagem;
    private List<Comentario> comentarios;
    private ComentarioViewModel comentarioViewModel;
    private HashMap<Comentario, Usuario> mapComentario = new HashMap<>();
    private HashMap<Comentario, Integer> mapVotos = new HashMap<>();
    private HashMap<Comentario, Integer> mapNegativacoes = new HashMap<>();
    private UsuarioViewModel usuarioViewModel;
    private VotoViewModel votoViewModel;
    private Usuario mUser;
    private class ViewHolder{
        TextView titulo, descricao, turma, post_count_votos, post_count_negativacoes;
        android.support.v7.widget.Toolbar toolbar;
        RecyclerView lista_comentarios;
        ComentarioAdapter comentarioAdapter;
        ImageButton cadastrar_comentario, post_vote, post_downvote, post_report, destacar;
        EditText titulo_comentario;
        ImageView avatar;
    }
    private ViewHolder mViewHolder = new ViewHolder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhar_post);


        Intent intent = getIntent();
        mPostagem = (Postagem) intent.getSerializableExtra(Constants.INTENT_POSTAGEM);
        mUser = (Usuario ) intent.getSerializableExtra(Constants.INTENT_USER);

        mViewHolder.toolbar = findViewById(R.id.toolbar2);
        mViewHolder.turma = findViewById(R.id.detalhes_turma);
        mViewHolder.titulo= findViewById(R.id.detalhar_post_titulo);
        mViewHolder.descricao= findViewById(R.id.detalhar_post_descricao);
        mViewHolder.post_vote = findViewById(R.id.post_vote);
        mViewHolder.post_downvote = findViewById(R.id.post_downvote);
        mViewHolder.post_count_votos = findViewById(R.id.post_count_vote);
        mViewHolder.post_count_negativacoes = findViewById(R.id.post_count_downvote);
        mViewHolder.titulo_comentario = findViewById(R.id.detalhar_post_comentario_descricao);
        mViewHolder.cadastrar_comentario = findViewById(R.id.detalhar_post_publicar_comentario);
        mViewHolder.post_report = findViewById(R.id.post_report);
        mViewHolder.destacar = findViewById(R.id.comentario_destacar);
        //set postagem
        mViewHolder.turma.setText(mPostagem.getComponente());
        mViewHolder.titulo.setText(mPostagem.getTitulo());
        mViewHolder.descricao.setText(mPostagem.getDescricao());
        //pegar toolbar
        setSupportActionBar(mViewHolder.toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        //atualizar contadores
        //inativar destaque se o usuário não for o autor da postagem
        if (mPostagem.getId_autor()!= mUser.getId_usuario()){
            mViewHolder.destacar.setVisibility(View.INVISIBLE);
        }
        //set comentario adapter
        mViewHolder.avatar = findViewById(R.id.detalhar_post_comentario_avatar);
        Glide.with(this).load(mUser.getUrl_foto())
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mViewHolder.avatar);
        mViewHolder.lista_comentarios = findViewById(R.id.detalhar_post_lista_comentarios);
        comentarios = new ArrayList<Comentario>();
        votoViewModel = ViewModelProviders.of(this).get(VotoViewModel.class);
        mViewHolder.comentarioAdapter = new ComentarioAdapter(DetalharPostActivity.this,
                comentarios,
                mapVotos,
                mapNegativacoes,
                new ComentarioAdapterListener() {
                    @Override
                    public void voteOnClick(View v, int position) {
                        Voto voto = new Voto(0,
                                mPostagem.getId_postagem(),
                                comentarios.get(position).getId(),
                                true,
                                false,
                                mUser.getId_usuario(),
                                new Date().toString());
                        votoViewModel.inserir(voto);
                        //new atualizarVoto().execute(comentarios.get(position));
                        atualizaVotosComentarios(mPostagem,comentarios.get(position));
                        mViewHolder.comentarioAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void downvoteOnClick(View v, int position) {
                        Voto downvote = new Voto(0,
                                mPostagem.getId_postagem(),
                                comentarios.get(position).getId(),
                                false,
                                true,
                                mUser.getId_usuario(),
                                new Date().toString());
                        votoViewModel.inserir(downvote);
                        //new atualizarNegativacao().execute(comentarios.get(position));
                        atualizaVotosComentarios(mPostagem,comentarios.get(position));
                        mViewHolder.comentarioAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void destacarOnClick(View v, int position) {
                        Comentario top = comentarios.get(position);
                        destacarComentario(top);
                        mViewHolder.comentarioAdapter.notifyDataSetChanged();

                    }
                });
        mViewHolder.lista_comentarios.setAdapter(mViewHolder.comentarioAdapter);
        mViewHolder.lista_comentarios.setLayoutManager(new LinearLayoutManager(this));
        //set comentario viewmodel live data
        usuarioViewModel = ViewModelProviders.of(this).get(UsuarioViewModel.class);
        comentarioViewModel = ViewModelProviders.of(this).get(ComentarioViewModel.class);
        comentarioViewModel.getListaComentarios(mPostagem.getId_postagem()).observe(this, new Observer<List<Comentario>>() {
            @Override
            public void onChanged(@Nullable List<Comentario> comentario) {
                //postagemAdapter.setPalavras(post);
                comentarios.clear();
                comentarios.addAll(comentario);
                for (Comentario c:comentario) {
                    atualizaVotosComentarios(mPostagem,c);
                    if (c.isEscolhido()){
                        Comentario top = c;
                        comentarios.remove(c);
                        comentarios.add(0,top);
                    }
                    mViewHolder.comentarioAdapter.notifyDataSetChanged();
                }


            }
        });

        mViewHolder.cadastrar_comentario.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Comentario comentario = new Comentario(
                                "",
                                mPostagem.getId_postagem(),
                                mPostagem.getId_autor(),
                                new Date().toString(),
                                false,
                                mViewHolder.titulo_comentario.getText().toString(),
                                mUser.getNome(),
                                mUser.getUrl_foto());
                        comentarioViewModel.inserir(comentario);
                        mViewHolder.titulo_comentario.setText("");
                    }
                }
        );
//        new atualizarVotoPostagem().execute(mPostagem);
//        new atualizarNegativacaoPostagem().execute(mPostagem);
        atualizaVotosPostagem(mPostagem.getId_postagem());
        //listener dos votos/downvotes do post
        mViewHolder.post_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Voto post_vote = new Voto(0,
                        mPostagem.getId_postagem(),
                        "",
                        true,
                        false,
                        mUser.getId_usuario(),
                        new Date().toString());
                votoViewModel.inserirPostagem(post_vote);
                //new atualizarVotoPostagem().execute(mPostagem);
                atualizaVotosPostagem(mPostagem.getId_postagem());
            }
        });
        mViewHolder.post_downvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Voto post_vote = new Voto(0,
                        mPostagem.getId_postagem(),
                        "",
                        false,
                        true,
                        mUser.getId_usuario(),
                        new Date().toString());
                votoViewModel.inserirPostagem(post_vote);
                atualizaVotosPostagem(mPostagem.getId_postagem());
                //new atualizarNegativacaoPostagem().execute(mPostagem);
            }
        });

        mViewHolder.post_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent form = new Intent(DetalharPostActivity.this, FormReport.class);
                form.putExtra(Constants.INTENT_POSTAGEM, mPostagem);
                form.putExtra(Constants.INTENT_USER,mUser);
                startActivity(form);
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    private void atualizaVotosPostagem(String id_postagem){
        FirebaseFirestore
                .getInstance()
                .collection("postagem")
                .document(id_postagem)
                .collection("voto")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(LOG_TAG, "Listen failed.", e);
                            return;
                        }
                        int countV=0;
                        int countN=0;
                        for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                            if(doc.getBoolean("voto")==true){
                                countV++;
                            }
                            if(doc.getBoolean("negativacao")==true){
                                countN++;
                            }

                        }
                        mViewHolder.post_count_votos.setText(String.valueOf(countV));
                        mViewHolder.post_count_negativacoes.setText(String.valueOf(countN));
                    }
                });
    }
    private void atualizaVotosComentarios(Postagem postagem, final Comentario comentario){
        FirebaseFirestore
                        .getInstance()
                        .collection("postagem")
                        .document(postagem.getId_postagem())
                .collection("comentario")
                .document(comentario.getId())
                .collection("voto")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(LOG_TAG, "Listen failed.", e);
                            return;
                        }
                        int countV=0;
                        int countN=0;
                        for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                            if(doc.getBoolean("voto")==true){
                                countV++;
                            }
                            if(doc.getBoolean("negativacao")==true){
                                countN++;
                            }

                        }
                        if(mapNegativacoes.get(comentario)==null){
                            mapNegativacoes.put(comentario, countN);
                            Log.d("mapNegativo2",mapNegativacoes.toString() + "total: " + countN);
                        }else{
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                mapNegativacoes.replace(comentario, countN);
                                Log.d("mapNegativo2",mapNegativacoes.toString() + "total: " + countN);
                            }else {
                                mapNegativacoes.put(comentario, countN);
                            }
                        }
                        if(mapVotos.get(comentario)==null){
                            mapVotos.put(comentario, countV);
                            Log.d("mapPositivo",mapNegativacoes.toString() + "total: " + countV);
                        }else{
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                mapVotos.put(comentario, countV);
                                Log.d("mapPositivo",mapNegativacoes.toString() + "total: " + countN);
                            }else {
                                mapVotos.put(comentario, countV);
                            }
                        }
                    }
                });
    }
    private void destacarComentario(Comentario comentario){
        comentario.setEscolhido(true);

        comentarioViewModel.atualizar(comentario);
    }
//    private class atualizarVotos extends AsyncTask<List<Comentario>, Void, Boolean>{
//    protected void onPreExecute() {
//        //pd = ProgressDialog.show(AutorizationActivity.this, "", "loading", true);
//    }

//    protected Boolean doInBackground(List<Comentario>... params) {
//
//        try {
//            for (Comentario c:params[0]) {
//
//                try{
//                    int count_votos =  votoViewModel.getCountVotosComentarios(mPostagem.getId_postagem(),c.getId());
//                    //Log.d("user comentario", user.toString());
//                    if(mapVotos.get(c)==null){
//                        mapVotos.put(c, count_votos);
//                        Log.d("map",mapVotos.toString() + "total: " + count_votos);
//                    }else{
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            mapVotos.replace(c, count_votos);
//                        }else {
//                            mapVotos.put(c, count_votos);
//                        }
//                    }
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//    @Override
//    protected void onPostExecute(Boolean result) {
//        super.onPostExecute(result);
//
//        if (result) {
//            mViewHolder.comentarioAdapter.notifyDataSetChanged();
//            Log.d("id", "deu certo");
//        }
//
//    }
//}

//    private class atualizarVoto extends AsyncTask<Comentario, Void, Boolean>{
//        protected void onPreExecute() {
//            //pd = ProgressDialog.show(AutorizationActivity.this, "", "loading", true);
//        }
//
//        protected Boolean doInBackground(Comentario... params) {
//
//            try {
//
//                int count_votos =  votoViewModel.getCountVotosComentarios(mPostagem.getId_postagem(),params[0].getId());
//
//
//                //Log.d("user comentario", user.toString());
//                if(mapVotos.get(params[0])==null){
//                    mapVotos.put(params[0], count_votos);
//                    Log.d("map",mapVotos.toString() + "total: " + count_votos);
//                }else{
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        mapVotos.replace(params[0], count_votos);
//                    }else {
//                        mapVotos.put(params[0], count_votos);
//                    }
//                }
//                return true;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return false;
//        }
//        @Override
//        protected void onPostExecute(Boolean result) {
//            super.onPostExecute(result);
//
//            if (result) {
//                mViewHolder.comentarioAdapter.notifyDataSetChanged();
//                Log.d("id", "deu certo");
//            }
//
//        }
//    }
//
//    private class atualizarNegativacoes extends AsyncTask<List<Comentario>, Void, Boolean>{
//        protected void onPreExecute() {
//            //pd = ProgressDialog.show(AutorizationActivity.this, "", "loading", true);
//        }
//
//        protected Boolean doInBackground(List<Comentario>... params) {
//
//            try {
//                for (Comentario c:params[0]) {
//
//                    try{
//                        int count_negativacoes = votoViewModel.getCountNegativacoesComentarios(mPostagem.getId_postagem(),c.getId());
//                        //Log.d("user comentario", user.toString());
//                        if(mapNegativacoes.get(c)==null){
//                            mapNegativacoes.put(c, count_negativacoes);
//                            Log.d("mapNegativo",mapNegativacoes.toString() + "total: " + count_negativacoes);
//                        }else{
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                mapNegativacoes.replace(c, count_negativacoes);
//                            }else {
//                                mapNegativacoes.put(c, count_negativacoes);
//                            }
//                        }
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//
//                return true;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return false;
//        }
//        @Override
//        protected void onPostExecute(Boolean result) {
//            super.onPostExecute(result);
//
//            if (result) {
//                mViewHolder.comentarioAdapter.notifyDataSetChanged();
//                Log.d("id", "rodou  atualizar Negativacoes");
//            }
//
//        }
//    }
//
//    private class atualizarNegativacao extends AsyncTask<Comentario, Void, Boolean>{
//        protected void onPreExecute() {
//            //pd = ProgressDialog.show(AutorizationActivity.this, "", "loading", true);
//        }
//
//        protected Boolean doInBackground(Comentario... params) {
//
//            try {
//                int count_negativacoes = votoViewModel.getCountNegativacoesComentarios(mPostagem.getId_postagem(),params[0].getId());
//
//                //Log.d("user comentario", user.toString());
//                if(mapNegativacoes.get(params[0])==null){
//                    mapNegativacoes.put(params[0], count_negativacoes);
//                    Log.d("mapNegativo2",mapNegativacoes.toString() + "total: " + count_negativacoes);
//                }else{
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        mapNegativacoes.replace(params[0], count_negativacoes);
//                        Log.d("mapNegativo2",mapNegativacoes.toString() + "total: " + count_negativacoes);
//                    }else {
//                        mapNegativacoes.put(params[0], count_negativacoes);
//                    }
//                }
//                return true;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return false;
//        }
//        @Override
//        protected void onPostExecute(Boolean result) {
//            super.onPostExecute(result);
//
//            if (result) {
//                mViewHolder.comentarioAdapter.notifyDataSetChanged();
//                Log.d("id", "rodou  atualizar Negativacao");
//            }
//
//        }
//    }

//    private class getAutor extends AsyncTask<List<Comentario>, Void, Boolean>{
//        protected void onPreExecute() {
//            //pd = ProgressDialog.show(AutorizationActivity.this, "", "loading", true);
//        }
//
//        protected Boolean doInBackground(List<Comentario>... params) {
//
//            try {
//                for (Comentario c:params[0]) {
//
//                    try{
//                        Log.d("comentario", c.getId_autor()+"");
//                        Usuario user = usuarioViewModel.findByIdUsuario(c.getId_autor());
//                        //Log.d("user comentario", user.toString());
//                        mapComentario.put(c, user);
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                    Log.d("autor", mapComentario.toString());
//                }
//
//                return true;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return false;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean result) {
//            super.onPostExecute(result);
//
//            if (result) {
//                mViewHolder.comentarioAdapter.notifyDataSetChanged();
//                Log.d("id", "deu certo");
//            }
//
//        }
//    }

//    private class atualizarVotoPostagem extends AsyncTask<Postagem, Void, Integer>{
//        protected void onPreExecute() {
//            //pd = ProgressDialog.show(AutorizationActivity.this, "", "loading", true);
//        }
//
//        protected Integer doInBackground(Postagem... params) {
//
//            try {
//                int count_votos =  votoViewModel.getCountVotosPostagem(mPostagem.getId_postagem());
//
//                return count_votos;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return 0;
//        }
//        @Override
//        protected void onPostExecute(Integer result) {
//            super.onPostExecute(result);
//
//            if (result>0) {
//                mViewHolder.post_count_votos.setText(String.valueOf(result));
//                Log.d("id", "executou atualizar voto postagem");
//            }
//
//        }
//    }
//    private class atualizarNegativacaoPostagem extends AsyncTask<Postagem, Void, Integer>{
//        protected void onPreExecute() {
//            //pd = ProgressDialog.show(AutorizationActivity.this, "", "loading", true);
//        }
//
//        protected Integer doInBackground(Postagem... params) {
//
//            try {
//
//                int count_negativacoes =  votoViewModel.getCountNegativacoesPostagem(mPostagem.getId_postagem());
//
//                return count_negativacoes;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return 0;
//        }
//        @Override
//        protected void onPostExecute(Integer result) {
//            super.onPostExecute(result);
//
//            if (result>0) {
//                mViewHolder.post_count_negativacoes.setText(String.valueOf(result));
//
//                Log.d("id", "executou atualizar negativação postagem");
//            }
//
//        }
//    }

}
