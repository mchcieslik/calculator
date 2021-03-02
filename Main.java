package Calculator;


import java.util.*;


public class Main {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String input = reader.nextLine();


        List<String> tokens  = new ArrayList<>();

        //StringTokenizer is a legacy class that is retained for compatibility reasons
        // although its use is discouraged in new code.
        // It is recommended that anyone seeking this functionality
        // use the split method of String or the java.util.regex package instead.
        StringTokenizer token =  new StringTokenizer(input, "+*/-()", true);
        while(token.hasMoreTokens()){
            tokens.add(token.nextToken());
        }
        System.out.println(tokens.toString());
        System.out.println(postfix(tokens));
        System.out.println(calculate(postfix(tokens)));



    }
    public static List<String> postfix(List<String> infix) {
        Stack<String> converter = new Stack<>();
        List<String> postfix = new ArrayList<>();

        for (String token : infix) {
            if (isNumeric(token)) {
                postfix.add(token);

            } else if (token.equals("(")) {
                converter.push(token);
            } else if (token.equals(")")) {
                while (!converter.isEmpty() && !converter.peek().equals("(")) {
                    postfix.add(converter.pop());
                }
                converter.pop();

            } else if (isOperator(token)) {
                if(converter.isEmpty()) {
                    converter.push(token);
                } else {
                    if(seniority(token) <= seniority(converter.peek())){
                        postfix.add(converter.pop());
                        converter.push(token);
                    } else {
                        converter.push(token);
                    }
                }

            }

        }
        while(!converter.isEmpty()){
            postfix.add(converter.pop());
        }
        return postfix;

    }

    public static int seniority(String input) {
        return switch (input) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> 0;
        };
    }
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isOperator(String str){
        return str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/");
    }
    public static double calculate(List<String> postfix){
        if(postfix.isEmpty()){
            return 0;
        }
        Stack<Double> calculator = new Stack<Double>();
        for(String token: postfix){
            Double x,y;
            switch(token){
                case "+":calculator.push(calculator.pop()+calculator.pop()); break;
                case "-":y=calculator.pop(); x=calculator.pop(); calculator.push(x-y); break;
                case "*":calculator.push((calculator.pop()*calculator.pop())); break;
                case "/":y=calculator.pop(); x=calculator.pop(); calculator.push(x/y); break;
                default: calculator.push(Double.parseDouble(token));
            }
        }
        return calculator.pop();
    }

}






