package java;

import java.util.function.Function;

public class TestLambda {
    public static void main(String[] args) {

    }

    void func2() {
        func(s -> null);
    }

    void func(Function<String, String> func) {
        String result = func.apply("");
    }
}
