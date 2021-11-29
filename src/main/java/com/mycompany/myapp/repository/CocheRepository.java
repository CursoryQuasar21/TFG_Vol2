package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Coche;
import java.time.Instant;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Coche entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CocheRepository extends JpaRepository<Coche, Long> {
    @Query("select coche from Coche coche where coche.venta.id =:ventaId")
    Set<Coche> getCochesByVentaId(@Param("ventaId") Long ventaId);

    @Modifying
    @Query("update Coche c set c.venta = null")
    void updateAllCocheDeleteVenta();

    @Modifying
    @Query("update Coche c set c.venta.id =:ventaId where c.id =:cocheId")
    void updateCocheSaveVentaByVentaIdAndCocheId(@Param("ventaId") Long ventaId, @Param("cocheId") Long cocheId);

    @Modifying
    @Query("update Coche c set c.venta = null where c.id =:cocheId")
    void updateCocheDeleteVentaByCocheId(@Param("cocheId") Long cocheId);

    @Modifying
    @Query("update Coche c set c.venta = null where c.venta.id =:ventaId")
    void updateCocheDeleteVentaByVentaId(@Param("ventaId") Long ventaId);

    @Query(
        value = "select coche from Coche coche where " +
        "coche.color like concat('%',:color,'%') " +
        "and coche.modelo like concat('%',:modelo,'%') " +
        "and coche.marca like concat('%',:marca,'%') " +
        "and (coche.anio between :fechaI and :fechaF)"
    )
    Page<Coche> getCarsByFilter(
        @Param("color") String color,
        @Param("modelo") String modelo,
        @Param("marca") String marca,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        Pageable pageable
    );

    @Query(
        value = "select coche from Coche coche where " +
        "coche.color like concat('%',:color,'%') " +
        "and coche.modelo like concat('%',:modelo,'%') " +
        "and coche.marca like concat('%',:marca,'%') " +
        "and (coche.anio between :fechaI and :fechaF)" +
        "and coche.venta.id like concat('%',:venta,'%')"
    )
    Page<Coche> getCarsByFilterAndVenta(
        @Param("color") String color,
        @Param("modelo") String modelo,
        @Param("marca") String marca,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("venta") Long venta,
        Pageable pageable
    );

    @Query(
        value = "select coche from Coche coche where " +
        "coche.color like concat('%',:color,'%') " +
        "and coche.modelo like concat('%',:modelo,'%') " +
        "and coche.marca like concat('%',:marca,'%') " +
        "and (coche.anio between :fechaI and :fechaF)" +
        "and (coche.precio between (select min(precio) from Coche) and :precioF)"
    )
    Page<Coche> getCarsByFilterAndPrecioF(
        @Param("color") String color,
        @Param("modelo") String modelo,
        @Param("marca") String marca,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("precioF") Double precioF,
        Pageable pageable
    );

    @Query(
        value = "select coche from Coche coche where " +
        "coche.color like concat('%',:color,'%') " +
        "and coche.modelo like concat('%',:modelo,'%') " +
        "and coche.marca like concat('%',:marca,'%') " +
        "and (coche.anio between :fechaI and :fechaF)" +
        "and (coche.precio between (select min(precio) from Coche) and :precioF)" +
        "and coche.venta.id like concat('%',:venta,'%')"
    )
    Page<Coche> getCarsByFilterAndPrecioFAndVenta(
        @Param("color") String color,
        @Param("modelo") String modelo,
        @Param("marca") String marca,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("precioF") Double precioF,
        @Param("venta") Long venta,
        Pageable pageable
    );

    @Query(
        value = "select coche from Coche coche where " +
        "coche.color like concat('%',:color,'%') " +
        "and coche.modelo like concat('%',:modelo,'%') " +
        "and coche.marca like concat('%',:marca,'%') " +
        "and (coche.anio between :fechaI and :fechaF)" +
        "and (coche.precio between :precioI and (select max(precio) from Coche))"
    )
    Page<Coche> getCarsByFilterAndPrecioI(
        @Param("color") String color,
        @Param("modelo") String modelo,
        @Param("marca") String marca,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("precioI") Double precioI,
        Pageable pageable
    );

    @Query(
        value = "select coche from Coche coche where " +
        "coche.color like concat('%',:color,'%') " +
        "and coche.modelo like concat('%',:modelo,'%') " +
        "and coche.marca like concat('%',:marca,'%') " +
        "and (coche.anio between :fechaI and :fechaF)" +
        "and (coche.precio between :precioI and (select max(precio) from Coche))" +
        "and coche.venta.id like concat('%',:venta,'%')"
    )
    Page<Coche> getCarsByFilterAndPrecioIAndVenta(
        @Param("color") String color,
        @Param("modelo") String modelo,
        @Param("marca") String marca,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("precioI") Double precioI,
        @Param("venta") Long venta,
        Pageable pageable
    );

    @Query(
        value = "select coche from Coche coche where " +
        "coche.color like concat('%',:color,'%') " +
        "and coche.modelo like concat('%',:modelo,'%') " +
        "and coche.marca like concat('%',:marca,'%') " +
        "and (coche.anio between :fechaI and :fechaF)" +
        "and (coche.precio between :precioI and :precioF)"
    )
    Page<Coche> getCarsByFilterAndPrecios(
        @Param("color") String color,
        @Param("modelo") String modelo,
        @Param("marca") String marca,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("precioI") Double precioI,
        @Param("precioF") Double precioF,
        Pageable pageable
    );

    @Query(
        value = "select coche from Coche coche where " +
        "coche.color like concat('%',:color,'%') " +
        "and coche.modelo like concat('%',:modelo,'%') " +
        "and coche.marca like concat('%',:marca,'%') " +
        "and (coche.anio between :fechaI and :fechaF)" +
        "and (coche.precio between :precioI and :precioF)" +
        "and coche.venta.id like concat('%',:venta,'%')"
    )
    Page<Coche> getCarsByFilterAndPreciosAndVenta(
        @Param("color") String color,
        @Param("modelo") String modelo,
        @Param("marca") String marca,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("precioI") Double precioI,
        @Param("precioF") Double precioF,
        @Param("venta") Long venta,
        Pageable pageable
    );

    @Query(
        value = "select coche from Coche coche where " +
        "coche.id like concat('%',:id,'%')" +
        "and coche.color like concat('%',:color,'%') " +
        "and coche.modelo like concat('%',:modelo,'%') " +
        "and coche.marca like concat('%',:marca,'%') " +
        "and (coche.anio between :fechaI and :fechaF)"
    )
    Page<Coche> getCarsByFilterAndId(
        @Param("id") Long id,
        @Param("color") String color,
        @Param("modelo") String modelo,
        @Param("marca") String marca,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        Pageable pageable
    );

    @Query(
        value = "select coche from Coche coche where " +
        "coche.id like concat('%',:id,'%')" +
        "and coche.color like concat('%',:color,'%') " +
        "and coche.modelo like concat('%',:modelo,'%') " +
        "and coche.marca like concat('%',:marca,'%') " +
        "and (coche.anio between :fechaI and :fechaF)" +
        "and coche.venta.id like concat('%',:venta,'%')"
    )
    Page<Coche> getCarsByFilterAndIdAndVenta(
        @Param("id") Long id,
        @Param("color") String color,
        @Param("modelo") String modelo,
        @Param("marca") String marca,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("venta") Long venta,
        Pageable pageable
    );

    @Query(
        value = "select coche from Coche coche where " +
        "coche.id like concat('%',:id,'%')" +
        "and coche.color like concat('%',:color,'%') " +
        "and coche.modelo like concat('%',:modelo,'%') " +
        "and coche.marca like concat('%',:marca,'%') " +
        "and (coche.anio between :fechaI and :fechaF)" +
        "and (coche.precio between (select min(precio) from Coche) and :precioF)"
    )
    Page<Coche> getCarsByFilterAndIdAndPrecioF(
        @Param("id") Long id,
        @Param("color") String color,
        @Param("modelo") String modelo,
        @Param("marca") String marca,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("precioF") Double precioF,
        Pageable pageable
    );

    @Query(
        value = "select coche from Coche coche where " +
        "coche.id like concat('%',:id,'%')" +
        "and coche.color like concat('%',:color,'%') " +
        "and coche.modelo like concat('%',:modelo,'%') " +
        "and coche.marca like concat('%',:marca,'%') " +
        "and (coche.anio between :fechaI and :fechaF)" +
        "and (coche.precio between (select min(precio) from Coche) and :precioF)" +
        "and coche.venta.id like concat('%',:venta,'%')"
    )
    Page<Coche> getCarsByFilterAndIdAndPrecioFAndVenta(
        @Param("id") Long id,
        @Param("color") String color,
        @Param("modelo") String modelo,
        @Param("marca") String marca,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("precioF") Double precioF,
        @Param("venta") Long venta,
        Pageable pageable
    );

    @Query(
        value = "select coche from Coche coche where " +
        "coche.id like concat('%',:id,'%')" +
        "and coche.color like concat('%',:color,'%') " +
        "and coche.modelo like concat('%',:modelo,'%') " +
        "and coche.marca like concat('%',:marca,'%') " +
        "and (coche.anio between :fechaI and :fechaF)" +
        "and (coche.precio between :precioI and (select max(precio) from Coche))"
    )
    Page<Coche> getCarsByFilterAndIdAndPrecioI(
        @Param("id") Long id,
        @Param("color") String color,
        @Param("modelo") String modelo,
        @Param("marca") String marca,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("precioI") Double precioI,
        Pageable pageable
    );

    @Query(
        value = "select coche from Coche coche where " +
        "coche.id like concat('%',:id,'%')" +
        "and coche.color like concat('%',:color,'%') " +
        "and coche.modelo like concat('%',:modelo,'%') " +
        "and coche.marca like concat('%',:marca,'%') " +
        "and (coche.anio between :fechaI and :fechaF)" +
        "and (coche.precio between :precioI and (select max(precio) from Coche))" +
        "and coche.venta.id like concat('%',:venta,'%')"
    )
    Page<Coche> getCarsByFilterAndIdAndPrecioIAndVenta(
        @Param("id") Long id,
        @Param("color") String color,
        @Param("modelo") String modelo,
        @Param("marca") String marca,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("precioI") Double precioI,
        @Param("venta") Long venta,
        Pageable pageable
    );

    @Query(
        value = "select coche from Coche coche where " +
        "coche.id like concat('%',:id,'%')" +
        "and coche.color like concat('%',:color,'%') " +
        "and coche.modelo like concat('%',:modelo,'%') " +
        "and coche.marca like concat('%',:marca,'%') " +
        "and (coche.anio between :fechaI and :fechaF)" +
        "and (coche.precio between :precioI and :precioF)"
    )
    Page<Coche> getCarsByFilterAndIdAndPrecios(
        @Param("id") Long id,
        @Param("color") String color,
        @Param("modelo") String modelo,
        @Param("marca") String marca,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("precioI") Double precioI,
        @Param("precioF") Double precioF,
        Pageable pageable
    );

    @Query(
        value = "select coche from Coche coche where " +
        "coche.id like concat('%',:id,'%')" +
        "and coche.color like concat('%',:color,'%') " +
        "and coche.modelo like concat('%',:modelo,'%') " +
        "and coche.marca like concat('%',:marca,'%') " +
        "and (coche.anio between :fechaI and :fechaF)" +
        "and (coche.precio between :precioI and :precioF)" +
        "and coche.venta.id like concat('%',:venta,'%')"
    )
    Page<Coche> getCarsByFilterAndIdAndPreciosAndVenta(
        @Param("id") Long id,
        @Param("color") String color,
        @Param("modelo") String modelo,
        @Param("marca") String marca,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("precioI") Double precioI,
        @Param("precioF") Double precioF,
        @Param("venta") Long venta,
        Pageable pageable
    );
}
