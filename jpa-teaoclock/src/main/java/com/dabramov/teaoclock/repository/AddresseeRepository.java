package com.dabramov.teaoclock.repository;

import com.dabramov.teaoclock.entity.Addressee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddresseeRepository extends JpaRepository<Addressee, Long> {
}
