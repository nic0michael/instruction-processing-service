package com.nicom.processing.processors.forth.dictionary;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


import com.nicom.processing.processors.forth.exceptions.VerbNotInDictionaryException;


@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class ForthDictionaryTest {

    ForthDictionary dictionary = ForthDictionary.INSTANCE;
    
    @Test
    public void showVerbDetailsTest()throws VerbNotInDictionaryException {
        dictionary.clearDictionary();
        String expected="Verb: +. Definition: + .";
        String name="+.";
        String definition="+ .";
        String compiledDefinition="+ .";
        Verb verb=new Verb(name, definition, compiledDefinition);
        dictionary.addVerbToDictionary(verb);
        String found=dictionary.showVerbDetails();
        assertThat(found , is(expected));
    }
    
 
    @Test
    public void addAndGetVerbFromDictionaryTest() throws VerbNotInDictionaryException {
        String name = "addAndGetTest";
        int initialSize =  dictionary.size();
        String definition = ". .";
        String compiledDefinition = ". .";
        Verb verb = new Verb(name, definition, compiledDefinition);
        dictionary.addVerbToDictionary(verb);
        String definition2 = dictionary.getDefinition(name);
        String compiledDefinition2 = dictionary.getCompiledDefinition(name);
        assertThat(definition, is(definition2));
        assertThat(compiledDefinition, is(compiledDefinition2));
        dictionary.removeFromDictionary(verb);
        int size = dictionary.size();
        assertThat(size , is(initialSize));
        
    }

    @Test
    public void addAddAndGetTest() throws VerbNotInDictionaryException {
        String name = "addAddAndGetTest";
        String definition = ".";
        String compiledDefinition = ".";
        int initialSize = 1;
        Verb verb = new Verb(name, definition, compiledDefinition);
        dictionary.addVerbToDictionary(verb);
        definition = "..";
        compiledDefinition = "..";
        verb = new Verb(name, definition, compiledDefinition);
        dictionary.addVerbToDictionary(verb);
        String definition2 = dictionary.getDefinition(name);
        String compiledDefinition2 = dictionary.getCompiledDefinition(name);
        assertThat(definition, is(definition2));
        assertThat(compiledDefinition, is(compiledDefinition2));
        dictionary.removeFromDictionary(verb);
        int size = dictionary.size();
        assertThat(size , is(initialSize));
    }


}
