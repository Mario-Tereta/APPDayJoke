package com.example.appchistesdeldia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView jokeTextView;
    private Button getJokeButton, aboutButton, shareButton;
    private SharedPreferences prefs;
    private static final String PREFS_NAME = "chistesprogra_prefs";
    private static final String[] LAST_JOKES_KEYS = {"joke1", "joke2", "joke3"};
    private String currentJoke = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setTitle("ChistesProgra");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        jokeTextView = findViewById(R.id.jokeTextView);
        getJokeButton = findViewById(R.id.getJokeButton);
        aboutButton = findViewById(R.id.aboutButton);
        shareButton = findViewById(R.id.shareButton);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        getJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchJoke();
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareJoke();
            }
        });

        jokeTextView.setText(getLastJoke());
    }

    private void fetchJoke() {
        // Usar JokeApiHelper para obtener el chiste usando OkHttp
        JokeApiHelper.getJoke(new JokeApiHelper.JokeCallback() {
            @Override
            public void onJokeReceived(String joke) {
                // Mostrar el chiste en el TextView
                jokeTextView.setText(joke);
                currentJoke = joke;
                saveJoke(joke);
            }

            @Override
            public void onError(String errorMsg) {
                // Mostrar el error en un Toast y en el TextView
                Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                jokeTextView.setText("Error al obtener el chiste. Intenta de nuevo.");
            }
        });
    }

    private void saveJoke(String joke) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LAST_JOKES_KEYS[2], prefs.getString(LAST_JOKES_KEYS[1], ""));
        editor.putString(LAST_JOKES_KEYS[1], prefs.getString(LAST_JOKES_KEYS[0], ""));
        editor.putString(LAST_JOKES_KEYS[0], joke);
        editor.apply();
    }

    private String getLastJoke() {
        String last = prefs.getString(LAST_JOKES_KEYS[0], "¡Presiona el botón para obtener un chiste! 😁");
        currentJoke = last;
        return last;
    }

    private void shareJoke() {
        if (currentJoke.isEmpty()) {
            Toast.makeText(this, "Primero obtén un chiste para compartir.", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, currentJoke);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Compartir chiste vía"));
    }
}