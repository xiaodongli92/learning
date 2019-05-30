package com.study.util;

import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by xiaodong on 2017/10/18.
 * 对象序列化工具类
 */
public class SerializeUtil {

    private static final Logger LOG = Logger.getLogger(SerializeUtil.class);

    private SerializeUtil() {}

    public static byte[] serialize(final Object object) {
        ObjectOutputStream objectOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            LOG.error("serialize error ..", e);
            return null;
        } finally {
            close(byteArrayOutputStream);
            close(objectOutputStream);
        }
    }

    @SuppressWarnings("unchecked")
    public static <E> E unSerialize(byte[] bytes) {
        ByteArrayInputStream inputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            inputStream = new ByteArrayInputStream(bytes);
            objectInputStream = new ObjectInputStream(inputStream);
            return (E) objectInputStream.readObject();
        } catch (Exception e) {
            LOG.error("unSerialize error ..", e);
            return null;
        } finally {
            close(inputStream);
            close(objectInputStream);
        }
    }

    public static void serializeToFile(Object object, File file) {
        ObjectOutputStream objectOutputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
        } catch (Exception e) {
            LOG.error(e);
        } finally {
            close(fileOutputStream);
            close(objectOutputStream);
        }
    }

    @SuppressWarnings("unchecked")
    public static <E> E unSerializeFromFile(File file) {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);
            return (E) objectInputStream.readObject();
        } catch (Exception e) {
            LOG.error(e);
            return null;
        } finally {
            close(fileInputStream);
            close(objectInputStream);
        }
    }

    @SuppressWarnings("unchecked")
    public static <E> E unSerializeFromFile(InputStream inputStream) {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(inputStream);
            return (E) objectInputStream.readObject();
        } catch (Exception e) {
            LOG.error(e);
            return null;
        } finally {
            close(inputStream);
            close(objectInputStream);
        }
    }

    private static void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception e) {
                LOG.error(e);
            }
        }
    }

}
