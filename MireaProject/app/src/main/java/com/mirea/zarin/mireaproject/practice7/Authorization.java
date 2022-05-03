package com.mirea.zarin.mireaproject.practice7;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mirea.zarin.mireaproject.MainActivity;
import com.mirea.zarin.mireaproject.R;

public class Authorization extends AppCompatActivity implements View.OnClickListener
{
    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView statusTextView;
    private int i= 0;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        statusTextView = findViewById(R.id.statusTextView);

        findViewById(R.id.signInButton).setOnClickListener(this);
        findViewById(R.id.createAccButton).setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);
    }


    @Override
    public void onClick(View view)
    {
        int i = view.getId();

        if (i == R.id.createAccButton)
        {
            createAccount(emailEditText.getText().toString(), passwordEditText.getText().toString());
        }
        else if (i == R.id.signInButton)
        {
            signIn(emailEditText.getText().toString(), passwordEditText.getText().toString());
        }
    }

    private void updateUI(FirebaseUser user)
    {
        if (user != null)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else
        {
            statusTextView.setText(R.string.signed_out);
            findViewById(R.id.passwordEditText).setVisibility(View.VISIBLE);
            findViewById(R.id.emailEditText).setVisibility(View.VISIBLE);
            findViewById(R.id.signInButton).setVisibility(View.VISIBLE);
            findViewById(R.id.createAccButton).setVisibility(View.VISIBLE);
        }
    }

    private boolean validateForm()
    {
        boolean valid = true;
        String email = emailEditText.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            emailEditText.setError("Required.");
            valid = false;
        }
        else
        {
            emailEditText.setError(null);
        }

        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(password))
        {
            passwordEditText.setError("Required.");
            valid = false;
        }
        else
        {
            passwordEditText.setError(null);
        }

        return valid;
    }

    private void createAccount(String email, String password)
    {
        if (!validateForm())
        {
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task ->
                {
                    if (task.isSuccessful())
                    {
                        FirebaseUser user = auth.getCurrentUser();
                        updateUI(user);
                    }
                    else
                    {
                        Toast.makeText(Authorization.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private void signIn(String email, String password)
    {
        if (!validateForm())
        {
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task ->
                {
                    if (task.isSuccessful())
                    {
                        FirebaseUser user = auth.getCurrentUser();
                        updateUI(user);
                    }
                    else
                    {
                        Toast.makeText(Authorization.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                    if (!task.isSuccessful())
                    {
                        statusTextView.setText(R.string.auth_failed);
                    }
                });
    }

    private void signOut()
    {
        auth.signOut();
        updateUI(null);
    }
}
