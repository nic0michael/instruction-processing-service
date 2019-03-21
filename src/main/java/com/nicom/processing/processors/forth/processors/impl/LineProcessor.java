package com.nicom.processing.processors.forth.processors.impl;

import com.nicom.processing.processors.forth.dictionary.ForthDictionary;
import com.nicom.processing.processors.forth.processors.AbstractProcessor;
import com.nicom.processing.processors.forth.stack.ForthStack;
import com.nicom.processing.processors.forth.utils.Utilities;
import com.nicom.processing.processors.forth.variables.VariablesStore;

public class LineProcessor  extends AbstractProcessor {

    ForthDictionary dictionary = ForthDictionary.INSTANCE;
    ForthStack stack =ForthStack.INSTANCE;
    VariablesStore variables = VariablesStore.INSTANCE;


    @Override
    public String process(String line) throws Exception {
        if(Utilities.isEmptyString(line)){
            return "";
        }
        VerbProcessor verbProcessor = new VerbProcessor();
        StringBuilder result = new StringBuilder();
        String[] verbs = line.split(" ");
        for (String verb : verbs) {
            if (Utilities.isNumeric(verb)) {
                stack.push(verb);
            }else if (variables.isVariable(verb.toUpperCase())) {
                result.append(verbProcessor.process(verb));
            }else{
                String def = dictionary.getCompiledDefinition(verb);
                String[] definitions = def.split(" ");
                for (String definition : definitions) {
                    result.append(verbProcessor.process(definition));
                }
            }

        }

        return result.toString();
    }

    
    @Override
    public String preProcess(String line) throws Exception {throw new UnsupportedOperationException("Not supported yet.");}
    
    @Override
    public String postProcess(String line) throws Exception {throw new UnsupportedOperationException("Not supported yet."); }

    @Override
    public boolean getDefinitionIsNotComplete() throws Exception {throw new UnsupportedOperationException("Not supported yet.");}


}
