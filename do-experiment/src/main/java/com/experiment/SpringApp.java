package com.experiment;

import com.experiment.config.SpringAppConfig;
import com.experiment.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author chenxuegui
 * @since 2024/11/21
 */
public class SpringApp {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpringAppConfig.class);
        context.refresh();


        UserService userService = context.getBean(UserService.class);
        System.out.println(userService.selectById(2233));

    }
}
