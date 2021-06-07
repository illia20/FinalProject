package com.example.finalproject;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
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

public class MultiplyActivity extends AppCompatActivity {
    int rowsA = 2;
    int colsA = 2;
    int rowsB = 2;
    int colsB = 2;

    int pixelW = 250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiply);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TableLayout tableLayoutA = findViewById(R.id.multTableMatrixA);
        tableLayoutA.setPadding(0, 10, 0, 0);

        for(int i = 0; i < rowsA; i++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            tableRow.setGravity(Gravity.CENTER_HORIZONTAL);
            for(int j = 0; j < colsA; j++){
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
            tableLayoutA.addView(tableRow, i);
        }
        TableLayout tableLayoutB = findViewById(R.id.multTableMatrixB);
        tableLayoutB.setPadding(0, 10, 0, 0);
        for(int i = 0; i < rowsB; i++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            tableRow.setGravity(Gravity.CENTER_HORIZONTAL);
            for(int j = 0; j < colsB; j++){
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
            tableLayoutB.addView(tableRow, i);
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

    @SuppressLint("DefaultLocale")
    public void mult(View view){
        cleanTitle();
        cleanResult();
        try{
            TableLayout tableLayoutA = findViewById(R.id.multTableMatrixA);
            Matrix matrixA = new Matrix(rowsA, colsA);
            for(int i = 0; i < rowsA; i++){
                TableRow tableRow = (TableRow)tableLayoutA.getChildAt(i);
                for(int j = 0; j < colsA; j++){
                    EditText cell = (EditText)tableRow.getChildAt(j);
                    double value = Double.parseDouble(String.valueOf(cell.getText()));
                    matrixA.set(i, j, value);
                }
            }
            TableLayout tableLayoutB = findViewById(R.id.multTableMatrixB);
            Matrix matrixB = new Matrix(rowsB, colsB);
            for(int i = 0; i < rowsB; i++){
                TableRow tableRow = (TableRow)tableLayoutB.getChildAt(i);
                for(int j = 0; j < colsB; j++){
                    EditText cell = (EditText)tableRow.getChildAt(j);
                    double value = Double.parseDouble(String.valueOf(cell.getText()));
                    matrixB.set(i, j, value);
                }
            }

            Matrix result = matrixA.times(matrixB);
            TextView resultTitle = findViewById(R.id.multResTitle);
            resultTitle.setText("A * B");
            TableLayout resTable = findViewById(R.id.tableMultRes);
            resTable.setPadding(0, 10, 0, 0);
            int rows = result.getRowDimension();
            int cols = result.getColumnDimension();
            for(int i = 0; i < rows; i++){
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                tableRow.setGravity(Gravity.CENTER_HORIZONTAL);
                for(int j = 0; j < cols; j++){
                    TextView textView = new TextView(this);
                    textView.setText(String.format("%.2f", result.get(i, j)));
                    textView.setTextSize(30);
                    textView.setPadding(10, 10, 10, 10);
                    tableRow.addView(textView, j);
                }
                resTable.addView(tableRow, i);
            }
        }
        catch (Exception e){
            Toast toast = Toast.makeText(this, "Неможливо помножити задані матриці!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void addRowA(View view){
        if(rowsA > 3){
            Toast toast = Toast.makeText(this, "Це максимальний розмір матриці!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        cleanTitle();
        cleanResult();

        TableLayout tableA = findViewById(R.id.multTableMatrixA);
        TableRow tableRowA = new TableRow(this);
        tableRowA.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tableRowA.setGravity(Gravity.CENTER_HORIZONTAL);
        for(int j = 0; j < colsA; j++){
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
        tableA.addView(tableRowA, rowsA);
        rowsA = rowsA + 1;
    }

    public void addColA(View view){
        if(colsA > 3){
            Toast toast = Toast.makeText(this, "Це максимальний розмір матриці!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        cleanTitle();
        cleanResult();

        TableLayout tableA = findViewById(R.id.multTableMatrixA);
        for(int i = 0; i < rowsA; i++){
            TableRow tableRow = (TableRow)tableA.getChildAt(i);
            EditText editText = new EditText(this);
            TableRow.LayoutParams editTextParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            editText.setLayoutParams(editTextParams);
            editText.setMaxWidth(pixelW);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            editText.setText("0.0");
            editText.setTextSize(30);
            editText.setPadding(10, 10, 10, 10);
            tableRow.addView(editText, colsA);
        }
        colsA = colsA + 1;
    }

    public void cutRowA(View view){
        if(rowsA < 2){
            Toast toast = Toast.makeText(this, "Це мінімальний розмір матриці!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        cleanTitle();
        cleanResult();

        TableLayout tableA = findViewById(R.id.multTableMatrixA);
        TableRow lastTableRowA = (TableRow)tableA.getChildAt(rowsA-1);
        tableA.removeView(lastTableRowA);

        rowsA = rowsA - 1;
    }

    public void cutColA(View view){
        if(colsA < 2){
            Toast toast = Toast.makeText(this, "Це мінімальний розмір матриці!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        cleanTitle();
        cleanResult();

        TableLayout tableA = findViewById(R.id.multTableMatrixA);
        for(int i = 0; i < rowsA; i++){
            TableRow tableRow = (TableRow)tableA.getChildAt(i);
            EditText lastCol = (EditText)tableRow.getChildAt(colsA - 1);
            tableRow.removeView(lastCol);
        }
        colsA = colsA - 1;
    }

    public void addRowB(View view){
        if(rowsB > 3){
            Toast toast = Toast.makeText(this, "Це максимальний розмір матриці!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        cleanTitle();
        cleanResult();

        TableLayout tableB = findViewById(R.id.multTableMatrixB);
        TableRow tableRowB = new TableRow(this);
        tableRowB.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tableRowB.setGravity(Gravity.CENTER_HORIZONTAL);
        for(int j = 0; j < colsB; j++){
            EditText editText = new EditText(this);
            TableRow.LayoutParams editTextParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            editText.setLayoutParams(editTextParams);
            editText.setMaxWidth(pixelW);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            editText.setText("0.0");
            editText.setTextSize(30);
            editText.setPadding(10, 10, 10, 10);
            tableRowB.addView(editText, j);
        }
        tableB.addView(tableRowB, rowsB);
        rowsB = rowsB + 1;
    }

    public void addColB(View view){
        if(colsB > 3){
            Toast toast = Toast.makeText(this, "Це максимальний розмір матриці!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        cleanTitle();
        cleanResult();

        TableLayout tableB = findViewById(R.id.multTableMatrixB);
        for(int i = 0; i < rowsB; i++){
            TableRow tableRow = (TableRow)tableB.getChildAt(i);
            EditText editText = new EditText(this);
            TableRow.LayoutParams editTextParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            editText.setLayoutParams(editTextParams);
            editText.setMaxWidth(pixelW);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            editText.setText("0.0");
            editText.setTextSize(30);
            editText.setPadding(10, 10, 10, 10);
            tableRow.addView(editText, colsB);
        }
        colsB = colsB + 1;
    }

    public void cutRowB(View view){
        if(rowsB < 2){
            Toast toast = Toast.makeText(this, "Це мінімальний розмір матриці!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        cleanTitle();
        cleanResult();

        TableLayout tableB = findViewById(R.id.multTableMatrixB);
        TableRow lastTableRowB = (TableRow)tableB.getChildAt(rowsB-1);
        tableB.removeView(lastTableRowB);
        rowsB = rowsB - 1;
    }

    public void cutColB(View view){
        if(colsB < 2){
            Toast toast = Toast.makeText(this, "Це мінімальний розмір матриці!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        cleanTitle();
        cleanResult();

        TableLayout tableB = findViewById(R.id.multTableMatrixB);
        for(int i = 0; i < rowsB; i++){
            TableRow tableRow = (TableRow)tableB.getChildAt(i);
            EditText lastCol = (EditText)tableRow.getChildAt(colsB - 1);
            tableRow.removeView(lastCol);
        }
        colsB = colsB - 1;
    }

    public void cleanTitle(){
        TextView resultTitle = findViewById(R.id.multResTitle);
        resultTitle.setText("");
    }

    public void cleanResult(){
        TableLayout resultTable = findViewById(R.id.tableMultRes);
        resultTable.removeAllViewsInLayout();
    }

    // Request code for creating a PDF document.
    private static final int CREATE_FILE = 1;
    private static final int PICK_TXT_FILEA = 2;
    private static final int PICK_TXT_FILEB = 3;

    public void createFile(View view) {
        if(colsA != rowsB){
            Toast toast = Toast.makeText(this, "Неможливо помножити матриці!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "mult.txt");
        startActivityForResult(intent, CREATE_FILE);
    }

    public void openAFile(View view){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");

        startActivityForResult(intent, PICK_TXT_FILEA);
    }

    public void openBFile(View view){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");

        startActivityForResult(intent, PICK_TXT_FILEB);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CREATE_FILE){
            if(resultCode == RESULT_OK){
                try{
                    Uri uri = data.getData();
                    OutputStream os = getContentResolver().openOutputStream(uri);
                    StringBuilder text = new StringBuilder();
                    text.append("A = \n");
                    Matrix matrixA = new Matrix(rowsA, colsA);
                    TableLayout tableLayout = findViewById(R.id.multTableMatrixA);
                    for(int i = 0; i < rowsA; i++){
                        TableRow tableRow = (TableRow)tableLayout.getChildAt(i);
                        for(int j = 0; j < colsA; j++){
                            EditText cell = (EditText)tableRow.getChildAt(j);
                            double value = Double.parseDouble(String.valueOf(cell.getText()));
                            text.append(value + " ");
                            matrixA.set(i, j, value);
                        }
                        text.append('\n');
                    }
                    text.append("B = \n");
                    Matrix matrixB = new Matrix(rowsB, colsB);
                    TableLayout tableLayoutB = findViewById(R.id.multTableMatrixB);
                    for(int i = 0; i < rowsB; i++){
                        TableRow tableRow = (TableRow)tableLayoutB.getChildAt(i);
                        for(int j = 0; j < colsB; j++){
                            EditText cell = (EditText)tableRow.getChildAt(j);
                            double value = Double.parseDouble(String.valueOf(cell.getText()));
                            text.append(value + " ");
                            matrixB.set(i, j, value);
                        }
                        text.append('\n');
                    }
                    Matrix mult = matrixA.times(matrixB);
                    text.append("A * B = \n");
                    for(int i = 0; i < mult.getRowDimension(); i++){
                        for(int j = 0; j < mult.getColumnDimension(); j++){
                            double value = mult.get(i, j);
                            text.append(value + " ");
                        }
                        text.append('\n');
                    }

                    os.write(text.toString().getBytes());
                    os.close();
                    Toast toast = Toast.makeText(this, "File saved!", Toast.LENGTH_LONG);
                    toast.show();
                }
                catch (Exception e) {
                    Toast toast = Toast.makeText(this, "Failed to save file!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            else{
                Toast toast = Toast.makeText(this, "File not saved!", Toast.LENGTH_LONG);
                toast.show();
            }
        }
        else if(requestCode == PICK_TXT_FILEA){
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
        else if(requestCode == PICK_TXT_FILEB){
            if(resultCode == RESULT_OK){
                try {
                    Uri uri = data.getData();
                    readTextFromFile(uri, PICK_TXT_FILEB);
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
            else if(mark == PICK_TXT_FILEB) showMatrixB(matrix);
            else return;
        }
        catch (Exception e){
            e.printStackTrace();
            Toast toast = Toast.makeText(this, "Неправильний файл!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void showMatrixA(Matrix matrix){
        TableLayout table = findViewById(R.id.multTableMatrixA);
        int m = matrix.getRowDimension();
        int n = matrix.getColumnDimension();
        while (m > rowsA) addRowA(findViewById(R.id.matrixAaddRowBtn));
        while (m < rowsA) cutRowA(findViewById(R.id.matrixAsubRowBtn));
        while (n > colsA) addColA(findViewById(R.id.matrixAaddColBtn));
        while (n < colsA) cutColA(findViewById(R.id.matrixAsubColBtn));
        for(int i = 0; i < m; i++){
            TableRow row = (TableRow)table.getChildAt(i);
            for(int j = 0; j < n; j++){
                EditText cell = (EditText)row.getChildAt(j);
                cell.setText(String.valueOf(matrix.get(i, j)));
            }
        }
    }
    private void showMatrixB(Matrix matrix){
        TableLayout table = findViewById(R.id.multTableMatrixB);
        int m = matrix.getRowDimension();
        int n = matrix.getColumnDimension();
        while (m > rowsB) addRowB(findViewById(R.id.matrixBaddRowBtn));
        while (m < rowsB) cutRowB(findViewById(R.id.matrixBsubRowBtn));
        while (n > colsB) addColB(findViewById(R.id.matrixBaddColBtn));
        while (n < colsB) cutColB(findViewById(R.id.matrixBsubColBtn));
        for(int i = 0; i < m; i++){
            TableRow row = (TableRow)table.getChildAt(i);
            for(int j = 0; j < n; j++){
                EditText cell = (EditText)row.getChildAt(j);
                cell.setText(String.valueOf(matrix.get(i, j)));
            }
        }
    }
}