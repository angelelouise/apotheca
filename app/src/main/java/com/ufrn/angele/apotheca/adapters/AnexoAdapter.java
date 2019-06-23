package com.ufrn.angele.apotheca.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ufrn.angele.apotheca.R;

import java.util.ArrayList;
import java.util.List;

public class AnexoAdapter extends RecyclerView.Adapter{

    private List<String> mAnexos = new ArrayList<>();
    private Context mContext;
    private AnexoAdapterListener anexoAdapterListener;
    public AnexoAdapter (Context context, List anexos, AnexoAdapterListener anexoAdapterListener){
        this.mAnexos=anexos;
        this.mContext=context;
        this.anexoAdapterListener=anexoAdapterListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.anexo_item, viewGroup, false);
        return new AnexoAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((AnexoAdapter.ListViewHolder) viewHolder).bindView(i);
    }

    @Override
    public int getItemCount() {
        return mAnexos.size();
    }
    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView filename;

        public ListViewHolder(View view){
            super(view);

            filename = view.findViewById(R.id.filename);



            //view.setOnClickListener(this);
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        anexoAdapterListener.anexoOnClick(v, pos);
                    }
                }
            });

        }
        public void bindView(int position){
            filename.setText(mAnexos.get(position));


        }
        @Override
        public void onClick(View view) {

        }
    }
}