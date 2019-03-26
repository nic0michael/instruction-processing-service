package com.nicom.processing.processors.forth.utils;


import java.net.UnknownHostException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.nicom.processing.processors.forth.exceptions.LineIsEmptyException;

@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class UtilitiesTest {



   
	
    @Test
    public void removeUnwantedSpacesTest() throws LineIsEmptyException{
        String lineWithSpaces=" The    Quick   Brown              Fox Jumps     ";
        String expected="The Quick Brown Fox Jumps";
        String trimmedLine=Utilities.removeUnwantedSpaces(lineWithSpaces);
        assertThat(expected, is(trimmedLine));
    }
  
    @Test
    public void removeUnwantedSpacesTest2() throws LineIsEmptyException{
        String lineWithSpaces=" The    Quick   Brown              Fox Jumps";
        String expected="The Quick Brown Fox Jumps";
        String trimmedLine=Utilities.removeUnwantedSpaces(lineWithSpaces);
        assertThat(expected, is(trimmedLine));
    }
    
    @Test
    public void removeUnwantedSpacesTest3() throws LineIsEmptyException{
        String lineWithSpaces=" The    Quick   Brown              Fox Jumps ";
        String expected="The Quick Brown Fox Jumps";
        String trimmedLine=Utilities.removeUnwantedSpaces(lineWithSpaces);
        assertThat(expected, is(trimmedLine));
    }
    
}
