package MockBank.Repositories;

import MockBank.PersistanceModels.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Long>, JpaSpecificationExecutor<Transactions> {
    List<Transactions> findByCustomerId(Long id);

    List<Transactions> findTransactionsByIdAndCreatedBetweenOrderByCreated(Long id, Date start, Date end);
}
