import java.util.Stack;
import java.util.EmptyStackException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class InPrPo extends JFrame implements ActionListener {
    private JTextField infixExpressionField;
    private JTextField postfixExpressionField;
    private JTextField prefixExpressionField;

    InPrPo() {
        JLabel infixLabel = new JLabel("Infix Expression:");
        JLabel postfixLabel = new JLabel("Postfix Expression:");
        JLabel prefixLabel = new JLabel("Prefix Expression:");
        infixExpressionField = new JTextField();
        postfixExpressionField = new JTextField();
        prefixExpressionField = new JTextField();
        JButton convertButton = new JButton("Convert");

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(4, 2));
        contentPane.add(infixLabel);
        contentPane.add(infixExpressionField);
        contentPane.add(postfixLabel);
        contentPane.add(postfixExpressionField);
        contentPane.add(prefixLabel);
        contentPane.add(prefixExpressionField);
        contentPane.add(convertButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Infix to Postfix/Prefix Converter");
        setSize(400, 200);
        setContentPane(contentPane);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);

        convertButton.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            String infixExpression = infixExpressionField.getText();
            String postfixExpression = infixToPostfix(infixExpression);
            String prefixExpression = infixToPrefix(infixExpression);

            postfixExpressionField.setText(postfixExpression);
            prefixExpressionField.setText(prefixExpression);
        }
    }

    private int precedence(char ch) {
        switch (ch) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }

    private String infixToPostfix(String expression) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char ch : expression.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                postfix.append(ch);
            } else if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop());
                }
                stack.pop(); // Pop the remaining '('
            } else {
                while (!stack.isEmpty() && precedence(ch) <= precedence(stack.peek())) {
                    postfix.append(stack.pop());
                }
                stack.push(ch);
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }

        return postfix.toString();
    }

    private String infixToPrefix(String expression) {
        StringBuilder prefix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = expression.length() - 1; i >= 0; i--) {
            char ch = expression.charAt(i);

            if (Character.isLetterOrDigit(ch)) {
                prefix.insert(0, ch);
            } else if (ch == ')') {
                stack.push(ch);
            } else if (ch == '(') {
                while (!stack.isEmpty() && stack.peek() != ')') {
                    prefix.insert(0, stack.pop());
                }
                stack.pop(); // Pop the remaining ')'
            } else {
                while (!stack.isEmpty() && precedence(ch) < precedence(stack.peek())) {
                    prefix.insert(0, stack.pop());
                }
                stack.push(ch);
            }
        }

        while (!stack.isEmpty()) {
            prefix.insert(0, stack.pop());
        }

        return prefix.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InPrPo();
        });
    }
}
