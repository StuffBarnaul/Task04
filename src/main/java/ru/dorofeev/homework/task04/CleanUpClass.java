package ru.dorofeev.homework.task04;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.util.ReflectionUtils;

class CleanUpClass {

    void cleanup(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput) {
        Set<String> mySet = new HashSet<String>();
        mySet.addAll(fieldsToCleanup);
        mySet.addAll(fieldsToOutput);
        if (object instanceof Map) cleanMap(object, fieldsToCleanup, fieldsToOutput, mySet);
        else cleanObject(object, fieldsToCleanup, fieldsToOutput,mySet);
    }

    private void cleanMap(Object object, final Set<String> fieldsToCleanup, final Set<String> fieldsToOutput, final Set<String> mySet) {
        try {
            for (String field:mySet){
                isContains(field,object);
            }
            for (String key:fieldsToCleanup) {
                Method removeMethod = object.getClass().getMethod("remove", Object.class);
                removeMethod.setAccessible(true);
                removeMethod.invoke(object,key);
            }
            for (String key:fieldsToOutput){
                Method getMethod = object.getClass().getMethod("get", Object.class);
                getMethod.setAccessible(true);
                System.out.println(getMethod.invoke(object,key));
            }
        } catch (NoSuchMethodException e ) {
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

    private void cleanObject(final Object object, final Set<String> fieldsToCleanup, final Set<String> fieldsToOutput, final Set<String> mySet) {
        for (String myfield : mySet) {
            try {
                object.getClass().getDeclaredField(myfield);
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException();
            }
        }
        for (String field : fieldsToCleanup) {
            try {
                Field objectfield = object.getClass().getDeclaredField(field);
                objectfield.setAccessible(true);
                if (objectfield.getType().isPrimitive()) {
                    objectfield.set(object, 0);
                } else {
                    objectfield.set(object, null);
                }
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException();
            } catch (IllegalAccessException e) {
                System.out.println("No access to variable");
            }
        }
        for (String field : fieldsToOutput) {
            try {
                Field objectfield = object.getClass().getDeclaredField(field);
                objectfield.setAccessible(true);
                if (objectfield.getType().isPrimitive()) {
                    System.out.println(objectfield.get(object));
                } else {
                    String s = objectfield.get(object).toString();
                    System.out.println(s);
                }
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException();
            } catch (IllegalAccessException e) {
                System.out.println("No access to variable");
            }
        }
    }
}
