package com.nicom.processing.processors.forth.processors.impl;

import com.nicom.processing.processors.forth.processors.AbstractProcessor;
import com.nicom.processing.processors.forth.stack.ForthStack;
import com.nicom.processing.processors.forth.utils.Utilities;
import com.nicom.processing.processors.forth.variables.VariablesStore;

public class VariableAndConstantProcessor  extends AbstractProcessor {    
    VariablesStore variables = VariablesStore.INSTANCE;
    ForthStack stack = ForthStack.INSTANCE;

    boolean definitionIsNotComplete = true;


    @Override
    public String process(String line) throws Exception {
        String[] words = line.split(" ");
        StringBuilder verbsToExecute = new StringBuilder();
        String variableName = null;
        String variableType = null;
        boolean variableNameFound = false;
        int count = 0;

        for (String word : words) {
            if (Utilities.isNumeric(word)) {
                stack.push(word);
            } else if (word.toUpperCase().equals("VARIABLE")) {
                variableType = "VARIABLE";
            }  else if (word.toUpperCase().equals("CONSTANT")) {
                variableType = "CONSTANT";
            } else if (!variableNameFound) {
                variableName = word;
                variableNameFound = true;
                if (variableType.equals("VARIABLE")) {
                    variables.addVariable(variableName);
                }
            } else if (variableNameFound) {
                if (word.equals("!")) {
                    String valueToStoreInVariable = stack.pop();
                    if (variableType.equals("VARIABLE")) {
                        variables.updateVariable(variableName, valueToStoreInVariable);
                    } else if (variableType.equals("CONSTANT")) {
                        variables.addConstant(variableName, valueToStoreInVariable);
                    }
                } else {
                    if (count > 0) {
                        verbsToExecute.append(" ");
                    }
                    verbsToExecute.append(word);
                    count++;
                }
            }
        }

        return postProcess(verbsToExecute.toString());
    }

    @Override
    public String postProcess(String line) throws Exception {
        LineProcessor lineProcessor = new LineProcessor();
        return lineProcessor.process(line);
    }

    @Override
    public boolean getDefinitionIsNotComplete() throws Exception {
        return definitionIsNotComplete;
    }

    @Override
    public String preProcess(String line) throws Exception { throw new UnsupportedOperationException("Not supported yet.");  }
    

}
