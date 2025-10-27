package com.example.library.repository;

import com.example.library.model.HoldRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface HoldRequestRepository extends JpaRepository<HoldRequest, Long> {
    List<HoldRequest> findByBookIdOrderByRequestDateAsc(Long bookId);
}
