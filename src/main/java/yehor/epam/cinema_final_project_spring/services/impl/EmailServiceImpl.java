package yehor.epam.cinema_final_project_spring.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import yehor.epam.cinema_final_project_spring.exceptions.email.EmailException;
import yehor.epam.cinema_final_project_spring.services.EmailService;
import yehor.epam.cinema_final_project_spring.utils.constants.Constants;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.File;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendSimpleMsg(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Constants.NO_REPLY_EMAIL);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Override
    public void sendMsgWithAttachment(String to, String subject, String text, File file) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = getMimeMessageHelper(to, subject, text, message);
            final String filename = getFilename(file.getName());
            helper.addAttachment(filename, file);
        } catch (MessagingException e) {
            log.error("Couldn't send email message with attachment", e);
            throw new EmailException();
        }
        emailSender.send(message);
    }

    private String getFilename(String fileName) {
        return fileName != null && !fileName.isBlank() ? fileName : "attachment.pdf";
    }

    @Override
    public void sendMsgWithAttachment(String to, String subject, String text, ByteArrayOutputStream byteArrayOutputStream,
                                      String filename) {
        MimeMessage message = emailSender.createMimeMessage();
        final ByteArrayResource byteArrayResource = new ByteArrayResource(byteArrayOutputStream.toByteArray());
        try {
            MimeMessageHelper helper = getMimeMessageHelper(to, subject, text, message);
            final String checkedFilename = getFilename(filename);
            helper.addAttachment(checkedFilename, byteArrayResource);
        } catch (MessagingException e) {
            log.error("Couldn't send email message with attachment", e);
            throw new EmailException();
        }
        emailSender.send(message);
    }

    private MimeMessageHelper getMimeMessageHelper(String to, String subject, String text, MimeMessage message)
            throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(Constants.NO_REPLY_EMAIL);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        return helper;
    }
}
