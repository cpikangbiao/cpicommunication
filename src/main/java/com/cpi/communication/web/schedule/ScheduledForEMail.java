/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ScheduledForStatis
 * Author:   admin
 * Date:     2018/8/29 15:54
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.communication.web.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/8/29
 * @since 1.0.0
 */

@Component
public class ScheduledForEMail {

    private final Logger log = LoggerFactory.getLogger(ScheduledForEMail.class);

//    @Autowired
//    private MailService mailService;
//
//    @Autowired
//    private TemplateEngine templateEngine;



//    @Scheduled(cron = "* * * * * ?")
//    public void pushDataScheduled(){
//        log.info("start gather Statistic  data scheduled!");
//
//        Context context = new Context();
//        context.setVariable("hello", "NEW Hello!");
//        String emailText = templateEngine.process("TestTemplate", context);
//
//        mailService.sendEmail("kangbiao@chinapandi.com",
//            "admin@chinapandi.com",
//            "Mail for Test",
//            emailText);
//
//        log.info("end gather Statistic  data scheduled!");
//    }

}
