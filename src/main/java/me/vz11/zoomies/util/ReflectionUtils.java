package me.vz11.zoomies.util;

import java.lang.reflect.Method;

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
}
