package visibility.p1;

import visibility.p2.ParentClass;

public class Test extends ParentClass {
    public static void main(String[] args) {
        int a = ParentClass.pf1;
    }

    class InnerTest extends ParentClass.SubClass {
        void func1() {
            int b = SubClass.sf1;
        }
    }
}


