package MockBank.Controllers;


import MockBank.Assemblers.CustomerResourceAssembler;
import MockBank.PersistanceModels.Customer;
import MockBank.Repositories.CustomerRepository;
import MockBank.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class AccountController extends ResourceSupport {
    private final CustomerRepository customerRepository;
    private final CustomerResourceAssembler customerResourceAssembler;

    @Autowired
    private AccountService accountService;
    public AccountController(
            CustomerRepository customerRepository,
            CustomerResourceAssembler customerResourceAssembler) {
        this.customerRepository = customerRepository;
        this.customerResourceAssembler = customerResourceAssembler;
    }

    @PostMapping("/register")
    public ResponseEntity<?> newCustomer(@RequestBody Customer customer) throws Exception {
        accountService.customerSignUp(customer);
        Resource<Customer> resource = customerResourceAssembler.toResource(customerRepository.save(customer));
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

}
