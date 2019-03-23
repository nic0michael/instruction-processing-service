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


//    @Test
//    public void ipAddressTest() throws UnknownHostException{
//        String expectedIp="151.101.128.81";
//        String ip=Utilities.getComputerIpAddress();
//        String computerName = Utilities.getComputerName();
//        String networkIpAddress = Utilities.getIpAddressOfHosst("bbc.co.uk");
//        assertThat(networkIpAddress, is(expectedIp));
//    }
   
	
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
