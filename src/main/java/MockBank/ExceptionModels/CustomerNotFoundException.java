package MockBank.ExceptionModels;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException {
    //Refactor exception handling to return status code not just 500 error if there will be time for other error cases.
    public CustomerNotFoundException(Long id) {
        super("Could not find Customer with id: " + id);
    }
}
