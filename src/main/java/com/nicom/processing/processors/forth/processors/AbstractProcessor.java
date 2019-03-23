package com.nicom.processing.processors.forth.processors;

public abstract class AbstractProcessor {

    public abstract String preProcess(String line) throws Exception;
    public abstract String process(String line) throws Exception;
    public abstract String postProcess(String line) throws Exception;
    public abstract boolean getDefinitionIsNotComplete() throws Exception;

}
