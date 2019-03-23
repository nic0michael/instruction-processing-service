package com.nicom.processing.processors.forth.processors.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class DoLoopProcessorTest {

    DoLoopProcessor processor =new DoLoopProcessor();
    Inputprocessor inputprocessor =new Inputprocessor();
    

    @Test
    public void processDoOneLoopTest() throws Exception {
        String result1=processor.process("1 1 do 1 2 + . loop .");
        System.out.println(result1);
        assertThat(result1, is("31"));
    } 
    
    
    @Test
    public void processDoTwoLoopsTest() throws Exception {
        String result1=processor.process("1 2 do 1 2 + . loop . ");
        System.out.println(result1);
        assertThat(result1, is("331"));
    } 

    @Test
    public void processDoWithNestedLoopTest() throws Exception {
        String result1=processor.process("1 1 1 do 1 2 + . do 3 4 + . loop loop . ");
        System.out.println(result1);
        assertThat(result1, is("371"));
    } 
    
     
    
    @Test
    public void processDoWithNestedLoopAndTwoIterationsEachTest() throws Exception {
        String result1=processor.process("1 2 2 do 1 2 + . do 3 4 + . loop loop . ");
        System.out.println(result1);
        assertThat(result1, is("3773771"));
    } 
   
    @Test
    public void processDoWithNestedLoopAndTwoAndThreeIterationsEachTest() throws Exception {
        String result1=processor.process("1 2 3 do 1 2 + . do 3 4 + . loop loop . ");
        System.out.println(result1);
        assertThat(result1, is("3773773771"));
    } 
}
