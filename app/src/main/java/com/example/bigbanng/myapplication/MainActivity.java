package com.example.bigbanng.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.view.View.FOCUS_UP;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView t;
    EditText e1,e2;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("loginStatus",false) == true){
            Intent i = new Intent(MainActivity.this,HomeScreen.class);
            startActivity(i);
            finish();
        }
        setContentView(R.layout.activity_main);
        t = (TextView)findViewById(R.id.textView);
        e1 = (EditText)findViewById(R.id.editText);
        e2 = (EditText)findViewById(R.id.editText2);
        b = (Button)findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SignupScreen.class);
                startActivity(i);
                finish();
            }
        });

    }
    void login()
    {
        final String mUsername = e1.getText().toString();
        final String mPassword = e2.getText().toString();
        if(!mUsername.isEmpty() && !mPassword.isEmpty()){
            db.collection("users").document(mUsername).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists())
                            {
                                if(mUsername.contains(documentSnapshot.getString("username")) &&
                                        mPassword.contains(documentSnapshot.getString("password")))
                                {
                                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo",Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("loginStatus",true);
                                    editor.putString("userName",mUsername);
                                    editor.commit();

                                    Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(MainActivity.this,HomeScreen.class);
                                    startActivity(i);
                                    finish();
                                }
                                else{
                                    Toast.makeText(MainActivity.this,"Username or Password Incorrect",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"No such user found please register",Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,"Error Occured",Toast.LENGTH_SHORT).show();

                }
            });
        }else{
            if(mUsername.isEmpty()){
                Toast.makeText(MainActivity.this,"Enter username",Toast.LENGTH_SHORT).show();
            }
            else if(mPassword.isEmpty()){
                Toast.makeText(MainActivity.this,"Enter password",Toast.LENGTH_SHORT).show();
            }
        }

    }
}
