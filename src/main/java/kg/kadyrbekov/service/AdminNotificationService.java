package kg.kadyrbekov.service;

import kg.kadyrbekov.model.entity.Complaint;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;

@Service
public class AdminNotificationService {

    public void notifyAdmin(Complaint complaint) {
        String adminEmail = "admin@example.com";
        String subject = "New Complaint Received";
        String body = "A new complaint has been received:\n\n" +
                "User Id: " + complaint.getUser().getId() + "\n" +
                "Message: " + complaint.getMessage() + "\n" +
                "Created At: " + complaint.getCreatedAt();

        // Send email notification to the admin
        sendEmail(adminEmail, subject, body);
    }

    private void sendEmail(String recipient, String subject, String body) {
        // Implement the logic to send an email notification
        // using JavaMail, Spring Mail, or any other email library

        // Example using JavaMail
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.example.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("your-email@example.com", "12345");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("your-email@example.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Notification email sent successfully.");
        } catch (MessagingException e) {
            System.out.println("Failed to send notification email. Error: " + e.getMessage());
        }
    }
}
