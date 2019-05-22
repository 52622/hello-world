package com.test.orange;

import com.test.orange.entity.User;
import com.test.orange.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrangeApplicationTests {
	@Resource
	private UserService userService;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;
	private MockHttpSession session;

	@Test
	public void contextLoads() {
	}

	@Before
	public void setupMockMvc() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testServiceSave() {
		User user = new User();
		user.setId(UUID.randomUUID().toString().replaceAll("-",""));
		user.setName("小小");
		user.setSex(0);
		user.setAge(16);

		userService.saveUser(user);
	}

	@Test
	public void testServiceFind() {
		String id = "c09c06f5688047aaadc99d70e8330c46";
		User user = userService.getUserById(id);

		System.out.println(user);
	}

	@Test
	public void testControllerAdd() throws Exception{
		String json = "{\"name\":\"NaNa\",\"sex\":0,\"age\":10}";
		mvc.perform(MockMvcRequestBuilders.post("/user/add")
					.content(json.getBytes()))
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testControllerFind() throws Exception{
		String id = "c09c06f5688047aaadc99d70e8330c46";
		mvc.perform(MockMvcRequestBuilders.get("/user/" + id))
				.andDo(MockMvcResultHandlers.print());
	}
}

