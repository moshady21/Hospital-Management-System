package hospitalSystem.example.Hospital.mapper;

import hospitalSystem.example.Hospital.dto.request.MessageRequestDto;
import hospitalSystem.example.Hospital.dto.response.MessageResponseDto;
import hospitalSystem.example.Hospital.entity.Message;
import hospitalSystem.example.Hospital.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MessageMapper {

        // Convert Request DTO -> Entity
        public static Message toEntity(MessageRequestDto dto, User sender, User receiver) {
            Message message = new Message();
            message.setContent(dto.getContent());
            message.setTimestamp(LocalDateTime.now());
            message.setSender(sender);
            message.setReceiver(receiver);
            return message;
        }

        // Convert Entity -> Response DTO
        public static MessageResponseDto toResponseDto(Message message) {
            return new MessageResponseDto(
                    message.getId(),
                    message.getContent(),
                    message.getTimestamp(),
                    message.getSender().getId(),
                    message.getSender().getName(),
                    message.getReceiver().getId(),
                    message.getReceiver().getName()
            );
        }
    }

