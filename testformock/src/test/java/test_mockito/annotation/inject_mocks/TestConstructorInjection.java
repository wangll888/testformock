package test_mockito.annotation.inject_mocks;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class TestConstructorInjection {
    @InjectMocks
    TestClass clazz;

//    {
//        clazz = new TestClass(null, null, null, 1);
//    }

    @Mock
    List<String> list;

    @Spy
    Map<String, Integer> map; // spy对象依然可以被用来inject。

    Set<String> set = new HashSet<>();

    @Test
    void test() {
        System.out.println("test(), list: " + list + ", map: " + map + ", set: " + set);
    }

    static class TestClass {

        /*
         * Called when constructor injection won't happen.
         */
        private TestClass() {
            System.out.println("no params constructor.");
        }

        private TestClass(List<String> list,
                          Map<String, Integer> map) {
            System.out.println("two params constructor. list: " + list + ", map: " + map);
        }

        /*
         * Arguments are resolved with mocks declared in the test only.
         * If arguments can not be found, then null is passed.
         */
        private TestClass(List<String> list,
                          Map<String, Integer> map,
                          Set<String> set) { // null
            System.out.println("three params constructor. list: " + list + ", map: " + map + ", set: " + set);
        }

        // If non-mockable types are wanted, then constructor injection won't happen.
        private TestClass(List<String> list,
                          Map<String, Integer> map,
                          Set<String> set,
                          int integer) { // non-mockable
            System.out.println("four params constructor. list: " + list + ", map: " + map + ", set: " + set + ", integer: " + integer);
        }
    }
}



