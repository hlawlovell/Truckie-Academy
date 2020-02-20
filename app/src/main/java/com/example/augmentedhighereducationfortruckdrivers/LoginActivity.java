package com.example.augmentedhighereducationfortruckdrivers;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailTB, passwordTB;
    private Button login;
    private SignInButton google_signin;
    private TextView passwordreset,register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        initializeUI();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegisterIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(RegisterIntent);
            }
        });
        passwordreset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent ResetIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(ResetIntent);
            }
        });
    }


    private void loginUserAccount() {

        String email, password;
        email = emailTB.getText().toString();
        password = passwordTB.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Login Failed! Incorrect email or password.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void initializeUI() {
        emailTB = findViewById(R.id.email);
        passwordTB = findViewById(R.id.password);
        login = findViewById(R.id.Login);
        register = findViewById(R.id.Register);
        passwordreset = findViewById(R.id.forgotpassword);
    }

}
