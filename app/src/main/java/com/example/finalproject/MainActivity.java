package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showAbout(View view){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void startDeterminant(View view){
        Intent intent = new Intent(this, DeterminantActivity.class);
        startActivity(intent);
    }

    public void startInverse(View view){
        Intent intent = new Intent(this, InverseActivity.class);
        startActivity(intent);
    }
    public void startAddSub(View view){
        Intent intent = new Intent(this, AddSubActivity.class);
        startActivity(intent);
    }
    public void startMult(View view){
        Intent intent = new Intent(this, MultiplyActivity.class);
        startActivity(intent);
    }
    public void startGraphics(View view){
        Intent intent = new Intent(this, HeatmapActivity.class);
        startActivity(intent);
    }

    public void startUnivList(View view) {
        Intent intent = new Intent(this, UnivListActivity.class);
        startActivity(intent);
    }

    public void startSelection(View view) {
        Intent intent = new Intent(this, SelectionActivity.class);
        startActivity(intent);
    }

    public void startMaxMin(View view) {
        Intent intent = new Intent(this, CalcActivity.class);
        startActivity(intent);
    }

    public void startContacts(View view) {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }

    public void startTutorial(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }
}