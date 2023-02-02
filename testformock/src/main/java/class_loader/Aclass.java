package java.class_loader;

public class Aclass {

    static {
        System.out.println("static init");
    }

    {
        System.out.println("instance init");
    }

}
