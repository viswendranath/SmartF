package com.example.bigbanng.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SignupScreen extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static EditText e1;
    EditText e2,e3,e4;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("SignUp","In OnCreate");
        setContentView(R.layout.activity_signup_screen);
        e1 = (EditText)findViewById(R.id.uname);
        e2 = (EditText)findViewById(R.id.password);
        e3 = (EditText)findViewById(R.id.cnfrmpassword);
        e4 = (EditText)findViewById(R.id.phno);
        b = (Button)findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void reg(){
            Log.i("SignUp","In reg Fucntion ");

            final Map<String,Object> userdetails = new HashMap<>();
            userdetails.put("username",e1.getText().toString());
            userdetails.put("password",e2.getText().toString());
            userdetails.put("phoneno",e4.getText().toString());
            //userdetails.put("interests",null);
            db.collection("users").document(e1.getText().toString()).set(userdetails)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i("SignUp","Registeration Successful");
                            Toast.makeText(SignupScreen.this,"Registeration Successful",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignupScreen.this,Interests.class);
                            startActivity(i);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("SignUp","Error Occured");
                    Toast.makeText(SignupScreen.this,"Error Occured",Toast.LENGTH_SHORT).show();
                }
            });
            Log.i("SignUp","End of reg Function");
    }
    void registerUser()
    {
/*        String username = e1.getText().toString();
        String password = e2.getText().toString();
        String confirmpassword = e3.getText().toString();
        String phoneno = e4.getText().toString();
*/
        Log.i("SignUp","Before validate details func");
        validateDetails();
    }
    void validateDetails()
    {

        final String username = e1.getText().toString();
        final String password = e2.getText().toString();
        final String confirmpassword = e3.getText().toString();
        final String phoneno = e4.getText().toString();
        if(!username.isEmpty()){
            db.collection("users").document(username).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){

                        Toast.makeText(SignupScreen.this,"Username already exits",Toast.LENGTH_SHORT).show();

                    }else{
                        if(password.length() >= 8 && phoneno.length() == 10 && password.equals(confirmpassword)){
                            reg();
                        }
                        else if(password.length() < 8){
                            Toast.makeText(SignupScreen.this,"Password must contain minimum 8 characters",Toast.LENGTH_LONG).show();
                        }else if(!password.equals(confirmpassword)){
                            Log.i("SignUp",password+password.length());
                            Log.i("SignUp",confirmpassword+confirmpassword.length());

                            Toast.makeText(SignupScreen.this,"Password and confirm password must be same",Toast.LENGTH_LONG).show();
                        }else if(phoneno.length() != 10){
                            Toast.makeText(SignupScreen.this,"Enter valid phone number",Toast.LENGTH_LONG).show();
                        }else{
                            Log.i("SignUp","Unknown error");
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignupScreen.this,"Error occured",Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(SignupScreen.this,"Enter Username",Toast.LENGTH_SHORT).show();
        }

    }
}
