package com.luisitura.dlymansura.rssgrants.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.luisitura.dlymansura.rssgrants.R;
import com.luisitura.dlymansura.rssgrants.controller.AllNews;
import com.luisitura.dlymansura.rssgrants.controller.NewsDefault;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GrantFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GrantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GrantFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button all_news_btn, categ1_btn, categ2_btn, categ3_btn, categ4_btn, categ5_btn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public GrantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GrantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GrantFragment newInstance(String param1, String param2) {
        GrantFragment fragment = new GrantFragment();
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
        View rootView =  inflater.inflate(R.layout.fragment_grant, container, false);
        all_news_btn = (Button) rootView.findViewById(R.id.all_news_btn);
        categ1_btn = (Button) rootView.findViewById(R.id.categ1_btn);
        categ2_btn = (Button) rootView.findViewById(R.id.categ2_btn);
        categ3_btn = (Button) rootView.findViewById(R.id.categ3_btn);
        categ4_btn = (Button) rootView.findViewById(R.id.categ4_btn);
        categ5_btn = (Button) rootView.findViewById(R.id.categ5_btn);

        all_news_btn.setOnClickListener(this);
        categ1_btn.setOnClickListener(this);
        categ2_btn.setOnClickListener(this);
        categ3_btn.setOnClickListener(this);
        categ4_btn.setOnClickListener(this);
        categ5_btn.setOnClickListener(this);

        return rootView;
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

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.all_news_btn:
                intent = new Intent(getActivity(), AllNews.class);
                startActivity(intent);
                break;
            case R.id.categ1_btn:
                intent = new Intent(getActivity(), NewsDefault.class);
                intent.putExtra("rssClass", "konkursgrant");
                intent.putExtra("rssLink", "http://www.konkursgrant.ru/index.php/ru/obshchie-konkursy?format=feed&type=rss");
                startActivity(intent);
                break;
            case R.id.categ2_btn:
                intent = new Intent(getActivity(), NewsDefault.class);
                intent.putExtra("rssClass", "vsekonkursy");
                intent.putExtra("rssLink", "http://feeds.feedburner.com/Vsekonkursy");
                startActivity(intent);
                break;
            case R.id.categ3_btn:
                intent = new Intent(getActivity(), NewsDefault.class);
                intent.putExtra("rssClass", "rvc");
                intent.putExtra("rssLink", "https://www.rvc.ru/press-service/news/rss/");
                startActivity(intent);
                break;
            case R.id.categ4_btn:
                intent = new Intent(getActivity(), NewsDefault.class);
                intent.putExtra("rssClass", "rsci");
                intent.putExtra("rssLink", "http://www.rsci.ru/export/rss/rss_grants.php");
                startActivity(intent);
                break;
            case R.id.categ5_btn:
                intent = new Intent(getActivity(), NewsDefault.class);
                intent.putExtra("rssClass", "grantist");
                intent.putExtra("rssLink", "http://grantist.com/contests/vse-konkursy/feed/");
                startActivity(intent);
                break;
        }

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
}
