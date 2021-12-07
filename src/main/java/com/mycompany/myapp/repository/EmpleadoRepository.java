package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Empleado entity.
 * Clase encargada de acceder a la base de datos mediante hibernate a la tabla Empleado.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    @Query(
        value = "select empleado from Empleado empleado where empleado.id like concat('%',:id,'%')" +
        "and empleado.nombre like concat('%',:nombre,'%')" +
        "and empleado.apellidos like concat('%',:apellidos,'%')" +
        "and empleado.dni like concat('%',:dni,'%')"
    )
    Page<Empleado> getEmployeesByFilter(
        @Param("id") Long id,
        @Param("nombre") String nombre,
        @Param("apellidos") String apellidos,
        @Param("dni") String dni,
        Pageable pageable
    );

    @Query(
        value = "select empleado from Empleado empleado where empleado.nombre like concat('%',:nombre,'%')" +
        "and empleado.apellidos like concat('%',:apellidos,'%')" +
        "and empleado.dni like concat('%',:dni,'%')"
    )
    Page<Empleado> getEmployeesByFilter(
        @Param("nombre") String nombre,
        @Param("apellidos") String apellidos,
        @Param("dni") String dni,
        Pageable pageable
    );
}
