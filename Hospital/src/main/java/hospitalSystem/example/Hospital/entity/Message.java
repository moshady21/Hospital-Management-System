package hospitalSystem.example.Hospital.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Content is required")
    @Size(min = 2, max = 100)
    private String content;

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;

    // Sender of the message
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    // Receiver of the message
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;
}
