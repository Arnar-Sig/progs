package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText textInput;
    TextView textOutcome;
    Button calc;
    RadioButton radioBin;
    RadioButton radioDec;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textInput = findViewById(R.id.textInput);
        textOutcome = findViewById(R.id.textOutcome);
        radioDec = findViewById(R.id.radioButton1);
        radioBin = findViewById(R.id.radioButton2);
        radioGroup = findViewById(R.id.radioGroup);
        calc = findViewById(R.id.calc_button);
        calc.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String val = textInput.getText().toString();
        if(radioDec.isChecked()){
            textOutcome.setText(String.valueOf(calcDecimal(val)));
        }
        else if(radioBin.isChecked()){
            textOutcome.setText(calcBinary(val));
        }
        else{
            System.out.println("Hmm, this shouldn't be happening..");
        }
    }

    /**
     *
     * @param val = String of integer values representing a binary number, for example "111"
     * @return int value of the binary representation of val, for example 7.
     */
    private int calcDecimal(String val){
        StringBuilder valSB = new StringBuilder();
        valSB.append(val);
        valSB.reverse();
        val = valSB.toString();
        int outcome = 0;
        for(int i=val.length()-1; i>=0; i--){
            System.out.println(val.charAt(i));
            outcome = outcome + ((Integer.parseInt(String.valueOf(val.charAt(i))))*(int)Math.pow(2, i));
        }
        return outcome;
    }

    /**
     *
     * @param val = String of integer values representinga decimal number, for example "7".
     * @return String of integer values representing the binary of val, for example "111".
     */
    private String calcBinary(String val){
        int temp = Integer.valueOf(val);
        int lengthOfBin = 0;
        while(Math.pow(2,lengthOfBin) < temp){
            lengthOfBin++;
        }
        int [] binaryArray =  new int[lengthOfBin];
        int i = 0;
        while(temp>0){
            if(temp%2==0){
                binaryArray[i]=0;
            }
            else{
                binaryArray[i]=1;
            }
            temp = temp/2;
            i++;
        }
        String outcome = "";
        for(int j=0; j<lengthOfBin; j++){
            outcome = outcome + binaryArray[j];
        }
        return outcome;
    }
}