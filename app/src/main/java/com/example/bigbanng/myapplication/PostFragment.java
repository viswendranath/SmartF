package com.example.bigbanng.myapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;


public class PostFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button next;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Toast.makeText(getActivity().getApplicationContext(), "", Toast.LENGTH_SHORT).show();

        return inflater.inflate(R.layout.fragment_post, container, false);
    }



}
