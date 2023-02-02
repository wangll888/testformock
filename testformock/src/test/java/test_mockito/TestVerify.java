package test_mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestVerify {
    @Mock
    List<String> mockedList;

    @Test
    void testVerifyNumberOfInteractions() {
        mockedList.size();
        Mockito.verify(mockedList).size();
        Mockito.verify(mockedList, Mockito.times(1)).size();
    }

    @Test
    void testNoInteraction() {
        Mockito.verifyNoInteractions(mockedList);
    }

    @Test
    void testNoInteractionWithASpecificMethod() {
        mockedList.add("one");
        Mockito.verify(mockedList, Mockito.times(0)).size();
        Mockito.verify(mockedList, Mockito.never()).clear();
    }

    @Test
    void testNoUnexpectedInteractions() {
        mockedList.size();
        Mockito.verify(mockedList).size();
        Mockito.verifyNoMoreInteractions(mockedList);

        /*
         * 后面再有操作并不影响，其准确含义为：
         * Checks if any of given mocks has any unverified interaction.
         */

        mockedList.clear();
        Mockito.verify(mockedList).clear();
    }

    @Test
    void testOrderOfInteractions() {
        mockedList.size();
        mockedList.add("one");
        mockedList.add("two");
        mockedList.clear();
        mockedList.add("three");

        InOrder inOrder = Mockito.inOrder(mockedList);
        inOrder.verify(mockedList).size();
        inOrder.verify(mockedList, Mockito.times(2)).add(Mockito.anyString());
        inOrder.verify(mockedList).clear();
        inOrder.verify(mockedList, Mockito.times(1)).add("three");
    }

    @Test
    void testOccurredAtLeastCertainNumberOfTimes() {
        mockedList.clear();
        mockedList.clear();
        mockedList.clear();

        Mockito.verify(mockedList, Mockito.atLeast(1)).clear();
        Mockito.verify(mockedList, Mockito.atMost(10)).clear();
    }

    @Test
    void testDoNothingIsDefaultBehaviorForVoidMethods() {

        /*
         * doNothing()是void方法的默认mock行为。
         */

        mockedList.add(0, "");
        Mockito
                .lenient()
                .doNothing().when(mockedList).add(Mockito.isA(Integer.class), Mockito.isA(String.class));
        Mockito.verify(mockedList).add(0, "");
    }
}
