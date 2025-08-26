package hospitalSystem.example.Hospital.service.messaging;

import hospitalSystem.example.Hospital.dto.request.MessageRequestDto;
import hospitalSystem.example.Hospital.dto.response.MessageResponseDto;
import hospitalSystem.example.Hospital.repository.MessageRepository;

import java.util.List;

public interface IMessageService {
    MessageResponseDto sendMessage(MessageRequestDto messageRequestDto);
    List<MessageResponseDto> getConversation(Long senderId,Long receiverId);
    List<MessageResponseDto> getAllReceivedMessages(Long receiverId);
    List<MessageResponseDto> getAllSentMessages(Long senderId);

}
