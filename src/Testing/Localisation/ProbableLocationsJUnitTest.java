package Testing.Localisation;


import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import rp.robotics.mapping.MapUtils;

@RunWith(Parameterized.class)
public class ProbableLocationsJUnitTest {

	@Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {     
        	{90.0f, 72.3f, 48} , {180.0f, 40.9f, 18} , {-90.0f, 72.3f, 48 } , {0.0f, 13.2f, 28}  });
    }
    
    private float headingInput;
    private float rangeInput;
    private int arraySizeExpected;
    
    public ProbableLocationsJUnitTest(float headingInput, float rangeInput, int arraySizeExpected){
    	this.headingInput = headingInput;
    	this.rangeInput = rangeInput;
    	this.arraySizeExpected = arraySizeExpected;
    }
    private ProbableLocations locs;
    
    @Before
    public void before(){
    	locs = new ProbableLocations(MapUtils.createRealWarehouse());
    }
    
	@Test
	public void test() {
		locs.setLocations(headingInput, rangeInput);
		assertEquals(arraySizeExpected, locs.size());
	}

}
