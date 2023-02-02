package test_mockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestMockStatic {
    @Test
    void testPrefix() {
        try (MockedStatic<StaticUtils> su = Mockito.mockStatic(StaticUtils.class)) {
            Assertions.assertNull(StaticUtils.prefix("yyy"));
            su.when(() -> StaticUtils.prefix(Mockito.anyString())).thenReturn("123");
            Assertions.assertEquals("123", StaticUtils.prefix("yyy"));
        }
        Assertions.assertEquals("_xxx", StaticUtils.prefix("xxx"));
    }

    @Test
    void testClear() {
        List<String> list = new ArrayList<>();
        list.add("xxx");
        try (MockedStatic<StaticUtils> su = Mockito.mockStatic(StaticUtils.class)) {
            StaticUtils.clear(list);

            /*
             * 注意，Mockito不支持对static void方法做"doXXX()...when()"形式的mock。
             */
        }
        Assertions.assertEquals(1, list.size());
    }
}


class StaticUtils {

    private StaticUtils() {}

    public static String prefix(String str) {
        return "_" + str;
    }

    public static void clear(List<String> list) {
        list.clear();
    }
}
