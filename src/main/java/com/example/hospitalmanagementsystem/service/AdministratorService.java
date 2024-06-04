package com.example.hospitalmanagementsystem.service;

import com.example.hospitalmanagementsystem.entity.Administrator;
import com.example.hospitalmanagementsystem.exception.ResourceNotFoundException;
import com.example.hospitalmanagementsystem.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratorService {

    private final AdministratorRepository administratorRepository;

    @Autowired
    public AdministratorService(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    public Optional<Administrator> getAdministratorByUsernameAndPassword(String username, String password) {
        return administratorRepository.findByUsernameAndPassword(username, password);
    }

}
