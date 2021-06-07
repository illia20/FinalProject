package com.example.finalproject;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class CreateUniversityActivity extends AppCompatActivity {

    University university;
    EditText univName, univCityName, univNSt, univWR, univIR, univOR, univER, univLat, univLng;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_university);

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
    }

    public void create(View view){
        university = new University();
        university.setId(university.getId());
        university.setName(univName.getText().toString());
        university.setCity(univCityName.getText().toString());
        university.setNumberOfStudents(Integer.valueOf(univNSt.getText().toString()));
        university.setWorldrank(Integer.valueOf(univWR.getText().toString()));
        university.setImpactrank(Integer.valueOf(univIR.getText().toString()));
        university.setOpennessrank(Integer.valueOf(univOR.getText().toString()));
        university.setExcellencerank(Integer.valueOf(univER.getText().toString()));
        university.setLat(Integer.valueOf(univLat.getText().toString()));
        university.setLng(Integer.valueOf(univLng.getText().toString()));

        ContentValues cv = new ContentValues();

        cv.put("name", university.getName());
        cv.put("city", university.getCity());
        cv.put("number_of_students", university.getNumberOfStudents());
        cv.put("worldrank", university.getWorldrank());
        cv.put("impactrank", university.getImpactrank());
        cv.put("opennessrank", university.getOpennessrank());
        cv.put("excellencerank", university.getExcellencerank());
        cv.put("address_lng", university.getLng());
        cv.put("address_lat", university.getLat());

        long insCount = mDb.insert("universities", null, cv);

        System.out.println("Inserted " + insCount + " row.");

        mDBHelper.close();

        finish();
    }
}