package MockBank.Services;

import MockBank.Assemblers.CustomerResourceAssembler;
import MockBank.ExceptionModels.CustomerNotFoundException;
import MockBank.PersistanceModels.Customer;
import MockBank.PersistanceModels.CustomerAccount;
import MockBank.Repositories.CustomerAccountRepository;
import MockBank.Repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static MockBank.Utils.IbanGenerator.generateIbanByRegex;

@Slf4j
@Service
public class CustomerService extends ResourceSupport {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerResourceAssembler customerResourceAssembler;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    public Customer createCustomerAccount(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        CustomerAccount customerAccount = new CustomerAccount(generateIbanByRegex(), 0L);
        customer.getAccounts().add(customerAccount);
        return customer;
    }

    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public CustomerAccount getCustomerAccount(Customer customer, Integer accountId) throws Exception {
        int accountAmount = customer.getAccounts().size();
        if (accountAmount < accountId) {
            log.warn("Could not find account for this customer by specified id");
            throw new Exception("Could not find account for this customer by specified id");
        }
        return customer.getAccounts().get(accountId - 1);
    }

    public List<Resource<Customer>> getAllCustomers() {
        List<Resource<Customer>> allCustomers = customerRepository.findAll().stream()
                .map(customerResourceAssembler::toResource).collect(Collectors.toList());
        return allCustomers;
    }

    public CustomerAccount getCustomersBalance(String accountNumber) throws Exception {
        CustomerAccount customerAccount;
        try {
            customerAccount = customerAccountRepository.findByAccountNumber(accountNumber);
        } catch (Exception e) {
            log.warn("Could not find account number");
            throw new Exception("Could not find account number");
        }
        return customerAccount;
    }
}
