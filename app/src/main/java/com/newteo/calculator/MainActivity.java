package com.newteo.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;
    Button btn0;
    Button btn_plus;
    Button btn_minus;
    Button btn_multiply;
    Button btn_divide;
    Button btn_equal;
    Button btn_clear;
    Button btn_delete;
    Button btn_point;
    EditText display;
    String storage;
    String pattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = (EditText) findViewById(R.id.display);
        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btn_plus = (Button) findViewById(R.id.btn_plus);
        btn_minus = (Button) findViewById(R.id.btn_minus);
        btn_multiply = (Button) findViewById(R.id.btn_multiply);
        btn_divide = (Button) findViewById(R.id.btn_divide);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_equal = (Button) findViewById(R.id.btn_equal);
        btn_point = (Button) findViewById(R.id.btn_point);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn_plus.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        btn_multiply.setOnClickListener(this);
        btn_divide.setOnClickListener(this);
        btn_equal.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_point.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String input = ((Button) v).getText().toString();
        String value = display.getText().toString();

        switch (v.getId()) {
            case R.id.btn0:
            case R.id.btn1:
            case R.id.btn2:
            case R.id.btn3:
            case R.id.btn4:
            case R.id.btn5:
            case R.id.btn6:
            case R.id.btn7:
            case R.id.btn8:
            case R.id.btn9:
                handleInput(input, value);
                break;
            case R.id.btn_point:
                handlePoint(input, value);
                break;
            case R.id.btn_plus:
            case R.id.btn_minus:
            case R.id.btn_multiply:
            case R.id.btn_divide:
                handlePattern(input, value);
                break;
            case R.id.btn_delete:
                handleDelete(value);
                break;
            case R.id.btn_clear:
                display.setText(null);
                storage = null;
                pattern = null;
                break;
            case R.id.btn_equal:
                handleEqual(value, null);
                break;
        }
    }

    private void handleInput(String input, String value) {
        if (value.length() == 1) {
            if (value.equals("0"))
                return;
            else
                display.setText(value + input);
        }
        else
            display.setText(value + input);
    }


    private void handlePoint(String input, String value) {
        if (value.length() == 0)
            return;
        if (pattern != null) {
            String patterRight = value.substring(value.indexOf(pattern) + 2, value.length());
            if (!patterRight.contains(".") && patterRight.length() > 0) {
                display.setText(value + input);
                return;
            }
            return;
        }
        if (!value.contains(".")) {
            display.setText(value + input);
        }

    }

    private void handleDelete(String value) {
        if (value != null && !value.equals("")) {
            String last = value.substring(value.length() - 1, value.length());
            if (last.equals(" ")) {
                value = value.substring(0, value.length() - 3);
                display.setText(value);
                pattern = null;
            }
            else {
                value = value.substring(0, value.length() - 1);
                display.setText(value);
            }
        }
    }

    private void handlePattern(String input, String value) {
        if (value.length() == 0)
            return;
        if (pattern != null) {
            String patterRight = value.substring(value.indexOf(pattern) + 2, value.length());
            if (patterRight.length() > 0) {
                handleEqual(value, input);
                return;
            }
            else
                return;
        }

        pattern = input;
        display.setText(value + " " + input + " ");
    }


    private void handleEqual(String value, String input) {
        if (!value.contains(" "))
            return;
        String patterRight = value.substring(value.indexOf(pattern) + 2, value.length());
        if (patterRight.length() == 0)
           return;
        String zero = value.substring(value.indexOf("/") + 2, value.indexOf("/") + 3);
        String zeroRight = value.substring(value.indexOf("/") + 3, value.length());
        if (zero.equals("0") && !zeroRight.contains("."))
            return;
        double left = Double.parseDouble(value.substring(0, value.indexOf(" ")));
        double right = Double.parseDouble(value.substring(value.indexOf(" ") + 3));
        handleResult(left, right, input);
    }

    private void handleResult(double left, double right, String input) {
        if (pattern.equals("+"))
            storage = (left + right) + "";
        else if (pattern.equals("-"))
            storage = (left - right) + "";
        else if (pattern.equals("*"))
            storage = (left * right) + "";
        else if (pattern.equals("/"))
            storage = (left / right) + "";
        if (storage.substring(storage.indexOf(".") + 1, storage.length()).equals("0"))
            storage = storage.substring(0, storage.indexOf("."));
        if (input != null) {
            display.setText(storage + " " + input + " " );
            pattern = input;
            return;
        }
        display.setText(storage);
        pattern = null;
    }

}
