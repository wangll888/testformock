package test_mockito;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
@ExtendWith(MockitoExtension.class)
public class TestMock {

    @Test
    void testReturnValue() {
        List<String> mockedList = Mockito.mock(List.class);

        Assertions.assertFalse(mockedList.isEmpty());
        Assertions.assertEquals(0, mockedList.size());
        Assertions.assertNull(mockedList.iterator());
        Assertions.assertDoesNotThrow(() -> mockedList.get(999));
    }

    @Test
    void testFieldValue() {
        class ThreeFieldClass {
            private int intField = 100;
            private String stringField = "some string";
            private boolean booleanField = true;
        }

        ThreeFieldClass clazz = Mockito.mock(ThreeFieldClass.class);

        Assertions.assertEquals(0, clazz.intField);
        Assertions.assertNull(clazz.stringField);
        Assertions.assertFalse(clazz.booleanField);
    }

    @Test
    void testVerify() {
        List<String> mockedList = Mockito.mock(List.class);

        mockedList.add("one");
        mockedList.add("two");
        mockedList.add("two");
        mockedList.add(null);

        Mockito.verify(mockedList, Mockito.times(1)).add("one");
        Mockito.verify(mockedList).add("one");
        Mockito.verify(mockedList, Mockito.times(2)).add("two");
        Mockito.verify(mockedList, Mockito.times(1)).add(null);
        Mockito.verify(mockedList, Mockito.times(0)).add("three");
        Mockito.verify(mockedList, Mockito.never()).add("three");
    }

    @Test
    void testStubbing() {
        // You can mock concrete classes, not just interfaces
        LinkedList<String> mockedList = Mockito.mock(LinkedList.class);

        // stubbing
        Mockito.when(mockedList.get(0)).thenReturn("first");
        Mockito.when(mockedList.get(1)).thenThrow(new RuntimeException());

        Assertions.assertEquals("first", mockedList.get(0));

        Assertions.assertThrows(Throwable.class, () -> mockedList.get(1));
        Mockito.verify(mockedList).get(1);

        // following prints "null" because get(999) was not stubbed
        Assertions.assertNull(mockedList.get(999));
    }

    @Test
    void testCallRealMethod() {
        LinkedList<String> mockedList = Mockito.mock(LinkedList.class);
        Assertions.assertNull(mockedList.get(100));

        Mockito.when(mockedList.get(100)).thenCallRealMethod();
        Assertions.assertThrows(Throwable.class, () -> mockedList.get(100));
    }

    @Test
    void testStubbingConsecutiveCalls () {
        //You can mock concrete classes, not just interfaces
        LinkedList<String> mockedList = Mockito.mock(LinkedList.class);

        //stubbing
        Mockito.when(mockedList.get(0))
                .thenReturn("first")
                .thenThrow(new RuntimeException());

        Assertions.assertEquals("first", mockedList.get(0));
        Assertions.assertThrows(Throwable.class, () -> mockedList.get(0));

        // following prints "null" because get(999) was not stubbed
        Assertions.assertNull(mockedList.get(999));
    }

    @Test
    void testArgumentMatchers() {
        List<String> mockedList = Mockito.mock(List.class);

        Mockito.when(mockedList.add(Mockito.anyString())).thenReturn(true);
        Mockito.when(mockedList.add(Mockito.isNull())).thenReturn(true);

        // following prints "element"
        Assertions.assertTrue(mockedList.add("element"));
        Assertions.assertTrue(mockedList.add(null));

        // you can also verify using argument matcher
        Mockito.verify(mockedList).add("element");
        Mockito.verify(mockedList).add(Mockito.anyString());

        Mockito.verify(mockedList).add(null);
        Mockito.verify(mockedList).add(Mockito.isNull());

        Mockito.verify(mockedList, Mockito.times(2)).add(Mockito.nullable(String.class));
        Mockito.verify(mockedList, Mockito.times(2)).add(
                Mockito.argThat(argument -> argument == null || "element".equals(argument)));

//        Mockito.verify(mockedList).add("wrong"); // failed
    }

    @Test
    void testArgumentCaptor() {
        List<String> mockedList = Mockito.mock(List.class);

        Mockito.when(mockedList.add(Mockito.anyString())).thenReturn(true);
        Mockito.when(mockedList.add(Mockito.eq("two"))).thenReturn(false);
        Mockito.when(mockedList.add("three")).thenReturn(false);

        Assertions.assertTrue(mockedList.add("one"));
        Assertions.assertFalse(mockedList.add("two"));
        Assertions.assertFalse(mockedList.add("three"));

        ArgumentCaptor<String> argCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(mockedList, Mockito.times(3)).add(argCaptor.capture());

        List<String> args = argCaptor.getAllValues();

        Assertions.assertEquals(3, args.size());
        Assertions.assertEquals("one", args.get(0));
        Assertions.assertEquals("two", args.get(1));
        Assertions.assertEquals("three", args.get(2));
    }

    @Test
    void testStubbingVoidMethods() {
        Consumer<String> consumer = Mockito.mock(Consumer.class);

        Mockito.doThrow(new RuntimeException())
                .doNothing()
                .when(consumer).accept("param");

        /*
         * 使用doXXX()...when(obj)...methodCall()模式的一个原因：
         * 被stub的方法methodCall()返回void，无法放在when方法中。
         */

        Assertions.assertThrows(RuntimeException.class, () -> consumer.accept("param"));
        Assertions.assertDoesNotThrow(() -> consumer.accept("param"));
    }

    @Test
    void testAnsweringCallToVoid() {
        List<String> mockedList = Mockito.mock(List.class);

        Mockito.doAnswer(invocation -> {
            Assertions.assertEquals(mockedList, invocation.getMock());
            Assertions.assertEquals("add", invocation.getMethod().getName());

            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);

            Assertions.assertEquals(3, arg0);
            Assertions.assertEquals("answer me", arg1);
            return null;
        }).when(mockedList).add(Mockito.any(Integer.class), Mockito.any(String.class));

        mockedList.add(3, "answer me");
    }
}
