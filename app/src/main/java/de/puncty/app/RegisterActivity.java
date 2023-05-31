package de.puncty.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.puncty.lib.Session;
import com.puncty.lib.exceptions.BrokenResponse;
import com.puncty.lib.exceptions.UserAlreadyExists;
import com.puncty.lib.networking.Requester;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText usernameField = findViewById(R.id.registerUsernameTextEdit);
        EditText emailField = findViewById(R.id.regoisterUsernameTextEdit);
        EditText passwordField = findViewById(R.id.registerPasswordEditText);
        EditText passwordConfirmationField = findViewById(R.id.registerPasswordConfirmationEditText);
        findViewById(R.id.submitRegisterButton).setOnClickListener(v -> {
            String username = String.valueOf(usernameField.getText());
            String email = String.valueOf(emailField.getText());
            String password = String.valueOf(passwordField.getText());
            String passwordConfirmation = String.valueOf(passwordConfirmationField.getText());
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || !password.equals(passwordConfirmation)) return;

            new Thread(() -> {
                try {
                    Requester r = new Requester(Puncty.BASE_URL);
                    Session s = Session.register(r, username, email, password);
                    Puncty.create(s);
                } catch (BrokenResponse e) {
                    e.printStackTrace();
                } catch (UserAlreadyExists e) {
                    System.err.println(e.getMessage());
                }

                startActivity(new Intent(this, MainActivity.class));
            }).start();
        });
    }
}