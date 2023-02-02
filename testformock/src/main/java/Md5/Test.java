package java.Md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Test {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String f = "abcdefg";
        String s1 = "abcd";
        String s2 = "efg";

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(f.getBytes());
        String md5 = new String(md.digest());
        System.out.println(md5);


        MessageDigest md1 = MessageDigest.getInstance("MD5");
        md1.update(s1.getBytes());
        md1.update(s2.getBytes());
        String md51 = new String(md1.digest());
        System.out.println(md51);

        System.out.println(md5.equals(md51));
    }
}
