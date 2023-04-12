package kg.kadyrbekov.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto {
    private String senderEmail;
    private String recipientEmail;
    private String content;

    // Getters and setters omitted for brevity

    public MessageDto() {
    }

    public MessageDto(String senderEmail, String recipientEmail, String content) {
        this.senderEmail = senderEmail;
        this.recipientEmail = recipientEmail;
        this.content = content;
    }
}
