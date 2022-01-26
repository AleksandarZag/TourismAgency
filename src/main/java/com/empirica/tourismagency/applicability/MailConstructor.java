package com.empirica.tourismagency.applicability;

import com.empirica.tourismagency.field.Order;
import com.empirica.tourismagency.field.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Locale;


@Component
public class MailConstructor {
    @Autowired
    private Environment env;
    @Autowired
    private TemplateEngine templateEngine;


    public SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, User user, String password

    ) {
        String url = contextPath + "/newUser?token=" + token;
        String message = "\nMolimo kliknite na ovaj link kako biste potvrdili Vaš email te uredili korisničke informacije. Vaša šifra je: \n" + password;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Turistička agencija - Novi korisnik");
        email.setText(url + message);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    public MimeMessagePreparator constructOrderConfirmationEmail(User user, Order order, Locale locale){
        Context context = new Context();
        context.setVariable("order",order);
        context.setVariable("user", user);
        context.setVariable("reservationItemList", order.getReservationItemList());
        String text = templateEngine.process("orderConfirmationEmailTemplate", context);

        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                email.setTo(user.getEmail());
                email.setSubject("Order Confirmation - "+order.getId());
                email.setText(text, true);
                email.setFrom(new InternetAddress("op.exogotic@gmail.com"));
            }
        };
        return messagePreparator;


    }
}
