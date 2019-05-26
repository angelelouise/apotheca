package com.ufrn.angele.apotheca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ufrn.angele.apotheca.R;
import com.ufrn.angele.apotheca.dominio.Postagem;
import com.ufrn.angele.apotheca.outros.Constants;

public class DetalharPerguntaActivity extends AppCompatActivity {
    private Postagem mPostagem;

    private class ViewHolder{
        TextView titulo, descricao, turma;
        android.support.v7.widget.Toolbar toolbar;
    }
    private ViewHolder mViewHolder = new ViewHolder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhar_pergunta);

        Intent intent = getIntent();
        mPostagem = (Postagem) intent.getSerializableExtra(Constants.INTENT_POSTAGEM);

        mViewHolder.toolbar = findViewById(R.id.toolbar2_det_pergunta);
        mViewHolder.turma = findViewById(R.id.detalhes_turma_det_pergunta);
        mViewHolder.titulo= findViewById(R.id.detalhar_post_titulo_det_pergunta);
        mViewHolder.descricao= findViewById(R.id.detalhar_post_descricao_det_pergunta);

        mViewHolder.turma.setText(mPostagem.getTurma());
        mViewHolder.titulo.setText(mPostagem.getTitulo());
        mViewHolder.descricao.setText(mPostagem.getDescricao());
        setSupportActionBar(mViewHolder.toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
