//Lam Vinh Vong

package com.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Calculator extends JFrame {
    private Label myLabel;
    private TextField outputField;
    private ArrayList<Button> digits;
    private Button plusSign;
    private Button minusSign;
    private Button multSign;
    private Button divSign;
    private Button eqSign;
    private String currentOperation;
    private Double firstOperand;

    public Calculator() {
        this.currentOperation = "";
        this.firstOperand = 0.0;

        Container contentPane = getContentPane();
        JPanel displayPanel = new JPanel(new FlowLayout());
        outputField = new TextField("0", 20);
        displayPanel.add(outputField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

        JPanel digitButtonPanel = new JPanel(new GridLayout(4, 3));
        digits = new ArrayList<Button>();
        digits.add(new Button("0"));
        digits.add(new Button("1"));
        digits.add(new Button("2"));
        digits.add(new Button("3"));
        digits.add(new Button("4"));
        digits.add(new Button("5"));
        digits.add(new Button("6"));
        digits.add(new Button("7"));
        digits.add(new Button("8"));
        digits.add(new Button("9"));
        digits.add(new Button("."));
        digits.add(new Button("AC"));

        digits.get(10).setForeground(Color.MAGENTA);
        digits.get(11).setForeground(Color.MAGENTA);

        digitButtonPanel.add(digits.get(0));
        digitButtonPanel.add(digits.get(1));
        digitButtonPanel.add(digits.get(2));
        digitButtonPanel.add(digits.get(3));
        digitButtonPanel.add(digits.get(4));
        digitButtonPanel.add(digits.get(5));
        digitButtonPanel.add(digits.get(6));
        digitButtonPanel.add(digits.get(7));
        digitButtonPanel.add(digits.get(8));
        digitButtonPanel.add(digits.get(9));
        digitButtonPanel.add(digits.get(10));
        digitButtonPanel.add(digits.get(11));
        buttonPanel.add(digitButtonPanel);

        JPanel operatorButtonPanel = new JPanel(new GridLayout(5, 1));
        plusSign = new Button("+");
        minusSign = new Button("-");
        multSign = new Button("*");
        divSign = new Button("/");
        eqSign = new Button("=");

        plusSign.setForeground(Color.BLUE);
        minusSign.setForeground(Color.BLUE);
        multSign.setForeground(Color.BLUE);
        divSign.setForeground(Color.BLUE);
        eqSign.setForeground(Color.GREEN);

        operatorButtonPanel.add(plusSign);
        operatorButtonPanel.add(minusSign);
        operatorButtonPanel.add(multSign);
        operatorButtonPanel.add(divSign);
        operatorButtonPanel.add(eqSign);
        buttonPanel.add(operatorButtonPanel);

        displayPanel.add(buttonPanel);

        contentPane.add(displayPanel);
        //add(displayPanel);

        digits.get(11).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //currentOperation = "";
                //firstOperand = 0.0;
                //outputField.setText("0");
                resetValues();
            }
        });

        digits.get(10).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String currentText = outputField.getText();
                if (currentText.indexOf(".") < 0) {
                    outputField.setText(currentText + ".");
                }
            }
        });

        OperatorListener opListener = new OperatorListener();
        plusSign.addActionListener(opListener);
        minusSign.addActionListener(opListener);
        multSign.addActionListener(opListener);
        divSign.addActionListener(opListener);

        for (int i = 0; i <= 9; i++) {
            digits.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    String currentText = outputField.getText();
                    Button source = (Button) event.getSource();
                    String newDigit = "";
                    if (source == digits.get(0)) {
                        newDigit = "0";
                    } else if (source == digits.get(1)) {
                        newDigit = "1";
                    } else if (source == digits.get(2)) {
                        newDigit = "2";
                    } else if (source == digits.get(3)) {
                        newDigit = "3";
                    } else if (source == digits.get(4)) {
                        newDigit = "4";
                    } else if (source == digits.get(5)) {
                        newDigit = "5";
                    } else if (source == digits.get(6)) {
                        newDigit = "6";
                    } else if (source == digits.get(7)) {
                        newDigit = "7";
                    } else if (source == digits.get(8)) {
                        newDigit = "8";
                    } else if (source == digits.get(9)) {
                        newDigit = "9";
                    }

                    currentText = currentText + newDigit;
                    currentText = currentText.replaceFirst("^0+(?!$)", "");
                    outputField.setText(currentText);
                }
            });
        }

        eqSign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Double result = 0.0;
                String currentText = outputField.getText();
                try {
                    Double secondOperand = new Double(currentText);

                    if (currentOperation == "+") {
                        result = firstOperand + secondOperand;
                    } else if (currentOperation == "-") {
                        result = firstOperand - secondOperand;
                    } else if (currentOperation == "*") {
                        result = firstOperand * secondOperand;
                    } else if (currentOperation == "/") {
                        if (secondOperand != 0.0) {
                            result = firstOperand / secondOperand;
                        } else {
                            resetValues();
                            outputField.setBackground(Color.PINK);
                        }
                    }

                    outputField.setText(result.toString());
                    firstOperand = result;
                } catch (NumberFormatException e) {
                    resetValues();
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Calculator");
        setSize(500, 200);
        setLocation(100, 200);
        setVisible(true);
    }

    private void resetValues() {
        currentOperation = "";
        firstOperand = 0.0;
        outputField.setText("0");
        outputField.setBackground(Color.WHITE);
    }

    private class OperatorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            Button source = (Button) event.getSource();
            if (source == plusSign) {
                currentOperation = "+";
            } else if (source == minusSign) {
                currentOperation = "-";
            } else if (source == multSign) {
                currentOperation = "*";
            } else if (source == divSign) {
                currentOperation = "/";
            }

            String currentText = outputField.getText();
            try {
                Double currentTextDouble = new Double(currentText);
                firstOperand = currentTextDouble;
                outputField.setText("0");
            } catch (NumberFormatException e) {
                resetValues();
            }
        }
    }

    public static void main(String[] args) {
        JFrame myCalculator = new Calculator();
        myCalculator.show();
    }
}
