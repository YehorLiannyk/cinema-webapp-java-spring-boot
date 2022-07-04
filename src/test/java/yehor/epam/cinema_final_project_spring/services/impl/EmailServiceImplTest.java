package yehor.epam.cinema_final_project_spring.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import yehor.epam.cinema_final_project_spring.exceptions.email.EmailException;

import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    private final String rightEmail = "user@cinema-epam-localhost.com";
    private final String wrongEmail = "wrong email";
    @Spy
    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    void sendSimpleMsg() {
        emailService.sendSimpleMsg("", "", "");
        then(emailService).should().sendSimpleMsg("", "", "");
    }

    @Test
    void sendMsgWithAttachmentStream() {
        MimeMessage message = mock(MimeMessage.class);
        ByteArrayOutputStream stream = mock(ByteArrayOutputStream.class);
        given(javaMailSender.createMimeMessage()).willReturn(message);
        given(stream.toByteArray()).willReturn(new byte[]{});
        final String s = "";
        emailService.sendMsgWithAttachment(rightEmail, s, s, stream, s);
        then(javaMailSender).should().send(message);
    }

    @Test
    void sendMsgWithAttachmentStreamWithWrongEmailThrowEmailException() {
        MimeMessage message = mock(MimeMessage.class);
        ByteArrayOutputStream stream = mock(ByteArrayOutputStream.class);
        given(javaMailSender.createMimeMessage()).willReturn(message);
        given(stream.toByteArray()).willReturn(new byte[]{});
        final String s = "";
        try {
            emailService.sendMsgWithAttachment(wrongEmail, s, s, stream, s);
            fail("EmailException should be thrown");
        } catch (EmailException e) {
        }
        then(javaMailSender).should(never()).send(message);
    }


    @Test
    void sendMsgWithAttachmentFile() {
        MimeMessage message = mock(MimeMessage.class);
        File file = mock(File.class);
        given(javaMailSender.createMimeMessage()).willReturn(message);
        final String s = "";
        emailService.sendMsgWithAttachment(rightEmail, s, s, file);
        then(javaMailSender).should().send(message);
    }

    @Test
    void sendMsgWithAttachmentFileWithWrongEmailThrowEmailException() {
        MimeMessage message = mock(MimeMessage.class);
        File file = mock(File.class);

        given(javaMailSender.createMimeMessage()).willReturn(message);
        final String s = "";
        try {
            emailService.sendMsgWithAttachment(wrongEmail, s, s, file);
            fail("EmailException should be thrown");
        } catch (EmailException e) {
        }
        then(javaMailSender).should(never()).send(message);
    }

}