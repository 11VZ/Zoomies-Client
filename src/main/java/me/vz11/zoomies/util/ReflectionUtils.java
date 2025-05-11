package me.vz11.zoomies.util;

import java.lang.reflect.Method;
import java.lang.reflect.Field;

public class ReflectionUtils 
{
    public static Method tryGetMethod(String methodName, Class<?> class1)
    {
        try
        {
            return class1.getDeclaredMethod(methodName, new Class[1]);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return (Method)null;
        }
    }

    public static void tryCallMethod(Method method, Object... parameters)
    {
        try
        {
            method.invoke(null, parameters);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }

    public static Object getPrivateField(Class<?> clazz, Object instance, String fieldName) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(instance);
    }

    public static void setPrivateField(Class<?> clazz, Object instance, String fieldName, Object value) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, value);
    }
}
