package com.nicom.processing.processors.forth.processors.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.nicom.processing.processors.forth.variables.VariablesStore;

@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class VariableAndConstantProcessorTest {

    VariableAndConstantProcessor processor=new VariableAndConstantProcessor();
    VariablesStore variables=VariablesStore.INSTANCE;
    
    @Test
    public void processTest() throws Exception {
        processor.process("1 variable one !");
        processor.process("2 constant two !");
        processor.process("variable three");
        variables.updateVariable("three",3);
        int result1=variables.getIntValueOfVariable("one");
        int result2=variables.getIntValueOfVariable("two");
        int result3=variables.getIntValueOfVariable("three");
        
        assertThat(result1, is(1));
        assertThat(result2, is(2));
        assertThat(result3, is(3));
    }   

}
