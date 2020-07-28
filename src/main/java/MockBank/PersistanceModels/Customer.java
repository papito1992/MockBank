package MockBank.PersistanceModels;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity(name = "customers")
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_gen")
    @SequenceGenerator(name = "customer_id_gen", sequenceName = "customer_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private String email;
    private String password;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<CustomerAccount> accounts = new LinkedList<>();

    public Customer(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Customer() {
    }
}
