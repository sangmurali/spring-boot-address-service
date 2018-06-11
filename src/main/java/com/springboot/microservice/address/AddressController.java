package com.springboot.microservice.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class AddressController {

    @Autowired
    private Environment environment;

    @Autowired
    private AddressRepository repository;

    @GetMapping("/address/{id}")
    public Address getAddress(@PathVariable Long id) {

        Optional<Address> addressOpt = repository.findById(id);

        if (addressOpt.isPresent()) {
            Address address = addressOpt.get();
            address.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
            return address;
        }
        return null;//todo throw 404 not found
    }

    @GetMapping("/address")
    @ResponseBody
    public List<Address> getAddressByCustomer(@RequestParam("customerId") Long customerId) {
        List<Address> addresses = repository.findByCustomerId(customerId);
        for (Address address : addresses) {
            address.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
        }

        return addresses;
    }

    @GetMapping("/address/list")
    @ResponseBody
    public List<Address> getAll() {
        List<Address> addresses = (List<Address>) repository.findAll();
        for (Address address : addresses) {
            address.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
        }

        return addresses;
    }

    @PostMapping("/address")
    @ResponseBody
    public Long saveAddress(@Valid @RequestBody Address address) {

        Address savedAddr = repository.save(address);

        savedAddr.setPort(Integer.parseInt(environment.getProperty("local.server.port")));

        return savedAddr.getId();
    }

    @PutMapping("/address")
    public Long updateAddress(@RequestBody Address address) {

        return saveAddress(address);
    }
}