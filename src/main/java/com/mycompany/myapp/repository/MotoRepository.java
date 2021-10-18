package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Moto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Moto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MotoRepository extends JpaRepository<Moto, Long> {}
