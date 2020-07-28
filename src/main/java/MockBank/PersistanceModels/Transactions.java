package MockBank.PersistanceModels;

import MockBank.Utils.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "Transactions")
@Table(name = "transactions")
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_gen")
    @SequenceGenerator(name = "customer_id_gen", sequenceName = "customer_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private Long customerId;
    private String paymentDescription;
    private Long transactionAmount;
    private TransactionStatus transactionStatus;
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date created;
    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date updated;

    public Transactions(Long customerId, String paymentDescription, Long transactionAmount, TransactionStatus transactionStatus) {
        this.customerId = customerId;
        this.paymentDescription = paymentDescription;
        this.transactionAmount = transactionAmount;
        this.transactionStatus = transactionStatus;

    }

    public Transactions() {
    }

}
