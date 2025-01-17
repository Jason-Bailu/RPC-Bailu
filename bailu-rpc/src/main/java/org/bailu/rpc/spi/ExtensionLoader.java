package org.bailu.rpc.spi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// SPI机制
public class ExtensionLoader {
    private Logger logger = LoggerFactory.getLogger(ExtensionLoader.class);

    // 系统默认SPI
    private static String SYS_EXTENSION_LOADER_DIR_PREFIX = "META-INF/sys-rpc/";
    // 用户定义SPI
    private static String DIY_EXTENSION_LOADER_DIR_PREFIX = "META-INF/rpc/";

    private static String[] prefixs = {SYS_EXTENSION_LOADER_DIR_PREFIX, DIY_EXTENSION_LOADER_DIR_PREFIX};
    // bean定义信息：key 定义，value 具体类
    private static Map<String, Class> extensionClassCache = new ConcurrentHashMap<>();
    // bean定义信息：key 接口，value 接口子类
    private static Map<String, Map<String, Class>> extensionClassCaches = new ConcurrentHashMap<>();
    // 实例化bean
    private static Map<String, Object> singletonsObject = new ConcurrentHashMap<>();

    private static ExtensionLoader extensionLoader;

    static {
        extensionLoader = new ExtensionLoader();
    }

    private ExtensionLoader(){}

    public static ExtensionLoader getInstance() {
        return extensionLoader;
    }

    // 获取bean
    public <V> V get(String name) {
        if (!singletonsObject.containsKey(name)) {
            try {
                singletonsObject.put(name, extensionClassCache.get(name).newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return (V) singletonsObject.get(name);
    }

    // 获取接口下所有类
    public List<Object> gets(Class clazz) {
        final String name = clazz.getName();
        if (!extensionClassCaches.containsKey(name)) {
            try {
                throw new ClassNotFoundException(clazz + "未找到");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        final Map<String, Class> stringClassMap = extensionClassCaches.get(name);
        List<Object> objects = new ArrayList<>();
        if (stringClassMap.size() > 0) {
            stringClassMap.forEach((k, v) -> {
                try {
                    objects.add(singletonsObject.getOrDefault(k, v.newInstance()));
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        }
        return objects;
    }

    // spi加载bean的信息到map
    public void loadExtension(Class clazz) throws IOException, ClassNotFoundException {
        if (clazz == null) {
            throw new IllegalArgumentException("class 未找到");
        }
        ClassLoader classLoader = this.getClass().getClassLoader();
        Map<String, Class> classMap = new HashMap<>();
        for (String prefix : prefixs) {
            String spiFilePath = prefix + clazz.getName();
            Enumeration<URL> enumeration = classLoader.getResources(spiFilePath);
            while (enumeration.hasMoreElements()) {
                URL url = enumeration.nextElement();
                InputStreamReader inputStreamReader = null;
                inputStreamReader = new InputStreamReader(url.openStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] lineArr = line.split("=");
                    String key = lineArr[0];
                    String name = lineArr[1];
                    final Class<?> aClass = Class.forName(name);
                    extensionClassCache.put(key, aClass);
                    classMap.put(key, aClass);
                    logger.info("加载bean key:{}, valeu:{}", key, name);
                }
            }
        }
        extensionClassCaches.put(clazz.getName(), classMap);
    }
}
