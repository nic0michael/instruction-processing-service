package com.nicom.processing.processors.forth.compilers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.nicom.processing.processors.forth.exceptions.LineIsEmptyException;
import com.nicom.processing.processors.forth.exceptions.VerbNotInDictionaryException;


@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class ForthCompilerTest {

    ForthCompiler compiler=new ForthCompiler();
    
    @Test
    public void Test() throws VerbNotInDictionaryException, LineIsEmptyException{
        String verbDefToCompile=" . . push pop ";
        String expectedCompiledDefinition=". . PUSH POP";
        String compiledDefinition=compiler.compile(verbDefToCompile);
        
        assertThat(expectedCompiledDefinition, is(compiledDefinition));
    }

}
