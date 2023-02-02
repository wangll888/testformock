package cloneable;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        SubClazz sub = new SubClazz();
        System.out.println(sub instanceof Cloneable);
    }
}

class SuperClazz implements Cloneable {
    private int x = 1;

    @Override
    public String toString() {
        return "x=" + x;
    }
}

class SubClazz extends SuperClazz {
    private int y = 2;

    @Override
    public String toString() {
        return "y=" + y;
    }
}
