package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Cliente;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Cliente}.
 */
public interface ClienteService {
    /**
     * Save a cliente.
     *
     * @param cliente the entity to save.
     * @return the persisted entity.
     */
    Cliente save(Cliente cliente);

    /**
     * Partially updates a cliente.
     *
     * @param cliente the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Cliente> partialUpdate(Cliente cliente);

    /**
     * Get all the clientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Cliente> findAll(Pageable pageable);

    /**
     * Get the "id" cliente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Cliente> findOne(Long id);

    /**
     * Get cliente by filter.
     * @param id the id of the entity.
     * @param nombre the nombre of the entity.
     * @param apellidos the apellidos of the entity.
     * @param dni the dni of the entity.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Cliente> getClientsByFilter(Long id, String nombre, String apellidos, String dni, Pageable pageable);

    /**
     * Delete the "id" cliente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
