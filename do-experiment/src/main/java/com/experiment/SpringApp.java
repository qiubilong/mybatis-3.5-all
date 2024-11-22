package com.experiment;

import com.experiment.config.SpringAppConfig;
import com.experiment.service.MomentService;
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

        //简单参数 - 返回单个对象
        UserService userService = context.getBean(UserService.class);
        //System.out.println(userService.selectById(2233));

        MomentService momentService = context.getBean(MomentService.class);
        momentService.batchInsertCounter();
        momentService.selectMomentLikeNum();
        momentService.insertCounter();

    }
}
