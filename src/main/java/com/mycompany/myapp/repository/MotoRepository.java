package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Moto;
import java.time.Instant;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Moto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MotoRepository extends JpaRepository<Moto, Long> {
    @Query("select moto from Moto moto where moto.venta.id =:ventaId")
    Set<Moto> getMotoByVentaId(@Param("ventaId") Long ventaId);

    @Modifying
    @Query("update Moto m set m.venta = null")
    void updateAllMotoDeleteVenta();

    @Modifying
    @Query("update Moto m set m.venta.id =:ventaId where m.id =:motoId")
    void updateMotoSaveVentaByVentaIdAndMotoId(@Param("ventaId") Long ventaId, @Param("motoId") Long motoId);

    @Modifying
    @Query("update Moto m set m.venta = null where m.id =:motoId")
    void updateMotoDeleteVentaByMotoId(@Param("motoId") Long motoId);

    @Modifying
    @Query("update Moto m set m.venta = null where m.venta.id =:ventaId")
    void updateMotoDeleteVentaByVentaId(@Param("ventaId") Long ventaId);

    @Query(
        value = "select moto from Moto moto where " +
        "moto.color like concat('%',:color,'%') " +
        "and moto.modelo like concat('%',:modelo,'%') " +
        "and moto.marca like concat('%',:marca,'%') " +
        "and (moto.anio between :fechaI and :fechaF)"
    )
    Page<Moto> getMotosByFilter(
        @Param("color") String color,
        @Param("modelo") String modelo,
        @Param("marca") String marca,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        Pageable pageable
    );

    @Query(
        value = "select moto from Moto moto where " +
        "moto.color like concat('%',:color,'%') " +
        "and moto.modelo like concat('%',:modelo,'%') " +
        "and moto.marca like concat('%',:marca,'%') " +
        "and (moto.anio between :fechaI and :fechaF)" +
        "and moto.venta.id like concat('%',:venta,'%')"
    )
    Page<Moto> getMotosByFilterAndVenta(
        @Param("color") String color,
        @Param("modelo") String modelo,
        @Param("marca") String marca,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("venta") Long venta,
        Pageable pageable
    );

    @Query(
        value = "select moto from Moto moto where " +
        "moto.color like concat('%',:color,'%') " +
        "and moto.modelo like concat('%',:modelo,'%') " +
        "and moto.marca like concat('%',:marca,'%') " +
        "and (moto.anio between :fechaI and :fechaF)" +
        "and (moto.precio between (select min(precio) from Moto) and :precioF)"
    )
    Page<Moto> getMotosByFilterAndPrecioF(
        @Param("color") String color,
        @Param("modelo") String modelo,
        @Param("marca") String marca,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("precioF") Double precioF,
        Pageable pageable
    );

    @Query(
        value = "select moto from Moto moto where " +
        "moto.color like concat('%',:color,'%') " +
        "and moto.modelo like concat('%',:modelo,'%') " +
        "and moto.marca like concat('%',:marca,'%') " +
        "and (moto.anio between :fechaI and :fechaF)" +
        "and (moto.precio between (select min(precio) from Moto) and :precioF)" +
        "and moto.venta.id like concat('%',:venta,'%')"
    )
    Page<Moto> getMotosByFilterAndPrecioFAndVenta(
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
        value = "select moto from Moto moto where " +
        "moto.color like concat('%',:color,'%') " +
        "and moto.modelo like concat('%',:modelo,'%') " +
        "and moto.marca like concat('%',:marca,'%') " +
        "and (moto.anio between :fechaI and :fechaF)" +
        "and (moto.precio between :precioI and (select max(precio) from Moto))"
    )
    Page<Moto> getMotosByFilterAndPrecioI(
        @Param("color") String color,
        @Param("modelo") String modelo,
        @Param("marca") String marca,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("precioI") Double precioI,
        Pageable pageable
    );

    @Query(
        value = "select moto from Moto moto where " +
        "moto.color like concat('%',:color,'%') " +
        "and moto.modelo like concat('%',:modelo,'%') " +
        "and moto.marca like concat('%',:marca,'%') " +
        "and (moto.anio between :fechaI and :fechaF)" +
        "and (moto.precio between :precioI and (select max(precio) from Moto))" +
        "and moto.venta.id like concat('%',:venta,'%')"
    )
    Page<Moto> getMotosByFilterAndPrecioIAndVenta(
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
        value = "select moto from Moto moto where " +
        "moto.color like concat('%',:color,'%') " +
        "and moto.modelo like concat('%',:modelo,'%') " +
        "and moto.marca like concat('%',:marca,'%') " +
        "and (moto.anio between :fechaI and :fechaF)" +
        "and (moto.precio between :precioI and :precioF)"
    )
    Page<Moto> getMotosByFilterAndPrecios(
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
        value = "select moto from Moto moto where " +
        "moto.color like concat('%',:color,'%') " +
        "and moto.modelo like concat('%',:modelo,'%') " +
        "and moto.marca like concat('%',:marca,'%') " +
        "and (moto.anio between :fechaI and :fechaF)" +
        "and (moto.precio between :precioI and :precioF)" +
        "and moto.venta.id like concat('%',:venta,'%')"
    )
    Page<Moto> getMotosByFilterAndPreciosAndVenta(
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
        value = "select moto from Moto moto where " +
        "moto.id like concat('%',:id,'%')" +
        "and moto.color like concat('%',:color,'%') " +
        "and moto.modelo like concat('%',:modelo,'%') " +
        "and moto.marca like concat('%',:marca,'%') " +
        "and (moto.anio between :fechaI and :fechaF)"
    )
    Page<Moto> getMotosByFilterAndId(
        @Param("id") Long id,
        @Param("color") String color,
        @Param("modelo") String modelo,
        @Param("marca") String marca,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        Pageable pageable
    );

    @Query(
        value = "select moto from Moto moto where " +
        "moto.id like concat('%',:id,'%')" +
        "and moto.color like concat('%',:color,'%') " +
        "and moto.modelo like concat('%',:modelo,'%') " +
        "and moto.marca like concat('%',:marca,'%') " +
        "and (moto.anio between :fechaI and :fechaF)" +
        "and moto.venta.id like concat('%',:venta,'%')"
    )
    Page<Moto> getMotosByFilterAndIdAndVenta(
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
        value = "select moto from Moto moto where " +
        "moto.id like concat('%',:id,'%')" +
        "and moto.color like concat('%',:color,'%') " +
        "and moto.modelo like concat('%',:modelo,'%') " +
        "and moto.marca like concat('%',:marca,'%') " +
        "and (moto.anio between :fechaI and :fechaF)" +
        "and (moto.precio between (select min(precio) from Moto) and :precioF)"
    )
    Page<Moto> getMotosByFilterAndIdAndPrecioF(
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
        value = "select moto from Moto moto where " +
        "moto.id like concat('%',:id,'%')" +
        "and moto.color like concat('%',:color,'%') " +
        "and moto.modelo like concat('%',:modelo,'%') " +
        "and moto.marca like concat('%',:marca,'%') " +
        "and (moto.anio between :fechaI and :fechaF)" +
        "and (moto.precio between (select min(precio) from Moto) and :precioF)" +
        "and moto.venta.id like concat('%',:venta,'%')"
    )
    Page<Moto> getMotosByFilterAndIdAndPrecioFAndVenta(
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
        value = "select moto from Moto moto where " +
        "moto.id like concat('%',:id,'%')" +
        "and moto.color like concat('%',:color,'%') " +
        "and moto.modelo like concat('%',:modelo,'%') " +
        "and moto.marca like concat('%',:marca,'%') " +
        "and (moto.anio between :fechaI and :fechaF)" +
        "and (moto.precio between :precioI and (select max(precio) from Moto))"
    )
    Page<Moto> getMotosByFilterAndIdAndPrecioI(
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
        value = "select moto from Moto moto where " +
        "moto.id like concat('%',:id,'%')" +
        "and moto.color like concat('%',:color,'%') " +
        "and moto.modelo like concat('%',:modelo,'%') " +
        "and moto.marca like concat('%',:marca,'%') " +
        "and (moto.anio between :fechaI and :fechaF)" +
        "and (moto.precio between :precioI and (select max(precio) from Moto))" +
        "and moto.venta.id like concat('%',:venta,'%')"
    )
    Page<Moto> getMotosByFilterAndIdAndPrecioIAndVenta(
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
        value = "select moto from Moto moto where " +
        "moto.id like concat('%',:id,'%')" +
        "and moto.color like concat('%',:color,'%') " +
        "and moto.modelo like concat('%',:modelo,'%') " +
        "and moto.marca like concat('%',:marca,'%') " +
        "and (moto.anio between :fechaI and :fechaF)" +
        "and (moto.precio between :precioI and :precioF)"
    )
    Page<Moto> getMotosByFilterAndIdAndPrecios(
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
        value = "select moto from Moto moto where " +
        "moto.id like concat('%',:id,'%')" +
        "and moto.color like concat('%',:color,'%') " +
        "and moto.modelo like concat('%',:modelo,'%') " +
        "and moto.marca like concat('%',:marca,'%') " +
        "and (moto.anio between :fechaI and :fechaF)" +
        "and (moto.precio between :precioI and :precioF)" +
        "and moto.venta.id like concat('%',:venta,'%')"
    )
    Page<Moto> getMotosByFilterAndIdAndPreciosAndVenta(
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
