package java.class_loader;

public class CustomClassLoader extends ClassLoader {

    public CustomClassLoader (ClassLoader parent) {
        super(parent);
    }

    @Override
    public Class findClass(String name) {
        return null;
    }

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        return super.loadClass(name, resolve);
    }
}