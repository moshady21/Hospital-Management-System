package hospitalSystem.example.Hospital.repository;

import hospitalSystem.example.Hospital.entity.Medicine;
import hospitalSystem.example.Hospital.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {

    @Query("SELECT p FROM Prescription p WHERE p.patient.email = :patientEmail")
    List<Prescription> findAllPrescriptionsByPatientEmail(String patientEmail);

}
