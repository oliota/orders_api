package com.rubem.oliota.api2023.pt.repository;

import com.rubem.oliota.api2023.pt.model.UserSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing user system entities.
 */
public interface UserSystemRepository extends JpaRepository<UserSystem, Long> {
}