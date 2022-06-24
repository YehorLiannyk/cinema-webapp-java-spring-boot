package yehor.epam.cinema_final_project_spring.services;

import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayOutputStream;
import java.io.File;

public interface EmailService {
    void sendSimpleMsg(String to, String subject, String text);

    void sendMsgWithAttachment(String to, String subject, String text, File attachment);

    void sendMsgWithAttachment(String to, String subject, String text, ByteArrayOutputStream byteArrayOutputStream, String filename);
}
