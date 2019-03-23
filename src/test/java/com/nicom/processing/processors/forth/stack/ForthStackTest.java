package com.nicom.processing.processors.forth.stack;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.nicom.processing.processors.forth.exceptions.LineIsEmptyException;
import com.nicom.processing.processors.forth.exceptions.StackIsEmptyException;




@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class ForthStackTest {

    ForthStack stack =ForthStack.INSTANCE;

    @Test
    public void hexTest() throws StackIsEmptyException{
        stack.setModeToHex();
        stack.push(28);
        stack.push(27);
        stack.push(26);        
        String third=stack.pop(); 
        String second=stack.pop(); 
        String first=stack.pop(); 
        stack.setModeToDecimal();
        assertThat(first, is("1C"));
        assertThat(second, is("1B"));     
        assertThat(third, is("1A"));
    }
   
    @Test
    public void decTest() throws StackIsEmptyException{
        stack.push(1);
        stack.push(10);
        stack.push(100);
        String third=stack.pop(); 
        String second=stack.pop(); 
        String first=stack.pop(); 
                  
        assertThat(first, is("1"));
        assertThat(second, is("10"));     
        assertThat(third, is("100"));
    }
    
    @Test
    public void excersizeTest() throws LineIsEmptyException,StackIsEmptyException,NumberFormatException {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(3);
        stack.add();
        stack.pop();
        int second=stack.popInt();
        int first=stack.popInt();       
        assertThat(second, is(2));     
        assertThat(first, is(1));
    }
    
    
    @Test
    public void addTest() throws LineIsEmptyException,StackIsEmptyException,NumberFormatException {
        int expectedValue =3;
        int poppedValue;
        stack.push(1);
        stack.push(2);
        stack.add();
        poppedValue=stack.popInt();    
        assertThat(poppedValue, is(expectedValue));  
    }
    
    @Test
    public void subtractTest() throws LineIsEmptyException,StackIsEmptyException,NumberFormatException {
        int expectedValue =2;
        int poppedValue;
        stack.push(3);
        stack.push(1);
        stack.subtract();
        poppedValue=stack.popInt();    
        assertThat(poppedValue, is(expectedValue));  
    }
    
    @Test
    public void multiplyTest() throws LineIsEmptyException,StackIsEmptyException,NumberFormatException {
        int expectedValue =6;
        int poppedValue;
        stack.push(3);
        stack.push(2);
        stack.multiply();
        poppedValue=stack.popInt();    
        assertThat(poppedValue, is(expectedValue));  
    }
    
    @Test
    public void divideTest() throws LineIsEmptyException,StackIsEmptyException,NumberFormatException {
        int expectedValue =3;
        int poppedValue;
        stack.push(6);
        stack.push(2);
        stack.divide();
        poppedValue=stack.popInt();    
        assertThat(poppedValue, is(expectedValue));  
    }
    
    
    @Test
    public void pushPopTest() throws LineIsEmptyException,StackIsEmptyException,NumberFormatException {
        int expected =10;
        int poppedValue;
        stack.push(expected);
        poppedValue=stack.popInt();        
        assertThat(expected, is(poppedValue));
    }
   
    @Test
    public void dropTest() throws LineIsEmptyException,StackIsEmptyException {
        int expected =10;
        int expectedStackSize=stack.size();
        stack.push(expected);
        stack.drop();
        int stackSize=stack.size();
        assertThat(stackSize, is(expectedStackSize));
    }

     
    @Test
    public void dupTest() throws LineIsEmptyException,StackIsEmptyException,NumberFormatException {
        int expected =10;
        int poppedValue;
        int poppedValue2;
        stack.push(expected);
        stack.dup();
        poppedValue=stack.popInt();   
        poppedValue2=stack.popInt();        
        assertThat(expected, is(poppedValue));    
        assertThat(expected, is(poppedValue2));
    }
    
     
    @Test
    public void overTest() throws LineIsEmptyException,StackIsEmptyException,NumberFormatException {
        int expected =10;
        int expected2 =20;
        int poppedValue;
        int poppedValue2;
        stack.push(expected);
        stack.push(expected2);
        stack.over();
        poppedValue=stack.popInt();   
        poppedValue2=stack.popInt();        
        assertThat(expected, is(poppedValue));    
        assertThat(expected2, is(poppedValue2));
    }
   
    
    @Test
    public void rotTest() throws LineIsEmptyException,StackIsEmptyException,NumberFormatException {
        int expected =10;
        int expected2 =20;
        int expected3 =30;
        int poppedValue;
        int poppedValue2;
        int poppedValue3;
        stack.push(expected);
        stack.push(expected2);
        stack.push(expected3);
        stack.rot();
        poppedValue=stack.popInt();   
        poppedValue2=stack.popInt();    
        poppedValue3=stack.popInt();        
        assertThat(expected, is(poppedValue));    
        assertThat(expected3, is(poppedValue2));  
        assertThat(expected2, is(poppedValue3));
    }
 
    
    @Test
    public void swapTest() throws LineIsEmptyException,StackIsEmptyException,NumberFormatException {
        int expected =10;
        int expected2 =20;
        int poppedValue;
        int poppedValue2;
        stack.push(expected);
        stack.push(expected2);
        stack.swap();
        poppedValue=stack.popInt();   
        poppedValue2=stack.popInt();        
        assertThat(expected2, is(poppedValue));    
        assertThat(expected, is(poppedValue2));
    }

 
    @Test
    public void showTest() throws LineIsEmptyException,StackIsEmptyException,NumberFormatException {
        String expected="10 20 30";
        int value1 =10;
        int value2 =20;
        int value3 =30;
        stack.push(value1);
        stack.push(value2);
        stack.push(value3);
        String found=stack.show();    
        assertThat(found, is(expected));
    }


}
