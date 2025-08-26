package hospitalSystem.example.Hospital.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageRequestDto {
    @NotNull(message = "Sender Id is required")
    private Long senderId;
    @NotNull(message = "Receiver Id is required")
    private Long receiverId;
    @NotBlank(message = "Content of message is required")
    private String content;
}
