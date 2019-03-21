package com.nicom.processing.processors.forth.processors.impl;

import com.nicom.processing.processors.forth.exceptions.UnknownProcessorType;
import com.nicom.processing.processors.forth.processors.AbstractLoopProcessor;
import com.nicom.processing.processors.forth.processors.AbstractProcessor;
import com.nicom.processing.processors.forth.stack.ForthStack;
import com.nicom.processing.processors.forth.utils.Utilities;

public class Inputprocessor  extends AbstractProcessor {

    ForthStack stack = ForthStack.INSTANCE;

    final String QUOTE = "" + '"';
    boolean definingNewVerb = false;
    boolean definingLoop = false;
    boolean definingVariable = false;
    boolean definingString = false;
    boolean definingIfStatement=false;

    @Override
    public String process(String input) throws UnknownProcessorType, Exception {
        String line = Utilities.removeUnwantedSpaces(input);

        if (line.contains(":")) {
            definingNewVerb = true;
        } else if (line.toUpperCase().contains("DO")) {
            definingLoop = true;
        }  else if (line.toUpperCase().contains("IF")) {
            definingIfStatement = true;
        } else if (line.toUpperCase().contains("VARIABLE")) {
            definingVariable = true;
        } else if (line.toUpperCase().contains("CONSTANT")) {
            definingVariable = true;
        } else if (line.toUpperCase().contains(QUOTE)) {
            definingString = true;
        }

        if (definingVariable) {
            VariableAndConstantProcessor processor = new VariableAndConstantProcessor();
            String result = processor.process(line);
            definingVariable = processor.getDefinitionIsNotComplete();
            return result;
        } else if (definingNewVerb) {
            DefineVerbProcessor defineVerbProcessor = new DefineVerbProcessor();
            String result = defineVerbProcessor.process(line);
            definingNewVerb = defineVerbProcessor.getDefinitionIsNotComplete();
            return result;
        } else if (definingLoop) {
            AbstractLoopProcessor loopProcessor = new DoLoopProcessor();
            String result = loopProcessor.process(line);
            definingLoop = loopProcessor.getDefinitionIsNotComplete();
            return result;
        }  else if (definingIfStatement) {
            IfStatementProcessor ifStatementProcessor = new IfStatementProcessor();
            String result = ifStatementProcessor.process(line);
            definingIfStatement = ifStatementProcessor.getDefinitionIsNotComplete();
            return result;
        }else if (definingString) {
            line = storingString(line);
            AbstractProcessor lineProcessor = new LineProcessor();
            return lineProcessor.process(line);
        } else {
            AbstractProcessor lineProcessor = new LineProcessor();
            return lineProcessor.process(line);
        }
    }

    private String storingString(String input) {
        String processLine = new String();
        String[] lines = input.split(QUOTE);
        int count = 0;
        int index = 0;
        for (String line : lines) {
            if (line.length() > 0) {
                if (index == 1) {
                    stack.push(line);
                } else if (count > 0) {
                    processLine += " " + line;
                    count++;
                } else {
                    processLine = line;
                    count++;
                }                
            }
            index++;
        }

        return processLine;
    }

    @Override
    public String preProcess(String line) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String postProcess(String line) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean getDefinitionIsNotComplete() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
