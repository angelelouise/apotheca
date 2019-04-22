package com.ufrn.angele.apotheca.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufrn.angele.apotheca.R;
import com.ufrn.angele.apotheca.dominio.Notificacao;

import java.util.ArrayList;

public class NotificacaoAdapter  extends RecyclerView.Adapter{

    private final ArrayList<Notificacao> mNotificacoes;
    private ArrayList<String> mFrase;

    public NotificacaoAdapter(ArrayList notificacoes, ArrayList frase){
        mNotificacoes =notificacoes;
        mFrase = frase;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notificacao_item, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ListViewHolder) viewHolder).bindView(i);
    }

    @Override
    public int getItemCount() {
        return mNotificacoes.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView list_titulo;
        private TextView list_timestamp;
        private ImageView list_avatar;

        public ListViewHolder(View view){
            super(view);

            list_titulo = view.findViewById(R.id.notificacoes_titulo);
            list_timestamp = view.findViewById(R.id.notificacoes_timestamp);
            list_avatar = view.findViewById(R.id.notificacoes_avatar);

            view.setOnClickListener(this);
        }
        public void bindView(int position){
            Log.d("frase",mFrase.get(position));
            list_titulo.setText(mFrase.get(position));
            list_timestamp.setText(mNotificacoes.get(position).getData_cadastro().toString());
            list_avatar.setImageResource(mNotificacoes.get(position).getAvatar());
        }
        @Override
        public void onClick(View view) {

        }
    }
}
