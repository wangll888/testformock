package test_mockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestMockObjectConstruction {
    @Test
    void test() {
        try (MockedConstruction<HelperClass> mockedHelperClass = Mockito.mockConstruction(
                HelperClass.class,
                (mocked, context) -> Mockito.when(mocked.getHelp()).thenReturn("out help"))
        ) {
            SomeClass sc = new SomeClass();
            Assertions.assertEquals("do something without help", sc.doSomething());

            List<HelperClass> mockedList = mockedHelperClass.constructed(); // 所有被mock的对象
            Assertions.assertEquals(1, mockedList.size());
            HelperClass mocked = mockedList.get(0);
            Assertions.assertNull(mocked.gratitude); // 构造方法被mock掉了
        }
    }
}

class SomeClass {
    public String doSomething() {
        HelperClass hc = new HelperClass("gratitude");
        return "do something with" + hc.getHelp();
    }
}

class HelperClass {
    String gratitude;

    HelperClass(String gratitude) {
        this.gratitude = gratitude;
    }

    public String getHelp() {
        return "help";
    }
}