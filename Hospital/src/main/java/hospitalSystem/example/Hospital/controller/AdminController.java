package hospitalSystem.example.Hospital.controller;

import hospitalSystem.example.Hospital.dto.response.UserResponseDto;
import hospitalSystem.example.Hospital.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        return ResponseEntity.ok(adminService.getAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        adminService.deleteById(id);
        return ResponseEntity.ok("user with id " + id + " has been deleted");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get/user/{id}")
    public ResponseEntity<UserResponseDto>getUser(@PathVariable Long id){
        return ResponseEntity.ok(adminService.getById(id));
    }
}
