package MockBank.Services;

import MockBank.ExceptionModels.CustomerNotFoundException;
import MockBank.PersistanceModels.Customer;
import MockBank.PersistanceModels.CustomerAccount;
import MockBank.PersistanceModels.Transactions;
import MockBank.Repositories.CustomerRepository;
import MockBank.Repositories.TransactionRepository;
import MockBank.Utils.TransactionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static MockBank.Utils.DateFormater.convertStringToDate;

@Slf4j
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<Transactions> getTransactionsByDateRange(Long customerId, String start, String end) throws Exception {
        Date dateFrom = convertStringToDate(start);
        Date dateTo = convertStringToDate(end);
        List<Transactions> transactionsList;
        try {
            transactionsList = transactionRepository.
                    findTransactionsByIdAndCreatedBetweenOrderByCreated(customerId, dateFrom, dateTo);
        } catch (Exception e) {
            log.warn("Could not retrieve customer transactions with id = {}, error :", customerId);
            e.printStackTrace();
            throw new Exception("Could not retrieve customer transactions with id = {}");
        }
        return transactionsList;

    }

    public List<Transactions> getTransactions(Long id) throws Exception {
        List<Transactions> transactions;
        try {
            transactions = transactionRepository.findByCustomerId(id);
        } catch (Exception e) {
            log.warn("Could not retrieve user transactions with id = {}, error :", id);
            e.printStackTrace();
            throw new Exception("Could not retrieve customer transactions");
        }
        return transactions;
    }

    public Customer depositToCustomerAccount(CustomerAccount customerAccount, Long id, Long amount) throws Exception {
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        Transactions transactions;
        List<CustomerAccount> matchedCustomerAccount = existingCustomer.getAccounts().stream()
                .filter(cAccNr -> customerAccount.getAccountNumber().equals(cAccNr.getAccountNumber())).collect(Collectors.toList());
        if (matchedCustomerAccount.size() > 1) {
            transactions = new Transactions(existingCustomer.getId(), "deposit", amount, TransactionStatus.TRANSACTION_STATUS_REJECTED);
            matchedCustomerAccount.get(0).getTransactions().add(transactions);
            transactionRepository.save(transactions);
            customerRepository.save(existingCustomer);
            log.warn("Multiples of account number exist: {}", customerAccount.getAccountNumber());
            throw new Exception("Multiples of account number exist");
        } else if (matchedCustomerAccount.size() == 0) {
            log.warn("Account not found for this customer: {}", customerAccount.getAccountNumber());
            throw new Exception("Account not found for this customer");
        }

        matchedCustomerAccount.get(0).setAccountBalance(matchedCustomerAccount.get(0).getAccountBalance() + amount);
        transactions = new Transactions(existingCustomer.getId(), "deposit", amount, TransactionStatus.TRANSACTION_STATUS_PROCESSED);
        matchedCustomerAccount.get(0).getTransactions().add(transactions);
        transactionRepository.save(transactions);
        customerRepository.save(existingCustomer);
        return existingCustomer;
    }

    public Customer withdrawFromCustomerAccount(CustomerAccount customerAccount, Long id, Long amount) throws Exception {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        Transactions transactions;
        List<CustomerAccount> matchedCustomerAccount = customer.getAccounts().stream()
                .filter(cAccNr -> customerAccount.getAccountNumber().equals(cAccNr.getAccountNumber())).collect(Collectors.toList());
        if (matchedCustomerAccount.size() == 0) {
            log.warn("Account number not found = {}", customerAccount.getAccountNumber());
            throw new Exception("Account number not found");
        }
        if (matchedCustomerAccount.size() > 1) {
            log.warn("Multiples of account number exist: {}", customerAccount.getAccountNumber());
            transactions = new Transactions(customer.getId(), "withdrawal", amount, TransactionStatus.TRANSACTION_STATUS_REJECTED);
            matchedCustomerAccount.get(0).getTransactions().add(transactions);
            customerRepository.save(customer);
            throw new Exception("Multiples of account number exist");
        }
        Long currentBalance = matchedCustomerAccount.get(0).getAccountBalance();
        if (currentBalance - amount < 0) {
            transactions = new Transactions(customer.getId(), "withdrawal", amount, TransactionStatus.TRANSACTION_STATUS_REJECTED);
            matchedCustomerAccount.get(0).getTransactions().add(transactions);
            customerRepository.save(customer);
            log.warn("Withdraw amount exceeds balance for this account = {}", customerAccount.getAccountNumber());
            throw new Exception("Withdraw amount exceeds balance for this account");
        }
        matchedCustomerAccount.get(0).setAccountBalance(matchedCustomerAccount.get(0).getAccountBalance() - amount);
        transactions = new Transactions(customer.getId(), "withdrawal", amount, TransactionStatus.TRANSACTION_STATUS_PROCESSED);
        matchedCustomerAccount.get(0).getTransactions().add(transactions);
        customerRepository.save(customer);
        return customer;
    }
}
