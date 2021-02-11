package com.example.recursoshumanos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView email;
    private TextView password;
    private Button btnLogin;
    private Button btnRegister;
    private SignInButton btnGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnGoogle = findViewById(R.id.btnGoogle);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //SharedPreferences
        Context context = LoginActivity.this;
        SharedPreferences sharedPref = context.getSharedPreferences("com.example.recursoshumanos.PREFERENCE_FIL", Context.MODE_PRIVATE);
        //Comprobar si ya estabas logeado
        if (sharedPref.getString("Email", null) != null) {
            showMain(sharedPref.getString("Email", null));
        }

        //Boton de login de google
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions googleConf = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                GoogleSignInClient googleClient = GoogleSignIn.getClient(getBaseContext(), googleConf);
                googleClient.signOut();
                startActivityForResult(googleClient.getSignInIntent(), 100);
            }
        });

        //Boton de Registrarse
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    SharedPreferences sharedPref = getSharedPreferences("com.example.recursoshumanos.PREFERENCE_FIL", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("Email", user.getEmail());
                                    editor.commit();
                                    showMain(user.getEmail());
                                } else {
                                    Toast.makeText(LoginActivity.this, "ERROR: El usuario ya existe",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        //Boton de Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    SharedPreferences sharedPref = getSharedPreferences("com.example.recursoshumanos.PREFERENCE_FIL", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("Email", user.getEmail());
                                    editor.commit();
                                    showMain(user.getEmail());
                                } else {
                                    Toast.makeText(LoginActivity.this, "ERROR: Usuario o password incorrecto", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                final GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null){
                    AuthCredential credencial = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    FirebaseAuth.getInstance().signInWithCredential(credencial).
                            addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                       // SharedPreferences sharedPref = LoginActivity.this.getPreferences(Context.MODE_PRIVATE);
                                        SharedPreferences sharedPref = getSharedPreferences("com.example.recursoshumanos.PREFERENCE_FIL", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putString("Email", account.getEmail());
                                        editor.commit();
                                        showMain(account.getEmail());
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, R.string.acceso_incorrecto, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    void showMain(String email) {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra("email", email);
        startActivity(i);
        finish();
    }


}