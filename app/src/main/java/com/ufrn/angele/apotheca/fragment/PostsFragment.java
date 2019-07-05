package com.ufrn.angele.apotheca.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.ufrn.angele.apotheca.R;
import com.ufrn.angele.apotheca.adapters.PostAdapter;
import com.ufrn.angele.apotheca.dominio.Postagem;
import com.ufrn.angele.apotheca.dominio.Usuario;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    private Usuario mUser;
    private OnFragmentInteractionListener mListener;
    private HashMap<Postagem,Usuario> map;

    public PostsFragment() {
        // Required empty public constructor
    }

    public static class ViewHolder{
        private RecyclerView recyclerView;
        private PostAdapter mAdapter;
        private RecyclerView.LayoutManager layoutManager;
        private View view;
    }

    private ViewHolder mViewHolder = new ViewHolder();
    private ArrayList<Postagem> posts;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostsFragment newInstance(String param1, String param2) {
        PostsFragment fragment = new PostsFragment();
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
        if (getArguments() != null) {
            mUser = (Usuario) getArguments().getSerializable("usuario");
        }
        //criar dados para testes
        posts = new ArrayList<>();

        consultaUsuario(mUser.getId_usuario());
//        posts.add(new Postagem("Lista de exercício 1","Linguagem de Programação",new Date().toString()));
//        posts.add(new Postagem("Resolução de exercícios em sala","Controladores",new Date().toString()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewHolder.view = inflater.inflate(R.layout.fragment_posts, container, false);
        mViewHolder.recyclerView = mViewHolder.view.findViewById(R.id.lista_minha_postagem);

        mViewHolder.layoutManager = new LinearLayoutManager(getActivity());
        mViewHolder.recyclerView.setLayoutManager( mViewHolder.layoutManager);

        mViewHolder.mAdapter = new PostAdapter(container.getContext(),posts,mUser, map);
        mViewHolder.recyclerView.setAdapter(mViewHolder.mAdapter);

        return mViewHolder.view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext=context;
    }

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

    private void consultaUsuario(int id_usuario){
        FirebaseFirestore
                .getInstance()
                .collection("postagem")
                .whereEqualTo("id_usuario", id_usuario)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                            Postagem p = new Postagem(doc.getString("titulo"),doc.getString("componente"),doc.getString("data_cadastro"));
                            p.setId_autor(doc.getLong("id_usuario").intValue());
                            p.setId_postagem(doc.getId());
                            p.setId_componente(doc.getLong("id_componente").intValue());
                            p.setDescricao(doc.getString("descricao"));
                            p.setAtivo(doc.getBoolean("ativo"));
                            p.setUrl_autor(doc.getString("url_autor"));
                            posts.add(p);

                        }
                        mViewHolder.mAdapter.notifyDataSetChanged();
                    }
                });
    }
}
