package com.calculator.model;


import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class CalculatorModel {

    public double calculate(String expression) {
        String formattedExpression = expression.replace("×", "*")
                .replace("÷", "/")
                .replace("√", "sqrt")
                .replace("²", "^2")
                .replace("%", "/100")
                .replace("mod", "%");
        try {
            Expression e = new ExpressionBuilder(formattedExpression).build();
            return e.evaluate();
        } catch (Exception e) {
            return Double.NaN;
        }
    }
}
