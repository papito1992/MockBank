package MockBank.Assemblers;

import MockBank.Controllers.CustomerController;
import MockBank.PersistanceModels.Customer;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CustomerResourceAssembler implements ResourceAssembler<Customer, Resource<Customer>> {
    //Might be better solution to implement swagger if there's time
    @Override
    public Resource<Customer> toResource(Customer customer) {
            return new Resource<>(customer,
                    linkTo(methodOn(CustomerController.class).getCustomerById(customer.getId())).withSelfRel()
            );


    }

}
