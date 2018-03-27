package life.arganzheng.study.springmvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@PropertySource(value = "classpath:profile.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:${profile:dev}/application.properties")
@ComponentScan(basePackages = { "life.arganzheng.study.springmvc.controller" })
public class WebConfig extends WebMvcConfigurerAdapter {

}
