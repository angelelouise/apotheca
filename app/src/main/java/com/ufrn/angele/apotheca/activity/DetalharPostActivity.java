package com.ufrn.angele.apotheca.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.ufrn.angele.apotheca.R;
import com.ufrn.angele.apotheca.adapters.ComentarioAdapter;
import com.ufrn.angele.apotheca.bd.UsuarioRepository;
import com.ufrn.angele.apotheca.dominio.Comentario;
import com.ufrn.angele.apotheca.dominio.Postagem;
import com.ufrn.angele.apotheca.dominio.Usuario;
import com.ufrn.angele.apotheca.outros.CircleTransform;
import com.ufrn.angele.apotheca.outros.Constants;
import com.ufrn.angele.apotheca.viewmodel.ComentarioViewModel;
import com.ufrn.angele.apotheca.viewmodel.UsuarioViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DetalharPostActivity extends AppCompatActivity {
    private Postagem mPostagem;
    private List<Comentario> comentarios;
    private ComentarioViewModel comentarioViewModel;
    private HashMap<Comentario, Usuario> mapComentario = new HashMap<>();
    private UsuarioViewModel usuarioViewModel;
    private UsuarioRepository usuarioRepository;
    private Usuario mUser;
    private class ViewHolder{
        TextView titulo, descricao, turma;
        android.support.v7.widget.Toolbar toolbar;
        RecyclerView lista_comentarios;
        ComentarioAdapter comentarioAdapter;
        ImageButton cadastrar_comentario;
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
        mViewHolder.titulo_comentario = findViewById(R.id.detalhar_post_comentario_descricao);
        mViewHolder.cadastrar_comentario = findViewById(R.id.detalhar_post_publicar_comentario);
        //set postagem
        mViewHolder.turma.setText(mPostagem.getTurma());
        mViewHolder.titulo.setText(mPostagem.getTitulo());
        mViewHolder.descricao.setText(mPostagem.getDescricao());
        //pegar toolbar
        setSupportActionBar(mViewHolder.toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);

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
        mViewHolder.comentarioAdapter = new ComentarioAdapter(DetalharPostActivity.this, mapComentario,comentarios);
        mViewHolder.lista_comentarios.setAdapter(mViewHolder.comentarioAdapter);
        mViewHolder.lista_comentarios.setLayoutManager(new LinearLayoutManager(this));
        //set comentario viewmodel live data
        usuarioRepository =new UsuarioRepository(this.getApplication());
        usuarioViewModel = ViewModelProviders.of(this).get(UsuarioViewModel.class);
        comentarioViewModel = ViewModelProviders.of(this).get(ComentarioViewModel.class);
        comentarioViewModel.getListaComentarios(mPostagem.getId_postagem()).observe(this, new Observer<List<Comentario>>() {
            @Override
            public void onChanged(@Nullable List<Comentario> comentario) {
                //postagemAdapter.setPalavras(post);
                comentarios.clear();
                comentarios.addAll(comentario);
                new getAutor().execute(comentario);
            }
        });

        mViewHolder.cadastrar_comentario.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Comentario comentario = new Comentario(
                                0,
                                mPostagem.getId_postagem(),
                                0,
                                mPostagem.getId_autor(),
                                new Date().toString(),
                                false,
                                false,
                                mViewHolder.titulo_comentario.getText().toString());
                        comentarioViewModel.inserir(comentario);
                        mViewHolder.titulo_comentario.setText("");
                    }
                }
        );
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private class getAutor extends AsyncTask<List<Comentario>, Void, Boolean>{
        protected void onPreExecute() {
            //pd = ProgressDialog.show(AutorizationActivity.this, "", "loading", true);
        }

        protected Boolean doInBackground(List<Comentario>... params) {

            try {
                for (Comentario c:params[0]) {

                    try{
                        Log.d("comentario", c.getId_autor()+"");
                        Usuario user = usuarioViewModel.findById(c.getId_autor());
                        //Log.d("user comentario", user.toString());
                        mapComentario.put(c, user);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Log.d("autor", mapComentario.toString());
                }

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (result) {
                mViewHolder.comentarioAdapter.notifyDataSetChanged();
                Log.d("id", "deu certo");
            }

        }
    }

}
