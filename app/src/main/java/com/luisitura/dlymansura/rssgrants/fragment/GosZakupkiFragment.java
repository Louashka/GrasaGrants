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
import com.luisitura.dlymansura.rssgrants.controller.Fz44NavigationActivity;
import com.luisitura.dlymansura.rssgrants.controller.NewsDefault;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GosZakupkiFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GosZakupkiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GosZakupkiFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button buttonProcurement1, buttonProcurement2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public GosZakupkiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GosZakupkiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GosZakupkiFragment newInstance(String param1, String param2) {
        GosZakupkiFragment fragment = new GosZakupkiFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_gos_zakupki, container, false);

        buttonProcurement1 = (Button) rootView.findViewById(R.id.buttonProcurement1);
        buttonProcurement2 = (Button) rootView.findViewById(R.id.buttonProcurement2);

        buttonProcurement1.setOnClickListener(this);
        buttonProcurement2.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(getActivity(), NewsDefault.class);
        switch (v.getId()) {
            case R.id.buttonProcurement1:
                Intent intent1 = new Intent(getActivity(), Fz44NavigationActivity.class);
                startActivity(intent1);
                break;
            case R.id.buttonProcurement2:
                intent.putExtra("rssClass", "gosZakupki");
                intent.putExtra("rssLink", "http://zakupki.gov.ru/epz/order/quicksearch/rss?searchString=&morphology=on&pageNumber=1&sortDirection=false&recordsPerPage=_10&showLotsInfoHidden=false&fz223=on&af=on&ca=on&priceFrom=&priceTo=&currencyId=-1&agencyTitle=&agencyCode=&agencyFz94id=&agencyFz223id=&agencyInn=&regions=&publishDateFrom=&publishDateTo=&sortBy=UPDATE_DATE&updateDateFrom=&updateDateTo=");
                startActivity(intent);
                break;
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
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
