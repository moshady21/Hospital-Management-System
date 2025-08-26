package hospitalSystem.example.Hospital.controller;

import hospitalSystem.example.Hospital.dto.request.MessageRequestDto;
import hospitalSystem.example.Hospital.dto.response.MessageResponseDto;
import hospitalSystem.example.Hospital.service.messaging.IMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessagingController {
    private final IMessageService messageService;
    @PostMapping()
    public ResponseEntity<MessageResponseDto>sendMessage(@Valid @RequestBody MessageRequestDto messageRequestDto)
    {
        return ResponseEntity.ok(messageService.sendMessage(messageRequestDto));
    }

    // Get full conversation between doctor & patient
    // Shows all messages exchanged between user 1 and user 2.
    @GetMapping("/conversation/{senderId}/{receiverId}")
    public ResponseEntity<List<MessageResponseDto>> getConversation(
            @PathVariable Long senderId,
            @PathVariable Long receiverId) {
        return ResponseEntity.ok(messageService.getConversation(senderId, receiverId));
    }

    // Get inbox (all messages received by a user)
    //Shows all messages where user with ID=? is the receiver
    //so to get my inbox(all messages that I received) put myId in receivedId
    @GetMapping("/inbox/{receiverId}")
    public ResponseEntity<List<MessageResponseDto>> getInbox(@PathVariable Long receiverId) {
        return ResponseEntity.ok(messageService.getAllReceivedMessages(receiverId));
    }

    // Get outbox (all messages sent by a user)
    //Shows all messages where user with ID=? is the sender
    //so to get my outbox(all messages that I sent ) put myId in senderId
    @GetMapping("/outbox/{senderId}")
    public ResponseEntity<List<MessageResponseDto>> getOutbox(@PathVariable Long senderId) {
        return ResponseEntity.ok(messageService.getAllSentMessages(senderId));
    }

}
