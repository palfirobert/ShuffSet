package com.example.shuffset;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    protected FirebaseAuth mAuth;
    private EditText emailEditText,passwordEditText;
    private FloatingActionButton facebookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText=findViewById(R.id.email);
        passwordEditText=findViewById(R.id.password);
        facebookButton=findViewById(R.id.facebook);

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();


    }







    private static void hideSoftKeyboard(Activity activity) { // sa dispara tastatura cand dai in afara scrisului
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }
    public void onClickBackground(View view)
    {
        hideSoftKeyboard(this);
    }

    public void SignIn(View view)
    {
        mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Sign Up was a success.",
                                    Toast.LENGTH_SHORT).show();

                        } else {


                            Toast.makeText(getApplicationContext(), "Sign Up failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    public void LogIn(View view)
    {  if(emailEditText.getText()!=null && passwordEditText.getText()!=null) {
        mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            openMainPageActivity();
                        } else {

                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            ;
                        }
                    }
                });

    }
    else {
        Toast.makeText(this, "Add email or password", Toast.LENGTH_SHORT).show();

    }

    }



    public void onClickFacebookButton(View view)
    {
        Intent intent=new Intent(MainActivity.this,FacebookActivity.class);
        startActivity(intent);
    }


    protected void openMainPageActivity()
    {
        Intent intent=new Intent(MainActivity.this,MainPageActivity.class);
        startActivity(intent);
    }
}