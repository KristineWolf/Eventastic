package com.example.kristine.eventastic.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kristine.eventastic.R;

public class FourthFragment extends Fragment implements View.OnClickListener{

    View view;

    public FourthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fourth, container, false);
        Button mailButton =(Button) view.findViewById(R.id.button_mail);
        mailButton.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_mail:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.e_mail)});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.extra_subject));
                emailIntent.putExtra(Intent.EXTRA_TEXT,getResources().getString(R.string.extra_body));
                emailIntent.setType("text/html");
                startActivity(Intent.createChooser(emailIntent,getResources().getString(R.string.choose_email_client)));
        }
    }
}
