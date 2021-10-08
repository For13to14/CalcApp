package ru.gb.cpourse1.calcapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    private SwitchMaterial darkThemeSwitch;
    private final String SWITCH_STATE_KEY = "switch_state_key";

    private Button findActivityBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        darkThemeSwitch = findViewById(R.id.dark_theme_switch);
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            darkThemeSwitch.setChecked(true);
        } else {
            darkThemeSwitch.setChecked(false);
        }

        darkThemeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)  {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        findActivityBtn = findViewById(R.id.find_activity_by_intent_button);
        findActivityBtn.setOnClickListener(view -> {
            Intent calculateIntent = new Intent();
            calculateIntent.setAction("ru.gb.intent.action.CALCULATE");
            startActivity(calculateIntent);
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(SWITCH_STATE_KEY, darkThemeSwitch.isChecked());

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        darkThemeSwitch.setChecked(savedInstanceState.getBoolean(SWITCH_STATE_KEY));

    }



}