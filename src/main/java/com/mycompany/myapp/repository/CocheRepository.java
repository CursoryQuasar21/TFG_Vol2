package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Coche;
import java.util.Set;
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
}
