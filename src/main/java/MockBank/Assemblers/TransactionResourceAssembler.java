package MockBank.Assemblers;

import MockBank.Controllers.TransactionController;
import MockBank.PersistanceModels.Transactions;
import lombok.SneakyThrows;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class TransactionResourceAssembler implements ResourceAssembler<Transactions, Resource<Transactions>> {
    @SneakyThrows
    @Override
    public Resource<Transactions> toResource(Transactions transactions) {

        return new Resource<>(transactions,
                linkTo(methodOn(TransactionController.class).getCustomerTransactionsById(transactions.getId())).withSelfRel()
        );

    }

}
