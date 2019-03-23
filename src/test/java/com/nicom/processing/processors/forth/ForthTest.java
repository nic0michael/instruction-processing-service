package com.nicom.processing.processors.forth;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.nicom.processing.processors.forth.exceptions.LineIsEmptyException;
import com.nicom.processing.processors.forth.exceptions.VerbNotInDictionaryException;


@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class ForthTest {

    @Test
    public void Test() throws Exception {
	Forth forth=new Forth();
	
	String input=" 1 2 3 + + .";
	String expectedResult="6";
	String result=forth.processInput(input);
	

    assertThat(result, is(expectedResult));
	
    }
    

    @Test
    public void Test2() throws Exception {
    	Forth forth= new Forth();
	
	String input1=" : +. + . ;";
	String input2=" 1 2 +.";
	String expectedResult="3";
	forth.processInput(input1);
	String result=forth.processInput(input2);
	

    assertThat(result, is(expectedResult));
	
    }

}
