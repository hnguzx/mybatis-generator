package cn.guzx.java;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class MockitoTest {

    @Test
    void testVerify() {
        List mock = Mockito.mock(List.class);
        mock.add(1);
        mock.add(2);
        mock.clear();
        Mockito.verify(mock).add(1);
        Mockito.verify(mock).add(2);
        //Mockito.verify(mock).add(3);
        Mockito.verify(mock).clear();

    }
}
