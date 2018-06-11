package com.springboot.microservice.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends
        CrudRepository<Address, Long> {
    @Query(value = "SELECT * FROM address a where a.customer_id = :id",
            nativeQuery = true    )
    List<Address> findByCustomerId(@Param("id") Long id);
}