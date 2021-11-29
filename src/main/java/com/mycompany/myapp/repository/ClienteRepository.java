package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Cliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query(
        value = "select cliente from Cliente cliente where cliente.id like concat('%',:id,'%')" +
        "and cliente.nombre like concat('%',:nombre,'%')" +
        "and cliente.apellidos like concat('%',:apellidos,'%')" +
        "and cliente.dni like concat('%',:dni,'%')"
    )
    Page<Cliente> getClientsByFilter(
        @Param("id") Long id,
        @Param("nombre") String nombre,
        @Param("apellidos") String apellidos,
        @Param("dni") String dni,
        Pageable pageable
    );

    @Query(
        value = "select cliente from Cliente cliente where cliente.nombre like concat('%',:nombre,'%')" +
        "and cliente.apellidos like concat('%',:apellidos,'%')" +
        "and cliente.dni like concat('%',:dni,'%')"
    )
    Page<Cliente> getClientsByFilter(
        @Param("nombre") String nombre,
        @Param("apellidos") String apellidos,
        @Param("dni") String dni,
        Pageable pageable
    );
}
