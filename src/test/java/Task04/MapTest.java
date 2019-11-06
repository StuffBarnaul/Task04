package Task04;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class MapTest {
    CleanUpClass test = new CleanUpClass();
    Map map = new HashMap();

    @Before
    public void setUp() throws Exception {
        map.put("qwe",1);
        map.put("asd",2);
        map.put("zxc",3);
        map.put("rty",4);
    }

    @Test
    public void cleanupMap() {
        Set<String> fieldsToCleanup = new HashSet<String>();
        fieldsToCleanup.add("qwe");
        fieldsToCleanup.add("zxc");
        Set<String> fieldsToOutput = new HashSet<String>();
        fieldsToOutput.add("asd");
        fieldsToOutput.add("rty");
        test.cleanup(map,fieldsToCleanup,fieldsToOutput);
        assertEquals(map.get("qwe"),null);
        assertEquals(map.get("zxc"),null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Exception1() {
        Set<String> fieldsToCleanup = new HashSet<String>();
        fieldsToCleanup.add("qwe");
        fieldsToCleanup.add("zxc");
        fieldsToCleanup.add("fgh");
        Set<String> fieldsToOutput = new HashSet<String>();
        fieldsToOutput.add("asd");
        fieldsToOutput.add("rty");
        test.cleanup(map,fieldsToCleanup,fieldsToOutput);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Exception2() {
        Set<String> fieldsToCleanup = new HashSet<String>();
        fieldsToCleanup.add("qwe");
        fieldsToCleanup.add("zxc");
        Set<String> fieldsToOutput = new HashSet<String>();
        fieldsToOutput.add("asd");
        fieldsToOutput.add("rty");
        fieldsToCleanup.add("fgh");
        test.cleanup(map,fieldsToCleanup,fieldsToOutput);
    }
}
