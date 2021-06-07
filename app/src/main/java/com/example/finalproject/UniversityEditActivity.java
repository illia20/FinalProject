package com.example.finalproject;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class UniversityEditActivity extends AppCompatActivity {

    University university;
    EditText univName, univCityName, univNSt, univWR, univIR, univOR, univER, univLat, univLng;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_edit);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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

        univName = findViewById(R.id.univName);
        univCityName = findViewById(R.id.univCityName);
        univNSt = findViewById(R.id.univNumberName);
        univWR = findViewById(R.id.univWRName);
        univIR = findViewById(R.id.univIRName);
        univOR = findViewById(R.id.univORName);
        univER = findViewById(R.id.univERName);
        univLat = findViewById(R.id.univLatValue);
        univLng = findViewById(R.id.univLngValue);
        univLat.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        univLng.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

        Bundle arguments = getIntent().getExtras();
        if(arguments != null){
            university = (University)arguments.getSerializable(University.class.getSimpleName());
            univName.setText(university.getName());
            univCityName.setText(university.getCity());
            univNSt.setText(university.getNumberOfStudents() + "");
            univWR.setText(university.getWorldrank() + "");
            univIR.setText(university.getImpactrank() + "");
            univOR.setText(university.getOpennessrank() + "");
            univER.setText(university.getExcellencerank() + "");
            univLat.setText(university.getLat() + "");
            univLng.setText(university.getLng() + "");
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

    public void save(View view){
        University upUniv = new University();
        upUniv.setId(university.getId());
        upUniv.setName(univName.getText().toString());
        upUniv.setCity(univCityName.getText().toString());
        upUniv.setNumberOfStudents(Integer.valueOf(univNSt.getText().toString()));
        upUniv.setWorldrank(Integer.valueOf(univWR.getText().toString()));
        upUniv.setImpactrank(Integer.valueOf(univIR.getText().toString()));
        upUniv.setOpennessrank(Integer.valueOf(univOR.getText().toString()));
        upUniv.setExcellencerank(Integer.valueOf(univER.getText().toString()));
        upUniv.setLat(Double.valueOf(univLat.getText().toString()));
        upUniv.setLng(Double.valueOf(univLng.getText().toString()));

        ContentValues cv = new ContentValues();

        cv.put("name", upUniv.getName());
        cv.put("city", upUniv.getCity());
        cv.put("number_of_students", upUniv.getNumberOfStudents());
        cv.put("worldrank", upUniv.getWorldrank());
        cv.put("impactrank", upUniv.getImpactrank());
        cv.put("opennessrank", upUniv.getOpennessrank());
        cv.put("excellencerank", upUniv.getExcellencerank());
        cv.put("address_lng", upUniv.getLng());
        cv.put("address_lat", upUniv.getLat());

        int upCount = mDb.update("universities", cv, "_id =" + String.valueOf(upUniv.getId()), null);

        System.out.println("Updated " + upCount + " row.");

        mDBHelper.close();

        finish();
    }
}