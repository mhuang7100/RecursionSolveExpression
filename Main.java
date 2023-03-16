import java.util.ArrayList;
import java.util.Scanner;
public class Main{
    public static void main(String[] args){
        System.out.println("What is the equation?");
        String input = getInput();
        System.out.println(solveExpression(input));
                /* 
        String expression = "1.2 + 5.1";
        ArrayList<Integer> list = new ArrayList<Integer>();
        list = find(expression, "+");

        String one = expression.substring(0, list.get(0));
        String two = expression.substring(list.get(0) + 1, expression.length());
        System.out.println(Double.parseDouble(one) + Double.parseDouble(two)); 
        */
        /*
        */
    }
    
    // gets player input (String)
    public static String getInput(){
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    public static double solveExpression(String expression){
        int start;
        int end;
        String endExpression = "";
        if (multOperations(expression)){
            
            if (search(expression, "(")){
                start = find(expression, "(").get(0);
                end = find(expression, ")").get(0);
                return solveExpression(expression.substring(0, start) + solveExpression(expression.substring(start, end)) + expression.substring(end, expression.length()));
            } else if (search(expression, "^")){
                start = findStart(expression, "^");
                end = findEnd(expression, "^");
                if (!expression.substring(0, start).equals("")){
                    endExpression += expression.substring(0, start);
                }
                System.out.println(start + " " + end); // troubleshooting
                endExpression += solveExpression(expression.substring(start, end));
                if (!expression.substring(end, expression.length() - 1).equals("")){
                    endExpression += expression.substring(end, expression.length() - 1);
                }
                
                return solveExpression(endExpression);
            
            } else return 0.0; // PLACEHOLDER - FINISH THIS
        } else return solveOperation(expression);
    }

    //used in solveExpression
    public static int findStart(String expression, String character){
        int start = find(expression, character).get(0) - 1;
        while (start > 0){
            start -= 1;
            if (isOperation(expression.substring(start, start + 1))){
                start += 1;
                return start;
            }
        }
        return start;
    }

    //used in solveExpression
    public static int findEnd(String expression, String character){
        int end = find(expression, character).get(0) + 1;
        while (end < expression.length() - 1){
            end += 1;
            if (isOperation(expression.substring(end, end + 1))){
                end -= 1;
                break;
            }
        }
        end += 1;
        return end;
    }

    // solve ONE operation and returns the result
    public static double solveOperation(String expression){
        // if there are parentheses, delete them
        if (expression.substring(0, 1).equals("(")){
            expression = expression.substring(1, expression.length() - 1);
        }
        // find the operator
        if (search(expression, "+")){
            ArrayList<Integer> list = new ArrayList<Integer>();
            list = find(expression, "+");

            String one = expression.substring(0, list.get(0));
            String two = expression.substring(list.get(0) + 1, expression.length());
            return Double.parseDouble(one) + Double.parseDouble(two);
        } else if (search(expression, "-")){
            ArrayList<Integer> list = new ArrayList<Integer>();
            list = find(expression, "-");

            String one = expression.substring(0, list.get(0));
            String two = expression.substring(list.get(0) + 1, expression.length());
            return Double.parseDouble(one) - Double.parseDouble(two);
        } else if(search(expression, "^")){

            ArrayList<Integer> list = new ArrayList<Integer>();
            list = find(expression, "^");

            String one = expression.substring(0, list.get(0));
            String two = expression.substring(list.get(0) + 1, expression.length());
            return Math.pow(Double.parseDouble(one), Double.parseDouble(two));
        }else if (search(expression, "*")){
            ArrayList<Integer> list = new ArrayList<Integer>();
            list = find(expression, "*");

            String one = expression.substring(0, list.get(0));
            String two = expression.substring(list.get(0) + 1, expression.length());
            return Double.parseDouble(one) * Double.parseDouble(two);
        }else if(search(expression, "/")){
            ArrayList<Integer> list = new ArrayList<Integer>();
            list = find(expression, "/");

            String one = expression.substring(0, list.get(0));
            String two = expression.substring(list.get(0) + 1, expression.length());
            return Double.parseDouble(one) / Double.parseDouble(two);
        }

        return 0.0;
    }


    // returns list of locations of all the instances of that operator (character) in the expression 
    public static ArrayList<Integer> find(String expression, String character){
        ArrayList<Integer> list = new ArrayList<Integer>();
        
        for (int i = 0; i < expression.length(); i++){
            if (expression.substring(i, i + 1).equals(character)){
                list.add(i);
            }
        }

        return list;
    }

    // checks if there is an instance of that operator (character) in the expression 
    public static boolean search(String expression, String character){
        for (int i = 0; i < expression.length(); i++){
            if (expression.substring(i, i + 1).equals(character)){
                return true;
            }
        }

        return false;
    }

    // checks if there are multiple operations to solve in the expression
    public static boolean multOperations(String expression){
        int count = 0;
        
        if (search(expression, "+")){
            count += 1;
        }
        if (search(expression, "-")){
            count += 1;
        }
        if (search(expression, "*")){
            count += 1;
        }
        if (search(expression, "/")){
            count += 1;
        }
        if (search(expression, "^")){
            count += 1;
        }
        
        if (count == 1){
            return true;
        } else return false;
    }

    public static boolean isOperation(String character){
        String[] operators = {"+", "-", "*", "/", "^"};
        for (String op : operators){
            if (op.equals(character)){
                return true;
            }
        }
        return false;
    }
}