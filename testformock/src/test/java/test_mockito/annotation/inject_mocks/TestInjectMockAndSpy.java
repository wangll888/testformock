package test_mockito.annotation.inject_mocks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class TestInjectMockAndSpy {
    @Spy
    @InjectMocks
    TestClass clazz;

    @Mock
    List<String> mocked;

    @Spy
    Map<String, Integer> map; // spy对象依然可以被用来inject。

    @Test
    void test() {
        System.out.println("---------------test start-------------");
        System.out.println("mocked: " + mocked);
        System.out.println("clazz.p: " + clazz.p);
        System.out.println("clazz.methodA(): " + clazz.methodA());

        // 验证inject mock生效
        Assertions.assertNotNull(clazz.p);
        Assertions.assertEquals(mocked, clazz.p);
        Assertions.assertNotNull(clazz.m);
        Assertions.assertEquals(map, clazz.m);

        // 验证inject mock只影响字段，不影响方法。
        Assertions.assertEquals("do something", clazz.methodA());

        /*
         * 1、@InjectMocks只mock字段，不影响任何成员方法。并且@InjectMocks产生的是real object。必须配合@Spy才能对成员方法进行stub。
         * 2、@Mock对字段和方法都进行mock，因此与仅mock字段的@InjectMocks是相互冲突的，无法同时使用。
         */
        Mockito.when(clazz.methodA()).thenReturn("do something else");
        Assertions.assertEquals("do something else", clazz.methodA());
    }

    static class TestClass {
        private List<String> p;

        private Map<String, Integer> m;

        public String methodA() {
            return "do something";
        }
    }
}




