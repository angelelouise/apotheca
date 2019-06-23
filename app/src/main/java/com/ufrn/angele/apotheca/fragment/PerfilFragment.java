package com.ufrn.angele.apotheca.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ufrn.angele.apotheca.R;
import com.ufrn.angele.apotheca.dominio.Discente;
import com.ufrn.angele.apotheca.dominio.Usuario;
import com.ufrn.angele.apotheca.outros.CircleTransform;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PerfilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    private ArrayList<Discente> discentes = new ArrayList<>();
    private String urlProfileImg;
    private static final String urlNavHeaderBg = "https://backgrounddownload.com/wp-content/uploads/2018/09/beautiful-plain-background-hd-5.jpg";
    private OnFragmentInteractionListener mListener;
    private Usuario usuario = new Usuario();

    public static class ViewHolder{
        private RecyclerView.LayoutManager layoutManager;
        private View view;
        private View navHeader;
        private ImageView imgNavHeaderBg, imgProfile;
        private TextView txtNome, txtRank, txtCurso, txtPosts, txtPontos ;
    }
    private ViewHolder mViewHolder = new ViewHolder();
    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */

    public static PerfilFragment newInstance(Usuario param1, ArrayList<Discente> param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Discentes_fragment", usuario.toString());
        mViewHolder.view =inflater.inflate(R.layout.fragment_perfil, container, false);

        mViewHolder.txtNome =  mViewHolder.view.findViewById(R.id.perfil_nome);
        mViewHolder.txtRank = mViewHolder.view.findViewById(R.id.perfil_rank);
        mViewHolder.imgNavHeaderBg = mViewHolder.view.findViewById(R.id.perfil_background);
        mViewHolder.imgProfile = mViewHolder.view.findViewById(R.id.perfil_avatar);
        mViewHolder.txtCurso = mViewHolder.view.findViewById(R.id.perfil_curso);
        mViewHolder.txtPontos = mViewHolder.view.findViewById(R.id.perfil_pontos);
        mViewHolder.txtPosts = mViewHolder.view.findViewById(R.id.perfil_post);



        return mViewHolder.view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            usuario = (Usuario) getArguments().getSerializable("usuario");
            discentes = (ArrayList<Discente>) getArguments().getSerializable("discente");
            urlProfileImg = usuario.getUrl_foto();

        }
        String nome =usuario.getNome().toString();
        mViewHolder.txtNome.setText(nome);
        mViewHolder.txtPosts.setText("2");
        mViewHolder.txtPontos.setText("35");

        StringBuilder builder = new StringBuilder();

        //Log.d("Discentes_fragment", discentes.toString());
        if(discentes!=null){
            for (Discente discente : discentes) {
                builder.append(discente.getNome_curso() + "\n");
            }
        }

        mViewHolder.txtCurso.setText(builder.toString());

        loadNavHeader();
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

    private void loadNavHeader() {
        // trocar pela consulta do nome

        //trocar pela consulta do rank
        mViewHolder.txtRank.setText("Universitário Sofrido");
        urlProfileImg = usuario.getUrl_foto();
        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mViewHolder.imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(getActivity()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mViewHolder.imgProfile);


    }
}
