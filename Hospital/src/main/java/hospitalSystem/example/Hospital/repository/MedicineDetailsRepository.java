package hospitalSystem.example.Hospital.repository;

import hospitalSystem.example.Hospital.entity.MedicineDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineDetailsRepository extends JpaRepository<MedicineDetail,Long> {
}
