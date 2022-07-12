package com.qingqiao.mail.consume;

import com.qingqiao.vhr.bean.Employee;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component
@RabbitListener(queues = "qingqiao.mail.welcome")
public class MailSend {
    @Autowired
    public JavaMailSender mailSender;
    @Autowired
    TemplateEngine templateEngine;

    @RabbitHandler
    public void consume(Employee employee) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            helper.setFrom("1194683677@qq.com");
            // 发送地址
            helper.setTo(employee.getEmail());
            // 发送时间
            helper.setSentDate(new Date());
            // 右键主题
            helper.setSubject("欢迎入职");
            Context context = new Context();
            context.setVariable("empName", employee.getName());
            context.setVariable("departmentName", employee.getDepartment().getName());
            context.setVariable("posName", employee.getPosition().getName());
            context.setVariable("jobLevelName", employee.getJobLevel().getName());
            // 设置模板属性
            String process = templateEngine.process("mail", context);
            // 发送的内容
            helper.setText(process, true);
            // 发送邮件
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
