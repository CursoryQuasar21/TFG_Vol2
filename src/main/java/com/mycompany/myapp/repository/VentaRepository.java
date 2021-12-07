package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Venta;
import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Venta entity.
 * Clase encargada de acceder a la base de datos mediante hibernate a la tabla Venta.
 */
@SuppressWarnings("unused")
@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    @Query(value = "select venta from Venta venta where venta.fecha between :fechaI and :fechaF")
    Page<Venta> getSalesByFilter(@Param("fechaI") Instant fechaI, @Param("fechaF") Instant fechaF, Pageable pageable);

    @Query(
        value = "select venta from Venta venta where " +
        "(venta.fecha between :fechaI and :fechaF)" +
        "and venta.empleado.id like concat('%',:idE,'%')"
    )
    Page<Venta> getSalesByFilterAndIdE(
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idE") Long idE,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "(venta.fecha between :fechaI and :fechaF)" +
        "and venta.cliente.id like concat('%',:idC,'%')"
    )
    Page<Venta> getSalesByFilterAndIdC(
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idC") Long idC,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "(venta.fecha between :fechaI and :fechaF)" +
        "and venta.cliente.id like concat('%',:idC,'%')" +
        "and venta.empleado.id like concat('%',:idE,'%')"
    )
    Page<Venta> getSalesByFilterAndIds(
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idC") Long idC,
        @Param("idE") Long idE,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "(venta.total between (select min(total) from Venta) and :totalF)" +
        "and (venta.fecha between :fechaI and :fechaF)"
    )
    Page<Venta> getSalesByFilterAndTotalF(
        @Param("totalF") Double totalF,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "(venta.total between (select min(total) from Venta) and :totalF)" +
        "and (venta.fecha between :fechaI and :fechaF)" +
        "and venta.empleado.id like concat('%',:idE,'%')"
    )
    Page<Venta> getSalesByFilterAndTotalFAndIdE(
        @Param("totalF") Double totalF,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idE") Long idE,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "(venta.total between (select min(total) from Venta) and :totalF)" +
        "and (venta.fecha between :fechaI and :fechaF)" +
        "and venta.cliente.id like concat('%',:idC,'%')"
    )
    Page<Venta> getSalesByFilterAndTotalFAndIdC(
        @Param("totalF") Double totalF,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idC") Long idC,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "(venta.total between (select min(total) from Venta) and :totalF)" +
        "and (venta.fecha between :fechaI and :fechaF)" +
        "and venta.cliente.id like concat('%',:idC,'%')" +
        "and venta.empleado.id like concat('%',:idE,'%')"
    )
    Page<Venta> getSalesByFilterAndTotalFAndIds(
        @Param("totalF") Double totalF,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idC") Long idC,
        @Param("idE") Long idE,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "(venta.total between :totalI and (select max(total) from Venta))" +
        "and venta.fecha between :fechaI and :fechaF"
    )
    Page<Venta> getSalesByFilterAndTotalI(
        @Param("totalI") Double totalI,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "(venta.total between :totalI and (select max(total) from Venta))" +
        "and (venta.fecha between :fechaI and :fechaF)" +
        "and venta.empleado.id like concat('%',:idE,'%')"
    )
    Page<Venta> getSalesByFilterAndTotalIAndIdE(
        @Param("totalI") Double totalI,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idE") Long idE,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "(venta.total between :totalI and (select max(total) from Venta))" +
        "and (venta.fecha between :fechaI and :fechaF)" +
        "and venta.cliente.id like concat('%',:idC,'%')"
    )
    Page<Venta> getSalesByFilterAndTotalIAndIdC(
        @Param("totalI") Double totalI,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idC") Long idC,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "(venta.total between :totalI and (select max(total) from Venta))" +
        "and (venta.fecha between :fechaI and :fechaF)" +
        "and venta.cliente.id like concat('%',:idC,'%')" +
        "and venta.empleado.id like concat('%',:idE,'%')"
    )
    Page<Venta> getSalesByFilterAndTotalIAndIds(
        @Param("totalI") Double totalI,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idC") Long idC,
        @Param("idE") Long idE,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "(venta.total between :totalI and :totalF)" +
        "and (venta.fecha between :fechaI and :fechaF)"
    )
    Page<Venta> getSalesByFilterAndTotals(
        @Param("totalI") Double totalI,
        @Param("totalF") Double totalF,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "(venta.total between :totalI and :totalF)" +
        "and (venta.fecha between :fechaI and :fechaF)" +
        "and venta.empleado.id like concat('%',:idE,'%')"
    )
    Page<Venta> getSalesByFilterAndTotalsAndIdE(
        @Param("totalI") Double totalI,
        @Param("totalF") Double totalF,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idE") Long idE,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "(venta.total between :totalI and :totalF)" +
        "and (venta.fecha between :fechaI and :fechaF)" +
        "and venta.cliente.id like concat('%',:idC,'%')"
    )
    Page<Venta> getSalesByFilterAndTotalsAndIdC(
        @Param("totalI") Double totalI,
        @Param("totalF") Double totalF,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idC") Long idC,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "(venta.total between :totalI and :totalF)" +
        "and (venta.fecha between :fechaI and :fechaF)" +
        "and venta.cliente.id like concat('%',:idC,'%')" +
        "and venta.empleado.id like concat('%',:idE,'%')"
    )
    Page<Venta> getSalesByFilterAndTotalsAndIds(
        @Param("totalI") Double totalI,
        @Param("totalF") Double totalF,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idC") Long idC,
        @Param("idE") Long idE,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "venta.id like concat('%',:id,'%')" +
        "and (venta.fecha between :fechaI and :fechaF)"
    )
    Page<Venta> getSalesByFilterAndId(
        @Param("id") Long id,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "venta.id like concat('%',:id,'%')" +
        "and (venta.fecha between :fechaI and :fechaF)" +
        "and venta.empleado.id like concat('%',:idE,'%')"
    )
    Page<Venta> getSalesByFilterAndIdAndIdE(
        @Param("id") Long id,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idE") Long idE,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "venta.id like concat('%',:id,'%')" +
        "and (venta.fecha between :fechaI and :fechaF)" +
        "and venta.cliente.id like concat('%',:idC,'%')"
    )
    Page<Venta> getSalesByFilterAndIdAndIdC(
        @Param("id") Long id,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idC") Long idC,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "venta.id like concat('%',:id,'%')" +
        "and (venta.fecha between :fechaI and :fechaF)" +
        "and venta.cliente.id like concat('%',:idC,'%')" +
        "and venta.empleado.id like concat('%',:idE,'%')"
    )
    Page<Venta> getSalesByFilterAndIdAndIds(
        @Param("id") Long id,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idC") Long idC,
        @Param("idE") Long idE,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "venta.id like concat('%',:id,'%')" +
        "and (venta.total between (select min(total) from Venta) and :totalF)" +
        "and (venta.fecha between :fechaI and :fechaF)"
    )
    Page<Venta> getSalesByFilterAndIdAndTotalF(
        @Param("id") Long id,
        @Param("totalF") Double totalF,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "venta.id like concat('%',:id,'%')" +
        "and (venta.total between (select min(total) from Venta) and :totalF)" +
        "and (venta.fecha between :fechaI and :fechaF)" +
        "and venta.empleado.id like concat('%',:idE,'%')"
    )
    Page<Venta> getSalesByFilterAndIdAndTotalFAndIdE(
        @Param("id") Long id,
        @Param("totalF") Double totalF,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idE") Long idE,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "venta.id like concat('%',:id,'%')" +
        "and (venta.total between (select min(total) from Venta) and :totalF)" +
        "and (venta.fecha between :fechaI and :fechaF)" +
        "and venta.cliente.id like concat('%',:idC,'%')"
    )
    Page<Venta> getSalesByFilterAndIdAndTotalFAndIdC(
        @Param("id") Long id,
        @Param("totalF") Double totalF,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idC") Long idC,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "venta.id like concat('%',:id,'%')" +
        "and (venta.total between (select min(total) from Venta) and :totalF)" +
        "and (venta.fecha between :fechaI and :fechaF)" +
        "and venta.cliente.id like concat('%',:idC,'%')" +
        "and venta.empleado.id like concat('%',:idE,'%')"
    )
    Page<Venta> getSalesByFilterAndIdAndTotalFAndIds(
        @Param("id") Long id,
        @Param("totalF") Double totalF,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idC") Long idC,
        @Param("idE") Long idE,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "venta.id like concat('%',:id,'%')" +
        "and (venta.total between :totalI and (select max(total) from Venta))" +
        "and (venta.fecha between :fechaI and :fechaF)"
    )
    Page<Venta> getSalesByFilterAndIdAndTotalI(
        @Param("id") Long id,
        @Param("totalI") Double totalI,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "venta.id like concat('%',:id,'%')" +
        "and (venta.total between :totalI and (select max(total) from Venta))" +
        "and (venta.fecha between :fechaI and :fechaF)" +
        "and venta.empleado.id like concat('%',:idE,'%')"
    )
    Page<Venta> getSalesByFilterAndIdAndTotalIAndIdE(
        @Param("id") Long id,
        @Param("totalI") Double totalI,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idE") Long idE,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "venta.id like concat('%',:id,'%')" +
        "and (venta.total between :totalI and (select max(total) from Venta))" +
        "and (venta.fecha between :fechaI and :fechaF)" +
        "and venta.cliente.id like concat('%',:idC,'%')"
    )
    Page<Venta> getSalesByFilterAndIdAndTotalIAndIdC(
        @Param("id") Long id,
        @Param("totalI") Double totalI,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idC") Long idC,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "venta.id like concat('%',:id,'%')" +
        "and (venta.total between :totalI and (select max(total) from Venta))" +
        "and (venta.fecha between :fechaI and :fechaF)" +
        "and venta.cliente.id like concat('%',:idC,'%')" +
        "and venta.empleado.id like concat('%',:idE,'%')"
    )
    Page<Venta> getSalesByFilterAndIdAndTotalIAndIds(
        @Param("id") Long id,
        @Param("totalI") Double totalI,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idC") Long idC,
        @Param("idE") Long idE,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "venta.id like concat('%',:id,'%')" +
        "and (venta.total between :totalI and :totalF)" +
        "and (venta.fecha between :fechaI and :fechaF)"
    )
    Page<Venta> getSalesByFilterAndIdAndTotals(
        @Param("id") Long id,
        @Param("totalI") Double totalI,
        @Param("totalF") Double totalF,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "venta.id like concat('%',:id,'%')" +
        "and (venta.total between :totalI and :totalF)" +
        "and (venta.fecha between :fechaI and :fechaF)" +
        "and venta.empleado.id like concat('%',:idE,'%')"
    )
    Page<Venta> getSalesByFilterAndIdAndTotalsAndIdE(
        @Param("id") Long id,
        @Param("totalI") Double totalI,
        @Param("totalF") Double totalF,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idE") Long idE,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "venta.id like concat('%',:id,'%')" +
        "and (venta.total between :totalI and :totalF)" +
        "and (venta.fecha between :fechaI and :fechaF)" +
        "and venta.cliente.id like concat('%',:idC,'%')"
    )
    Page<Venta> getSalesByFilterAndIdAndTotalsAndIdC(
        @Param("id") Long id,
        @Param("totalI") Double totalI,
        @Param("totalF") Double totalF,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idC") Long idC,
        Pageable pageable
    );

    @Query(
        value = "select venta from Venta venta where " +
        "venta.id like concat('%',:id,'%')" +
        "and (venta.total between :totalI and :totalF)" +
        "and (venta.fecha between :fechaI and :fechaF)" +
        "and venta.cliente.id like concat('%',:idC,'%')" +
        "and venta.empleado.id like concat('%',:idE,'%')"
    )
    Page<Venta> getSalesByFilterAndIdAndTotalsAndIds(
        @Param("id") Long id,
        @Param("totalI") Double totalI,
        @Param("totalF") Double totalF,
        @Param("fechaI") Instant fechaI,
        @Param("fechaF") Instant fechaF,
        @Param("idC") Long idC,
        @Param("idE") Long idE,
        Pageable pageable
    );
}
