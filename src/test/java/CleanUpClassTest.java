import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CleanUpClassTest {

//    @Test
//    void cleanupObject() {
//        ClassForCleaning test = new ClassForCleaning();
//        Set<String> fieldsToCleanup = new HashSet<String>();
//        fieldsToCleanup.add("one");
//        fieldsToCleanup.add("list");
//        Set<String> fieldsToOutput = new HashSet<String>();
//        fieldsToOutput.add("two");
//        fieldsToOutput.add("set");
//        CleanUpClass cleaner = new CleanUpClass();
//        cleaner.cleanup(test,fieldsToCleanup,fieldsToOutput);
//        assertEquals(test.one,0);
//        assertEquals(test.list,null);
//    }

    @Test
    void cleanupMap() {
        CleanUpClass test = new CleanUpClass();
        Map map = new HashMap();
        Method[] methods = map.getClass().getMethods();
        map.put("qwe",1);
        map.put("asd",2);
        map.put("zxc",3);
        map.put("rty",4);
        final Set<String> fieldsToCleanup = new HashSet<String>();
        fieldsToCleanup.add("qwe");
        fieldsToCleanup.add("zxc");
        Set<String> fieldsToOutput = new HashSet<String>();
        fieldsToOutput.add("asd");
        fieldsToOutput.add("rty");
        test.cleanup(map,fieldsToCleanup,fieldsToOutput);

        assertEquals(map.get("qwe"),null);
        assertEquals(map.get("zxc"),null);
    }
}