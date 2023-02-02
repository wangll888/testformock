package java;

public class TestUnsafePublish {

    private static Holder holder;

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            int count = 0;
            @Override
            public void run() {
                while (true) {
                    holder = new Holder(++count);
                    System.out.println("set holder to " + holder);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.setDaemon(true);
        t1.start();

        while (true) {
            if (holder != null) {
                holder.assertSanity();
            } else {
                System.out.println("holder is null" + System.currentTimeMillis());
            }
        }
    }

}

class Holder {
    private int n;

    Holder(int n) {
        this.n = n;
    }

    void assertSanity() {
        if (n != n) {
            throw new AssertionError("this statement is false.");
        }
    }

    @Override
    public String toString() {
        return n + "";
    }
}
