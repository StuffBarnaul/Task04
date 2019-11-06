package Task04;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import org.springframework.util.ReflectionUtils;

class CleanUpClass<K> {

    void cleanup(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput) {
        if (object instanceof Map) cleanMap(object, fieldsToCleanup, fieldsToOutput);
        else cleanObject(object, fieldsToCleanup, fieldsToOutput);
    }

    private void cleanMap(Object object, final Set<String> fieldsToCleanup, final Set<String> fieldsToOutput) {
        Class clazz = object.getClass();
        try {
            for (String key:fieldsToCleanup) {
                isContains(key,object);
                Method removeMethod = clazz.getMethod("remove", Object.class);
                removeMethod.setAccessible(true);
                removeMethod.invoke(object,key);
            }
            for (String key:fieldsToOutput){
                isContains(key,object);
                Method getMethod = clazz.getMethod("get", Object.class);
                getMethod.setAccessible(true);
                System.out.println(getMethod.invoke(object,key));
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void isContains(String key, Object object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method containsKeyMethod = object.getClass().getMethod("containsKey", Object.class);
        containsKeyMethod.setAccessible(true);
        if (containsKeyMethod.invoke(object,key).equals(false)) {throw new IllegalArgumentException();}
    }

    private void cleanObject(final Object object, final Set<String> fieldsToCleanup, final Set<String> fieldsToOutput) {
        for (String field:fieldsToCleanup){
            if (ReflectionUtils.findField(object.getClass(),field) == null) {
                throw new IllegalArgumentException();
            }
        }
        for (String field:fieldsToOutput){
            if (ReflectionUtils.findField(object.getClass(),field) == null) {
                throw new IllegalArgumentException();
            }
        }
        ReflectionUtils.doWithFields(object.getClass(), new ReflectionUtils.FieldCallback() {
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                if (fieldsToCleanup.contains(field.getName())) {
                    if (field.getType().isPrimitive()){
                        field.set(object,0);
                    } else {
                        field.set(object,null);
                    }
                }
                if (fieldsToOutput.contains(field.getName())) {
                    if (field.getType().isPrimitive()){
                        System.out.println(String.valueOf(field.get(object)));
                    } else {
                        String s = field.get(object).toString();
                        System.out.println(s);
                    }
                }
            }
        });
    }
}
