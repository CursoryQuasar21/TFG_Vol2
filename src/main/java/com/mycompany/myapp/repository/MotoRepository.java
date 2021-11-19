package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Moto;
import java.util.Set;
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
    Set<Moto> getMotosByVentaId(@Param("ventaId") Long ventaId);

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
}
