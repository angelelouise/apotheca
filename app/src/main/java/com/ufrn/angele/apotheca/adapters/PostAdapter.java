package com.ufrn.angele.apotheca.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.ufrn.angele.apotheca.activity.DetalharPostActivity;
import com.ufrn.angele.apotheca.dominio.Postagem;
import com.ufrn.angele.apotheca.dominio.Usuario;
import com.ufrn.angele.apotheca.outros.CircleTransform;
import com.ufrn.angele.apotheca.outros.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class PostAdapter extends RecyclerView.Adapter{

    private final ArrayList<Postagem> mPostagens;
    private Context mContext;
    private Usuario actualUser;
    private Map<Postagem,Usuario> map;


    public PostAdapter(Context context, ArrayList posts, Usuario actualUser, Map<Postagem,Usuario> map){
        this.mPostagens=posts;
        this.mContext=context;
        this.actualUser= actualUser;
        this.map=map;
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

            //view.setOnClickListener(this);
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        Postagem clickedDataItem = mPostagens.get(pos);
                        Intent send = new Intent(mContext, DetalharPostActivity.class);
                        send.putExtra(Constants.INTENT_POSTAGEM, (Serializable) clickedDataItem);
                        send.putExtra(Constants.INTENT_USER, actualUser);
                        mContext.startActivity(send);
//                        if(clickedDataItem.getTipo_postagem() == 1){//do tipo livre
//                            Intent send = new Intent(mContext, DetalharPostActivity.class);
//                            send.putExtra(Constants.INTENT_POSTAGEM, (Serializable) clickedDataItem);
//                            send.putExtra(Constants.INTENT_USER, actualUser);
//                            mContext.startActivity(send);
//                        }else if (clickedDataItem.getTipo_postagem() == 0){//do tipo perguntas e respostas
//                            Intent sendP = new Intent(mContext, DetalharPerguntaActivity.class);
//                            sendP.putExtra(Constants.INTENT_POSTAGEM, (Serializable) clickedDataItem);
//                            sendP.putExtra(Constants.INTENT_USER, actualUser);
//                            mContext.startActivity(sendP);
//                        }

                    }
                }
            });
        }
        public void bindView(int position){
            list_titulo.setText(mPostagens.get(position).getTitulo());
            list_turma.setText(mPostagens.get(position).getTurma());
            list_timestamp.setText(mPostagens.get(position).getData_cadastro().toString());
            //list_avatar.setImageResource(mPostagens.get(position).getAvatar());
            if(map!=null){
                Glide.with(mContext).load(map.get(mPostagens.get(position)).getUrl_foto())
                        .crossFade()
                        .thumbnail(0.5f)
                        .bitmapTransform(new CircleTransform(mContext))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(list_avatar);
            }
        }
        @Override
        public void onClick(View view) {

        }
    }
}
