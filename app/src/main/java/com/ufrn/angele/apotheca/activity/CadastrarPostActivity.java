package com.ufrn.angele.apotheca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.ufrn.angele.apotheca.R;
import com.ufrn.angele.apotheca.dominio.Postagem;
import com.ufrn.angele.apotheca.dominio.Turma;
import com.ufrn.angele.apotheca.dominio.Usuario;
import com.ufrn.angele.apotheca.outros.Constants;
import com.ufrn.angele.apotheca.viewmodel.PostagemViewModel;

import java.util.ArrayList;
import java.util.Date;

import static com.ufrn.angele.apotheca.outros.Constants.TIPO0;
import static com.ufrn.angele.apotheca.outros.Constants.TIPO1;

public class CadastrarPostActivity extends AppCompatActivity {

    private ArrayList<Turma> mTurmas;
    private ArrayList<String> mTipos;

    private PostagemViewModel postagemViewModel;
    private Postagem p;
    private Usuario mUser;
    private int mSelecionada =-1;

    private static class ViewHolder{
        TextView mTituloView, mDescricaoView, mTagView;
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
        mTurmas = (ArrayList<Turma>) intent.getSerializableExtra(Constants.INTENT_TURMA);
        mSelecionada = (int) intent.getSerializableExtra("selecionada"); //pega posição da turma selecionada na lista de turma
        mUser = (Usuario) intent.getSerializableExtra(Constants.INTENT_USER);

        mTipos = new ArrayList<>();
        mTipos.add(TIPO0);
        mTipos.add(TIPO1);

        mViewHolder.mTituloView = findViewById(R.id.tituloText);
        mViewHolder.mDescricaoView = findViewById(R.id.descricaoText);
        mViewHolder.mTagView = findViewById(R.id.tagAutoCompleteTextView);
        mViewHolder.mPublicar = findViewById(R.id.publicar_button);
        mViewHolder.mTipo = findViewById(R.id.spinnerTipo);
        mViewHolder.mTurma = findViewById(R.id.spinnerTurma);
        // Creating adapter for spinner
        ArrayAdapter<Turma> turmaDataAdapter = new ArrayAdapter<Turma>(this, android.R.layout.simple_spinner_item, mTurmas);
        ArrayAdapter<String> tipoDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mTipos);
        // Drop down layout style - list view with radio button
        turmaDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        mViewHolder.mTurma.setAdapter(turmaDataAdapter);
        if(mSelecionada > 0){
            mViewHolder.mTurma.setSelection(mSelecionada);
        }
        mViewHolder.mTipo.setAdapter(tipoDataAdapter);

        mViewHolder.mPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Turma t = (Turma) mViewHolder.mTurma.getSelectedItem();
                String tipo = mViewHolder.mTurma.getSelectedItem().toString();
                p = new Postagem(mViewHolder.mTituloView.getText().toString(), t.getNome_componente() , new Date().toString());
                p.setDescricao(mViewHolder.mDescricaoView.getText().toString());
                p.setAtivo(true);
                p.setId_turma(t.getId_turma());
                if(tipo == TIPO0){
                    p.setTipo_postagem(0);
                }else{
                    p.setTipo_postagem(1);
                }
                p.setId_autor(mUser.getId_usuario());

                //cadastrar tag

                postagemViewModel.inserir(p);
                finish();
            }
        });


    }

}
