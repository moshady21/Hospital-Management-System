package hospitalSystem.example.Hospital.repository;

import hospitalSystem.example.Hospital.entity.FollowUp;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FollowUpRepository extends JpaRepository<FollowUp, Long> {
    List<FollowUp> findByPatientIdOrderByFollowUpDateDesc(Long patientId);
}
