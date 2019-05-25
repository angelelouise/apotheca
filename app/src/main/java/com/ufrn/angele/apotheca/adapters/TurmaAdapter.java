package com.ufrn.angele.apotheca.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ufrn.angele.apotheca.R;
import com.ufrn.angele.apotheca.activity.CadastrarPostActivity;
import com.ufrn.angele.apotheca.dominio.Turma;
import com.ufrn.angele.apotheca.outros.Constants;

import java.io.Serializable;
import java.util.ArrayList;

public class TurmaAdapter extends RecyclerView.Adapter{

    private ArrayList<Turma> mTurmas = new ArrayList<>();
    private Context mContext;
    public TurmaAdapter (Context context, ArrayList turmas){
        this.mTurmas=turmas;
        this.mContext=context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.turma_item, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((TurmaAdapter.ListViewHolder) viewHolder).bindView(i);
    }

    @Override
    public int getItemCount() {
        return mTurmas.size();
    }
    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView codigo_turma;
        private TextView nome_turma;

        public ListViewHolder(View view){
            super(view);

            codigo_turma = view.findViewById(R.id.turma_codigo);
            nome_turma = view.findViewById(R.id.turma_nome);


            //view.setOnClickListener(this);
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        Turma clickedDataItem = mTurmas.get(pos);

                        Intent send = new Intent(mContext, CadastrarPostActivity.class);
                        send.putExtra(Constants.INTENT_TURMA, (Serializable) mTurmas);
                        send.putExtra("selecionada", pos);

                        mContext.startActivity(send);
                    }
                }
            });
        }
        public void bindView(int position){
            codigo_turma.setText(mTurmas.get(position).getCodigo_componente());
            nome_turma.setText(mTurmas.get(position).getNome_componente());

        }
        @Override
        public void onClick(View view) {

        }
    }
}
