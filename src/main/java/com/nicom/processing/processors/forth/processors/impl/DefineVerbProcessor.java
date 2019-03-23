package com.nicom.processing.processors.forth.processors.impl;

import com.nicom.processing.processors.forth.compilers.ForthCompiler;
import com.nicom.processing.processors.forth.dictionary.ForthDictionary;
import com.nicom.processing.processors.forth.dictionary.Verb;
import com.nicom.processing.processors.forth.processors.AbstractProcessor;

public class DefineVerbProcessor  extends AbstractProcessor {

    ForthDictionary dictionary = ForthDictionary.INSTANCE;

    boolean definitionIsNotComplete = true;
    StringBuilder verbDefinition = new StringBuilder();
    String verbName;
    String compiledDefinition;
    int iterationCount = 0;


    @Override
    public String process(String line) throws Exception {
        ForthCompiler compiler = new ForthCompiler();
        String returnString = "";
        String[] words = line.split(" ");

        for (String word : words) {
            if (word.equals(":")) {
                definitionIsNotComplete = true;
                returnString = "";
                verbDefinition = new StringBuilder();
                iterationCount = 0;
            } else if (word.equals(";")) {
                definitionIsNotComplete = false;
                returnString = "Ok";
                String definition = verbDefinition.toString();
                String compiledDefinition = compiler.compile(definition);
                Verb verb = new Verb(verbName, verbDefinition.toString(), compiledDefinition);
                verb.setDescription("USER DEFINED VERB");
                dictionary.addVerbToDictionary(verb);
            } else if (iterationCount > 0) {
                if (iterationCount == 1) {
                    verbName = word;
                } else {
                    if (iterationCount > 2) {
                        verbDefinition.append(" ");
                    }
                    verbDefinition.append(word);
                }
            }
            iterationCount++;
        }
        return returnString;

    }

    @Override
    public boolean getDefinitionIsNotComplete() throws Exception {
        return definitionIsNotComplete;
    }
    
    @Override
    public String preProcess(String line) throws Exception {throw new UnsupportedOperationException("Not supported yet."); }
    
    @Override
    public String postProcess(String line) throws Exception {throw new UnsupportedOperationException("Not supported yet.");}


}
