package cn.guzx.controller;

import cn.guzx.model.SysUser;
import cn.guzx.service.SysUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SysUserControllerTest {

    @Autowired
    private SysUserController userController;

    @MockBean
    private SysUserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    SysUser user = new SysUser();

    @Test
    void testAddUser() {



        String s = userController.addUser(user);
        Assertions.assertEquals("success", s);
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        user.setId(3);
        user.setUsername("guzx");
        user.setPassword("123");
        user.setAccountExpired(false);
        user.setAccountLocked(false);
        user.setEnabled(true);
        user.setCredentialsExpired(false);
    }

    @Test
    void testQueryUser() throws Exception {
        Mockito.when(userController.queryUser(2)).thenReturn(user);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/queryUser/2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}
