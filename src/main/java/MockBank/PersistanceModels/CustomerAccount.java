package MockBank.PersistanceModels;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "CustomerAccount")
@Data
@Table(name = "customer_account")
public class CustomerAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_account_id_gen")
    @SequenceGenerator(name = "customer_account_id_gen", sequenceName = "customer_account_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private String accountNumber;
    private Long accountBalance;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Transactions> transactions = new LinkedList<>();


    public CustomerAccount(String accountNumber, Long accountBalance) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
    }

    public CustomerAccount() {

    }

}
