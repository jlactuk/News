package com.example.news;

import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

class User {
    public String Name;
    public String Password;
    public String Email;
}



public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private Button SignUp;
    private Button ChangeForms;
    private Button SignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Test","I wanna be a proger");
        db = FirebaseFirestore.getInstance();
        SignUp = findViewById(R.id.SignUp);
        ChangeForms = findViewById(R.id.ChangeForm);

        ChangeForms.setOnClickListener(v -> {
            ChangeForm();
        });
        SignUp.setOnClickListener(v -> {
            SignUp();
        });
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
       db.collection("users")
           .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
               if (task.isSuccessful()) {
                   EditText Name = findViewById(R.id.Name);
                   for (QueryDocumentSnapshot document : task.getResult()) {
                       Log.d("Success", document.getId() + " => " + document.getData());

                       Name.setText(Name.getText() + document.get("Name").toString());
                   }
               } else {
                   Log.d("Error", "Error getting documents: ", task.getException());
               }
           }
       });
//        Map<String, Object> user = new HashMap<>();
//        user.put("Name", "Alan");
//        user.put("Surname", "Mathison");
//        user.put("Email", "Test@gay.ru");
//
//        FirebaseFirestore.setLoggingEnabled(true);
//// Add a new document with a generated ID
//        db.collection("users")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("Ara ra", "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("Ara ra", "Error adding document", e);
//                    }
//                });
    }
    private void SignUp () {
        User user = new User();

        EditText email = findViewById(R.id.Email);
        EditText name = findViewById(R.id.Name);
        EditText password = findViewById(R.id.Password);


        HashMap<String,String> test = new HashMap<>();
        test.put("Name",name.getText().toString());
        test.put("Email",email.getText().toString());
        test.put("Password",password.getText().toString());

        user.Name = name.getText().toString();
        user.Email = email.getText().toString();
        user.Password = password.getText().toString();
//        Log.d("Checking", user.Password.toString());
        if(user.Name.trim().length() > 0  && user.Email.trim().length() > 0 && user.Password.trim().length() > 0) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL("https://httpbin.org/post");;
                        HttpURLConnection con = (HttpURLConnection)url.openConnection();
                        con.setRequestProperty("Content-Type", "application/json; utf-8");
                        con.setRequestMethod("POST");
                        con.setDoOutput(true);
                        StrictMode.ThreadPolicy policy;
                        policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        Log.d("Test","I did titles");
                        JSONObject data  = new JSONObject();
                        data.put("username",user.Name);
                        data.put("password",user.Password);
                        Log.d("Test","I created JSON");
                        OutputStream os = con.getOutputStream();
                        Log.d("Test","I created os");
                        byte[] input = data.toString().getBytes(StandardCharsets.UTF_8);
                        os.write(input,0,input.length);
                        Log.d("Check","I send data");
                        try(BufferedReader br = new BufferedReader(
                                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                            StringBuilder response = new StringBuilder();
                            String responseLine = null;
                            while ((responseLine = br.readLine()) != null) {
                                response.append(responseLine.trim());
                            }
                            Log.d("Answer",response.toString());
                        }
                        Log.d("Test","I read data");

                        Log.d("JSON", data.toString());
                    } catch ( JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
        db.collection("users")
                .add(test)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Ara ra", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Ara ra", "Error adding document", e);
                    }
                });
//        Intent intent = new Intent(this, News.class);
//        startActivity(intent);
//        finish();
    }
    public void ChangeForm () {
        Intent intent = new Intent(this,SignIn.class);
        intent.putExtra("test","Suck");
        startActivity(intent);
        finish();
    }
    public void SignIn() {
        Log.d("Checking","test");
    }
}

