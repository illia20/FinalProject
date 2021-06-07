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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Scanner;

import Jama.Matrix;

public class InverseActivity extends AppCompatActivity {

    int cols = 2;
    int rows = 2;

    int pixelW = 250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inverse);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TableLayout tableLayout = findViewById(R.id.tableInvStart);
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

    public void cleanResult(){
        TableLayout resTableLayout = findViewById(R.id.tableInvRes);
        resTableLayout.removeAllViewsInLayout();
    }

    public void expandTable(View view) {
        if(rows > 3){
            Toast toast = Toast.makeText(this, "This is maximal size of matrix!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        cleanResult();

        TableLayout tableLayout = findViewById(R.id.tableInvStart);
        for(int i = 0; i < rows; i++){
            TableRow tableRow = (TableRow)tableLayout.getChildAt(i);
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
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
        tableLayout.addView(tableRow, rows);
        rows = rows + 1;
    }

    public void cutTable(View view){
        if(rows < 2) {
            Toast toast = Toast.makeText(this, "This is minimal size of matrix!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        cleanResult();

        TableLayout tableLayout = findViewById(R.id.tableInvStart);
        TableRow lastTableRow = (TableRow)tableLayout.getChildAt(rows-1);
        tableLayout.removeView(lastTableRow);
        rows = rows - 1;
        for(int i = 0; i < rows; i++){
            TableRow tableRow = (TableRow)tableLayout.getChildAt(i);
            EditText lastCol = (EditText)tableRow.getChildAt(cols - 1);
            tableRow.removeView(lastCol);
        }
        cols = cols - 1;
    }

    @SuppressLint("DefaultLocale")
    public void calcInverse(View view){
        TableLayout tableLayout = findViewById(R.id.tableInvStart);
        Matrix matrix = new Matrix(rows, cols);
        for(int i = 0; i < rows; i++){
            TableRow tableRow = (TableRow)tableLayout.getChildAt(i);
            for(int j = 0; j < cols; j++){
                EditText cell = (EditText)tableRow.getChildAt(j);
                double value = Double.parseDouble(String.valueOf(cell.getText()));
                matrix.set(i, j, value);
            }
        }
        try {
            Matrix inv = matrix.inverse();
            TableLayout resTableLayout = findViewById(R.id.tableInvRes);
            resTableLayout.removeAllViewsInLayout();
            resTableLayout.setPadding(0, 10, 0, 0);

            for(int i = 0; i < rows; i++){
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                tableRow.setGravity(Gravity.CENTER_HORIZONTAL);
                for(int j = 0; j < cols; j++){
                    TextView textView = new TextView(this);
                    textView.setText(String.format("%.2f", inv.get(i, j)));
                    textView.setTextSize(30);
                    textView.setPadding(10, 10, 10, 10);
                    tableRow.addView(textView, j);
                }
                resTableLayout.addView(tableRow, i);
            }
        }
        catch (Exception e){
            cleanResult();
            Toast toast = Toast.makeText(this, "Can't calculate inverse matrix!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private static final int CREATE_FILE = 1;

    public void createFile(View view) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "inv.txt");
        startActivityForResult(intent, CREATE_FILE);
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
                    Matrix matrix = new Matrix(rows, cols);
                    TableLayout tableLayout = findViewById(R.id.tableInvStart);
                    for(int i = 0; i < rows; i++){
                        TableRow tableRow = (TableRow)tableLayout.getChildAt(i);
                        for(int j = 0; j < cols; j++){
                            EditText cell = (EditText)tableRow.getChildAt(j);
                            double value = Double.parseDouble(String.valueOf(cell.getText()));
                            text.append(value + " ");
                            matrix.set(i, j, value);
                        }
                        text.append('\n');
                    }
                    text.append("\nОбернена матриця = \n");
                    if(rows != cols || matrix.det() == 0) throw new Exception();

                    Matrix inverse = matrix.inverse();
                    for(int i = 0; i < inverse.getRowDimension(); i++){
                        for(int j = 0; j < inverse.getColumnDimension(); j++){
                            double value = inverse.get(i, j);
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
        else if(requestCode == PICK_TXT_FILE){
            if(resultCode == RESULT_OK){
                try {
                    Uri uri = data.getData();
                    readTextFromFile(uri);
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
    private void readTextFromFile(Uri uri) throws IOException, Exception {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream =
                     getContentResolver().openInputStream(uri);
             Scanner scanner = new Scanner(new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(inputStream))))) {
            String nstr = scanner.nextLine().trim();
            int n = Integer.parseInt(nstr);
            Matrix matrix = new Matrix(n, n);
            while (scanner.hasNextLine()){
                for(int i = 0; i < n; i++){
                    String[] line = scanner.nextLine().trim().split(" ");
                    for(int j = 0; j < n; j++){
                        matrix.set(i, j, Double.parseDouble(line[j]));
                    }
                }
            }
            showMatrix(matrix);
            /*
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + '\n');
            }
            String text = stringBuilder.toString();
            Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
            toast.show();
            Matrix matrix = stringToMatrix(text);
            showMatrix(matrix);
            */
        }
        catch (Exception e){
            e.printStackTrace();
            Toast toast = Toast.makeText(this, "Неправильний файл!", Toast.LENGTH_LONG);
            toast.show();
        }
    }
    private void showMatrix(Matrix matrix){
        TableLayout table = findViewById(R.id.tableInvStart);
        int n = matrix.getRowDimension();
        while (n > rows) expandTable(findViewById(R.id.addRowCol));
        while (n < rows) cutTable(findViewById(R.id.remRowCol));
        for(int i = 0; i < n; i++){
            TableRow row = (TableRow)table.getChildAt(i);
            for(int j = 0; j < n; j++){
                EditText cell = (EditText)row.getChildAt(j);
                cell.setText(String.valueOf(matrix.get(i, j)));
            }
        }
    }
    // Request code for selecting a TXT document.
    private static final int PICK_TXT_FILE = 2;

    public void openFile(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");

        startActivityForResult(intent, PICK_TXT_FILE);
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
}