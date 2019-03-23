package com.nicom.processing.processors.forth.processors.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.nicom.processing.processors.forth.dictionary.ForthDictionary;
import com.nicom.processing.processors.forth.exceptions.VerbNotInDictionaryException;


@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class DefineVerbProcessorTest {
	

    DefineVerbProcessor defineVerbProcessor=new DefineVerbProcessor();
    ForthDictionary dictionary = ForthDictionary.INSTANCE;
    
    
    @Test
    public void addAndGetVerbFromDictionaryTest() throws VerbNotInDictionaryException, Exception {
        String name = "addAndGetTest";
        int initialSize =  dictionary.size();
        String retrievedCompiledDefinition;
        String result;
        String expectedResult="Ok";
        String expectedCompiledDefinition = ". .";
        String verbName="..";
        String instruction = ": .. . . ;";
        result=defineVerbProcessor.process(instruction);
        retrievedCompiledDefinition=dictionary.getCompiledDefinition(verbName); 
        int size = dictionary.size();
        assertThat(retrievedCompiledDefinition , is(expectedCompiledDefinition));
        assertThat(result , is(expectedResult));
        
    }

}
