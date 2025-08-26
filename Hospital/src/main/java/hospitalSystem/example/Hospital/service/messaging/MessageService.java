package hospitalSystem.example.Hospital.service.messaging;

import hospitalSystem.example.Hospital.dto.request.MessageRequestDto;
import hospitalSystem.example.Hospital.dto.response.MessageResponseDto;
import hospitalSystem.example.Hospital.entity.Message;
import hospitalSystem.example.Hospital.entity.User;
import hospitalSystem.example.Hospital.mapper.MessageMapper;
import hospitalSystem.example.Hospital.repository.MessageRepository;
import hospitalSystem.example.Hospital.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService implements IMessageService {
    private  final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    //send new message
    public MessageResponseDto sendMessage(MessageRequestDto messageRequestDto) {
        User Sender = userRepository.findById(messageRequestDto.getSenderId())
                .orElseThrow(()->new RuntimeException("Sender not found"));
        User Receiver = userRepository.findById(messageRequestDto.getReceiverId())
                .orElseThrow(()->new RuntimeException("Receiver not found"));
        Message message = MessageMapper.toEntity(messageRequestDto,Sender,Receiver);
        Message saved = messageRepository.save(message);
        return MessageMapper.toResponseDto(saved);
    }

    @Override
    // Get conversation history (both directions(Doctor,patient))
    public List<MessageResponseDto> getConversation(Long senderId,Long receiverId) {
        List<Message>messages = messageRepository.findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(senderId, receiverId, senderId, receiverId);
        return messages.stream()
                .map(MessageMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    // Get inbox (all messages received by a user)
    public List<MessageResponseDto> getAllReceivedMessages(Long receiverId) {
        return messageRepository.findByReceiverId(receiverId).stream()
                .map(MessageMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    //Get outbox (all messages sent by a user)
    public List<MessageResponseDto> getAllSentMessages(Long senderId) {
        return messageRepository.findBySenderId(senderId).stream()
                .map(MessageMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
