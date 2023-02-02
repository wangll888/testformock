package java;

public class Test extends Father{

    {
        System.out.println("instance block 1: NUM = " + NUM);
        System.out.println("instance block 1: set NUM = " + (NUM = 1));
    }

    Test(int n) {
        this(NUM = 2, "");
        System.out.println("constructor: NUM = " + NUM);
        System.out.println("constructor: set NUM = " + (NUM = 3));
    }

    Test(int n, String s) {
        super(10);
        System.out.println("another constructor: NUM = " + NUM);
        System.out.println("another constructor: set NUM = " + (NUM = 4));
    }

    {
        System.out.println("instance block 2: NUM = " + NUM);
        System.out.println("instance block 2: set NUM = " + (NUM = 5));
    }

    static int NUM = 0;

    static {
        System.out.println("static block 1: NUM = " + NUM);
        System.out.println("static block 1: set NUM = " + (NUM = 6));
    }

    {
        System.out.println("instance block 3: NUM = " + NUM);
        System.out.println("instance block 3: set NUM = " + (NUM = 7));
    }

    static {
        System.out.println("static block 2: NUM = " + NUM);
        System.out.println("static block 2: set NUM = " + (NUM = 8));
    }

    public static void main(String[] args) throws ClassNotFoundException {
        new Test((Test.NUM = 9));
    }
}



class Father {

    {
        System.out.println("java.Father instance block 1: NUM = " + NUM);
        System.out.println("java.Father instance block 1: set NUM = " + (NUM = 11));
    }

    Father(int n) {
        this(NUM = 12, "");
        System.out.println("java.Father constructor: NUM = " + NUM);
        System.out.println("java.Father constructor: set NUM = " + (NUM = 13));
    }

    Father(int n, String s) {
        super();
        System.out.println("java.Father another constructor: NUM = " + NUM);
        System.out.println("java.Father another constructor: set NUM = " + (NUM = 14));
    }

    {
        System.out.println("java.Father instance block 2: NUM = " + NUM);
        System.out.println("java.Father instance block 2: set NUM = " + (NUM = 15));
    }

    static int NUM = 20;

    static {
        System.out.println("java.Father static block 1: NUM = " + NUM);
        System.out.println("java.Father static block 1: set NUM = " + (NUM = 16));
    }

    {
        System.out.println("java.Father instance block 3: NUM = " + NUM);
        System.out.println("java.Father instance block 3: set NUM = " + (NUM = 17));
    }

    static {
        System.out.println("java.Father static block 2: NUM = " + NUM);
        System.out.println("java.Father static block 2: set NUM = " + (NUM = 18));
    }

    public static void main(String[] args) throws ClassNotFoundException {
        new Test((Test.NUM = 9));
    }
}