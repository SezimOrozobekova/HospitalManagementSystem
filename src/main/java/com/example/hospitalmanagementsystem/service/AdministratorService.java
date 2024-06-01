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

    public List<Administrator> getAllAdministrators() {
        try {
            return administratorRepository.findAll();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to retrieve administrators", ex);
        }
    }

    public Optional<Administrator> getAdministratorById(Long id) {
        return Optional.ofNullable(administratorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Administrator with ID " + id + " not found")));
    }

    public Optional<Administrator> getAdministratorByUsername(String username) {
        return Optional.ofNullable(administratorRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Administrator with username " + username + " not found")));
    }

    public Administrator createAdministrator(Administrator administrator) {
        try {
            return administratorRepository.save(administrator);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create administrator", ex);
        }
    }

    public Administrator updateAdministrator(Long id, Administrator updatedAdministrator) {
        return administratorRepository.findById(id)
                .map(existingAdministrator -> {
                    updatedAdministrator.setId(existingAdministrator.getId());
                    return administratorRepository.save(updatedAdministrator);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Administrator with ID " + id + " not found"));
    }

    public void deleteAdministrator(Long id) {
        administratorRepository.findById(id).ifPresentOrElse(
                administratorRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("Administrator with ID " + id + " not found");
                });
    }
}
