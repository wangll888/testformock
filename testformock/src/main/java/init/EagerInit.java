package java.init;

/**
 * 此为"饿汉"模式。
 *
 * 因为在加载 EagerInit 类时，静态初始化就会执行。
 */
public class EagerInit {
    private static Resource resource = new Resource();

    public static Resource getResource() { return resource; }
}

/**
 * 此为"懒汉"模式。
 *
 * 因为在加载 LazyInit 类时，并不会引起资源的初始化。
 * 而是在加载 ResourceHolder 类时才会触发资源加载。
 *
 * 将 ResourceHolder 设置为了 private，只有当程序真正要
 * 获取资源时，通过调用 getResource() 方法才触发加载 ResourceHolder 类，
 * 进而初始化静态 resource 成员。
 *
 * 这里的关键就是：
 * 1、依赖JVM的类加载机制
 * 2、并且用私有静态嵌套类做一些"封装"。
 */
class LazyInit {

    private static class ResourceHolder {
        static Resource resource = new Resource();
    }

    public static Resource getResource() { return ResourceHolder.resource; }
}


class Resource {} // 代表需要初始化的资源
