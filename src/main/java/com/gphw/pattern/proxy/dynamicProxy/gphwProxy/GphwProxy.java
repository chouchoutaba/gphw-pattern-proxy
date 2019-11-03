package com.gphw.pattern.proxy.dynamicProxy.gphwProxy;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class GphwProxy {
    public static final String ln = "\r\n";

    public static Object newProxyInstance(GphwClassLoader loader, Class<?>[] interfaces, GphwInvocationHandler h) {
        try {
            String src = generateSrc(interfaces);
//            System.out.println(src);
            if (null != src && !"".equals(src)) {
                String filePath = GphwProxy.class.getResource("").getPath();
                File file = new File(filePath, "$Proxy0.java");
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(src);
                fileWriter.flush();
                fileWriter.close();

                JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                StandardJavaFileManager manage = compiler.getStandardFileManager(null, null, null);
                Iterable iterable = manage.getJavaFileObjects(file);

                JavaCompiler.CompilationTask task = compiler.getTask(null, manage, null, null, null, iterable);
                task.call();
                manage.close();

                Class clazz = loader.findClass("$Proxy0");
                Constructor constructor = clazz.getConstructor(GphwInvocationHandler.class);

                file.delete();
                return constructor.newInstance(h);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String generateSrc(Class<?>[] interfaces) {
        StringBuffer sb = new StringBuffer();
        sb.append("package com.gphw.pattern.proxy.dynamicProxy.gphwProxy;" + ln);
        sb.append("import com.gphw.pattern.proxy.dynamicProxy.Person;" + ln);
        sb.append("import java.lang.reflect.*;" + ln);
        sb.append("public class $Proxy0 implements " + interfaces[0].getName() + "{" + ln);
        sb.append("GphwInvocationHandler h;" + ln);
        sb.append("public $Proxy0(GphwInvocationHandler h) { " + ln);
        sb.append("this.h = h;");
        sb.append("}" + ln);
        for (Method m : interfaces[0].getMethods()) {
            Class<?>[] params = m.getParameterTypes();

            StringBuffer paramNames = new StringBuffer();
            StringBuffer paramValues = new StringBuffer();
            StringBuffer paramClasses = new StringBuffer();

            for (int i = 0; i < params.length; i++) {
                Class clazz = params[i];
                String type = clazz.getName();
                String paramName = toLowerFirstCase(clazz.getSimpleName());
                paramNames.append(type + " " + paramName);
                paramValues.append(paramName);
                paramClasses.append(clazz.getName() + ".class");
                if (i > 0 && i < params.length - 1) {
                    paramNames.append(",");
                    paramClasses.append(",");
                    paramValues.append(",");
                }
            }

            sb.append("public " + m.getReturnType().getName() + " " + m.getName() + "(" + paramNames.toString() + ") {" + ln);
            sb.append("try{" + ln);
            sb.append("Method m = " + interfaces[0].getName() + ".class.getMethod(\"" + m.getName() + "\",new Class[]{" + paramClasses.toString() + "});" + ln);
            sb.append((hasReturnValue(m.getReturnType()) ? "return " : "") + getCaseCode("this.h.invoke(this,m,new Object[]{" + paramValues + "})", m.getReturnType()) + ";" + ln);
            sb.append("}catch(Error _ex) { }");
            sb.append("catch(Throwable e){" + ln);
            sb.append("throw new UndeclaredThrowableException(e);" + ln);
            sb.append("}");
            sb.append(getReturnEmptyCode(m.getReturnType()));
            sb.append("}");
        }
        sb.append("}" + ln);
        return sb.toString();
    }

    private static Map<Class, Class> mappings = new HashMap<Class, Class>();

    static {
        mappings.put(int.class, Integer.class);
    }

    private static String getReturnEmptyCode(Class<?> returnClass) {
        if (mappings.containsKey(returnClass)) {
            return "return 0;";
        } else if (returnClass == void.class) {
            return "";
        } else {
            return "return null;";
        }
    }

    private static String getCaseCode(String code, Class<?> returnClass) {
        if (mappings.containsKey(returnClass)) {
            return "((" + mappings.get(returnClass).getName() + ")" + code + ")." + returnClass.getSimpleName() + "Value()";
        }
        return code;
    }

    private static boolean hasReturnValue(Class<?> clazz) {
        return clazz != void.class;
    }

    private static String toLowerFirstCase(String src) {
        char[] chars = src.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
