package com.example.library.repository;

import com.example.library.model.HoldRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoldRequestRepository extends JpaRepository<HoldRequest, Long> {
    List<HoldRequest> findByBookIdOrderByRequestDateAsc(Long bookId);
}
