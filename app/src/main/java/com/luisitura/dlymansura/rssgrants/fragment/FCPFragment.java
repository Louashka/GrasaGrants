package com.luisitura.dlymansura.rssgrants.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.luisitura.dlymansura.rssgrants.R;
import com.luisitura.dlymansura.rssgrants.controller.NewsDefault;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FCPFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FCPFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FCPFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button education_btn, sport_btn, culture_btn, agro_btn, science_btn, economy_btn, info_btn, skfo_btn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FCPFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FCPFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FCPFragment newInstance(String param1, String param2) {
        FCPFragment fragment = new FCPFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_fc, container, false);

        education_btn = (Button) rootView.findViewById(R.id.education_btn);
        sport_btn = (Button) rootView.findViewById(R.id.sport_btn);
        culture_btn = (Button) rootView.findViewById(R.id.culture_btn);
        agro_btn = (Button) rootView.findViewById(R.id.agro_btn);
        science_btn = (Button) rootView.findViewById(R.id.science_btn);
        economy_btn = (Button) rootView.findViewById(R.id.economy_btn);
        info_btn = (Button) rootView.findViewById(R.id.info_btn);
        skfo_btn = (Button) rootView.findViewById(R.id.skfo_btn);

        education_btn.setOnClickListener(this);
        sport_btn.setOnClickListener(this);
        culture_btn.setOnClickListener(this);
        agro_btn.setOnClickListener(this);
        science_btn.setOnClickListener(this);
        economy_btn.setOnClickListener(this);
        info_btn.setOnClickListener(this);
        skfo_btn.setOnClickListener(this);

        return  rootView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), NewsDefault.class);;
        switch (v.getId()){
            case R.id.education_btn:
                intent.putExtra("rssClass", "fcp");
                intent.putExtra("rssLink", "http://government.ru/programs/202/events/rss/");
                startActivity(intent);
                break;
            case R.id.sport_btn:
                intent.putExtra("rssClass", "fcp");
                intent.putExtra("rssLink", "http://government.ru/programs/210/events/rss/");
                startActivity(intent);
                break;
            case R.id.culture_btn:
                intent.putExtra("rssClass", "fcp");
                intent.putExtra("rssLink", "http://government.ru/programs/217/events/rss/");
                startActivity(intent);
                break;
            case R.id.agro_btn:
                intent.putExtra("rssClass", "fcp");
                intent.putExtra("rssLink", "http://government.ru/programs/208/events/rss/");
                startActivity(intent);
                break;
            case R.id.science_btn:
                intent.putExtra("rssClass", "fcp");
                intent.putExtra("rssLink", "http://government.ru/programs/211/events/rss/");
                startActivity(intent);
                break;
            case R.id.economy_btn:
                intent.putExtra("rssClass", "fcp");
                intent.putExtra("rssLink", "http://government.ru/programs/225/events/rss/");
                startActivity(intent);
                break;
            case R.id.info_btn:
                intent.putExtra("rssClass", "fcp");
                intent.putExtra("rssLink", "http://government.ru/programs/218/events/rss/");
                startActivity(intent);
                break;
            case R.id.skfo_btn:
                intent.putExtra("rssClass", "fcp");
                intent.putExtra("rssLink", "http://government.ru/programs/235/events/rss/");
                startActivity(intent);
                break;
        }

    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

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
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

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
}
