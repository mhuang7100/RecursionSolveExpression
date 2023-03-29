import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
public class Main{
    public static void main(String[] args){
        System.out.println("\033[2J\033[H---CALCULATOR---");
        System.out.println("What is the equation? Say STOP to exit");
        String input = getInput();
        while (!input.equals("STOP")){
            System.out.println(solveExpression(input));
            System.out.println("--------------------");
            input = getInput();
        }
        
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
            
            if (search(expression, "(") > 0){
                // if useless parentheses surround the expression, delete them
                if (expression.substring(0, 1).equals("(") && expression.substring(expression.length() - 1, expression.length()).equals(")")){
                    expression = expression.substring(1, expression.length() - 1);
                    return solveExpression(expression);
                }
                start = find(expression, "(").get(find(expression, "(").size() - 1);
                end = find(expression, ")").get(0) + 1;
                
                if (!expression.substring(0, start).equals("")){
                    endExpression += expression.substring(0, start);
                }
                endExpression += solveExpression(expression.substring(start, end));
                if (!expression.substring(end, expression.length()).equals("")){
                    endExpression += expression.substring(end, expression.length());
                }
                return solveExpression(endExpression);
            } else if (search(expression, "^") > 0){
                start = findStart(expression, "^");
                end = findEnd(expression, "^");
                if (!expression.substring(0, start).equals("")){
                    endExpression += expression.substring(0, start);
                }
                endExpression += solveExpression(expression.substring(start, end));
                if (!expression.substring(end, expression.length()).equals("")){
                    endExpression += expression.substring(end, expression.length());
                }
                return solveExpression(endExpression);
            } else if (search(expression, "*") > 0 && (!(search(expression, "/") > 0) || find(expression, "*").get(0) < find(expression, "/").get(0))){
                start = findStart(expression, "*");
                end = findEnd(expression, "*");

                if (!expression.substring(0, start).equals("")){
                    endExpression += expression.substring(0, start);
                }
                endExpression += solveExpression(expression.substring(start, end));
                if (!expression.substring(end, expression.length()).equals("")){
                    endExpression += expression.substring(end, expression.length());
                }
                return solveExpression(endExpression);
            } else if (search(expression, "/") > 0 && (!(search(expression, "*") > 0) || find(expression, "/").get(0) < find(expression, "*").get(0))){
                start = findStart(expression, "/");
                end = findEnd(expression, "/");
                if (!expression.substring(0, start).equals("")){
                    endExpression += expression.substring(0, start);
                }
                endExpression += solveExpression(expression.substring(start, end));
                if (!expression.substring(end, expression.length()).equals("")){
                    endExpression += expression.substring(end, expression.length());
                }
                return solveExpression(endExpression);
            } else if (search(expression, "+") > 0 && (!(search(expression, "-") > 0) || find(expression, "+").get(0) < find(expression, "-").get(0))){
                start = findStart(expression, "+");
                end = findEnd(expression, "+");

                if (!expression.substring(0, start).equals("")){
                    endExpression += expression.substring(0, start);
                }
                endExpression += solveExpression(expression.substring(start, end));
                if (!expression.substring(end, expression.length()).equals("")){
                    endExpression += expression.substring(end, expression.length());
                }
                return solveExpression(endExpression);
            } else if (search(expression, "-") > 0 && (!(search(expression, "+") > 0) || find(expression, "-").get(0) < find(expression, "+").get(0))){
                start = findStart(expression, "-");
                end = findEnd(expression, "-");
                if (!expression.substring(0, start).equals("")){
                    endExpression += expression.substring(0, start);
                }
                endExpression += solveExpression(expression.substring(start, end));
                if (!expression.substring(end, expression.length()).equals("")){
                    endExpression += expression.substring(end, expression.length());
                }
                return solveExpression(endExpression);
            } else return 0.0;
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
        if (search(expression, "+") > 0){
            ArrayList<Integer> list = new ArrayList<Integer>();
            list = find(expression, "+");

            String one = expression.substring(0, list.get(0));
            String two = expression.substring(list.get(0) + 1, expression.length());
            return Double.parseDouble(one) + Double.parseDouble(two);
        } else if (search(expression, "-") > 0){
            ArrayList<Integer> list = new ArrayList<Integer>();
            list = find(expression, "-");
            String one;
            String two;
            if (expression.substring(0, 1).equals("-")){
                one = expression.substring(0, list.get(1));
                two = expression.substring(list.get(1) + 1, expression.length());
            } else {
                one = expression.substring(0, list.get(0));
                two = expression.substring(list.get(0) + 1, expression.length());
            }
            return Double.parseDouble(one) - Double.parseDouble(two);
        } else if(search(expression, "^") > 0){

            ArrayList<Integer> list = new ArrayList<Integer>();
            list = find(expression, "^");

            String one = expression.substring(0, list.get(0));
            String two = expression.substring(list.get(0) + 1, expression.length());
            return Math.pow(Double.parseDouble(one), Double.parseDouble(two));
        }else if (search(expression, "*") > 0){
            ArrayList<Integer> list = new ArrayList<Integer>();
            list = find(expression, "*");

            String one = expression.substring(0, list.get(0));
            String two = expression.substring(list.get(0) + 1, expression.length());
            return Double.parseDouble(one) * Double.parseDouble(two);
        }else if(search(expression, "/") > 0){
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
    public static int search(String expression, String character){
        int end = 0;
        for (int i = 0; i < expression.length(); i++){
            if (expression.substring(i, i + 1).equals(character)){
                end += 1;
                // doesn't count it if the - sign is a negative sign
                if (character.equals("-") && i == 0){
                    end -= 1;
                } else if (character.equals("-") && isSymbol(expression.substring(i - 1, i))){
                    end -= 1;
                }
            }
        }
        return end;
    }

    // checks if there are multiple operations to solve in the expression
    public static boolean multOperations(String expression){
        int count = 0;
        
        count += search(expression, "+");
        
        count += search(expression, "-");
        
        count += search(expression, "*");

        count += search(expression, "/");

        count += search(expression, "^");

        
        if (count == 1){
            return false;
        } else return true;
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

    // used only for checking if a minus sign is a negative sign
    public static boolean isSymbol(String character){
        String[] operators = {"+", "-", "*", "/", "^", "("};
        for (String op : operators){
            if (op.equals(character)){
                return true;
            }
        }
        return false;
    }
}