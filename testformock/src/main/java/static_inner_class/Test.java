package java.static_inner_class;

import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) throws Throwable {
        Class c = Outer.class;

        Method m = Outer.class.getDeclaredMethod("access$000", Outer.class);
        Outer o = new Outer();
        int i = (int) m.invoke(o, o);
        System.out.println(i);

//        Method[] m = Outer.class.getDeclaredMethods();
//        for (Method m1 : m) {
//            if ("access$000".equals(m1.getName())) {
//                Outer o = new Outer();
//                int i = (int) m1.invoke(o, o);
//                System.out.println(i);
//            }
//        }
    }
}

class Outer {
    private int outer_field = 100;
    private void outer_func() { }

    static class StaticInner {
        private int static_inner_Field = 100;
        private void static_inner_func() {
            int x = new Outer().outer_field;
        }
    }

    class InstanceInner {
        private int instance_inner_Field = 100;
        private void instance_inner_func() {
            int x = Outer.this.outer_field;
        }
    }
}
