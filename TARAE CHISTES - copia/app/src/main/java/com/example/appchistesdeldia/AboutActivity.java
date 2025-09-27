package com.example.appchistesdeldia;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView aboutText = findViewById(R.id.aboutTextView);
        aboutText.setText("App: ChistesProgra\nAutor: Lester Payes y Mario Tereta\nReto académico: App de chistes con IA\n\n¡Gracias por usar la app! 😄");
    }
}
