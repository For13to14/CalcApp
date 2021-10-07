package ru.gb.cpourse1.calcapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    private EditText calculateExpressionEt;
    private TextView calculatedResultTv;

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

    private Button calcResultBtn;
    private Button cleanViewsBtn;
    private Button deleteLastSymbolBtn;

    private StringBuilder localStringBuffer;
    private final int ROUND_COUNT = 5;

    private static final String STRING_KEY = "string_key";
    private ParcelData parcelData = new ParcelData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initDigitButtons();
        initOperationButtons();
        initSpecialButtons();

        handlingDigitButtons();
        handlingOperationButtons();
        handlingSpecialButtons();

    }

    private void initViews() {
        calculatedResultTv = findViewById(R.id.result_text_view);
        calculateExpressionEt = findViewById(R.id.calculated_expression_edit_text);
    }

    private void initDigitButtons() {
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
    }

    private void initOperationButtons() {
        addOperationBtn = findViewById(R.id.add_operation_button);
        subOperationBtn = findViewById(R.id.subtract_operation_button);
        mulOperationBtn = findViewById(R.id.multiply_operation_button);
        divOperationBtn = findViewById(R.id.division_operation_button);
    }

    private void initSpecialButtons() {
        calcResultBtn = findViewById(R.id.calculate_result_button);
        cleanViewsBtn = findViewById(R.id.clean_views_button);
        deleteLastSymbolBtn = findViewById(R.id.delete_last_symbol_button);
    }

    private void handlingDigitButtons() {
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
    }

    private void handlingOperationButtons() {
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
    }

    private void handlingSpecialButtons() {
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
                if (localString.charAt(lastCharIndex - 1) == 'X' || localString.charAt(lastCharIndex - 1) == '/')
                    break;
            case 'X':
            case '/':
                localString.setCharAt(lastCharIndex, operationSymbol);
                break;
            default:
                localString.append(operationSymbol);
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