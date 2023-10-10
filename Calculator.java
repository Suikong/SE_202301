package calculator; 

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator {
    /**
     * a simple calculator
     */
    JFrame frame = new JFrame("Calculator");

    // Operator stack and operand stack
    Stack<String> OPTR = new Stack<String>();
    Stack<Double> OPND = new Stack<Double>();


    JPanel pNorth = new JPanel();
    JTextField inputTextField = new JTextField();// Input text box
    JButton clearButton = new JButton("C");// Clear button

    // Middle component, set to grid layout
    JPanel pCenter = new JPanel(new GridLayout(4, 4, 4, 4));

    // Related member variable
    String opndS;// Operand string
    double opndD;// operand
    String operator = null;// operator
    String text = null;// Text box content
    double result;// Calculation result
    int index = -1;// String interception subscript

    // Compare operator priority
    public String Precede(String optr1, String optr2) {
        switch (optr1) {
            case "+":
            case "-":
                if (optr2.equals("*") || optr2.equals("/"))
                    return "<";
                else
                    return ">";
            case "*":
            case "/":
                return ">";
            case "=":
                if (optr2.equals("="))
                    return "=";
                else
                    return "<";
            default:
                return "";
        }
    }

    // Perform operations on operands
    public double calculate(double a, String optr, double b) {
        switch (optr) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return a / b;
            default:
                return 0;
        }
    }

    // Interface initialization
    public void init() {

        OPTR.push("=");
        // Window setting
        frame.setSize(252, 333);// Set size
        frame.setLocationRelativeTo(null);// Set the window to display in the center
        frame.setResizable(false);// Unchangeable size
        frame.setVisible(true);// Window visibility
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Exit window


        inputTextField.setPreferredSize(new Dimension(172, 53));// Text box size
        inputTextField.setEditable(false);// Set to not editable
        inputTextField.setFont(new Font("ДжЬх", Font.BOLD, 15));// Font size and style
        inputTextField.setHorizontalAlignment(JTextField.RIGHT);// Text is set to the right

        clearButton.setForeground(Color.red);// The Clear button is red in color
        clearButton.setPreferredSize(new Dimension(56, 53));// Set button size

        // Register a listener for the Clear button
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputTextField.setText("");
            }
        });
        pNorth.add(inputTextField);
        pNorth.add(clearButton);
        frame.add(pNorth, BorderLayout.NORTH);


        String btName = "123+456-789*0.=/";// Button name
        String regex = "[\\+\\-*/.=]";// Regular expression
        /**
         * This block uses a loop to create 16 buttons and registers the corresponding listener for these buttons
         */
        for (int i = 0; i < 16; i++) {
            String name = btName.substring(i, i + 1);// Cut characters from btName
            JButton bt = new JButton();
            bt.setText(name);

            // Set the size of the operator button in blue
            if (name.matches(regex)) {
                bt.setForeground(Color.blue);
                bt.setFont(new Font("bold", Font.BOLD, 14));
            } else {// Sets the size of the number button in italics
                bt.setFont(new Font("italic", Font.ITALIC, 14));
            }

            // Register a listener for these buttons
            bt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String click = e.getActionCommand();// Gets the string of mouse click buttons
                    // If you click the operator button
                    if (click.matches("[\\+\\-*/=]")) {
                        String input = inputTextField.getText();// Current text box content
                        opndS = input.substring(index + 1);// The operand string before the current operator
                        opndD = Double.parseDouble(opndS);// Converts a string to a double number
                        OPND.push(opndD);// Pushes the operand onto the stack

                        // Compares the priority size of the top operator of the operator stack with that of the currently clicked operator
                        switch (Precede(OPTR.peek(), click)) {
                            case ">":
                                // If the priority of the top operator is greater than click, the loop executes off the stack - in stack - out stack - Stack operation
                                while (Precede(OPTR.peek(), click).equals(">")) {
                                    // Two operands pop up
                                    double b = OPND.pop();
                                    double a = OPND.pop();
                                    // Pop-up operator
                                    String optr = OPTR.pop();
                                    double temp = calculate(a, optr, b);
                                    // Stack the results
                                    OPND.push(temp);
                                }
                                if (click.equals("="))
                                    result = OPND.pop();
                                else
                                    OPTR.push(click);
                                break;
                            case "<":
                                OPTR.push(click);// The top priority of the stack is less than click to push it onto the stack
                                break;
                        }
                        if (click.equals("=")) {
                            index = -1;
                            inputTextField.setText(result + "");// Show calculation result
                            text = inputTextField.getText();// Mark the text box content as the result of the calculation
                        } else {
                            index = input.length();// Current text box content length
                            inputTextField.setText(inputTextField.getText() + e.getActionCommand());// Set the text box content
                            text = null;// The text box content is marked empty
                        }
                        // If the text box content is not empty and a number button is clicked, the text box is cleared
                    } else if (text != null && !click.matches("[\\+\\-*/]")) {
                        inputTextField.setText("");
                        inputTextField.setText(inputTextField.getText() + e.getActionCommand());
                        text = null;
                    } else {
                        inputTextField.setText(inputTextField.getText() + e.getActionCommand());
                    }
                }
            });
            pCenter.add(bt);// Add 16 buttons to the middle container
        }
        frame.add(pCenter);// Adds the intermediate container to the window frame
    }

    public static void main(String[] args) {

        new Calculator().init();
    }
}
