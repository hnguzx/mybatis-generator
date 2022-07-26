package cn.guzx.java;

import cn.guzx.mapper.SysUserMapper;
import cn.guzx.model.SysUser;
import cn.guzx.service.SysUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MockitoTest {

    /**
     * 验证行为
     */
    @Test
    void testVerify() {
        List mock = mock(List.class);
        mock.add(1);
        mock.add(2);
        mock.clear();
        Mockito.verify(mock).add(1);
        Mockito.verify(mock).add(2);
        //Mockito.verify(mock).add(3);
        Mockito.verify(mock).clear();
    }

    /**
     * 模拟期望结果
     */
    @Test
    void testWhenReturn() {
        Iterator mock = mock(Iterator.class);
        Mockito.when(mock.next()).thenReturn("hello").thenReturn("world");
        String result = mock.next() + " " + mock.next() + " " + mock.next();
        assertEquals("hello world world", result);
    }

    /**
     * mock对象的对象
     */
    @Test
    void testSmartReturn() {
        List mock = mock(List.class, Mockito.RETURNS_SMART_NULLS);
        System.out.println(mock.get(0));
        System.out.println(mock.toArray().length);

        SysUser mock1 = mock(SysUser.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(mock1.getUsername()).thenReturn("guzx");
        mock1.getUsername();
        Mockito.verify(mock1).getUsername();
        assertEquals("guzx", mock1.getUsername());
    }

    /**
     * mock执行特定操作时抛出异常
     */
    @Test
    void testException() {
        List mock = mock(List.class);
        Mockito.doThrow(new RuntimeException()).when(mock).add(1);
        mock.add(1);
        mock.add(2);
    }

    @Mock
    private List mockLists;

    //需要进行初始化，可用注解方式初始化
    //public MockitoTest(){
    //    MockitoAnnotations.initMocks(this);
    //}

    @Test
    void mockListL() {
        mockLists.add(1);
        Mockito.verify(mockLists).add(1);
    }

    @Mock
    private Comparable comparable;

    /**
     * 参数匹配
     */
    @Test
    void testParameter() {
        Mockito.when(comparable.compareTo("test")).thenReturn(1);
        Mockito.when(comparable.compareTo("guzx")).thenReturn(2);
        assertEquals(1, comparable.compareTo("test"));
        assertEquals(2, comparable.compareTo("guzx"));
        assertEquals(0, comparable.compareTo("not found"));
    }

    /**
     * 定制参数匹配
     */
    @Test
    void testParameter2() {
        Mockito.when(mockLists.get(Mockito.anyInt())).thenReturn(true);
        // 自定义参数匹配规则
        Mockito.when(mockLists.contains(Mockito.argThat((new IsValid())))).thenReturn(true);
        assertEquals(true, mockLists.get(0));
        assertEquals(true, mockLists.get(1));
        Assertions.assertTrue(mockLists.contains(1));
        Assertions.assertTrue(!mockLists.contains(3));

        Comparator comparator = mock(Comparator.class);
        comparator.compare("nihao", "hello");
        //如果你使用了参数匹配，那么所有的参数都必须通过matchers来匹配
        Mockito.verify(comparator).compare(Mockito.anyString(), Mockito.eq("hello"));
        //下面的为无效的参数匹配使用
        //Mockito.verify(comparator).compare(Mockito.anyString(),"hello");
    }

    private class IsValid implements ArgumentMatcher<Object> {

        @Override
        public boolean matches(Object o) {
            return o.equals(1);
        }
    }

    /**
     * 参数捕获器
     */
    @Test
    void capturingArgs() {
        SysUserMapper userMapper = mock(SysUserMapper.class);
        SysUserService userService = mock(SysUserService.class);
        ArgumentCaptor<SysUser> sysUserArgumentCaptor = ArgumentCaptor.forClass(SysUser.class);

        SysUser user = new SysUser(1, "username", "password", false, false, false, true);
        userService.addUser(user);
        Mockito.verify(userService).addUser(sysUserArgumentCaptor.capture());

        assertEquals(1, sysUserArgumentCaptor.getValue().getId());
        assertEquals("username", sysUserArgumentCaptor.getValue().getUsername());
    }

    /**
     * 使用方法预期回调接口生成期望值
     */
    @Test
    void testAnswer() {
        Mockito.when(mockLists.get(Mockito.anyInt())).thenAnswer(new CustomAnswer());
        assertEquals("hello 0", mockLists.get(0));
        assertEquals("hello 999", mockLists.get(999));
    }

    private class CustomAnswer implements Answer<String> {

        @Override
        public String answer(InvocationOnMock invocationOnMock) throws Throwable {
            Object[] arguments = invocationOnMock.getArguments();
            return "hello " + arguments[0];
        }
    }

    /**
     * 设置未预设的调用返回默认值
     */
    @Test
    void testDefaultMock() {
        List mock = mock(List.class, invocationOnMock -> 999);
        Mockito.when(mock.get(0)).thenReturn(123);

        assertEquals(123, mock.get(0));
        assertEquals(999, mock.get(1));
    }

    /**
     * spy监控调用真实对象
     */
    @Test
    void testSpy() {
        List list = new LinkedList();
        List spy = Mockito.spy(list);
        //Mockito.when(spy.get(0)).thenReturn(3);
        //Assertions.assertThrows(Exception.class, () -> {
        //    spy.get(0);
        //});
        Mockito.doReturn(999).when(spy).get(999);
        assertEquals(999, spy.get(999));

        Mockito.when(spy.size()).thenReturn(9);
        spy.add(1);
        spy.add(2);
        assertEquals(9, spy.size());
        assertEquals(1, spy.get(0));
        assertEquals(2, spy.get(1));
        Mockito.verify(spy).add(1);
        Mockito.verify(spy).add(2);
        //Mockito.verify(spy).add(3);
    }

    @Test
    void testPartialMock() {
        // 通过spy调用真实的api
        ArrayList spy = Mockito.spy(new ArrayList());
        assertEquals(0, spy.size());

        // 调用真实对象
        SysUser mock = mock(SysUser.class);
        Mockito.when(mock.getUsername()).thenCallRealMethod();
        assertEquals(null, mock.getUsername());
        // 调用mock对象
        Mockito.when(mock.getPassword()).thenReturn("password");
        assertEquals("password", mock.getPassword());

    }

    /**
     * 清除所有mock信息
     */
    @Test
    void testResetMock() {
        List mock = mock(List.class);
        Mockito.when(mock.size()).thenReturn(10);
        mock.add(2);
        assertEquals(10, mock.size());
        Mockito.reset(mock);
        assertEquals(0, mock.size());
    }

    /**
     * 验证指定方法指定参数的调用次数
     */
    @Test
    void testNumberOfInvocation() {
        List mock = mock(List.class);
        mock.add(1);
        mock.add(2);
        mock.add(3);
        mock.add(4);
        mock.add(5);
        verify(mock).add(1);
        verify(mock, times(1)).add(1);
        //verify(mock, times(2)).add(2);
        verify(mock, atLeastOnce()).add(3);
        verify(mock, atLeast(1)).add(4);
        verify(mock, atMost(2)).add(5);
    }

    /**
     * 连续调用
     */
    @Test
    void testContinuousInvocation() {
        List mock = mock(List.class);
        when(mock.get(0)).thenReturn(11);
        when(mock.get(0)).thenReturn(22);
        //assertEquals(11, mock.get(0));
        assertEquals(22, mock.get(0));
        reset(mock);
        when(mock.get(0)).thenReturn(33).thenReturn(44).thenThrow(new RuntimeException());
        assertEquals(33, mock.get(0));
        assertEquals(44, mock.get(0));
        assertEquals(0, mock.get(0));
    }

    /**
     * 验证方法调用顺序
     */
    @Test
    void testMockOrder() {
        List mock1 = mock(List.class);
        List mock2 = mock(List.class);
        mock1.add(1);
        mock2.add("a");
        mock1.add(2);
        mock2.add("b");

        InOrder inOrder = inOrder(mock1, mock2);
        inOrder.verify(mock1).add(1);
        inOrder.verify(mock2).add("a");
        inOrder.verify(mock1).add(2);
        inOrder.verify(mock2).add("b");
    }

    /**
     * 验证mock对象某些行为发生的频率
     */
    @Test
    void testInteraction() {
        List mock = mock(List.class);
        SysUser mock1 = mock(SysUser.class);
        mock.add(2);
        mock.add(3);
        // 发生过调用
        verify(mock).add(2);
        // 从未发生过
        verify(mock, never()).add(3);
        // 没发生任何调用
        verifyNoInteractions(mock1);
        // 存在发生过调用但是没有验证
        verifyNoMoreInteractions(mock);
    }

    @Test
    void testDoThrow() throws IOException {
        OutputStream mock = mock(OutputStream.class);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(mock);

        doThrow(new IOException()).when(mock).close();
        mock.close();
    }


}
