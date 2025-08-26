package hospitalSystem.example.Hospital.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseDto {
    private Long id;
    private String content;
    private LocalDateTime timestamp;

    private Long senderId;
    private String senderName;

    private Long receiverId;
    private String receiverName;

}
