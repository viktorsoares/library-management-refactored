package com.example.library.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface ClerkRepository extends JpaRepository<Clerk, Long> {}