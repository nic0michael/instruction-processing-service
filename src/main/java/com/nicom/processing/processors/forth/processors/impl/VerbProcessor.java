package com.nicom.processing.processors.forth.processors.impl;

import java.net.UnknownHostException;

import com.nicom.processing.processors.forth.dictionary.ForthDictionary;
import com.nicom.processing.processors.forth.exceptions.StackIsEmptyException;
import com.nicom.processing.processors.forth.exceptions.VerbNotInDictionaryException;
import com.nicom.processing.processors.forth.processors.AbstractProcessor;
import com.nicom.processing.processors.forth.stack.ForthStack;
import com.nicom.processing.processors.forth.utils.NetworkUtils;
import com.nicom.processing.processors.forth.utils.Utilities;
import com.nicom.processing.processors.forth.variables.VariablesStore;

public class VerbProcessor  extends AbstractProcessor {

    ForthStack stack = ForthStack.INSTANCE;
    ForthDictionary dictionary = ForthDictionary.INSTANCE;
    VariablesStore variables = VariablesStore.INSTANCE;

    @Override
    public String preProcess(String line) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String process(String line) throws Exception {
        StringBuilder result = new StringBuilder();
        String[] lineItems = line.split(" ");
        for (String lineItem : lineItems) {
            if (Utilities.isNumeric(lineItem)) {
                stack.push(lineItem);
            } else if (variables.isVariable(lineItem.toUpperCase())) {
                variables.setCurrentvariableName(lineItem);
            } else {
                result.append(executeVerb(lineItem));
            }
        }
        return result.toString();
    }

    @Override
    public String postProcess(String line) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getDefinitionIsNotComplete() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Object executeVerb(String lineItem) throws VerbNotInDictionaryException, StackIsEmptyException, UnknownHostException {
        StringBuilder result = new StringBuilder();
        if (variables.isVariable(lineItem.toUpperCase())) {
            variables.setCurrentvariableName(lineItem);

        } else {

            try {
                switch (lineItem) {
                    case ".":
                        result.append(stack.pop());
                        break;

                    case "SPACE":
                        result.append(" ");
                        break;

                    case "SPACES":
                        int topValue = stack.popInt();
                        for (int i = 0; i < topValue; i++) {
                            result.append(" ");
                        }
                        break;

                    case "EMIT":
                        stack.intToChar();
                        result.append(stack.pop());
                        break;

                    case "=":
                        stack.equals();
                        break;

                    case "0=":
                        stack.equalsZero();
                        break;

                    case "0<":
                        stack.equalsZero();
                        break;

                    case ">":
                        stack.greaterThan();
                        break;

                    case "<":
                        stack.smallerThan();
                        break;
                        
                    case "=>":
                        stack.equalsOrGreaterThan();
                        break;
                    case "<=":
                        stack.smallerThanOrEquals();
                        break;
                    case "NOT":
                        stack.not();
                        break;
                    case "+":
                        stack.add();
                        break;

                    case "-":
                        stack.subtract();
                        break;

                    case "*":
                        stack.multiply();
                        break;

                    case "/":
                        stack.divide();
                        break;

                    case "MOD":
                        stack.modulus();
                        break;

                    case "D+":
                        stack.addDoubles();
                        break;

                    case "D-":
                        stack.subtractDoubles();
                        break;

                    case "D*":
                        stack.multiplyDoubles();
                        break;

                    case "D/":
                        stack.divideDoubles();
                        break;

                    case "D>":
                        stack.greaterThanDoubles();
                        break;

                    case "D<":
                        stack.smallerThanDoubles();
                        break;

                    case "D=":
                        stack.equalsDoubles();
                        break;

                    case "D0=":
                        stack.equalsZeroDoubles();
                        break;

                    case "D0<":
                        stack.equalsZeroDoubles();
                        break;

                    case "DMOD":
                        stack.modulusDoubles();
                        break;

                    case "DSQR":
                        stack.squareDoubles();
                        break;

                    case "SQARE":
                        stack.square();
                        break;                        

                    case "SQR":
                        stack.sqrt();
                        break;

                    case "PWR":
                        stack.power();
                        break;

                    case "AND":
                        stack.and();
                        break;

                    case "OR":
                        stack.or();
                        break;

                    case "XOR":
                        stack.exor();
                        break;

                    case "RANDOM":
                        stack.random();
                        break;

                    case "RND":
                        stack.random();
                        break;

                    case "MAX":
                        stack.max();
                        break;
                    case "MIN":
                        stack.min();
                        break;
                    case "ROUND":
                        stack.round();
                        break;
                    case "FLOOR":
                        stack.floor();
                        break;
                    case "CEIL":
                        stack.ceil();
                        break;
                    case "RADTODEG":
                        stack.radiansToDegrees();
                        break;
                    case "DEGTORAD":
                        stack.degreesToRadians();
                        break;
                    case "SIN":
                        stack.sin();
                        break;
                    case "COS":
                        stack.cos();
                        break;
                    case "TAN":
                        stack.tan();
                        break;
                    case "LOG":
                        stack.log();
                        break;
                    case "LOGBASE10":
                        stack.logBase10();
                        break;

                    case "DEC":
                        stack.setModeToDecimal();
                        break;

                    case "HEX":
                        stack.setModeToHex();
                        break;

                    case "BIN":
                        stack.setModeToBinary();
                        break;

                    case "OCT":
                        stack.setModeToOctal();
                        break;

                    case "BASE64ENCODE":
                        stack.base64Encode();
                        break;

                    case "BASE64DECODE":
                        stack.base64Decode();
                        break;

                    case "DECTOBINARY":
                        stack.convertToBinary();
                        break;

                    case "DECTOHEX":
                        stack.convertToHex();
                        break;

                    case "DECTOOCTAL":
                        stack.convertToOctal();
                        break;

                    case "HEXTODEC":
                        stack.convertHexToDecimal();
                        break;

                    case "OCTALTODEC":
                        stack.convertOctalToDecimal();
                        break;

                    case "BINARYTODEC":
                        stack.convertBinaryToDecimal();
                        break;

                    case "DROP":
                        stack.drop();
                        break;

                    case "DUP":
                        stack.dup();
                        break;

                    case "OVER":
                        stack.over();
                        break;

                    case "ROT":
                        stack.rot();
                        break;

                    case "SWAP":
                        stack.swap();
                        break;

                    case "TIME@":
                        stack.push(Utilities.dateY2k());
                        break;

                    case "TIMESTAMP@":
                        stack.push(Utilities.timeStamp());
                        break;

                    case "?TIME":
                        result.append(Utilities.dateY2k());
                        break;

                    case "?TIMESTAMP":
                        result.append(Utilities.timeStamp());
                        break;

                    case ".STACK":
                        result.append(stack.show());
                        break;

                    case "CR":
                        result.append("\n");
                        break;

                    case ".DAY":
                        result.append(Utilities.day());
                        break;

                    case ".MONTH":
                        result.append(Utilities.month());
                        break;

                    case ".MTH":
                        result.append(Utilities.month());
                        break;

                    case ".YEAR":
                        result.append(Utilities.year());
                        break;

                    case ".YR":
                        result.append(Utilities.year());
                        break;

                    case ".DATE":
                        result.append(Utilities.dateY2k());
                        break;

                    case ".DATESF":
                        result.append(Utilities.dateY2k());
                        break;

                    case ".DATESIMPLEFORMAT":
                        result.append(Utilities.dateY2k());
                        break;

                    case ".DATETIME":
                        result.append(Utilities.now());
                        break;

                    case ".DATEBRITISH":
                        result.append(Utilities.dateBritish());
                        break;
                    case ".DATEUSA":
                        result.append(Utilities.dateUSA());
                        break;

                    case ".TIME":
                        result.append(Utilities.time());
                        break;
                    case ".TIMESTAMP":
                        result.append(Utilities.timeStamp());
                        break;

                    case ".D":
                        result.append(dictionary.showVerbs());
                        break;

                    case ".DICT":
                        result.append(dictionary.showVerbs());
                        break;

                    case ".DICTDEF":
                        result.append(dictionary.showVerbDetails());
                        break;

                    case ".MODE":
                        result.append(stack.getCurrentMode());
                        break;

                    case ".COMPUTERNAME":
                        result.append(NetworkUtils.getLocalHostName());
                        break;
                    case ".CNAME":
                        result.append(NetworkUtils.getLocalHostName());
                        break;
                    case ".COMPUTERIP":
                        result.append(NetworkUtils.getLocalHostIpAddress());
                        break;
                    case ".CIP":
                        result.append(NetworkUtils.getLocalHostIpAddress());
                        break;

                    case ".SERVERIP":
                        String serverUlr = stack.pop();
                        result.append(NetworkUtils.getInetAddresses(serverUlr));
                        break;

                    case ".SIP":
                        String serverUlr2 = stack.pop();
                        result.append(NetworkUtils.getInetAddresses(serverUlr2));
                        break;

                    case "!":
                        String value = stack.pop();
                        variables.setCurrentvariableValue(value);
                        break;

                    case "@":
                        String currentvariableValue = variables.getCurrentvariableValue();
                        stack.push(currentvariableValue);
                        break;

                    case ".VARIASBLES":
                        result.append(variables.showVariables());
                        break;

                    case ".V":
                        result.append(variables.showVariables());
                        break;

                    case ".HELP":
                        result.append("The following verbs have been defined:\n");
                        result.append(dictionary.showVerbs());
                        break;

                    case ".H":
                        result.append("The following verbs have been defined:\n");
                        result.append(dictionary.showVerbs());
                        break;

                    default:
                        throw new VerbNotInDictionaryException(lineItem);
                }
            } catch (StackIsEmptyException e) {
                result.append("\nThe Stack is empty");
            }
        }
        return result.toString();
    }

}
