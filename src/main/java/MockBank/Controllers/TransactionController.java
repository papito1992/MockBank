package MockBank.Controllers;

import MockBank.Assemblers.CustomerResourceAssembler;
import MockBank.Assemblers.TransactionResourceAssembler;
import MockBank.PersistanceModels.Customer;
import MockBank.PersistanceModels.CustomerAccount;
import MockBank.PersistanceModels.Transactions;
import MockBank.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private CustomerResourceAssembler customerResourceAssembler;
    @Autowired
    private TransactionResourceAssembler transactionResourceAssembler;

    public TransactionController() {
    }

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions/customer/{customerId}")
    public List<Transactions> transactionsByDateRange(@RequestParam("from") String start, @RequestParam(value = "end") String end, @PathVariable Long customerId) throws Exception {
        //currently useless but for future if time range would be required, NOT required by task
        List<Transactions> dateRangeTransactions = transactionService.getTransactionsByDateRange(customerId, start, end);

        return dateRangeTransactions;
    }

    @GetMapping("/transactions/customer/{id}")
    public List<Transactions> getCustomerTransactionsById(@PathVariable Long id) throws Exception {
        //all transactions for all customer accounts, for future administrative/service desk implementations, NOT required by task
        return transactionService.getTransactions(id);
    }
    @PutMapping("/customer/{id}/deposit/{amount}")
    public Customer depositToAccount(@RequestBody CustomerAccount customerAccount, @PathVariable Long id, @PathVariable Long amount) throws Exception {
        //deposit to specific customer account number
        Customer customer = transactionService.depositToCustomerAccount(customerAccount, id, amount);
        return customer;
    }

    @PutMapping("/customer/{id}/withdraw/{amount}")
    public Customer withdrawFromAccount(@RequestBody CustomerAccount customerAccount, @PathVariable Long id, @PathVariable Long amount) throws Exception {
        //withdraw from specific customer account
        Customer customer = transactionService.withdrawFromCustomerAccount(customerAccount, id, amount);
        return customer;
    }

}
