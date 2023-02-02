package test_mockito.annotation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class TestAnnotation {
    @Mock
    private List<String> mockedList;

    /*
     * A field annotated with @Spy can be initialized explicitly at declaration point.
     * Alternatively, if you don't provide the instance
     * Mockito will try to find zero argument constructor (even private) and create an instance for you.
     */
    @Spy
    private Map<String, Integer> spyMap = new HashMap<>();

    @Test
    void testMock() {
        Assertions.assertFalse(mockedList.add("element"));
        Mockito.verify(mockedList).add("element");
    }

    @Test
    void testSpy() {
        Mockito.when(spyMap.size()).thenReturn(0);

        Assertions.assertNull(spyMap.put("key", 1));
        Assertions.assertEquals(1, spyMap.get("key"));
        Assertions.assertEquals(0, spyMap.size());
    }
}
