package ru.gb.cpourse1.calcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private EditText calculatedExpressionEt;
    private TextView resultTv;
    private Button calculateBtn;

    private Button digitZeroBtn;
    private Button digitOneBtn;
    private Button digitTwoBtn;
    private Button digitThreeBtn;
    private Button digitFourBtn;
    private Button digitFiveBtn;
    private Button digitSixBtn;
    private Button digitSevenBtn;
    private Button digitEightBtn;
    private Button digitNineBtn;
    private Button decimalDotBtn;

    private Button addOperationBtn;
    private Button subOperationBtn;
    private Button mulOperationBtn;
    private Button divOperationBtn;
    private StringBuilder localStringBuffer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculateBtn = findViewById(R.id.calculate_result_button);
        resultTv = findViewById(R.id.result_text_view);
        calculatedExpressionEt = findViewById(R.id.calculated_expression_edit_text);
        digitZeroBtn = findViewById(R.id.digit_zero_button);
        digitOneBtn = findViewById(R.id.digit_one_button);
        digitTwoBtn = findViewById(R.id.digit_two_button);
        digitThreeBtn = findViewById(R.id.digit_three_button);
        digitFourBtn = findViewById(R.id.digit_four_button);
        digitFiveBtn = findViewById(R.id.digit_five_button);
        digitSixBtn = findViewById(R.id.digit_six_button);
        digitSevenBtn = findViewById(R.id.digit_seven_button);
        digitEightBtn = findViewById(R.id.digit_eight_button);
        digitNineBtn = findViewById(R.id.digit_nine_button);
        decimalDotBtn = findViewById(R.id.decimal_dot_button);

        addOperationBtn = findViewById(R.id.add_operation_button);
        subOperationBtn = findViewById(R.id.subtract_operation_button);
        mulOperationBtn = findViewById(R.id.multiply_operation_button);
        divOperationBtn = findViewById(R.id.division_operation_button);

        digitZeroBtn.setOnClickListener(view -> calculatedExpressionEt.setText(calculatedExpressionEt.getText().toString()+'0'));
        digitOneBtn.setOnClickListener(view -> calculatedExpressionEt.setText(calculatedExpressionEt.getText().toString()+'1'));
        digitTwoBtn.setOnClickListener(view -> calculatedExpressionEt.setText(calculatedExpressionEt.getText().toString()+'2'));
        digitThreeBtn.setOnClickListener(view -> calculatedExpressionEt.setText(calculatedExpressionEt.getText().toString()+'3'));
        digitFourBtn.setOnClickListener(view -> calculatedExpressionEt.setText(calculatedExpressionEt.getText().toString()+'4'));
        digitFiveBtn.setOnClickListener(view -> calculatedExpressionEt.setText(calculatedExpressionEt.getText().toString()+'5'));
        digitSixBtn.setOnClickListener(view -> calculatedExpressionEt.setText(calculatedExpressionEt.getText().toString()+'6'));
        digitSevenBtn.setOnClickListener(view -> calculatedExpressionEt.setText(calculatedExpressionEt.getText().toString()+'7'));
        digitEightBtn.setOnClickListener(view -> calculatedExpressionEt.setText(calculatedExpressionEt.getText().toString()+'8'));
        digitNineBtn.setOnClickListener(view -> calculatedExpressionEt.setText(calculatedExpressionEt.getText().toString()+'9'));
        decimalDotBtn.setOnClickListener(view -> calculatedExpressionEt.setText(calculatedExpressionEt.getText().toString()+'.'));

        addOperationBtn.setOnClickListener(view -> {

            localStringBuffer = new StringBuilder(calculatedExpressionEt.getText().toString());
            if (CheckStringEmpty(localStringBuffer)) return;
            DeleteRepetitiveDotesInString(localStringBuffer);
            ChangeOperation(localStringBuffer, '+');
            calculatedExpressionEt.setText(localStringBuffer.toString());
        });

        subOperationBtn.setOnClickListener(view -> {

            localStringBuffer = new StringBuilder(calculatedExpressionEt.getText().toString());
            if (CheckStringEmpty(localStringBuffer)) return;
            DeleteRepetitiveDotesInString(localStringBuffer);
            ChangeOperation(localStringBuffer, '-');
            calculatedExpressionEt.setText(localStringBuffer.toString());
        });

        mulOperationBtn.setOnClickListener(view -> {

            localStringBuffer = new StringBuilder(calculatedExpressionEt.getText().toString());
            if (CheckStringEmpty(localStringBuffer)) return;
            DeleteRepetitiveDotesInString(localStringBuffer);
            ChangeOperation(localStringBuffer, 'X');
            calculatedExpressionEt.setText(localStringBuffer.toString());
        });

        divOperationBtn.setOnClickListener(view -> {

            localStringBuffer = new StringBuilder(calculatedExpressionEt.getText().toString());
            if (CheckStringEmpty(localStringBuffer)) return;
            DeleteRepetitiveDotesInString(localStringBuffer);
            ChangeOperation(localStringBuffer, '/');
            calculatedExpressionEt.setText(localStringBuffer.toString());
        });


    }

    public boolean CheckStringEmpty (StringBuilder localString) {
        if (localString.toString().isEmpty()) {
            Toast.makeText(this, "There is no operation", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }

    public StringBuilder DeleteRepetitiveDotesInString (StringBuilder localString) {
        boolean dotInTheString = false;
        for (int i = 0; i < localString.length(); i++) {
            if (localString.charAt(i) == '.') {
                if(dotInTheString) {
                    localString.deleteCharAt(i);
                    i--;
                } else {
                    dotInTheString = true;
                }
            };
        }
        return localString;
    }

    public StringBuilder ChangeOperation (StringBuilder localString, char operationSymbol) {
        int lastCharIndex = localString.length()-1;
        char lastSymbol = localString.charAt(lastCharIndex);
        if (lastSymbol == '+' || lastSymbol == '-'|| lastSymbol == 'X'|| lastSymbol == '/') {
            localString.setCharAt(lastCharIndex, operationSymbol);
        } else localString.append(operationSymbol);
        return localString;
    }






}