package com.ufrn.angele.apotheca.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufrn.angele.apotheca.R;
import com.ufrn.angele.apotheca.adapters.TurmaAdapter;
import com.ufrn.angele.apotheca.dominio.Turma;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TurmasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TurmasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TurmasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Turma> turmas = new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    private Context mContext;

    public static class ViewHolder{
        private View view;
        private RecyclerView recyclerView;
        private TurmaAdapter mAdapter;
        private RecyclerView.LayoutManager layoutManager;

    }
    private ViewHolder mViewHolder = new ViewHolder();

    public TurmasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TurmasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TurmasFragment newInstance(String param1, String param2) {
        TurmasFragment fragment = new TurmasFragment();
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

            turmas = (ArrayList<Turma>) getArguments().getSerializable("turma");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewHolder.view = inflater.inflate(R.layout.fragment_turmas, container, false);
        mViewHolder.recyclerView = mViewHolder.view.findViewById(R.id.lista_turmas);

        mViewHolder.layoutManager = new LinearLayoutManager(getActivity());
        mViewHolder.recyclerView.setLayoutManager( mViewHolder.layoutManager);

        mViewHolder.mAdapter = new TurmaAdapter(mContext,turmas);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }
}
