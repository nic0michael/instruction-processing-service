package com.nicom.processing.processors.forth;

import com.nicom.processing.processors.forth.processors.impl.Inputprocessor;

public class Forth {

	public String processInput(String input) throws Exception{
        Inputprocessor processor =new Inputprocessor();
        return processor.process(input);
    }
}
