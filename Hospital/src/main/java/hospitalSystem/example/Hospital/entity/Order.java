package hospitalSystem.example.Hospital.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Price is required")
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private OrderStatus status;

    @NotNull(message = "Order date is required")
    private LocalDate orderDate;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
    private List<OrderItem> orderItems;
}
