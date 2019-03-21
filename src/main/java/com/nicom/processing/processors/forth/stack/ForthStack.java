package com.nicom.processing.processors.forth.stack;

import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.codec.binary.Base64;

import com.nicom.processing.processors.forth.exceptions.StackIsEmptyException;
import com.nicom.processing.processors.forth.utils.Utilities;


public class ForthStack {

    Stack<String> stackStore = new Stack<String>();
    int topOfStack = -1;
    mode currentMode = mode.DECIMAL;

    public static final ForthStack INSTANCE = new ForthStack();

    private ForthStack() {
    }

    public int size() {
        return stackStore.size();
    }

    public void push(String value) {
        if (!Utilities.stringIsEmpty(value)) {
            stackStore.add(value);
            topOfStack++;
        }
    }

    public void push(byte[] value) {
        push(new String(value));
    }

    public void push(int value) {
        stackStore.push(Integer.toString(value));
        topOfStack++;
    }

    public void push(double value) {
        stackStore.push(Double.toString(value));
        topOfStack++;
    }

    public String pop() throws StackIsEmptyException {
        String retString = "";
        if (topOfStack < 0) {
            clear();
            throw new StackIsEmptyException();
        } else if (stackStore.size() < 1) {
            clear();
            throw new StackIsEmptyException();
        } else {
            retString = stackStore.pop();
            topOfStack--;
        }

        if (currentModeIsDecimal()) {
            return retString;
        } else {
            return convertToCurrentMode(retString);
        }
    }

    public int popInt() throws StackIsEmptyException, NumberFormatException {
        String retString = pop();
        int retValue;
        retValue = Integer.parseInt(retString);
        return retValue;
    }

    public double popDouble() throws StackIsEmptyException, NumberFormatException {
        String retString = pop();
        double retValue;
        retValue = Double.parseDouble(retString);
        return retValue;
    }

    public void clear() {
        stackStore = new Stack<String>();
        topOfStack = -1;
    }

    public void drop() throws StackIsEmptyException {
        pop();
    }

    public void swap() throws StackIsEmptyException { // 1 2 -> 2 1
        String top = pop();
        String next = pop();
        push(next);
        push(top);
    }

    public void rot() throws StackIsEmptyException { // 1 2 3    ->  2 3 1
        String top = pop();
        String next = pop();
        String bottom = pop();
        push(next);
        push(top);
        push(bottom);
    }

    public void over() throws StackIsEmptyException, NumberFormatException { // 1 2     ->  1 2 1 
        String top = pop();
        String first = pop();
        push(first);
        push(top);
        push(first);
    }

    public void dup() throws StackIsEmptyException { // 1 2     ->  1 2 2 
        String top = pop();
        push(top);
        push(top);
    }

    public void qdup() { // ?dup
        int val = getInt();
        if (val != 0) {
            push(get());
        }
    }

    public void add() throws StackIsEmptyException {
        int second = popInt();
        int first = popInt();
        push(first + second);
    }

    public void subtract() throws StackIsEmptyException {
        int second = popInt();
        int first = popInt();
        push(first - second);
    }

    public void multiply() throws StackIsEmptyException {
        int second = popInt();
        int first = popInt();
        push(first * second);
    }

    public void divide() throws StackIsEmptyException {
        int second = popInt();
        int first = popInt();
        push(first / second);
    }

    public void modulus() throws StackIsEmptyException {
        int second = popInt();
        int first = popInt();
        push(first % second);
    }

    /////////////////
    public void addDoubles() throws StackIsEmptyException {
        double second = popDouble();
        double first = popDouble();
        push(first + second);
    }

    public void subtractDoubles() throws StackIsEmptyException {
        double second = popDouble();
        double first = popDouble();
        push(first - second);
    }

    public void multiplyDoubles() throws StackIsEmptyException {
        double second = popDouble();
        double first = popDouble();
        push(first * second);
    }

    public void divideDoubles() throws StackIsEmptyException {
        double second = popDouble();
        double first = popDouble();
        push(first / second);
    }

    public void modulusDoubles() throws StackIsEmptyException {
        double second = popDouble();
        double first = popDouble();
        push(first % second);
    }

    public void squareDoubles() throws StackIsEmptyException {
        double first = popDouble();
        push(first * first);
    }

    public void and() throws StackIsEmptyException {
        int second = popInt();
        int first = popInt();
        push(first & second);
    }

    public void or() throws StackIsEmptyException {
        int second = popInt();
        int first = popInt();
        push(first | second);
    }

    public void exor() throws StackIsEmptyException {
        int second = popInt();
        int first = popInt();
        push(first ^ second);
    }

    public void square() throws StackIsEmptyException {
        int first = popInt();
        push(first ^ 2);
    }

    public void power() throws StackIsEmptyException {
        int second = popInt();
        int first = popInt();
        push(first ^ second);
    }

    public void max() throws StackIsEmptyException {
        int first = popInt();
        push(Math.max(first, first));
    }

    public void min() throws StackIsEmptyException {
        int first = popInt();
        push(Math.min(first, first));
    }

    public void sqrt() throws StackIsEmptyException {
        double value = popDouble();
        push(Math.sqrt(value));
    }

    public void round() throws StackIsEmptyException {
        double value = popDouble();
        push(Math.round(value));
    }

    public void floor() throws StackIsEmptyException {
        double value = popDouble();
        push(Math.floor(value));
    }

    public void ceil() throws StackIsEmptyException {
        double value = popDouble();
        push(Math.ceil(value));
    }

    public void radiansToDegrees() throws StackIsEmptyException {
        double value = popDouble();
        push(Math.toDegrees(value));
    }

    public void degreesToRadians() throws StackIsEmptyException {
        double value = popDouble();
        push(Math.toRadians(value));
    }

    public void sin() throws StackIsEmptyException {
        double value = popDouble();
        push(Math.sin(value));
    }

    public void cos() throws StackIsEmptyException {
        double value = popDouble();
        push(Math.cos(value));
    }

    public void tan() throws StackIsEmptyException {
        double value = popDouble();
        push(Math.tan(value));
    }

    public void log() throws StackIsEmptyException {
        double value = popDouble();
        push(Math.log(value));
    }

    public void logBase10() throws StackIsEmptyException {
        double value = popDouble();
        push(Math.log10(value));
    }

    public void random() throws StackIsEmptyException {
        int secondAndMax = popInt();
        int firstAndMin = popInt();
        int randomNum = ThreadLocalRandom.current().nextInt(firstAndMin, secondAndMax + 1);
        push(randomNum); 
    }

    public void base64Encode() throws StackIsEmptyException {
        String StringOnStack = pop();
        byte[] encodedstring = Base64.encodeBase64(StringOnStack.getBytes());
        push(new String(encodedstring));
    }

    public void base64Decode() throws StackIsEmptyException {
        String StringOnStack = pop();
        byte[] decodedstring = Base64.decodeBase64(StringOnStack.getBytes());
        push(new String(decodedstring));
    }

    public void convertToBinary() throws StackIsEmptyException {
        int topValue = popInt();
        String strBinaryNumber = Integer.toBinaryString(topValue);
        push(strBinaryNumber);
    }

    public void convertToHex() throws StackIsEmptyException {
        int topValue = popInt();
        String strBinaryNumber = Integer.toHexString(topValue);
        push(strBinaryNumber);
    }

    public void convertToOctal() throws StackIsEmptyException {
        int topValue = popInt();
        String strBinaryNumber = Integer.toOctalString(topValue);
        push(strBinaryNumber);
    }

    public void convertHexToDecimal() throws StackIsEmptyException {
        String StringOnStack = pop();
        int value = Integer.parseInt(StringOnStack, 16);
        push(value);
    }

    public void convertOctalToDecimal() throws StackIsEmptyException {
        String StringOnStack = pop();
        int value = Integer.parseInt(StringOnStack, 8);
        push(value);
    }

    public void convertBinaryToDecimal() throws StackIsEmptyException {
        String StringOnStack = pop();
        int value = Integer.parseInt(StringOnStack, 2);
        push(value);
    }

    public void count() {

    }

    public String get() {
        if (stackStore.size() > 0) {
            String str = (String) stackStore.get(topOfStack);
            return convertToCurrentMode(str);
        } else {
            return "End of stack reached";
        }
    }

    public int getInt() {
        int retValue;
        retValue = Integer.parseInt(get());
        return retValue;
    }

    public String show() {
        StringBuilder stackString = new StringBuilder();
        int count = 0;
        for (String item : stackStore) {
            if (!Utilities.stringIsEmpty(item)) {
                if (count > 0) {
                    stackString.append(" ");
                }
                stackString.append(item);
                count++;
            }
        }

        return stackString.toString();
    }

    public void setModeToDecimal() {
        currentMode = mode.DECIMAL;
    }

    public void setModeToHex() {
        currentMode = mode.HEX;
    }

    public void setModeToOctal() {
        currentMode = mode.OCTAL;
    }

    public void setModeToBinary() {
        currentMode = mode.BINARY;
    }
    
    public String getCurrentMode(){
        return currentMode.value;
    }
    
    public boolean currentModeIsDecimal(){
        return currentMode.value.equalsIgnoreCase(mode.DECIMAL.value);
    }

    String convertToCurrentMode(String value) {
        System.out.println(">" + value + "<");
        String retValue = "";
        String displayMode = currentMode.getValue();

        if (Utilities.stringIsEmpty(value) || !Utilities.isNumeric(value)) {
            return value;
        }
        System.out.println(">IS NUMERIC<");
        int numericValue = Integer.parseInt(value);

        switch (displayMode) {
            case "DECIMAL":
                retValue = value;
                break;

            case "HEX":
                retValue = Integer.toHexString(numericValue).toUpperCase();
                break;

            case "OCTAL":
                retValue = Integer.toOctalString(numericValue);
                break;

            case "BINARY":
                retValue = Integer.toBinaryString(numericValue);
                break;
        }

        return retValue;
    }

    public void StringtoAscii() throws StackIsEmptyException {
        String topString = pop();
        char character = topString.charAt(0); // This gives the character 'a'
        int ascii = (int) character;
        push(ascii);
    }

    public void intToChar() throws StackIsEmptyException {
        int topValue = popInt();
        char ch = (char) topValue;
        String character = new StringBuilder().append(ch).toString();
        push(character);
    }

    public void equals() throws StackIsEmptyException {
        int second = popInt();
        int first = popInt();
        if (first == second) {
            push(1);
        } else {
            push(0);
        }
    }

    public void equalsZero() throws StackIsEmptyException {
        int first = popInt();
        if (first == 0) {
            push(1);
        } else {
            push(0);
        }
    }

    public void smallerThanZero() throws StackIsEmptyException {
        int first = popInt();
        if (first < 0) {
            push(1);
        } else {
            push(0);
        }
    }

    public void greaterThan() throws StackIsEmptyException {
        int second = popInt();
        int first = popInt();
        if (first > second) {
            push(1);
        } else {
            push(0);
        }
    }

    public void smallerThan() throws StackIsEmptyException {
        int second = popInt();
        int first = popInt();
        if (first < second) {
            push(1);
        } else {
            push(0);
        }
    }
    
    public void equalsOrGreaterThan() throws StackIsEmptyException {   
        int second = popInt();
        int first = popInt();
        if (first > second || first==second ) {
            push(1);
        } else {
            push(0);
        }     
    }

    public void smallerThanOrEquals() throws StackIsEmptyException {
        int second = popInt();
        int first = popInt();
        if (first < second|| first==second ) {
            push(1);
        } else {
            push(0);
        }
    }

    public void not() throws StackIsEmptyException {
        int first = popInt();
        if (first ==0  ) {
            push(1);
        } else {
            push(0);
        }
    }

    public void equalsDoubles() throws StackIsEmptyException {
        double second = popDouble();
        double first = popDouble();
        if (first == second) {
            push(1);
        } else {
            push(0);
        }
    }

    public void equalsZeroDoubles() throws StackIsEmptyException {
        double first = popDouble();
        if (first == 0) {
            push(1);
        } else {
            push(0);
        }
    }

    public void smallerThanZeroDoubles() throws StackIsEmptyException {
        double first = popDouble();
        if (first < 0) {
            push(1);
        } else {
            push(0);
        }
    }

    public void greaterThanDoubles() throws StackIsEmptyException {
        double second = popDouble();
        double first = popDouble();
        if (first > second) {
            push(1);
        } else {
            push(0);
        }
    }

    public void smallerThanDoubles() throws StackIsEmptyException {
        double second = popDouble();
        double first = popDouble();
        if (first < second) {
            push(1);
        } else {
            push(0);
        }
    }

    
    public void equalsOrGreaterThanDoubles() throws StackIsEmptyException { 
        double second = popDouble();
        double first = popDouble();
        if (first > second || first==second ) {
            push(1);
        } else {
            push(0);
        }     
    }

    public void smallerThanOrEqualsDoubles() throws StackIsEmptyException {
        double second = popDouble();
        double first = popDouble();
        if (first < second|| first==second ) {
            push(1);
        } else {
            push(0);
        }
    }

    public void notDoubles() throws StackIsEmptyException {
        double first = popDouble();
        if (first ==0  ) {
            push(1);
        } else {
            push(0);
        }
    }

    public enum mode {
        DECIMAL("DECIMAL"),
        HEX("HEX"),
        OCTAL("OCTAL"),
        BINARY("BINARY");

        private String value;

        private mode(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
