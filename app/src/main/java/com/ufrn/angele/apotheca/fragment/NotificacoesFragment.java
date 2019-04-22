package com.ufrn.angele.apotheca.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufrn.angele.apotheca.R;
import com.ufrn.angele.apotheca.adapters.NotificacaoAdapter;
import com.ufrn.angele.apotheca.dominio.Notificacao;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotificacoesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotificacoesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificacoesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Notificacao> notificacoes;
    private OnFragmentInteractionListener mListener;
    private ArrayList<String> frases;

    public static class ViewHolder{
        private RecyclerView recyclerView;
        private NotificacaoAdapter mAdapter;
        private RecyclerView.LayoutManager layoutManager;
        private View view;
    }
    private ViewHolder mViewHolder = new ViewHolder();
    public NotificacoesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificacoesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificacoesFragment newInstance(String param1, String param2) {
        NotificacoesFragment fragment = new NotificacoesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        notificacoes =  new ArrayList<>();
        frases = new ArrayList<>();

        //modificar para pegar da API
        notificacoes.add(new Notificacao(0,1,0,0,new Date(),0,false));
        notificacoes.add(new Notificacao(0,0,0,0,new Date(),1,false));
        notificacoes.add(new Notificacao(0,1,0,0,new Date(),2,false));
        notificacoes.add(new Notificacao(0,1,0,0,new Date(),3,false));
        notificacoes.add(new Notificacao(0,1,0,0,new Date(),4,false));
        frases = composeFrases(notificacoes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewHolder.view = inflater.inflate(R.layout.fragment_notificacoes, container, false);
        mViewHolder.recyclerView = mViewHolder.view.findViewById(R.id.lista_notificacoes);

        mViewHolder.layoutManager = new LinearLayoutManager(getActivity());
        mViewHolder.recyclerView.setLayoutManager( mViewHolder.layoutManager);

        mViewHolder.mAdapter = new NotificacaoAdapter(notificacoes,frases);
        mViewHolder.recyclerView.setAdapter(mViewHolder.mAdapter);

        return mViewHolder.view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
/*
Tipos de notificações
0 curtir
1 comentar
2 negativar
3 ranking
4 reporte
*/
    private ArrayList<String> composeFrases(ArrayList<Notificacao> notificacoes){
        ArrayList<Notificacao> aux = new ArrayList<>();
        aux=notificacoes;

        for (Notificacao i:aux) {
            if(i.getTipo_notificacao() == 0){
                //consultar nome autor
                frases.add(i.getId_autor() + " Curtiu sua postagem.");
                //consultar foto do autor
                i.setAvatar(R.drawable.user);
            }else if(i.getTipo_notificacao() == 1){
                frases.add(i.getId_autor() + " Comentou em sua postagem.");
                //consultar foto do autor
                i.setAvatar(R.drawable.user);
            }else if(i.getTipo_notificacao() == 2){
                frases.add("Sua postagem foi negativada.");
                //consultar foto do autor
                i.setAvatar(R.drawable.thumbsdown);
            }else if(i.getTipo_notificacao() == 3){
                frases.add("Seu ranking subiu.");
                //consultar foto do autor
                i.setAvatar(R.drawable.trophy);
            }else if(i.getTipo_notificacao() == 4){
                frases.add("Sua postagem foi reportada, dessa forma ela não está mais disponível para visualização.");
                //consultar foto do autor
                i.setAvatar(R.drawable.report);
            }
        }
        return frases;
    }
}
