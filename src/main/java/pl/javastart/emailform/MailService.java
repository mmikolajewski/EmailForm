package pl.javastart.emailform;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final static String CONFIRMATION_MESSAGE_FOR_SENDER = "Dziękujemy za kontakt z SZOPPINGWORD, niedługo otrzymasz wiadomość";
    private final static String OWNER_EMAIL_ADDRESS = "szopping@byom.de";

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    private JavaMailSender javaMailSender;

    public MailService(JavaMailSender mailSender) {
        this.javaMailSender = mailSender;
    }

    public void sendConfirmationToSender(String senderEmail) {
        sendMail(senderEmail, OWNER_EMAIL_ADDRESS, senderEmail, CONFIRMATION_MESSAGE_FOR_SENDER);
    }

    public void sendEmailToOwner(String senderEmail, String senderName,String text) {
        sendMail(senderEmail, senderName, OWNER_EMAIL_ADDRESS, text);

    }

    private void sendMail(String senderEmail,String senderName, String reciever, String text) {
        logger.debug("Wysyłam maila do {}", reciever);
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            String htmlMsg = "<3>Hello World</h3>";
            helper.setFrom(""); //email pośredniczący - ten sam co w .yml
            helper.setTo(reciever);
            helper.setSubject(text);

            helper.setText("Od " + senderEmail + ", " + senderName + " " + text, true);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            logger.warn("Błąd wysłania wiadomości", e);
        }
        logger.debug("Mail do {} wysłany poprawnie", reciever);
    }
}
