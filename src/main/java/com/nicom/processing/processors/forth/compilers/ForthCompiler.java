package com.nicom.processing.processors.forth.compilers;

import com.nicom.processing.processors.forth.dictionary.ForthDictionary;
import com.nicom.processing.processors.forth.exceptions.LineIsEmptyException;
import com.nicom.processing.processors.forth.exceptions.VerbNotInDictionaryException;
import com.nicom.processing.processors.forth.utils.Utilities;

public class ForthCompiler {
    ForthDictionary dictionary = ForthDictionary.INSTANCE;
    
    public String compile(String rawDefinition) throws VerbNotInDictionaryException, LineIsEmptyException{
        String rawDefinitionWithNoExtraSpaces=Utilities.removeUnwantedSpaces(rawDefinition);
        StringBuilder result=new StringBuilder();
        String[] verbs =rawDefinitionWithNoExtraSpaces.split(" ");
        int count=0;
        for (String verb : verbs) {
            String compiledDefinition =dictionary.getCompiledDefinition(verb);
            if(count>0){
                result.append(" ");
            }
            result.append(compiledDefinition);
            
            count++;
        }
        return result.toString();
    }

}
