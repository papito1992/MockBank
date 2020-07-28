package MockBank.Services;

import MockBank.Assemblers.CustomerResourceAssembler;
import MockBank.PersistanceModels.Customer;
import MockBank.PersistanceModels.CustomerAccount;
import MockBank.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

import static MockBank.Utils.CustomerEmailValidator.isValidEmail;
import static MockBank.Utils.CustomerPasswordValidator.isPasswordValid;
import static MockBank.Utils.HashPassword.hashPasswordSha512;
import static MockBank.Utils.IbanGenerator.generateIbanByRegex;

@Service
public class AccountService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerResourceAssembler customerResourceAssembler;

    public void customerSignUp(@RequestBody Customer customer) throws Exception {
        if (isValidEmail(customer.getEmail()) && isPasswordValid(customer.getPassword())) {
            customer.setPassword(hashPasswordSha512(customer.getPassword()));
            String randomStr = generateIbanByRegex();
            CustomerAccount customerAccount = new CustomerAccount(randomStr, 0L);
            List<CustomerAccount> accountList = new ArrayList<>();
            accountList.add(customerAccount);
            customer.setAccounts(accountList);
        } else {
            throw new Exception("Email format invalid or password invalid, Min psw length is 8");
        }

    }
}
