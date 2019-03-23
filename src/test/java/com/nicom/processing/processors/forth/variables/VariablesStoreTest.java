package com.nicom.processing.processors.forth.variables;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.nicom.processing.processors.forth.processors.impl.VariableAndConstantProcessor;
import com.nicom.processing.processors.forth.variables.VariablesStore;

@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class VariablesStoreTest {
    VariablesStore variables=VariablesStore.INSTANCE;
    
    @Test
    public void processTest() throws Exception {
    	String variableName;
		String value;
		
    	variableName="one";
		value="1";
		variables.addVariable(variableName, value);		

    	variableName="three";
		variables.addVariable(variableName);
        variables.updateVariable("three",3);		

    	variableName="PI";
		value="3.14159";
		variables.addConstant(variableName, value);		

    	variableName="NAME";
		value="Nico";
		variables.addStringVariable(variableName, value);

    	variableName="SURNAME";
		value="Michael";
		variables.addStringConstant(variableName, value);		
		
    	
    	

        int result1=variables.getIntValueOfVariable("one");
        int result2=variables.getIntValueOfVariable("three");
        
        String result3=variables.getValueOfVariable("NAME");
        String result4=variables.getValueOfStringComnstant("SURNAME");
        
        double result5= variables.getDoubleValueOfVariable("PI");
        
        
        assertThat(result1, is(1));
        assertThat(result2, is(3));
        assertThat(result3, is("Nico"));
        assertThat(result4, is("Michael"));
        assertThat(result5, is(3.14159));
        
    }   

}
