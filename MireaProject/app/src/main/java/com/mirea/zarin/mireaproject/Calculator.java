package com.mirea.zarin.mireaproject;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.View.OnClickListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Calculator extends Fragment implements OnClickListener
{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Calculator()
    {

    }

    public static Calculator newInstance(String param1, String param2)
    {
        Calculator fragment = new Calculator();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View view)
    {
    switch (view.getId())
    {
        case R.id.zero:
            line+= 0;
            if(number1.equals("") | operation.equals(""))
                number1 += 0;

            else
                number2 += 0;
            resultView.setText(line);
            break;
        case R.id.one:
            line+= 1;
            if(number1.equals("") | operation.equals(""))
                number1 += 1;

            else
                number2 += 1;
            resultView.setText(line);
            break;
        case R.id.two:
            line+= 2;
            if(number1.equals("") | operation.equals(""))
                number1 += 2;
            else
                number2 += 2;
            resultView.setText(line);
            break;
        case R.id.three:
            line+= 3;
            if(number1.equals("") | operation.equals(""))
                number1 += 3;
            else
                number2 += 3;
            resultView.setText(line);
            break;
        case R.id.four:
            line+= 4;
            if(number1.equals("") | operation.equals(""))
                number1 += 4;
            else
                number2 += 4;
            resultView.setText(line);
            break;
        case R.id.five:
            line+= 5;
            if(number1.equals("") | operation.equals(""))
                number1 += 5;
            else
                number2 += 5;
            resultView.setText(line);
            break;
        case R.id.six:
            line+= 6;
            if(number1.equals("") | operation.equals(""))
                number1 += 6;
            else
                number2 += 6;
            resultView.setText(line);
            break;
        case R.id.seven:
            line+= 7;
            if(number1.equals("") | operation.equals(""))
                number1 += 7;
            else
                number2 += 7;
            resultView.setText(line);
            break;
        case R.id.eight:
            line+= 8;
            if(number1.equals("") | operation.equals(""))
                number1 += 8;
            else
                number2 += 8;
            resultView.setText(line);
            break;
        case R.id.nine:
            line+= 9;
            if(number1.equals("") | operation.equals(""))
                number1 += 9;
            else
                number2 += 9;
            resultView.setText(line);
            break;
        case R.id.plus:
            if(operation.equals(""))
            {
                line+="+";
                operation+="+";
                resultView.setText(line);
            }
            else
            {
                resultView.setText("Error");
            }
            break;
        case R.id.minus:
            if(operation.equals(""))
            {
                line+="-";
                operation+="-";
                resultView.setText(line);
            }
            else
            {
                resultView.setText("Error");
            }
            break;
        case R.id.multiply:
            if(operation.equals(""))
            {
                line+="*";
                operation+="*";
                resultView.setText(line);
            }
            else
            {
                resultView.setText("Error");
            }
            break;
        case R.id.division:
            if(operation.equals(""))
            {
                line+="/";
                operation+="/";
                resultView.setText(line);
            }
            else
            {
                resultView.setText("Error");
            }
            break;

        case R.id.equality:
            switch (operation)
            {
                case "+":
                    result = Integer.parseInt(number1)+ Integer.parseInt(number2);
                    break;
                case "-":
                    result = Integer.parseInt(number1) - Integer.parseInt(number2);
                    break;
                case "*":
                    result = Integer.parseInt(number1) * Integer.parseInt(number2);
                    break;
                case "/":
                    result = Integer.parseInt(number1) / Integer.parseInt(number2);
                    break;
            }
            number1 = String.valueOf(result);
            number2 = "";
            operation = "";
            line = String.valueOf(result);
            resultView.setText(line);
            break;
        case R.id.clear:
            number1 = "";
            number2 = "";
            line = "";
            operation = "";
            resultView.setText(" ");
            break;
    }
    }
    TextView resultView;
    String line = "";
    String number1 = "";
    String number2 = "";
    int result;
    String operation = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);
        resultView = view.findViewById(R.id.ResultView);
        Button zero = view.findViewById(R.id.zero);
        zero.setOnClickListener(this);
        Button one = view.findViewById(R.id.one);
        one.setOnClickListener(this);
        Button two = view.findViewById(R.id.two);
        two.setOnClickListener(this);
        Button three = view.findViewById(R.id.three);
        three.setOnClickListener(this);
        Button four = view.findViewById(R.id.four);
        four.setOnClickListener(this);
        Button five = view.findViewById(R.id.five);
        five.setOnClickListener(this);
        Button six = view.findViewById(R.id.six);
        six.setOnClickListener(this);
        Button seven = view.findViewById(R.id.seven);
        seven.setOnClickListener(this);
        Button eight = view.findViewById(R.id.eight);
        eight.setOnClickListener(this);
        Button nine = view.findViewById(R.id.nine);
        nine.setOnClickListener(this);
        Button plus = view.findViewById(R.id.plus);
        plus.setOnClickListener(this);
        Button minus = view.findViewById(R.id.minus);
        minus.setOnClickListener(this);
        Button multiply = view.findViewById(R.id.multiply);
        multiply.setOnClickListener(this);
        Button division = view.findViewById(R.id.division);
        division.setOnClickListener(this);
        Button equality = view.findViewById(R.id.equality);
        equality.setOnClickListener(this);;
        Button clear = view.findViewById(R.id.clear);
        clear.setOnClickListener(this);

        return view;
    }


}