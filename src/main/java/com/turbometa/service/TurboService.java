package com.turbometa.service;

import com.turbometa.model.Turbo;
import com.turbometa.repository.TurboRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TurboService {

    private final TurboRepository turboRepository;

    public TurboService(TurboRepository turboRepository) {
        this.turboRepository = turboRepository;
    }

    public Turbo findByRef(String ref) {
        return turboRepository.findByRef(ref);
    }

    public List<Turbo> findAll() {
        return turboRepository.findAll();
    }

    public List<Turbo> search(String query) {
        if (query == null || query.isBlank()) {
            return findAll();
        }
        return turboRepository.search(query.trim());
    }

    @Transactional
    public void create(Turbo turbo) {
        validateTurbo(turbo);

        if (turboRepository.exists(turbo.getRef())) {
            throw new IllegalArgumentException("Turbo reference already exists: " + turbo.getRef());
        }

        turboRepository.insert(turbo);
    }

    @Transactional
    public void update(Turbo turbo) {
        validateTurbo(turbo);
        turboRepository.update(turbo);
    }

    @Transactional
    public void delete(String ref) {
        turboRepository.delete(ref);
    }

    private void validateTurbo(Turbo turbo) {
        if (turbo.getRef() == null || turbo.getRef().isBlank()) {
            throw new IllegalArgumentException("Reference is required");
        }
        if (turbo.getBrand() == null || turbo.getBrand().isBlank()) {
            throw new IllegalArgumentException("Brand is required");
        }
        if (turbo.getModel() == null || turbo.getModel().isBlank()) {
            throw new IllegalArgumentException("Model is required");
        }
    }
}
