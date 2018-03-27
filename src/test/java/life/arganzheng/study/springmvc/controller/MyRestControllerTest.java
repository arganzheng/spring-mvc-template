package life.arganzheng.study.springmvc.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;
import life.arganzheng.study.springmvc.config.MainConfig;
import life.arganzheng.study.springmvc.controller.MyRestController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({ "dev" })
@ContextConfiguration(classes = { MainConfig.class, MyRestControllerTest.Config.class })
public class MyRestControllerTest {

    @Autowired
    MyRestController controller;

    MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Configuration
    static class Config {
        @Bean
        public MyRestController getMyRestController() {
            return new MyRestController();
        }
    }

    @Test
    public void testGetText() throws Exception {
        mockMvc.perform(get("/rest/get/text") //
        ).andDo(MockMvcResultHandlers.print()) //
                .andExpect(status().isOk()) //
                // Content type expected:<application/text> but was:<application/text;charset=UTF-8>
                // .andExpect(content().contentType(MediaType.TEXT_PLAIN)) //
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN)) //
                .andExpect(content().string(equalTo(("Spring MVC - REST Controller Hello World example."))));
    }

    @Test
    public void testGetJson() throws Exception {
        mockMvc.perform(get("/rest/get/json") //
        ).andDo(MockMvcResultHandlers.print()) //
                .andExpect(status().isOk()) //
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE)) //
                .andExpect(jsonPath("$[0]").value("One")); //
    }

    @Test
    public void testGetXml() throws Exception {
        String xmlContent = "<user><id>12</id><name>John</name></user>";
        mockMvc.perform(get("/rest/get/xml") //
        ).andDo(MockMvcResultHandlers.print()) //
                .andExpect(status().isOk()) //
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML_VALUE)) //
                .andExpect(xpath("user/name").string(equalTo("John"))).andExpect(content().xml(xmlContent));
    }

}
