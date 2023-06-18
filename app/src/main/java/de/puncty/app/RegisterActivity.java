package de.puncty.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.puncty.lib.Session;
import com.puncty.lib.exceptions.BrokenResponse;
import com.puncty.lib.exceptions.UserAlreadyExists;
import com.puncty.lib.networking.Requester;

import de.puncty.app.utility.Puncty;
import de.puncty.app.utility.Toaster;

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
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty()) {
                Toaster.error(this, "Bitte fülle alle Felder aus");
                return;
            }

            if (!password.equals(passwordConfirmation)) {
                Toaster.error(this, "Passwörter stimmen nicht überein");
                return;
            }

            new Thread(() -> {
                try {
                    Requester r = new Requester(Puncty.BASE_URL);
                    Session s = Session.register(r, username, email, password);
                    Puncty.create(s);
                    SharedPreferences creds = getSharedPreferences("credentials", MODE_PRIVATE);
                    creds.edit().putString("email", email).putString("password", password).apply();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } catch (BrokenResponse e) {
                    Toaster.error(this, "Etwas ist schief gelaufen...");
                    e.printStackTrace();
                } catch (UserAlreadyExists e) {
                    Toaster.error(this, "Ein Nutzer mit dieser E-Mail existiert bereits");
                    System.err.println(e.getMessage());
                }
            }).start();
        });
    }
}