package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Moto;
import java.time.Instant;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Moto}.
 */
public interface MotoService {
    /**
     * Save a moto.
     *
     * @param moto the entity to save.
     * @return the persisted entity.
     */
    Moto save(Moto moto);

    /**
     * Partially updates a moto.
     *
     * @param moto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Moto> partialUpdate(Moto moto);

    /**
     * Get all the motos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Moto> findAll(Pageable pageable);

    /**
     * Get the "id" moto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Moto> findOne(Long id);

    /**
     * Get Moto by filter.
     * @param id the id of the entity.
     * @param color the color of the entity.
     * @param modelo the modelo of the entity.
     * @param marca the marca of the entity.
     * @param fecha the fecha of the entity(Range).
     * @param precioI the precioInicial of the entity.
     * @param precioF the precioFinal of the entity.
     * @param venta the venta of the entity.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Moto> getMotosByFilter(
        Long id,
        String color,
        String modelo,
        String marca,
        Instant fechaI,
        Instant fechaF,
        Double precioI,
        Double precioF,
        Long venta,
        Pageable pageable
    );

    /**
     * Delete the "id" moto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
