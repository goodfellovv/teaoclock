package com.dabramov.teaoclock.repository;

import com.dabramov.teaoclock.entity.Addressee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddresseeRepository extends MongoRepository<Addressee, String> {
}
