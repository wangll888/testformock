package visibility.p2;

public class ParentClass {
    protected static int pf1;
    protected static class SubClass {
        protected static int sf1;
        private static int sf2;
    }

    void func1() {
        int a = SubClass.sf2;
    }
}

class AnotherClass {
    static void func1() {
        int a = ParentClass.SubClass.sf1;
    }
}
