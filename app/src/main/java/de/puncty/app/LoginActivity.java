package de.puncty.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.widget.EditText;

import com.puncty.lib.Session;
import com.puncty.lib.exceptions.BrokenResponse;
import com.puncty.lib.networking.Requester;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailField = findViewById(R.id.regoisterUsernameTextEdit);
        EditText passwordField = findViewById(R.id.registerPasswordEditText);
        findViewById(R.id.submitRegisterButton).setOnClickListener(v -> {
                    String email = String.valueOf(emailField.getText());
                    String password = String.valueOf(passwordField.getText());
                    if(email.isEmpty()||password.isEmpty()) {
                        Toaster.error(this, "Bitte fülle alle Felder aus");
                        return;
                    };

            new Thread(() -> {
                try {
                        Requester r = new Requester(Puncty.BASE_URL);
                        Session s = Session.login(r, email, password);
                        Puncty.create(s);
                        startActivity(new Intent(this, MainActivity.class));
                    } catch(Exception e) {
                        Toaster.error(this, "E-Mail oder Passwort ist falsch");
                        e.printStackTrace();
                    }
                }).start();
            });
        }
    }
