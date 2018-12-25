package com.example.bigbanng.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Interests extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button continu;
    private CheckBox b[];
    final private int DOMAINS_COUNT = 4;
    ArrayList<String> checkedInterests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);
        continu = findViewById(R.id.btn_continue);
        continu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Interests.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        checkedInterests = new ArrayList<String>();
        b = new CheckBox[DOMAINS_COUNT];

        setOnClickContinue();
    }


    public void onSelection(View view){
        boolean checked = ((CheckBox)view).isChecked();

        if(checked)
            checkedInterests.add(((CheckBox)view).getText().toString());
        else
            checkedInterests.remove(((CheckBox)view).getText().toString());
    }

    public void setOnClickContinue(){
        continu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Iterator itr = checkedInterests.iterator();
                Log.i("Interests","****************");

                Log.i("Interests",Integer.toString(checkedInterests.size()));
                while(itr.hasNext()){
                    Log.i("Interests",itr.next().toString());
                }
                Map<String,Object> interests = new HashMap<>();
                for(int i=0;i<checkedInterests.size();i++){
                    interests.put(Integer.toString(i),checkedInterests.get(i));
                }

                db.collection("users").document(SignupScreen.e1.getText().toString())
                        .collection("interests").document("intrsts")
                        .set(interests).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Interests.this,"Interests are set successfully",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Interests.this,"Error Occured",Toast.LENGTH_SHORT).show();
                    }
                });
                Intent i = new Intent(Interests.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
