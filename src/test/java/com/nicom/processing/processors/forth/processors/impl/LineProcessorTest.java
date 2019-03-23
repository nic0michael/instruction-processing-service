package com.nicom.processing.processors.forth.processors.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class LineProcessorTest {

    LineProcessor  processor=new LineProcessor();
    
    
    @Test
    public void processTest() throws Exception {
        String result1=processor.process("1 2 + .");
        String result2=processor.process("5 1 - .");
        String result3=processor.process("4 2 * .");
        String result4=processor.process("6 2 / .");
        
        assertThat(result1, is("3"));
        assertThat(result2, is("4"));
        assertThat(result3, is("8"));
        assertThat(result4, is("3"));
    }   

}
