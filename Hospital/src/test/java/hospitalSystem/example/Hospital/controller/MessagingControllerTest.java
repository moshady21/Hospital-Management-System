package hospitalSystem.example.Hospital.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hospitalSystem.example.Hospital.dto.request.MessageRequestDto;
import hospitalSystem.example.Hospital.entity.User;
import hospitalSystem.example.Hospital.entity.UserRole;
import hospitalSystem.example.Hospital.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
@Rollback
class MessagingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    private User doctor;
    private User patient;
    @BeforeEach
    void setup()
    {
        // create Doctor
        doctor = new User();
        doctor.setName("Dr. Ahmed");
        doctor.setEmail("ahmed@hospital.com");
        doctor.setPassword("doctor123");
        doctor.setUserRole(UserRole.DOCTOR);
        userRepository.save(doctor);

        // create Patient
        patient = new User();
        patient.setName("Salma");
        patient.setEmail("salma@hospital.com");
        patient.setPassword("patient123");
        patient.setUserRole(UserRole.PATIENT);
        userRepository.save(patient);
    }

    @Test
    void sendMessage()throws Exception {
        MessageRequestDto dto = new MessageRequestDto();
        dto.setSenderId(doctor.getId());
        dto.setReceiverId(patient.getId());
        dto.setContent("Hello Salma, how are you?");


        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.content").value("Hello Salma, how are you?"))
                .andExpect(jsonPath("$.senderName").value("Dr. Ahmed"))
                .andExpect(jsonPath("$.receiverName").value("Salma"));
    }

    @Test
    void getConversation() throws Exception {
        //send one message doctor to patient
        MessageRequestDto dto =new MessageRequestDto();
        dto.setSenderId(doctor.getId());
        dto.setReceiverId(patient.getId());
        dto.setContent("First Message");
        mockMvc.perform(post("/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));
        // Send one message patient to doctor
        MessageRequestDto dto2 = new MessageRequestDto();
        dto2.setSenderId(patient.getId());
        dto2.setReceiverId(doctor.getId());
        dto2.setContent("Reply from patient");
        mockMvc.perform(post("/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto2)));
        //fetch conversation
        mockMvc.perform(get("/messages/conversation/"+doctor.getId()+"/"+patient.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getInbox() throws Exception {
        // Send message to patient
        MessageRequestDto dto = new MessageRequestDto();
        dto.setSenderId(doctor.getId());
        dto.setReceiverId(patient.getId());
        dto.setContent("Inbox test");
        mockMvc.perform(post("/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        // Get inbox of patient
        mockMvc.perform(get("/messages/inbox/" + patient.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].receiverName").value("Salma"));
    }

    @Test
    void getOutbox() throws Exception {
        // Send message from doctor
        MessageRequestDto dto = new MessageRequestDto();
        dto.setSenderId(doctor.getId());
        dto.setReceiverId(patient.getId());
        dto.setContent("Outbox test");
        mockMvc.perform(post("/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        // Get outbox of doctor
        mockMvc.perform(get("/messages/outbox/" + doctor.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].senderName").value("Dr. Ahmed"));
    }
}
