package com.example.demo.Config;

import com.example.demo.Component.LoginHandleIntecptor;
import com.example.demo.Component.myLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyMVCConfig extends WebMvcConfigurerAdapter {
    public void addViewControllers(ViewControllerRegistry registry) {
        // super.addViewControllers(registry);
        //浏览器发送 /atguigu 请求来到 success
        registry.addViewController("/crud").setViewName("success");
    }
    @Bean//将其注册到容器中
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){

        WebMvcConfigurerAdapter adater= new WebMvcConfigurerAdapter(){

            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                //super.addViewControllers(registry);

                    registry.addViewController("/").setViewName("login");
                    registry.addViewController("/index.html").setViewName("login");
                    registry.addViewController("/main.html").setViewName("dashboard");
                }
                //注册拦截器
                @Override
                public void  addInterceptors(InterceptorRegistry registry){
                    //springboot做好了静态资源的映射
                    registry.addInterceptor(new LoginHandleIntecptor()).addPathPatterns("/**").excludePathPatterns("/index.html","/","/user/login","");
                };



        };

        return adater;
    }

    @Bean
    public LocaleResolver localeResolver(){
        return new myLocaleResolver();
    }
}
