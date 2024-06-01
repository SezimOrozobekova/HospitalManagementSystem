package com.example.hospitalmanagementsystem.repository;

import com.example.hospitalmanagementsystem.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AdministratorRepository extends JpaRepository<Administrator,Long> {
    Optional<Administrator> findByUsername(String username);
}
