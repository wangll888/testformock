package test_mockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestMockFinal {
    @Test
    void testFinalClass() {
        FinalClass fc = Mockito.mock(FinalClass.class);
        Mockito.when(fc.doSomething()).thenReturn("do something else...");
        Assertions.assertEquals("do something else...", fc.doSomething());
    }

    @Test
    void testFinalMethod() {
        FinalMethod fc = Mockito.mock(FinalMethod.class);
        Mockito.when(fc.doSomethingFinal()).thenReturn("do something else...");
        Assertions.assertEquals("do something else...", fc.doSomethingFinal());
    }

    @Test
    void testEnum() {
        FinalEnum fc = Mockito.mock(FinalEnum.class);
        Mockito.when(fc.valueOf(999)).thenReturn(FinalEnum.A);
        Assertions.assertEquals(FinalEnum.A, fc.valueOf(999));
    }
}

final class FinalClass {
    String doSomething() {
        return "do something...";
    }
}

class FinalMethod {
    final String doSomethingFinal() {
        return "do something...";
    }
}

enum FinalEnum {
    A, B, C;

    FinalEnum valueOf(int order) {
        switch (order) {
            case 0: return A;
            case 1: return B;
            case 2: return C;
            default: throw new RuntimeException();
        }
    }
}