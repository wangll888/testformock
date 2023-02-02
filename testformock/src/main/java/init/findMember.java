package java.init;

public class findMember {
    public static void main(String[] args) {
        System.out.println(finalMemberClass.str);
    }
}

class finalMemberClass {
    public static String str = "heshan" + func(1);

    static int func(int i) {
        return i;
    }


    {
        System.out.println("finalMemberClass constructor running");
    }
}
