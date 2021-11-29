package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Coche;
import com.mycompany.myapp.repository.CocheRepository;
import com.mycompany.myapp.service.CocheService;
import java.time.Instant;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Coche}.
 */
@Service
@Transactional
public class CocheServiceImpl implements CocheService {

    private final Logger log = LoggerFactory.getLogger(CocheServiceImpl.class);

    private final CocheRepository cocheRepository;

    public CocheServiceImpl(CocheRepository cocheRepository) {
        this.cocheRepository = cocheRepository;
    }

    @Override
    public Coche save(Coche coche) {
        log.debug("Request to save Coche : {}", coche);
        return cocheRepository.save(coche);
    }

    @Override
    public Optional<Coche> partialUpdate(Coche coche) {
        log.debug("Request to partially update Coche : {}", coche);

        return cocheRepository
            .findById(coche.getId())
            .map(
                existingCoche -> {
                    if (coche.getColor() != null) {
                        existingCoche.setColor(coche.getColor());
                    }
                    if (coche.getModelo() != null) {
                        existingCoche.setModelo(coche.getModelo());
                    }
                    if (coche.getMarca() != null) {
                        existingCoche.setMarca(coche.getMarca());
                    }
                    if (coche.getAnio() != null) {
                        existingCoche.setAnio(coche.getAnio());
                    }
                    if (coche.getPrecio() != null) {
                        existingCoche.setPrecio(coche.getPrecio());
                    }

                    return existingCoche;
                }
            )
            .map(cocheRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Coche> findAll(Pageable pageable) {
        log.debug("Request to get all Coches");
        return cocheRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Coche> findOne(Long id) {
        log.debug("Request to get Coche : {}", id);
        return cocheRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Coche> getCarsByFilter(
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
    ) {
        if (id == 0) {
            if (precioI == 0) {
                if (precioF == 0) {
                    if (venta == 0) {
                        return cocheRepository.getCarsByFilter(color, modelo, marca, fechaI, fechaF, pageable);
                    } else {
                        return cocheRepository.getCarsByFilterAndVenta(color, modelo, marca, fechaI, fechaF, venta, pageable);
                    }
                } else {
                    if (venta == 0) {
                        return cocheRepository.getCarsByFilterAndPrecioF(color, modelo, marca, fechaI, fechaF, precioF, pageable);
                    } else {
                        return cocheRepository.getCarsByFilterAndPrecioFAndVenta(
                            color,
                            modelo,
                            marca,
                            fechaI,
                            fechaF,
                            precioF,
                            venta,
                            pageable
                        );
                    }
                }
            } else {
                if (precioF == 0) {
                    if (venta == 0) {
                        return cocheRepository.getCarsByFilterAndPrecioI(color, modelo, marca, fechaI, fechaF, precioI, pageable);
                    } else {
                        return cocheRepository.getCarsByFilterAndPrecioIAndVenta(
                            color,
                            modelo,
                            marca,
                            fechaI,
                            fechaF,
                            precioI,
                            venta,
                            pageable
                        );
                    }
                } else {
                    if (venta == 0) {
                        return cocheRepository.getCarsByFilterAndPrecios(color, modelo, marca, fechaI, fechaF, precioI, precioF, pageable);
                    } else {
                        return cocheRepository.getCarsByFilterAndPreciosAndVenta(
                            color,
                            modelo,
                            marca,
                            fechaI,
                            fechaF,
                            precioI,
                            precioF,
                            venta,
                            pageable
                        );
                    }
                }
            }
        } else {
            if (precioI == 0) {
                if (precioF == 0) {
                    if (venta == 0) {
                        return cocheRepository.getCarsByFilterAndId(id, color, modelo, marca, fechaI, fechaF, pageable);
                    } else {
                        return cocheRepository.getCarsByFilterAndIdAndVenta(id, color, modelo, marca, fechaI, fechaF, venta, pageable);
                    }
                } else {
                    if (venta == 0) {
                        return cocheRepository.getCarsByFilterAndIdAndPrecioF(id, color, modelo, marca, fechaI, fechaF, precioF, pageable);
                    } else {
                        return cocheRepository.getCarsByFilterAndIdAndPrecioFAndVenta(
                            id,
                            color,
                            modelo,
                            marca,
                            fechaI,
                            fechaF,
                            precioF,
                            venta,
                            pageable
                        );
                    }
                }
            } else {
                if (precioF == 0) {
                    if (venta == 0) {
                        return cocheRepository.getCarsByFilterAndIdAndPrecioI(id, color, modelo, marca, fechaI, fechaF, precioI, pageable);
                    } else {
                        return cocheRepository.getCarsByFilterAndIdAndPrecioIAndVenta(
                            id,
                            color,
                            modelo,
                            marca,
                            fechaI,
                            fechaF,
                            precioI,
                            venta,
                            pageable
                        );
                    }
                } else {
                    if (venta == 0) {
                        return cocheRepository.getCarsByFilterAndIdAndPrecios(
                            id,
                            color,
                            modelo,
                            marca,
                            fechaI,
                            fechaF,
                            precioI,
                            precioF,
                            pageable
                        );
                    } else {
                        return cocheRepository.getCarsByFilterAndIdAndPreciosAndVenta(
                            id,
                            color,
                            modelo,
                            marca,
                            fechaI,
                            fechaF,
                            precioI,
                            precioF,
                            venta,
                            pageable
                        );
                    }
                }
            }
        }
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Coche : {}", id);
        cocheRepository.deleteById(id);
    }
}
