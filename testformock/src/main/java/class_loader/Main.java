package java.class_loader;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
//        System.out.println("element class: " + int.class.getName());
//        System.out.println("element class loader: " + int.class.getClassLoader());
//        int[] primiteBootstrapClassArrayy = new int[0];
//        System.out.println("array class: " + primiteBootstrapClassArrayy.getClass().getName());
//        System.out.println("array class loader: " + primiteBootstrapClassArrayy.getClass().getClassLoader());
//        System.out.println();
//
//        System.out.println("element class: " + Integer.class.getName());
//        System.out.println("element class loader: " + Integer.class.getClassLoader());
//        Integer[] wrapperBootstrapClassArray = new Integer[0];
//        System.out.println("array class: " + wrapperBootstrapClassArray.getClass().getName());
//        System.out.println("array class loader: " + wrapperBootstrapClassArray.getClass().getClassLoader());
//        System.out.println();
//
//        System.out.println("element class: " + FXMLLoader.class.getName());
//        System.out.println("element class loader: " + FXMLLoader.class.getClassLoader().getClass().getName());
//        FXMLLoader[] extClassArray = new FXMLLoader[0];
//        System.out.println("array class: " + extClassArray.getClass().getName());
//        System.out.println("array class loader: " + extClassArray.getClass().getClassLoader().getClass().getName());
//        System.out.println();
//
//        System.out.println("element class:" + AppClass.class.getName());
//        System.out.println("element class loader: " + AppClass.class.getClassLoader().getClass().getName());
//        AppClass[] appClassArray = new AppClass[0];
//        System.out.println("array class: " + appClassArray.getClass().getName());
//        System.out.println("array class loader: " + appClassArray.getClass().getClassLoader().getClass().getName());


//        System.out.println(Thread.currentThread().getContextClassLoader());
//
//
//
//        new Thread(() -> System.out.println(Thread.currentThread().getContextClassLoader())).start();

//        System.out.println(Class.forName("java.lang.Thread").getClassLoader());

//        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
//
//        CustomClassLoader ccl = new CustomClassLoader(systemClassLoader);
//
//        Class<?> clazz = ccl.loadClass("class_loader.Aclass", true);
//
//        System.out.println(clazz.getCanonicalName());

//        System.out.println(java.Main.class.getClassLoader());
//
//        System.out.println(Aclass.class.getClassLoader());

//        Class.forName("class_loader.Init", true, java.Main.class.getClassLoader());

//        System.out.println(java.awt.print.Book.class.getClassLoader());


        File file = new File("\\Users/heshan1/../Downloads/018.JPG1");

        System.out.println(file.getCanonicalPath());
        System.out.println(file.getPath());
    }
}



