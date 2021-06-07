package com.example.finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Scanner;

import Jama.Matrix;

public class HeatmapActivity extends AppCompatActivity {

    int rows = 2;
    int cols = 2;

    int pixelW = 250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heatmap);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TableLayout tableLayout = findViewById(R.id.tableMatrixHM);
        tableLayout.setPadding(0, 10, 0, 0);

        for(int i = 0; i < rows; i++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            tableRow.setGravity(Gravity.CENTER_HORIZONTAL);
            for(int j = 0; j < cols; j++){
                EditText editText = new EditText(this);
                TableRow.LayoutParams editTextParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                editText.setLayoutParams(editTextParams);
                editText.setMaxWidth(pixelW);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                editText.setText("0.0");
                editText.setTextSize(30);
                editText.setPadding(10, 10, 10, 10);
                tableRow.addView(editText, j);
            }
            tableLayout.addView(tableRow, i);
        }
    }

    public void addRow(View view){
        if(rows > 3){
            Toast toast = Toast.makeText(this, "Це максимальний розмір матриці!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        TableLayout tableA = findViewById(R.id.tableMatrixHM);
        TableRow tableRowA = new TableRow(this);
        tableRowA.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tableRowA.setGravity(Gravity.CENTER_HORIZONTAL);
        for(int j = 0; j < cols; j++){
            EditText editText = new EditText(this);
            TableRow.LayoutParams editTextParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            editText.setLayoutParams(editTextParams);
            editText.setMaxWidth(pixelW);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            editText.setText("0.0");
            editText.setTextSize(30);
            editText.setPadding(10, 10, 10, 10);
            tableRowA.addView(editText, j);
        }
        tableA.addView(tableRowA, rows);
        rows = rows + 1;
    }
    public void cutRow(View view){
        if(rows < 2){
            Toast toast = Toast.makeText(this, "Це мінімальний розмір матриці!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        TableLayout tableA = findViewById(R.id.tableMatrixHM);
        TableRow lastTableRowA = (TableRow)tableA.getChildAt(rows-1);
        tableA.removeView(lastTableRowA);

        rows = rows - 1;
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

    public void addCol(View view){
        if(cols > 3){
            Toast toast = Toast.makeText(this, "Це максимальний розмір матриці!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        TableLayout tableA = findViewById(R.id.tableMatrixHM);
        for(int i = 0; i < rows; i++){
            TableRow tableRow = (TableRow)tableA.getChildAt(i);
            EditText editText = new EditText(this);
            TableRow.LayoutParams editTextParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            editText.setLayoutParams(editTextParams);
            editText.setMaxWidth(pixelW);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            editText.setText("0.0");
            editText.setTextSize(30);
            editText.setPadding(10, 10, 10, 10);
            tableRow.addView(editText, cols);
        }
        cols = cols + 1;
    }
    public void cutCol(View view){
        if(cols < 2){
            Toast toast = Toast.makeText(this, "Це мінімальний розмір матриці!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        TableLayout tableA = findViewById(R.id.tableMatrixHM);
        for(int i = 0; i < rows; i++){
            TableRow tableRow = (TableRow)tableA.getChildAt(i);
            EditText lastCol = (EditText)tableRow.getChildAt(cols - 1);
            tableRow.removeView(lastCol);
        }
        cols = cols - 1;
    }
    public void plot(View view){
        Intent intent = new Intent(this, DrawingHMActivity.class);
        Matrix matrix = new Matrix(rows, cols);
        TableLayout tableLayout = findViewById(R.id.tableMatrixHM);
        for(int i = 0; i < rows; i++){
            TableRow tableRow = (TableRow)tableLayout.getChildAt(i);
            for(int j = 0; j < cols; j++){
                EditText cell = (EditText)tableRow.getChildAt(j);
                double value = Double.parseDouble(String.valueOf(cell.getText()));
                matrix.set(i, j, value);
            }
        }
        intent.putExtra(Matrix.class.getSimpleName(), matrix);
        startActivity(intent);
    }

    private static final int PICK_TXT_FILEA = 2;

    public void openAFile(View view){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");

        startActivityForResult(intent, PICK_TXT_FILEA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_TXT_FILEA){
            if(resultCode == RESULT_OK){
                try {
                    Uri uri = data.getData();
                    readTextFromFile(uri, PICK_TXT_FILEA);
                }
                catch (Exception e){
                    Toast toast = Toast.makeText(this, "Data not loaded!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            else{
                Toast toast = Toast.makeText(this, "File not opened!", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    private void readTextFromFile(Uri uri, int mark){
        try (InputStream inputStream =
                     getContentResolver().openInputStream(uri);
             Scanner scanner = new Scanner(new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(inputStream))))) {
            String[] mn = scanner.nextLine().trim().split(" ");
            int m = Integer.parseInt(mn[0]);
            int n = Integer.parseInt(mn[1]);
            Matrix matrix = new Matrix(m, n);
            while (scanner.hasNextLine()){
                for(int i = 0; i < m; i++){
                    String[] line = scanner.nextLine().trim().split(" ");
                    for(int j = 0; j < n; j++){
                        matrix.set(i, j, Double.parseDouble(line[j]));
                    }
                }
            }
            if(mark == PICK_TXT_FILEA) showMatrixA(matrix);
            else return;
        }
        catch (Exception e){
            e.printStackTrace();
            Toast toast = Toast.makeText(this, "Неправильний файл!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void showMatrixA(Matrix matrix){
        TableLayout table = findViewById(R.id.tableMatrixHM);
        int m = matrix.getRowDimension();
        int n = matrix.getColumnDimension();
        while (m > rows) addRow(findViewById(R.id.matrixHMaddRowBtn));
        while (m < rows) cutRow(findViewById(R.id.matrixHMsubRowBtn));
        while (n > cols) addCol(findViewById(R.id.matrixHMaddColBtn));
        while (n < cols) cutCol(findViewById(R.id.matrixHMsubColBtn));
        for(int i = 0; i < m; i++){
            TableRow row = (TableRow)table.getChildAt(i);
            for(int j = 0; j < n; j++){
                EditText cell = (EditText)row.getChildAt(j);
                cell.setText(String.valueOf(matrix.get(i, j)));
            }
        }
    }
}