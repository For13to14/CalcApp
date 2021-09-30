package ru.gb.cpourse1.calcapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private EditText calculateExpressionEt;
    private TextView calculatedResultTv;
    private Button calcResultBtn;

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

    private static final String STRING_KEY = "string_key";
    private ParcelData parcelData = new ParcelData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculatedResultTv = findViewById(R.id.result_text_view);
        calculateExpressionEt = findViewById(R.id.calculated_expression_edit_text);

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
        calcResultBtn = findViewById(R.id.calculate_result_button);

        digitZeroBtn.setOnClickListener(view -> calculateExpressionEt.setText(calculateExpressionEt.getText().toString() + '0'));
        digitOneBtn.setOnClickListener(view -> calculateExpressionEt.setText(calculateExpressionEt.getText().toString() + '1'));
        digitTwoBtn.setOnClickListener(view -> calculateExpressionEt.setText(calculateExpressionEt.getText().toString() + '2'));
        digitThreeBtn.setOnClickListener(view -> calculateExpressionEt.setText(calculateExpressionEt.getText().toString() + '3'));
        digitFourBtn.setOnClickListener(view -> calculateExpressionEt.setText(calculateExpressionEt.getText().toString() + '4'));
        digitFiveBtn.setOnClickListener(view -> calculateExpressionEt.setText(calculateExpressionEt.getText().toString() + '5'));
        digitSixBtn.setOnClickListener(view -> calculateExpressionEt.setText(calculateExpressionEt.getText().toString() + '6'));
        digitSevenBtn.setOnClickListener(view -> calculateExpressionEt.setText(calculateExpressionEt.getText().toString() + '7'));
        digitEightBtn.setOnClickListener(view -> calculateExpressionEt.setText(calculateExpressionEt.getText().toString() + '8'));
        digitNineBtn.setOnClickListener(view -> calculateExpressionEt.setText(calculateExpressionEt.getText().toString() + '9'));
        decimalDotBtn.setOnClickListener(view -> calculateExpressionEt.setText(calculateExpressionEt.getText().toString() + '.'));

        addOperationBtn.setOnClickListener(view -> {

            localStringBuffer = new StringBuilder(calculateExpressionEt.getText().toString());
            if (CheckStringEmpty(localStringBuffer)) return;
            ChangeOperation(localStringBuffer, '+');
            calculateExpressionEt.setText(localStringBuffer.toString());
        });

        subOperationBtn.setOnClickListener(view -> {

            localStringBuffer = new StringBuilder(calculateExpressionEt.getText().toString());
            if (CheckStringEmpty(localStringBuffer)) return;
            ChangeOperation(localStringBuffer, '-');
            calculateExpressionEt.setText(localStringBuffer.toString());
        });

        mulOperationBtn.setOnClickListener(view -> {

            localStringBuffer = new StringBuilder(calculateExpressionEt.getText().toString());
            if (CheckStringEmpty(localStringBuffer)) return;
            ChangeOperation(localStringBuffer, 'X');
            calculateExpressionEt.setText(localStringBuffer.toString());
        });

        divOperationBtn.setOnClickListener(view -> {

            localStringBuffer = new StringBuilder(calculateExpressionEt.getText().toString());
            if (CheckStringEmpty(localStringBuffer)) return;
            ChangeOperation(localStringBuffer, '/');
            calculateExpressionEt.setText(localStringBuffer.toString());
        });

        calcResultBtn.setOnClickListener(view -> {

            localStringBuffer = new StringBuilder(calculateExpressionEt.getText().toString());
            if (CheckStringEmpty(localStringBuffer)) return;
            DeleteRepetitiveDotesInString(localStringBuffer);
            //TODO calculate method
            calculatedResultTv.setText(localStringBuffer.toString());
        });


    }

    //Saved user data
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        parcelData.setParcelString(calculateExpressionEt.getText().toString());
        calculateExpressionEt.setText("");
        outState.putParcelable(STRING_KEY, parcelData);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null && savedInstanceState.containsKey(STRING_KEY)) {
            parcelData = (ParcelData) savedInstanceState.getParcelable(STRING_KEY);
            calculateExpressionEt.setText(parcelData.getParcelString());
        }

    }

    //Check is string empty to avoid useless work
    public boolean CheckStringEmpty(StringBuilder localString) {
        if (localString.toString().isEmpty()) {
            Toast.makeText(this, "There is no operation", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }

    //Delete repetitive decimal dots in numbers
    public StringBuilder DeleteRepetitiveDotesInString(StringBuilder localString) {
        boolean dotInTheString = false;
        for (int i = 0; i < localString.length(); i++) {
            switch (localString.charAt(i)) {
                case '.':
                    if (dotInTheString) {
                        localString.deleteCharAt(i--);
                    } else {
                        dotInTheString = true;
                    }
                    break;
                case '+':
                case '-':
                case 'X':
                case '/':
                    dotInTheString = false;
            }
            ;
        }
        return localString;
    }

    //Change math operation when user click operation buttons some consecutive times
    public StringBuilder ChangeOperation(StringBuilder localString, char operationSymbol) {
        int lastCharIndex = localString.length() - 1;
        char lastSymbol = localString.charAt(lastCharIndex);
        //if last symbol in the string is math operation - change it to the current operation (last checked)
        if (lastSymbol == '+' || lastSymbol == '-' || lastSymbol == 'X' || lastSymbol == '/') {
            localString.setCharAt(lastCharIndex, operationSymbol);
        } else localString.append(operationSymbol);
        return localString;
    }


}