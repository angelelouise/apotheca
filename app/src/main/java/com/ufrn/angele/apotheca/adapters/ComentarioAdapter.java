package com.ufrn.angele.apotheca.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ufrn.angele.apotheca.R;
import com.ufrn.angele.apotheca.dominio.Comentario;
import com.ufrn.angele.apotheca.dominio.Usuario;
import com.ufrn.angele.apotheca.outros.CircleTransform;

import java.util.HashMap;
import java.util.List;

public class ComentarioAdapter extends RecyclerView.Adapter {

    private List<Comentario> comentarios;
    private Context context;
    private HashMap<Comentario, Usuario> map;

    public ComentarioAdapter( Context context,HashMap<Comentario, Usuario> map, List<Comentario> comentarios) {
        this.map = map;
        this.comentarios = comentarios;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comentario_item, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ListViewHolder) viewHolder).bindView(i);
    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }
    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView comentario_titulo, comentario_counter_votes, comentario_counter_downvotes, comentario_autor;
        private ImageButton comentario_vote, comentario_downvote;
        private ImageView comentario_user;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            comentario_titulo = itemView.findViewById(R.id.comentario_item_titulo);
            comentario_user = itemView.findViewById(R.id.comentario_item_avatar);
            comentario_vote = itemView.findViewById(R.id.comentario_item_vote);
            comentario_downvote = itemView.findViewById(R.id.comentario_item_downvote);
            comentario_autor = itemView.findViewById(R.id.comentario_item_autor);
        }
        public void bindView(int position){
            comentario_titulo.setText(comentarios.get(position).getTitulo());
            if(map!=null){
                comentario_autor.setText(map.get(comentarios.get(position)).getNome());

                Glide.with(context).load(map.get(comentarios.get(position)).getUrl_foto())
                        .crossFade()
                        .thumbnail(0.5f)
                        .bitmapTransform(new CircleTransform(context))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(comentario_user);
            }


        }
        @Override
        public void onClick(View view) {

        }
    }
}
