package hospitalSystem.example.Hospital.service;

import hospitalSystem.example.Hospital.dto.response.UserResponseDto;
import hospitalSystem.example.Hospital.entity.User;
import hospitalSystem.example.Hospital.exception.ResourceNotFoundException;
import hospitalSystem.example.Hospital.mapper.UserMapper;
import hospitalSystem.example.Hospital.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDto> getAll(){
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto getById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));
        return userMapper.toResponseDto(user);
    }

    @Transactional
    public void deleteById(Long id){
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("User not found with id : " + id);
        }
        userRepository.deleteById(id);
    }
}
