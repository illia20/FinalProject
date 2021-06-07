package com.example.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class UniversityDetailsActivity extends AppCompatActivity {

    University university;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    TextView univName, univCityName, univNSt, univWR, univIR, univOR, univER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        univName = findViewById(R.id.univName);
        univCityName = findViewById(R.id.univCityName);
        univNSt = findViewById(R.id.univNumberName);
        univWR = findViewById(R.id.univWRName);
        univIR = findViewById(R.id.univIRName);
        univOR = findViewById(R.id.univORName);
        univER = findViewById(R.id.univERName);

        mDBHelper = new DatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }



        Bundle arguments = getIntent().getExtras();
        if(arguments != null){
            university = (University)arguments.getSerializable(University.class.getSimpleName());

            Cursor cursor = mDb.rawQuery("SELECT * FROM universities WHERE _id = " + university.getId(), null);
            cursor.moveToFirst();

            university.setName(cursor.getString(1));
            university.setCity(cursor.getString(2));
            university.setNumberOfStudents(cursor.getInt(3));
            university.setWorldrank(cursor.getInt(4));
            university.setImpactrank(cursor.getInt(5));
            university.setOpennessrank(cursor.getInt(6));
            university.setExcellencerank(cursor.getInt(7));
            university.setLng(cursor.getDouble(8));
            university.setLat(cursor.getDouble(9));

            univName.setText(university.getName());
            univCityName.setText(university.getCity());
            univNSt.setText(university.getNumberOfStudents() + "");
            univWR.setText(university.getWorldrank() + "");
            univIR.setText(university.getImpactrank() + "");
            univOR.setText(university.getOpennessrank() + "");
            univER.setText(university.getExcellencerank() + "");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void edit(View view){
        Intent intent = new Intent(getApplicationContext(), UniversityEditActivity.class);
        intent.putExtra(University.class.getSimpleName(), university);
        startActivity(intent);
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        mDBHelper = new DatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        Cursor cursor = mDb.rawQuery("SELECT * FROM universities WHERE _id = " + university.getId(), null);
        cursor.moveToFirst();

        university.setName(cursor.getString(1));
        university.setCity(cursor.getString(2));
        university.setNumberOfStudents(cursor.getInt(3));
        university.setWorldrank(cursor.getInt(4));
        university.setImpactrank(cursor.getInt(5));
        university.setOpennessrank(cursor.getInt(6));
        university.setExcellencerank(cursor.getInt(7));
        university.setLng(cursor.getDouble(8));
        university.setLat(cursor.getDouble(9));

        univName.setText(university.getName());
        univCityName.setText(university.getCity());
        univNSt.setText(university.getNumberOfStudents() + "");
        univWR.setText(university.getWorldrank() + "");
        univIR.setText(university.getImpactrank() + "");
        univOR.setText(university.getOpennessrank() + "");
        univER.setText(university.getExcellencerank() + "");
    }

    public void remove(View view){
        int r = mDb.delete("universities", "_id = " + university.getId(), null);
        finish();
    }

    public void showMap(View view){
        Intent intent = new Intent(getApplicationContext(), UniversityMapActivity.class);
        intent.putExtra(University.class.getSimpleName(), university);
        startActivity(intent);
    }
}