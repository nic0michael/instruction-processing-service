package com.nicom.processing.processors.forth.processors.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.nicom.processing.processors.forth.stack.ForthStack;


@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class IfStatementProcessorTest {
    IfStatementProcessor ifStatementProcessor=new IfStatementProcessor();
    ForthStack stack =ForthStack.INSTANCE;
    
    @Test
    public void Test() throws Exception{
        String line ="2 1 if 2 3 + . then .";
        String expected="52";
        String result=ifStatementProcessor.process(line);
        assertThat(result, is(expected));
    }
	

}
