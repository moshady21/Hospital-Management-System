package hospitalSystem.example.Hospital.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "medicines")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 20)
    @Column(nullable = false, length = 20)
    private String name;

    @NotNull(message = "Price is required")
    private double price;

    @NotNull(message = "Stock quantity is required")
    private int stockQuantity;

    private String description;

    @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "medicine",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicineDetail> medicineDetails;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id")
    private User user;
}
