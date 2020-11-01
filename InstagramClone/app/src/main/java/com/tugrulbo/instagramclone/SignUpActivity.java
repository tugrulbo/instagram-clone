package com.tugrulbo.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.Inet4Address;

public class SignUpActivity extends AppCompatActivity {

    EditText getTxt_login, getTxt_pass;
    Button btLogin;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //get component id from layout
        getTxt_login = findViewById(R.id.login_editText_login);
        getTxt_pass = findViewById(R.id.login_editText_pass);
        btLogin = findViewById(R.id.login_button);

        mAuth = FirebaseAuth.getInstance();

        //checking user
        FirebaseUser user = mAuth.getCurrentUser();
        if (user !=null){

            //redirect
            Intent intent = new Intent(SignUpActivity.this,FeedActivity.class);
            startActivity(intent);
            finish();

        }
    }

   public void SignInClicked(View view){
       //login data
       String email = getTxt_login.getText().toString();
       String pass = getTxt_pass.getText().toString();

       mAuth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
           @Override
           public void onSuccess(AuthResult authResult) {
               //redirect
               Intent intent = new Intent(SignUpActivity.this,FeedActivity.class);
               startActivity(intent);
               finish();
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Toast.makeText(SignUpActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
           }
       });

   }
   public void SignUpClicked(View view){

        //login data
        String email = getTxt_login.getText().toString();
        String pass = getTxt_pass.getText().toString();

        //also checking same email
        mAuth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(SignUpActivity.this, "User Created", Toast.LENGTH_SHORT).show();

                //redirect
                Intent intent = new Intent(SignUpActivity.this,FeedActivity.class);
                startActivity(intent);
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
   }

}