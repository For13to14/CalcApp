package ru.gb.cpourse1.calcapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {


    private EditText calculateExpressionEt;
    private TextView calculatedResultTv;

    private StringBuilder localStringBuffer;
    private final int ROUND_COUNT = 5;

    private static final String STRING_KEY = "string_key";
    private ParcelData parcelData = new ParcelData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculatedResultTv = findViewById(R.id.result_text_view);
        calculateExpressionEt = findViewById(R.id.calculated_expression_edit_text);

        Button digitZeroBtn = findViewById(R.id.digit_zero_button);
        Button digitOneBtn = findViewById(R.id.digit_one_button);
        Button digitTwoBtn = findViewById(R.id.digit_two_button);
        Button digitThreeBtn = findViewById(R.id.digit_three_button);
        Button digitFourBtn = findViewById(R.id.digit_four_button);
        Button digitFiveBtn = findViewById(R.id.digit_five_button);
        Button digitSixBtn = findViewById(R.id.digit_six_button);
        Button digitSevenBtn = findViewById(R.id.digit_seven_button);
        Button digitEightBtn = findViewById(R.id.digit_eight_button);
        Button digitNineBtn = findViewById(R.id.digit_nine_button);
        Button decimalDotBtn = findViewById(R.id.decimal_dot_button);

        Button addOperationBtn = findViewById(R.id.add_operation_button);
        Button subOperationBtn = findViewById(R.id.subtract_operation_button);
        Button mulOperationBtn = findViewById(R.id.multiply_operation_button);
        Button divOperationBtn = findViewById(R.id.division_operation_button);

        Button calcResultBtn = findViewById(R.id.calculate_result_button);
        Button cleanViewsBtn = findViewById(R.id.clean_views_button);
        Button deleteLastSymbolBtn = findViewById(R.id.delete_last_symbol_button);

        //
        digitZeroBtn.setOnClickListener(view -> calculateExpressionEt.append("0"));
        digitOneBtn.setOnClickListener(view -> calculateExpressionEt.append("1"));
        digitTwoBtn.setOnClickListener(view -> calculateExpressionEt.append("2"));
        digitThreeBtn.setOnClickListener(view -> calculateExpressionEt.append("3"));
        digitFourBtn.setOnClickListener(view -> calculateExpressionEt.append("4"));
        digitFiveBtn.setOnClickListener(view -> calculateExpressionEt.append("5"));
        digitSixBtn.setOnClickListener(view -> calculateExpressionEt.append("6"));
        digitSevenBtn.setOnClickListener(view -> calculateExpressionEt.append("7"));
        digitEightBtn.setOnClickListener(view -> calculateExpressionEt.append("8"));
        digitNineBtn.setOnClickListener(view -> calculateExpressionEt.append("9"));
        decimalDotBtn.setOnClickListener(view -> calculateExpressionEt.append("."));


        addOperationBtn.setOnClickListener(view -> {

            localStringBuffer = new StringBuilder(calculateExpressionEt.getText().toString());
            if (checkStringEmpty(localStringBuffer)) {
                emptyStringToast();
                return;
            }
            changeOperation(localStringBuffer, '+');
            calculateExpressionEt.setText(localStringBuffer.toString());
        });
        subOperationBtn.setOnClickListener(view -> {

            localStringBuffer = new StringBuilder(calculateExpressionEt.getText().toString());
            //check is first number negative
            if (checkStringEmpty(localStringBuffer)) {
                localStringBuffer.append('-');
                calculateExpressionEt.setText(localStringBuffer.toString());
            //check double subtract operation
            } else if (localStringBuffer.charAt(localStringBuffer.length() - 1) != '-') {
                localStringBuffer.append('-');
                calculateExpressionEt.setText(localStringBuffer.toString());
            }
        });

        mulOperationBtn.setOnClickListener(view -> {

            localStringBuffer = new StringBuilder(calculateExpressionEt.getText().toString());
            if (checkStringEmpty(localStringBuffer)) {
                emptyStringToast();
                return;
            }
            changeOperation(localStringBuffer, 'X');
            calculateExpressionEt.setText(localStringBuffer.toString());
        });
        divOperationBtn.setOnClickListener(view -> {

            localStringBuffer = new StringBuilder(calculateExpressionEt.getText().toString());
            if (checkStringEmpty(localStringBuffer)) {
                emptyStringToast();
                return;
            }
            changeOperation(localStringBuffer, '/');
            calculateExpressionEt.setText(localStringBuffer.toString());
        });

        cleanViewsBtn.setOnClickListener(View -> {
            calculateExpressionEt.setText("");
            calculatedResultTv.setText("");
        });
        deleteLastSymbolBtn.setOnClickListener(View -> {
            Editable calculateExpression = calculateExpressionEt.getText();
            int lengthOfExpression = calculateExpression.length();
            if (lengthOfExpression != 0) {
                calculateExpression = calculateExpression.delete(lengthOfExpression - 1, lengthOfExpression);
                calculateExpressionEt.setText(calculateExpression);
            }
        });

        calcResultBtn.setOnClickListener(view -> {

            localStringBuffer = new StringBuilder(calculateExpressionEt.getText().toString());
            if (checkStringEmpty(localStringBuffer)) return;
            deleteRepetitiveDotesInString(localStringBuffer);
            deleteLastSymbolIfItOperation(localStringBuffer);

            BigDecimal result = BigDecimal.valueOf(Calc.calculate(localStringBuffer));
            result = result.setScale(ROUND_COUNT, RoundingMode.HALF_UP);
            calculatedResultTv.setText(result.toString());

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
        if (savedInstanceState != null && savedInstanceState.containsKey(STRING_KEY)) {
            parcelData = savedInstanceState.getParcelable(STRING_KEY);
            calculateExpressionEt.setText(parcelData.getParcelString());
        }

    }

    //Check is string empty to avoid useless work
    public boolean checkStringEmpty(StringBuilder localString) {
        return localString.toString().isEmpty();
    }

    private void emptyStringToast() {
        Toast.makeText(this, R.string.empty_string_toast_text, Toast.LENGTH_SHORT).show();
    }

    //Change math operation when user click operation buttons some consecutive times
    public StringBuilder changeOperation(StringBuilder localString, char operationSymbol) {
        int lastCharIndex = localString.length() - 1;
        char lastSymbol = localString.charAt(lastCharIndex);
        switch (lastSymbol) {
            case '+':
            case '-':
                if (localString.charAt(lastCharIndex-1) == 'X' || localString.charAt(lastCharIndex-1) == '/') break;
            case 'X':
            case '/':
                localString.setCharAt(lastCharIndex, operationSymbol); break;
            default: localString.append(operationSymbol);
        }
        return localString;
    }

    //Delete repetitive decimal dots in numbers
    public StringBuilder deleteRepetitiveDotesInString(StringBuilder localString) {
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
        }
        return localString;
    }

    public StringBuilder deleteLastSymbolIfItOperation(StringBuilder localString) {
        switch (localString.charAt(localString.length() - 1)) {
            case '+':
            case '-':
            case 'X':
            case '/':
                localString.deleteCharAt(localString.length() - 1);
                break;
            default:
                break;
        }
        return localString;
    }


}