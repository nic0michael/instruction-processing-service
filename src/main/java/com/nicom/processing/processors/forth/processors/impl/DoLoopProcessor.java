package com.nicom.processing.processors.forth.processors.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.nicom.processing.processors.forth.processors.AbstractLoopProcessor;
import com.nicom.processing.processors.forth.stack.ForthStack;
import com.nicom.processing.processors.forth.utils.Utilities;

public class DoLoopProcessor  extends AbstractLoopProcessor {

    ForthStack stack = ForthStack.INSTANCE;
    List<String> loopNames = new LinkedList<String>();
    Map<String, String> loopDefinitions = new HashMap<String, String>();
    Map<String, Integer> loopIterations = new HashMap<String, Integer>();

    boolean definitionIsNotComplete = true;

    final String FIRST_LOOP_NAME = "LOOP_0";

    StringBuilder lineOfTheLoop = new StringBuilder();
    StringBuilder preProcessLine = new StringBuilder();
    StringBuilder postProcessLine = new StringBuilder();

    @Override
    public String process(String line) throws Exception {
        StringBuilder result = new StringBuilder();

        String preResult;
        String proResult;
        String postResult;
        String stackValues;

        setLineBeforeLoop(line);
        stackValues = stack.show();
        preResult = preProcess(preProcessLine.toString());
        stackValues = stack.show();

        setLineAfterLoop(line);
        stackValues = stack.show();
        setLineOfTheLoop(line);
        stackValues = stack.show();

        proResult = processLineOfTheLoop();
        postResult = postProcess(postProcessLine.toString());

        result.append(proResult);
        stackValues = stack.show();
        result.append(postResult);
        stackValues = stack.show();

        return result.toString();
    }

    @Override
    public String preProcess(String line) throws Exception {
        
        if(Utilities.isEmptyString(line)){
            return "";
        }
        LineProcessor processor = new LineProcessor();
        return processor.process(line);
    }

    @Override
    public String postProcess(String line) throws Exception {
        
        if(Utilities.isEmptyString(line)){
            return "";
        }
        LineProcessor processor = new LineProcessor();
        return processor.process(line);
    }

    @Override
    public boolean getDefinitionIsNotComplete() throws Exception {
        return definitionIsNotComplete;
    }

    @Override
    public void setLineBeforeLoop(String line) {
        String[] lineItems = line.split(" ");

        boolean doFound = false;
        int count = 0;

        for (String lineItem : lineItems) {
            if (lineItem.equalsIgnoreCase("Do")) {
                doFound = true;
            } else if (!doFound) {
                if (count > 0) {
                    preProcessLine.append(" ");
                }
                preProcessLine.append(lineItem);
                count++;
            }
        }
    }

    @Override
    public void setLineAfterLoop(String line) {
        String[] lineItems = line.split(" ");
        boolean doFound = false;
        boolean loopFound = false;
        int nrOfDoFound = 0;
        int nrOfLoopFound = 0;

        int count = 0;

        for (String lineItem : lineItems) {
            if (lineItem.equalsIgnoreCase("Do")) {
                doFound = true;
                nrOfDoFound++;
            } else if (lineItem.equalsIgnoreCase("loop")) {
                nrOfLoopFound++;
                if (nrOfDoFound == nrOfLoopFound) {
                    loopFound = true;
                    definitionIsNotComplete = false;
                }
            } else if (loopFound) {
                if (count > 0) {
                    postProcessLine.append(" ");
                }
                count++;
                postProcessLine.append(lineItem);
            }
        }
    }

    @Override
    public void setLineOfTheLoop(String line) throws Exception {
        String[] lineItems = line.split(" ");
        List<String> loopsFound = new ArrayList<>();
        StringBuilder currentDoLoopName = new StringBuilder();
        StringBuilder currentDoLoopDefinition = new StringBuilder();
        int currentDoLoop = 0;
        boolean doFound = false;
        boolean loopFound = false;
        int nrOfDoFound = 0;
        int nrOfLoopsFound = 0;
        int count = 0;

        for (String lineItem : lineItems) {
            if (lineItem.equalsIgnoreCase("Do")) {
                if (currentDoLoop > 0) {
                    currentDoLoopDefinition.append(" ");
                    currentDoLoopDefinition.append("LOOP_");
                    currentDoLoopDefinition.append(currentDoLoop);
                    loopDefinitions.remove(currentDoLoopName);
                    loopDefinitions.put(currentDoLoopName.toString(), currentDoLoopDefinition.toString());
                }
                currentDoLoopDefinition = new StringBuilder();
                currentDoLoopName = new StringBuilder("LOOP_");
                currentDoLoopName.append(currentDoLoop);
                String stackValues = stack.show();
                int nrOfIterations = stack.popInt();
                if (loopIterations.get(currentDoLoopName.toString()) == null) {
                    loopIterations.put(currentDoLoopName.toString(), nrOfIterations);
                    loopNames.add(currentDoLoopName.toString());
                }

                if (loopDefinitions.get(currentDoLoopName) != null) {
                    loopDefinitions.remove(currentDoLoopName);
                }
                loopDefinitions.put(currentDoLoopName.toString(), " " + currentDoLoopDefinition.toString());
                doFound = true;
                currentDoLoop++;
                nrOfDoFound++;
                count = 0;
            } else if (lineItem.equalsIgnoreCase("loop")) {
                nrOfLoopsFound++;
                currentDoLoop--;
                loopDefinitions.remove(currentDoLoopName);
                loopDefinitions.put(currentDoLoopName.toString(), currentDoLoopDefinition.toString());

                if (currentDoLoop > 0) {
                    currentDoLoopName = new StringBuilder("LOOP_");
                    currentDoLoopName.append(currentDoLoop - 1);
                    currentDoLoopDefinition = new StringBuilder(loopDefinitions.get(currentDoLoopName.toString()));
                }
                if (nrOfDoFound == nrOfLoopsFound) {
                    loopFound = true;
                    definitionIsNotComplete = false;
                } else {
                    definitionIsNotComplete = true;
                }

            } else if (doFound && !loopFound) {
                if (count > 0) {
                    currentDoLoopDefinition.append(" ");
                }
                currentDoLoopDefinition.append(lineItem);
                count++;
            }
        }
    }

    @Override
    public String processLineOfTheLoop() throws Exception {
        StringBuilder result = new StringBuilder();

        int nrOfIterations = loopIterations.get(FIRST_LOOP_NAME);
        String loopRunResult = runLoop(FIRST_LOOP_NAME, nrOfIterations);
        result.append(loopRunResult);

        return result.toString();
    }

    String runLoop(String loopName, int nrOfIterations) throws Exception {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < nrOfIterations; i++) {
            result.append(runLoopOnce(loopName));
        }

        return result.toString();
    }

    String runLoopOnce(String loopName) throws Exception {
        String stackValues = stack.show();
        LineProcessor processor = new LineProcessor();
        StringBuilder result = new StringBuilder();
        String loopDef = loopDefinitions.get(loopName);
        String[] verbs = loopDef.split(" ");

        for (String verb : verbs) {
            if (!verb.toUpperCase().contains("LOOP_")) {
                result.append(processor.process(verb));
            } else {
                String innerLoopName = verb;
                int nrOfIterations = loopIterations.get(innerLoopName);
                result.append(runLoop(innerLoopName, nrOfIterations));
            }
        }
        stackValues = stack.show();

        return result.toString();

    }

}
