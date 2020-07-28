package MockBank.Controllers;

import MockBank.Assemblers.CustomerResourceAssembler;
import MockBank.PersistanceModels.Customer;
import MockBank.PersistanceModels.CustomerAccount;
import MockBank.PersistanceModels.Transactions;
import MockBank.Repositories.CustomerRepository;
import MockBank.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerResourceAssembler customerResourceAssembler;

    @Autowired
    private CustomerService customerService;

    public CustomerController() {

    }

    @PostMapping("/customer/account/{customerId}")
    public Customer newCustomerAccount(@PathVariable Long customerId) {
        //create additional account number for an existing user
        Customer customer = customerService.createCustomerAccount(customerId);
        customerRepository.save(customer);
        return customer;
    }

    @GetMapping("/customer/{id}")
    public Resource<Customer> getCustomerById(@PathVariable Long id) {
        //customer overview by customer id
        Customer customer = customerService.getCustomer(id);
        return customerResourceAssembler.toResource(customer);
    }

    @GetMapping("/customer/{id}/account/{accountId}/balance")
    public CustomerAccount getCustomerAccountBalanceById1(@PathVariable Long id, @PathVariable Integer accountId) throws Exception {
        //chose accountId instead of actual iban(LT...) for safety.
        //receive balance of existing customers specific account
        Customer customer = customerService.getCustomer(id);
        CustomerAccount customerAccount = customerService.getCustomerAccount(customer, accountId);
        customerAccount.setTransactions(null);
        return customerAccount;
    }

    @GetMapping("/customer/{id}/account/{accountId}/statement")
    public List<Transactions> getCustomerAccountStatementById(@PathVariable Long id, @PathVariable Integer accountId) throws Exception {
        //receive statement of existing customers specific account
        Customer customer = customerService.getCustomer(id);
        CustomerAccount customerAccount = customerService.getCustomerAccount(customer, accountId);
        return customerAccount.getTransactions();
    }


}
