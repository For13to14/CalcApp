package ru.gb.cpourse1.calcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    private Switch darkThemeSwitch;
    private Button backNavigationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        darkThemeSwitch = findViewById(R.id.dark_theme_switch);
        backNavigationButton = findViewById(R.id.back_navigation_button);


    }
}