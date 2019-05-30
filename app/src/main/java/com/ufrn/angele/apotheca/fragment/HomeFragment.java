package com.ufrn.angele.apotheca.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ufrn.angele.apotheca.R;
import com.ufrn.angele.apotheca.adapters.PostAdapter;
import com.ufrn.angele.apotheca.dominio.Postagem;
import com.ufrn.angele.apotheca.dominio.Turma;
import com.ufrn.angele.apotheca.dominio.Usuario;
import com.ufrn.angele.apotheca.outros.Constants;
import com.ufrn.angele.apotheca.viewmodel.PostagemViewModel;
import com.ufrn.angele.apotheca.viewmodel.UsuarioViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    public static class ViewHolder{
        private RecyclerView recyclerView;
        private PostAdapter mAdapter;
        private RecyclerView.LayoutManager layoutManager;
        private View view;
        private Spinner filtro_turma;
    }
    private ViewHolder mViewHolder = new ViewHolder();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ArrayList<Postagem> posts;
    private PostagemViewModel postagemViewModel;
    private Context context;
    private Usuario usuario;
    private List<Turma> mTurmas;
    private UsuarioViewModel usuarioViewModel;
    private HashMap<Postagem, Usuario> mapPostagem = new HashMap<>();
    private int idt;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
            usuario = (Usuario) getArguments().getSerializable("usuario");
            mTurmas = (List<Turma>) getArguments().getSerializable(Constants.INTENT_TURMA);
        }
        //criar dados para testes
        usuarioViewModel = ViewModelProviders.of(this).get(UsuarioViewModel.class);
        postagemViewModel = ViewModelProviders.of(this).get(PostagemViewModel.class);
        posts = new ArrayList<>();
        //posts.add(new Postagem("","",new Date().toString()));
        //mapPostagem.put(new Postagem("","",new Date().toString()), new Usuario());
        idt = mTurmas.get(0).getId_componente();

        //posts.add(new Postagem("Resolução de exercícios em sala","Controladores",new Date()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewHolder.view = inflater.inflate(R.layout.fragment_home, container, false);
        mViewHolder.recyclerView = mViewHolder.view.findViewById(R.id.lista_postagem);

        mViewHolder.layoutManager = new LinearLayoutManager(getActivity());
        mViewHolder.recyclerView.setLayoutManager( mViewHolder.layoutManager);

        mViewHolder.mAdapter = new PostAdapter(context,posts,usuario, mapPostagem);
        mViewHolder.recyclerView.setAdapter(mViewHolder.mAdapter);
        mViewHolder.filtro_turma = mViewHolder.view.findViewById(R.id.filtro_turma);
        ArrayAdapter<Turma> turmaDataAdapter = new ArrayAdapter<Turma>(context, android.R.layout.simple_spinner_item, mTurmas);
        turmaDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mViewHolder.filtro_turma.setAdapter(turmaDataAdapter);
        mViewHolder.filtro_turma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                idt = mTurmas.get(position).getId_componente();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                idt = mTurmas.get(0).getId_componente();
                mViewHolder.mAdapter.notifyDataSetChanged();
            }

        });
        postagemViewModel.getListaPostagem(idt).observe(this, new Observer<List<Postagem>>() {
            @Override
            public void onChanged(@Nullable List<Postagem> post) {
                Log.d("idt","idt" +idt);
                Log.d("post","post" +post);
                posts.clear();
                posts.addAll(post);
                new getAutor().execute(post);
            }
        });

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
        this.context =context;
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        postagemViewModel.getListaPostagem(ids).observe(this, new Observer<List<Postagem>>() {
//            @Override
//            public void onChanged(@Nullable List<Postagem> post) {
//                Log.d("post","post" +post);
//                posts.clear();
//                posts.addAll(post);
//                new getAutor().execute(post);
//            }
//        });
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
    private class getAutor extends AsyncTask<List<Postagem>, Void, Boolean> {
        protected void onPreExecute() {
            //pd = ProgressDialog.show(AutorizationActivity.this, "", "loading", true);
        }

        protected Boolean doInBackground(List<Postagem>... params) {

            try {
                for (Postagem c : params[0]) {

                    try {
                        Usuario user = usuarioViewModel.findById(c.getId_autor());
                        Log.d("user comentario", user.toString());
                        mapPostagem.put(c, user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.d("autor", mapPostagem.toString());
                }

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (result) {
                mViewHolder.mAdapter.notifyDataSetChanged();
                Log.d("id", "deu certo");
            }

        }
    }
}
