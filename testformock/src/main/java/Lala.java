package java;

import java.util.function.Consumer;

/**
 * @author heshan
 */
public class Lala {

    public static void main(String[] args) {
        Sender s = new Sender();
        Receiver r = new Receiver(s);

        new Thread(s).start();
        new Thread(r).start();
    }

}

class Receiver implements Consumer<String>, Runnable {

    final Sender s;

    Receiver(Sender s) {
        this.s = s;
    }

    @Override
    public void run() {
        s.register(this);
        System.out.println("注册监听回调后，本线程就死了。");
    }

    @Override
    public void accept(String s) {
        System.out.println("但此回调方法仍然存在。" + s);
    }


}

class Sender implements Runnable {
    private volatile Consumer<String> receiver;

    void register(Consumer<String> c) {
        this.receiver = c;
    }

    @Override
    public void run() {
        while (true) {
            if (receiver != null) {
                String msg = System.currentTimeMillis() + "";
                receiver.accept(msg);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}