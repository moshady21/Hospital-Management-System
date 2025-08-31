package hospitalSystem.example.Hospital.service.prescription;

import hospitalSystem.example.Hospital.dto.request.PrescriptionRequestDto;
import hospitalSystem.example.Hospital.dto.response.PrescriptionResponseDto;
import hospitalSystem.example.Hospital.entity.Medicine;
import hospitalSystem.example.Hospital.entity.MedicineDetail;
import hospitalSystem.example.Hospital.entity.Prescription;
import hospitalSystem.example.Hospital.entity.User;
import hospitalSystem.example.Hospital.exception.ResourceNotFoundException;
import hospitalSystem.example.Hospital.mapper.MedicineDetailMapper;
import hospitalSystem.example.Hospital.mapper.PrescriptionMapper;
import hospitalSystem.example.Hospital.repository.MedicineRepository;
import hospitalSystem.example.Hospital.repository.PrescriptionRepository;
import hospitalSystem.example.Hospital.repository.UserRepository;
import hospitalSystem.example.Hospital.service.medicine.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescriptionService implements IPrescriptionService{
    private final PrescriptionRepository prescriptionRepository;
    private final UserRepository repository;
    private final UserRepository userRepository;
    private final MedicineRepository medicineRepository;

    @Override
    public PrescriptionResponseDto createPrescription(PrescriptionRequestDto prescriptionRequestDto, String doctorEmail) {
        User doctor = userRepository.findByEmail(doctorEmail).orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        User patient = userRepository.findById(prescriptionRequestDto.getPatientId()).orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        List<MedicineDetail> medicineDetails =  prescriptionRequestDto.getMedicineDetails().stream().map(dto -> {
            Medicine medicine = medicineRepository.findById(dto.getMedicineId()).orElseThrow(() -> new ResourceNotFoundException("Medicine not found"));
            return MedicineDetailMapper.toEntity(dto, medicine);
        }
        ).collect(Collectors.toList());

        Prescription prescription = PrescriptionMapper.toEntity(prescriptionRequestDto, doctor , patient, medicineDetails);

        prescriptionRepository.save(prescription);

        return PrescriptionMapper.toDto(prescription);
    }

    @Override
    public PrescriptionResponseDto getPrescription(Long id, String patientEmail) {
        Prescription prescription = prescriptionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Prescription not found"));

        if(!Objects.equals(patientEmail, prescription.getPatient().getEmail())){
            throw new IllegalArgumentException("You are not allowed to view this prescription");
        }
        return PrescriptionMapper.toDto(prescription);
    }

    @Override
    public List<PrescriptionResponseDto> getAllPrescription() {
        List<Prescription> prescriptions = prescriptionRepository.findAll();
        if(prescriptions.isEmpty()){
            throw new ResourceNotFoundException("No Prescription found");
        }

        return prescriptions.stream().map(PrescriptionMapper::toDto).toList();
    }

    @Override
    public List<PrescriptionResponseDto> getAllPatientPrescription(String patientEmail) {
        List<Prescription> patientPrescriptions = prescriptionRepository.findAllPrescriptionsByPatientEmail(patientEmail);
        if(patientPrescriptions.isEmpty()){
            throw new ResourceNotFoundException("No Prescription found");
        }
        return patientPrescriptions.stream().map(PrescriptionMapper::toDto).toList();
    }

    @Override
    public PrescriptionResponseDto updatePrescription(Long id, PrescriptionRequestDto newPrescriptionRequestDto, String doctorEmail) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found"));

        User doctor = userRepository.findByEmail(doctorEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        if (!prescription.getDoctor().getId().equals(doctor.getId())) {
            throw new IllegalArgumentException("You are not allowed to update this prescription");
        }

        User patient = userRepository.findById(newPrescriptionRequestDto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        List<MedicineDetail> updatedDetails = newPrescriptionRequestDto.getMedicineDetails().stream()
                .map(dto -> {
                    Medicine medicine = medicineRepository.findById(dto.getMedicineId())
                            .orElseThrow(() -> new ResourceNotFoundException("Medicine not found"));
                    return MedicineDetailMapper.toEntity(dto, medicine);
                }).collect(Collectors.toList());


        prescription.setMedicineDetails(updatedDetails);
        prescription.setIssuedAt(newPrescriptionRequestDto.getIssuedAt());

        Prescription updatedPrescription = prescriptionRepository.save(prescription);

        return PrescriptionMapper.toDto(updatedPrescription);
    }

}
