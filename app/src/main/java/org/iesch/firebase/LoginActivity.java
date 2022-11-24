package org.iesch.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    Button btnRegister;
    Button btnLogin;
    LinearLayout authLayout;


    private FirebaseAnalytics myFirebaseAnalytics;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            currentUser.reload();
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login Firebase");
        etEmail = findViewById(R.id.EditText_Email);
        etPassword = findViewById(R.id.EditText_Password);
        btnLogin = findViewById(R.id.Button_Login);
        btnRegister = findViewById(R.id.Button_Register);
        myFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString("DAM2", "Aplicacion Iniaciada");
        myFirebaseAnalytics.logEvent("DAM2", bundle);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(v -> {
            registrarConEmailPassword();
        });
    }

    private void registrarConEmailPassword() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FIREBASE", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "El usuario se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FIREBASE", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Error al registrar el usuario",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}