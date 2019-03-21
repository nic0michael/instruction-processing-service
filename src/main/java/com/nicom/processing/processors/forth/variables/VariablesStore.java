package com.nicom.processing.processors.forth.variables;

import java.util.HashMap;
import java.util.Map;

public class VariablesStore {


    Map<String, String> valueStore = new HashMap<>();
    Map<String, String> variableNames = new HashMap<>();
    Map<String, String> constantNames = new HashMap<>();
    Map<String, String> stringVariableNames = new HashMap<>();
    Map<String, String> stringConstantNames = new HashMap<>();

    public static final VariablesStore INSTANCE = new VariablesStore();
    
    String currentvariableName;

    private VariablesStore() {
    }

    public void addVariable(String variableName, String value) {
        valueStore.put(variableName.toUpperCase(), value);
        variableNames.put(variableName.toUpperCase(), value);
        System.out.println("Created variable "+variableName.toUpperCase());
    }
    
    
    public void addVariable(String variableName) {
        valueStore.put(variableName.toUpperCase(), "");
        variableNames.put(variableName.toUpperCase(), variableName.toUpperCase());
        System.out.println("Created variable "+variableName.toUpperCase());
    }

    public void addConstant(String variableName, String value) {
        valueStore.put(variableName.toUpperCase(), value);
        constantNames.put(variableName.toUpperCase(), value);
        System.out.println("Created constant "+variableName.toUpperCase());
    }

    public void addStringConstant(String variableName, String value) {
        valueStore.put(variableName.toUpperCase(), value);
        stringConstantNames.put(variableName.toUpperCase(), value);
        System.out.println("Created constant "+variableName.toUpperCase());
    }
 
    public void addStringVariable(String variableName,String value) {
        valueStore.put(variableName.toUpperCase(), value);
        stringVariableNames.put(variableName.toUpperCase(), variableName.toUpperCase());
        System.out.println("Created String variable "+variableName.toUpperCase());
    }
    
    public void updateVariable(String variableName, String value) {
        if (variableNames.get(variableName.toUpperCase()) != null) {
            valueStore.remove(variableName.toUpperCase());
            valueStore.put(variableName.toUpperCase(), value);
        }
    }

    public void updateVariable(String variableName, int value) {
        if (variableNames.get(variableName.toUpperCase()) != null) {
            valueStore.remove(variableName.toUpperCase());
            valueStore.put(variableName.toUpperCase(),new Integer(value).toString());
        }
    }

    public void updateVariable(String variableName, double value) {
        if (variableNames.get(variableName.toUpperCase()) != null) {
            valueStore.remove(variableName.toUpperCase());
            valueStore.put(variableName.toUpperCase(),new Double(value).toString());
        }
    }
    
    public void updateStringVariable(String variableName, String value) {
        if (stringVariableNames.get(variableName.toUpperCase()) != null) {
            valueStore.remove(variableName.toUpperCase());
            valueStore.put(variableName.toUpperCase(), value);
        }
    }

    public int getIntValueOfVariable( String variableName) {
        String value = valueStore.get(variableName.toUpperCase());
        int returnValue = Integer.parseInt(value);
        return returnValue;
    }

    public double getDoubleValueOfVariable(String variableName) {
        String value = valueStore.get(variableName.toUpperCase());
        double returnValue = Double.parseDouble(value);
        return returnValue;
    }

    public boolean isVariable(String variableName) {
        return variableNames.get(variableName.toUpperCase()) != null || 
                constantNames.get(variableName.toUpperCase()) != null ||
                stringVariableNames.get(variableName.toUpperCase()) != null || 
                stringConstantNames.get(variableName.toUpperCase()) != null ;
    }

    public String getCurrentvariableValue() {
        return valueStore.get(currentvariableName);
    }
    
    public void setCurrentvariableValue( int value) {
        if (variableNames.get(currentvariableName) != null) {
            valueStore.remove(currentvariableName);
            valueStore.put(currentvariableName,new Integer(value).toString());
        }
    }
    
    public void setCurrentvariableValue( double value) {
        if (variableNames.get(currentvariableName) != null) {
            valueStore.remove(currentvariableName);
            valueStore.put(currentvariableName,new Double(value).toString());
        }
    }
    
    public void setCurrentvariableValue( String value) {
        if (variableNames.get(currentvariableName) != null) {
            valueStore.remove(currentvariableName);
            valueStore.put(currentvariableName,value);
        }
    }

    public void setCurrentvariableName(String currentvariableName) {
        this.currentvariableName = currentvariableName.toUpperCase();
    }

    public String showVariables(){
        
        StringBuilder result = new StringBuilder();
        int count = 0;

        for (Map.Entry<String, String> entry : valueStore.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (count > 0) {
                result.append("\n");
            }
            
            result.append(key);
            result.append(" : ");
            result.append(value);
            count++;
        }
        return result.toString();
    }
}
