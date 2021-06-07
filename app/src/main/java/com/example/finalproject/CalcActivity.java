package com.example.finalproject;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class CalcActivity extends AppCompatActivity {

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    int wrMax, wrMin, irMax, irMin, orMax, orMin, erMax, erMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

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

        getData();

        writeTable();

        mDBHelper.close();
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

    private void getData(){
        Cursor wrM = mDb.rawQuery("SELECT MAX(worldrank) FROM universities", null);
        wrM.moveToFirst();
        Cursor wrm = mDb.rawQuery("SELECT MIN(worldrank) FROM universities", null);
        wrm.moveToFirst();
        wrMax = wrM.getInt(0);
        wrMin = wrm.getInt(0);

        Cursor irM = mDb.rawQuery("SELECT MAX(impactrank) FROM universities", null);
        irM.moveToFirst();
        Cursor irm = mDb.rawQuery("SELECT MIN(impactrank) FROM universities", null);
        irm.moveToFirst();
        irMax = irM.getInt(0);
        irMin = irm.getInt(0);

        Cursor orM = mDb.rawQuery("SELECT MAX(opennessrank) FROM universities", null);
        orM.moveToFirst();
        Cursor orm = mDb.rawQuery("SELECT MIN(opennessrank) FROM universities", null);
        orm.moveToFirst();
        orMax = orM.getInt(0);
        orMin = orm.getInt(0);

        Cursor erM = mDb.rawQuery("SELECT MAX(excellencerank) FROM universities", null);
        erM.moveToFirst();
        Cursor erm = mDb.rawQuery("SELECT MIN(excellencerank) FROM universities", null);
        erm.moveToFirst();
        erMax = erM.getInt(0);
        erMin = erm.getInt(0);
    }

    private void writeTable(){
        TextView wrM, wrm, irM, irm, orM, orm, erM, erm;
        wrM = findViewById(R.id.wrMax);
        wrm = findViewById(R.id.wrMin);
        irM = findViewById(R.id.irMax);
        irm = findViewById(R.id.irMin);
        orM = findViewById(R.id.orMax);
        orm = findViewById(R.id.orMin);
        erM = findViewById(R.id.erMax);
        erm = findViewById(R.id.erMin);

        wrM.setText(wrMax + "");
        wrm.setText(wrMin + "");
        irM.setText(irMax + "");
        irm.setText(irMin + "");
        orM.setText(orMax + "");
        orm.setText(orMin + "");
        erM.setText(erMax + "");
        erm.setText(erMin + "");
    }
}