package com.nicom.processing.processors.forth.processors.impl;

import com.nicom.processing.processors.forth.processors.AbstractProcessor;
import com.nicom.processing.processors.forth.stack.ForthStack;
import com.nicom.processing.processors.forth.utils.Utilities;

public class IfStatementProcessor  extends AbstractProcessor {

    ForthStack stack = ForthStack.INSTANCE;

    StringBuilder lineOfTheIfStatement = new StringBuilder();
    StringBuilder lineOfTheElseStatement = new StringBuilder();
    StringBuilder preProcessLine = new StringBuilder();
    StringBuilder postProcessLine = new StringBuilder();

    boolean elseStatementFound = false;

    boolean definitionIsNotComplete = true;

    @Override
    public String preProcess(String line) throws Exception {
        if (Utilities.isEmptyString(line)) {
            return "";
        }
        LineProcessor processor = new LineProcessor();
        return processor.process(line);
    }

    @Override
    public String postProcess(String line) throws Exception {
        if (Utilities.isEmptyString(line)) {
            return "";
        }
        LineProcessor processor = new LineProcessor();
        return processor.process(line);
    }

    @Override
    public String process(String line) throws Exception {
        StringBuilder result = new StringBuilder();

        String preResult;
        String proResult;
        String postResult;
        String stackValues;

        elseStatementFound = line.toUpperCase().contains("ELSE");

        setLineBeforeIfStatement(line);
        stackValues = stack.show();
        preResult = preProcess(preProcessLine.toString());
        stackValues = stack.show();

        setLineAfterThenStatement(line);
        stackValues = stack.show();
        setLineOfTheIfStatement(line);
        stackValues = stack.show();
        int descision = stack.popInt();
        if (descision == 1) {
            proResult = processLineOfTheIfStatement();
        } else {
            proResult = processLineOfTheElseStatement();
        }
        postResult = postProcess(postProcessLine.toString());

        result.append(proResult);
        stackValues = stack.show();
        result.append(postResult);
        stackValues = stack.show();
        return result.toString();
    }

    public void setLineBeforeIfStatement(String line) {
        String[] lineItems = line.split(" ");

        boolean ifFound = false;
        int count = 0;

        for (String lineItem : lineItems) {
            if (lineItem.equalsIgnoreCase("IF")) {
                ifFound = true;
            } else if (!ifFound) {
                if (count > 0) {
                    preProcessLine.append(" ");
                }
                preProcessLine.append(lineItem);
                count++;
            }
        }
    }

    public void setLineAfterThenStatement(String line) {
        String[] lineItems = line.split(" ");
        boolean ifFound = false;
        boolean thenFound = false;
        int nrOfIfFound = 0;
        int nrOfThenFound = 0;

        int count = 0;

        for (String lineItem : lineItems) {
            if (lineItem.equalsIgnoreCase("IF")) {
                ifFound = true;
                nrOfIfFound++;
            } else if (lineItem.equalsIgnoreCase("THEN")) {
                nrOfThenFound++;
                if (nrOfIfFound == nrOfThenFound) {
                    thenFound = true;
                    definitionIsNotComplete = false;
                }
            } else if (thenFound) {
                if (count > 0) {
                    postProcessLine.append(" ");
                }
                count++;
                postProcessLine.append(lineItem);
            }
        }
    }

    public void setLineOfTheIfStatement(String line) {
        String[] lineItems = line.split(" ");
        boolean ifFound = false;
        boolean elseFound = false;
        boolean thenFound = false;
        int nrOfIfFound = 0;
        int nrOfThenFound = 0;

        int count = 0;

        for (String lineItem : lineItems) {
            if (lineItem.equalsIgnoreCase("IF")) {
                ifFound = true;
                nrOfIfFound++;
            } else if (lineItem.equalsIgnoreCase("THEN")) {
                nrOfThenFound++;
                if (nrOfIfFound == nrOfThenFound) {
                    thenFound = true;
                    definitionIsNotComplete = false;
                }
            } else if (lineItem.equalsIgnoreCase("ELSE")) {
                nrOfThenFound++;
                if (nrOfIfFound == nrOfThenFound) {
                    thenFound = true;
                    definitionIsNotComplete = false;
                }
            } else if (ifFound && !elseFound && !thenFound) {
                if (count > 0) {
                    lineOfTheIfStatement.append(" ");
                }
                count++;
                lineOfTheIfStatement.append(lineItem);
            } else if (ifFound && elseFound && !thenFound) {
                if (count > 0) {
                    lineOfTheElseStatement.append(" ");
                }
                count++;
                lineOfTheElseStatement.append(lineItem);
            }
        }
    }

    @Override
    public boolean getDefinitionIsNotComplete() throws Exception {
        return definitionIsNotComplete;
    }

    private String processLineOfTheIfStatement() throws Exception {

        if (Utilities.isEmptyString(lineOfTheIfStatement.toString())) {
            return "";
        }
        LineProcessor processor = new LineProcessor();
        return processor.process(lineOfTheIfStatement.toString());
    }

    private String processLineOfTheElseStatement() throws Exception {//
        if (Utilities.isEmptyString(lineOfTheElseStatement.toString())) {
            return "";
        }
        LineProcessor processor = new LineProcessor();
        return processor.process(lineOfTheElseStatement.toString());
    }

}
