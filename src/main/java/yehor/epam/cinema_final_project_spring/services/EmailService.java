package yehor.epam.cinema_final_project_spring.services;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Service for any email sending
 */
public interface EmailService {
    /**
     * Send simple text only email
     *
     * @param to      Receiver email
     * @param subject Email subject
     * @param text    Email text
     */
    void sendSimpleMsg(String to, String subject, String text);

    /**
     * Send  email with attachment
     *
     * @param to         Receiver email
     * @param subject    Email subject
     * @param text       Email text
     * @param attachment Attachment file (PDF usually)
     */
    void sendMsgWithAttachment(String to, String subject, String text, File attachment);

    /**
     * Send  email with attachment as byteArrayOutputStream
     *
     * @param to                    Receiver email
     * @param subject               Email subject
     * @param text                  Email text
     * @param byteArrayOutputStream Attachment stream (PDF usually)
     */
    void sendMsgWithAttachment(String to, String subject, String text, ByteArrayOutputStream byteArrayOutputStream, String filename);
}
