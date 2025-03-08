import java.util.*;
import java.lang.*;
public class Main {
    public static void main(String[] args) {
        /* Coding Exercise: Perform operations (addition, subtraction, and multiplication) on two extremely large numbers.
        Numbers can have up to 500 digits. It uses decision-based string manipulations instead of
        merely converting strings into integers, performing math operations, and converting the result back to a string. */
        String num1 = "123";
        String num2 = "12";
        System.out.println(Addition(num1, num2));
        System.out.println(Subtraction(num1, num2));
        System.out.println(Multiplication(num1, num2));

    }

    public static String Addition(String num1, String num2) {
        int carryover = 0;
        int num1Digit = num1.length() - 1;
        int num2Digit = num2.length() - 1;
        ArrayList<Character> result = new ArrayList<>();

        while (num1Digit >= 0 || num2Digit >= 0 || carryover != 0) {
            int n1 = (num1Digit >= 0) ? num1.charAt(num1Digit) - '0' : 0;
            int n2 = (num2Digit >= 0) ? num2.charAt(num2Digit) - '0' : 0;
            int current = n1 + n2 + carryover;
            carryover = current / 10;
            current = current % 10;
            result.add((char) ('0' + current));
            num1Digit--;
            num2Digit--;
        }
        Collections.reverse(result);
        StringBuilder resultString = new StringBuilder(result.size());
        for (Character c : result) {
            resultString.append(c);
        }

        return resultString.toString();
    }

    public static String Subtraction(String num1, String num2) {
        //Subtract num 2 from num 1 without converting the strings into integers
        //num 1 will always be greater than num2, so no negative numbers
        int num1Digit = num1.length() - 1;
        int num2Digit = num2.length() - 1;
        int borrow = 0;
        StringBuilder result = new StringBuilder();

        while (num1Digit >= 0) {
            int n1 = num1.charAt(num1Digit) - '0' - borrow;
            int n2 = (num2Digit >= 0) ? num2.charAt(num2Digit) - '0' : 0;

            if (n1 < n2) {
                n1 += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }

            result.append(n1 - n2);
            num1Digit--;
            num2Digit--;
        }

        // Remove leading zeros
        while (result.length() > 1 && result.charAt(result.length() - 1) == '0') {
            result.deleteCharAt(result.length() - 1);
        }

        return result.reverse().toString();

    }

    public static String Multiplication(String num1, String num2) {
        //Takes two string parameters and return the product as a string.
        //The multiplication is done without converting the entire strings into integers.
        StringBuilder row = new StringBuilder();
        String[] rows = new String[num2.length()];
        int line = 0;
        for (int i = num2.length()- 1; i >= 0; i--){
            int num2Digit = num2.charAt(i) - '0';
            int carryover = 0;
            for (int j = num1.length() - 1; j >= 0; j--){
                int num1Digit = num1.charAt(j) - '0';
                row.append((char)(num1Digit * num2Digit + carryover)% 10);
                carryover = (num1Digit * num2Digit + carryover)/10;
            }
            if (carryover > 0)
                row.append((char)(carryover + '0'));
            row.reverse();
            row.append("0".repeat(Math.max(0, line)));
            rows[line] = row.toString();
            row.setLength(0);
            line++;

        }
        return AddUpRows(rows);
    }


    public static String AddUpRows (String[] rows){
        StringBuilder result = new StringBuilder();
        int carryover = 0;
        int columnSum = 0;
        int columnFromEnd = 0;

        for (int i = rows[rows.length - 1].length() -1; i >= 0; i--){
            for (int j = 0; j < rows.length; j++){
                int digit = 0;
                if (rows[j].length() > columnFromEnd){
                    digit = (rows[j].charAt(rows[j].length() - 1 - columnFromEnd)) - '0';
                }
                columnSum += digit;
            }
            int current = (columnSum + carryover) % 10;
            carryover = (columnSum + carryover) / 10;
            result.append((char)('0' + current));
            columnFromEnd++;
            columnSum = 0;

        }
        result.reverse();
        //remove leading zeroes
        String resultString = result.toString();
        int index = 0;
        //increase index until you find you reach the end or you find a char that isn't '0''
        while(index < resultString.length() && resultString.charAt(index) == '0' ){
            index++;
        }
        //if index equals resultString.length it means all the characters were zero. Just return one zero.
        //Otherwise return all the characters after the zeroes.
        return index == resultString.length() ? "0" : resultString.substring(index);
    }


}