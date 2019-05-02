package com.ufrn.angele.apotheca.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ufrn.angele.apotheca.R;
import com.ufrn.angele.apotheca.dominio.Turma;

import java.util.ArrayList;

public class TurmaAdapter extends RecyclerView.Adapter{

    private ArrayList<Turma> mTurmas = new ArrayList<>();
    public TurmaAdapter (ArrayList turmas){
        mTurmas=turmas;
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


            view.setOnClickListener(this);
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
