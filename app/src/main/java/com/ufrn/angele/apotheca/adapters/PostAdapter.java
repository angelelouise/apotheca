package com.ufrn.angele.apotheca.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufrn.angele.apotheca.R;
import com.ufrn.angele.apotheca.dominio.Postagem;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter{

    private final ArrayList<Postagem> mPostagens;

    public PostAdapter(ArrayList posts){
        mPostagens=posts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ListViewHolder) viewHolder).bindView(i);
    }

    @Override
    public int getItemCount() {
        return mPostagens.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView list_titulo;
        private TextView list_turma;
        private TextView list_timestamp;
        private ImageView list_avatar;
        private ImageButton list_menu;

        public ListViewHolder(View view){
            super(view);

            list_titulo = view.findViewById(R.id.list_titulo);
            list_turma = view.findViewById(R.id.list_turma);
            list_timestamp = view.findViewById(R.id.list_timestamp);
            list_avatar = view.findViewById(R.id.list_avatar);
            list_menu = view.findViewById(R.id.list_menu);

            view.setOnClickListener(this);
        }
        public void bindView(int position){
            list_titulo.setText(mPostagens.get(position).getTitulo());
            list_turma.setText(mPostagens.get(position).getTurma());
            list_titulo.setText(mPostagens.get(position).getTitulo());
            list_timestamp.setText(mPostagens.get(position).getData_cadastro().toString());
            list_avatar.setImageResource(mPostagens.get(position).getAvatar());
        }
        @Override
        public void onClick(View view) {

        }
    }
}
