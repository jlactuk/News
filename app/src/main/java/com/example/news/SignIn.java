package com.example.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignIn extends AppCompatActivity {
    private Button SignIn;
    private FirebaseFirestore db;
    private TextView ChangeForms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Checking", "I change form");
        setContentView(R.layout.activity_sign_in);
        db = FirebaseFirestore.getInstance();
        ChangeForms = findViewById(R.id.ChangeForm);
        ChangeForms.setOnClickListener(v -> {
            ChangeForm();
        });
        SignIn = findViewById(R.id.signIn);
        SignIn.setOnClickListener(v -> {
            signIn();
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            HttpURLConnection con = (HttpURLConnection) new URL("http://cars.areas.su/cars").openConnection();

//            if (con.getResponseCode()  == HttpURLConnection.HTTP_OK) {
//            } else {
                String res = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
                Log.d("Test", res);
//
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        String test = getIntent().getExtras().get("test").toString();
        Log.d("Is is is",test);
    }

    public void signIn() {

        EditText email = findViewById(R.id.Email);
        EditText password = findViewById(R.id.Password);
//        db.collection("users")
//                .whereEqualTo("Email", email.getText().toString())
//                .whereEqualTo("Password", password.getText().toString())
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Log.d("Success", document.getId() + " => " + document.getData());
//                    }
//                } else {
//                    Log.d("Error", "Error getting documents: ", task.getException());
//                }
//            }
//
//        });
        Intent intent = new Intent(this,MainScreen.class);
        startActivity(intent);
        finish();
    }
    public void ChangeForm () {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}

