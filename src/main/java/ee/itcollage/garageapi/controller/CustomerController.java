package ee.itcollage.garageapi.controller;


import ee.itcollage.garageapi.model.Customer;
import ee.itcollage.garageapi.repository.CustomerRepository;
import ee.itcollage.garageapi.server.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @GetMapping("{id}")
    public Customer findById(@PathVariable Long id) {
        Optional<Customer> byId = customerRepository.findById(id);
        return byId.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "id doesnt exist"));
    }

    @PostMapping
    public Customer save(@RequestBody Customer customer) {
        customer.setId(null);
        return customerRepository.save(customer);
    }

    @PutMapping("{id}")
    public Customer update(@PathVariable Long id, @RequestBody Customer customer) {
        Customer byId = findById(id);
        byId.setFirstName(customer.getFirstName());
        byId.setLastName(customer.getLastName());
        return customerRepository.save(byId);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        Customer byId = findById(id);
        customerRepository.delete(byId);
    }

    @DeleteMapping("/v2/{id}")
    public void delete2(@PathVariable Long id) {
        customerRepository.deleteById(id);
    }

    //todo
    // * do you understand what's going on?
    // * connect to database
    // url: jdbc:h2:~/test;AUTO_SERVER=TRUE
    // username: test password:test
    // * create methods GET methods for findById and findByLastName
    // * what to do if database doesn't have Customer by that id or last name
    // * create another database entity
    // * fill that entity with data
    // * create controller, getmapping and service for that entity

}
