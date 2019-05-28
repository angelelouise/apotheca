package com.ufrn.angele.apotheca.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ufrn.angele.apotheca.R;
import com.ufrn.angele.apotheca.dominio.Postagem;
import com.ufrn.angele.apotheca.dominio.Report;
import com.ufrn.angele.apotheca.dominio.Usuario;
import com.ufrn.angele.apotheca.outros.Constants;

import java.util.Date;

public class FormReport extends AppCompatActivity {
    private Usuario mUser;
    private Postagem mPostagem;

    class ViewHolder{
        EditText motivo;
        Button cadastrar_reporte;
    }

    ViewHolder mViewHolder = new ViewHolder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_report);

        mViewHolder.motivo =findViewById(R.id.report_motivo);
        mViewHolder.cadastrar_reporte= findViewById(R.id.cadastrar_report);
        Intent intent = getIntent();
        mUser = (Usuario) intent.getSerializableExtra(Constants.INTENT_USER);
        mPostagem = (Postagem) intent.getSerializableExtra(Constants.INTENT_POSTAGEM);

        mViewHolder.cadastrar_reporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Report report = new Report(
                        0,
                        mPostagem.getId_postagem(),
                        mUser.getId_usuario(),
                        new Date().toString(),
                        mViewHolder.motivo.getText().toString()
                        );
                finish();
            }

        });


    }
}
