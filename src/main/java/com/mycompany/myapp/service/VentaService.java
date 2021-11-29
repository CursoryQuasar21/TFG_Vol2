package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Venta;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Venta}.
 */
public interface VentaService {
    /**
     * Save a venta.
     *
     * @param venta the entity to save.
     * @return the persisted entity.
     */
    Venta save(Venta venta);

    /**
     * Partially updates a venta.
     *
     * @param venta the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Venta> partialUpdate(Venta venta);

    /**
     * Get all the ventas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Venta> findAll(Pageable pageable);

    /**
     * Get the "id" venta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Venta> findOne(Long id);

    /**
     * Get cliente by filter.
     * @param id the id of the entity.
     * @param totalI the total of the entity(Range).
     * @param totalF the total of the entity(Range).
     * @param idC the id of  cliente the entity(Join).
     * @param idE the id of empleado the entity(Join).
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Venta> getSelesByFilter(
        Long id,
        Double totalI,
        Double totalF,
        Instant fechaI,
        Instant fechaF,
        Long idC,
        Long idE,
        Pageable pageable
    );

    /**
     * Delete the "id" venta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
