package test_mockito.annotation.inject_mocks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestPropertyInjection {
    @InjectMocks
    TestClass clazz;

    @Mock
    CharSequence mocked; // 注入到property "p"中。类型一致即可，与参数名、property名均不一致。

    @Test
    void test() {
        System.out.println("---------------test start-------------");
        // 验证未发生对field的直接赋值
        Assertions.assertNotNull(mocked);
        Assertions.assertNull(clazz.p);
    }

    static class TestClass {
        private CharSequence p;

        private TestClass() {
            System.out.println("TestClass()");
        }

        /*
         * property名称为"p"。
         * 访问级别必须是public。
         */
        public void setP(CharSequence cs) {
            System.out.println("setP()： " + cs);
        }
    }
}




