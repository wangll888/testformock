package test_mockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestSpying {

    @Test
    void testSpy() {
        List<String> list = new LinkedList<>();
        List<String> spy = Mockito.spy(list);

        spy.add("one");
        Assertions.assertEquals("one", spy.get(0));
        Assertions.assertEquals(1, spy.size());

        Mockito.when(spy.size()).thenReturn(100);
        Assertions.assertEquals(100, spy.size());

        // Mockito *does not* delegate calls to the passed real instance, instead it actually creates a copy of it.
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        Assertions.assertEquals(0, list.size());
    }

    @Test
    void testStubbingRealMethod() {
        List<String> list = new LinkedList<>();
        List<String> spy = Mockito.spy(list);

        // Impossible: real method is called so spy.get(0) throws IndexOutOfBoundsException (the list is yet empty)
//        Mockito.when(spy.get(0)).thenReturn("foo");

        Mockito.doReturn("foo").when(spy).get(0);

        Assertions.assertEquals("foo", spy.get(0));

        /*
         * 使用doXXX()...when(obj)...methodCall()模式的一个原因：
         * 调用真实的methodCall()方法会抛异常，或者有其他影响。
         */
    }

    @Test
    void testStubbingFinalMethod() {

        /*
         * Mockito doesn't mock final methods so the bottom line is:
         * when you spy on real objects + you try to stub a final method = trouble.
         * Also, you won't be able to verify those methods as well.
         */

        class ClazzA {
            public final String finalMethod() {
                return "finalMethod";
            }
        }

        ClazzA spy = Mockito.spy(new ClazzA());

//        Mockito.when(spy.finalMethod()).thenReturn("mocked finalMethod"); // failed

        System.out.println(spy.finalMethod());

//        Mockito.verify(spy).finalMethod(); // failed
    }
}
