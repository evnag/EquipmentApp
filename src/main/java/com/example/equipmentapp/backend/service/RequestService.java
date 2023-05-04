package com.example.equipmentapp.backend.service;

import com.example.equipmentapp.backend.entity.Request;
import com.example.equipmentapp.backend.repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {
    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<Request> findALL() {
        return requestRepository.findAll();
    }

    public long count() {
        return requestRepository.count();
    }

    public void save(Request request) {
        if (request == null) {
            return;
        }
        requestRepository.save(request);
    }
}
