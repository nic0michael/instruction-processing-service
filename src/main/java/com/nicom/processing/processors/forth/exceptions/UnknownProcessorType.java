package com.nicom.processing.processors.forth.exceptions;

public class UnknownProcessorType extends Exception {
    public UnknownProcessorType(){}
    
    public UnknownProcessorType(String loopType) {
        super(loopType);
    }    

}
