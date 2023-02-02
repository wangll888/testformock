package java;

public class TestEnum {

    public static void main(String[] args) {
//        Device device = new Device();
//        device.setManufacturer("Topsec");
//        device.setType(DeviceType.FIREWALL);
//
//        System.out.println(SpecialDevice.getEnv(device));

        System.out.println(A.A1);
    }
}




enum A {
    A1(null), A2(A1);

    A(A a) {
        if (a != null) {
            System.out.println(a.name());
        }

    }
}
