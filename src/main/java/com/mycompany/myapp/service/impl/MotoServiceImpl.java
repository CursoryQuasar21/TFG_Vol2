package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Moto;
import com.mycompany.myapp.repository.MotoRepository;
import com.mycompany.myapp.service.MotoService;
import java.time.Instant;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Moto}.
 */
@Service
@Transactional
public class MotoServiceImpl implements MotoService {

    private final Logger log = LoggerFactory.getLogger(MotoServiceImpl.class);

    private final MotoRepository motoRepository;

    public MotoServiceImpl(MotoRepository motoRepository) {
        this.motoRepository = motoRepository;
    }

    @Override
    public Moto save(Moto moto) {
        log.debug("Request to save Moto : {}", moto);
        return motoRepository.save(moto);
    }

    @Override
    public Optional<Moto> partialUpdate(Moto moto) {
        log.debug("Request to partially update Moto : {}", moto);

        return motoRepository
            .findById(moto.getId())
            .map(
                existingMoto -> {
                    if (moto.getColor() != null) {
                        existingMoto.setColor(moto.getColor());
                    }
                    if (moto.getModelo() != null) {
                        existingMoto.setModelo(moto.getModelo());
                    }
                    if (moto.getMarca() != null) {
                        existingMoto.setMarca(moto.getMarca());
                    }
                    if (moto.getAnio() != null) {
                        existingMoto.setAnio(moto.getAnio());
                    }
                    if (moto.getPrecio() != null) {
                        existingMoto.setPrecio(moto.getPrecio());
                    }

                    return existingMoto;
                }
            )
            .map(motoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Moto> findAll(Pageable pageable) {
        log.debug("Request to get all Motos");
        return motoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Moto> findOne(Long id) {
        log.debug("Request to get Moto : {}", id);
        return motoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Moto> getMotosByFilter(
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
                        return motoRepository.getMotosByFilter(color, modelo, marca, fechaI, fechaF, pageable);
                    } else {
                        return motoRepository.getMotosByFilterAndVenta(color, modelo, marca, fechaI, fechaF, venta, pageable);
                    }
                } else {
                    if (venta == 0) {
                        return motoRepository.getMotosByFilterAndPrecioF(color, modelo, marca, fechaI, fechaF, precioF, pageable);
                    } else {
                        return motoRepository.getMotosByFilterAndPrecioFAndVenta(
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
                        return motoRepository.getMotosByFilterAndPrecioI(color, modelo, marca, fechaI, fechaF, precioI, pageable);
                    } else {
                        return motoRepository.getMotosByFilterAndPrecioIAndVenta(
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
                        return motoRepository.getMotosByFilterAndPrecios(color, modelo, marca, fechaI, fechaF, precioI, precioF, pageable);
                    } else {
                        return motoRepository.getMotosByFilterAndPreciosAndVenta(
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
                        return motoRepository.getMotosByFilterAndId(id, color, modelo, marca, fechaI, fechaF, pageable);
                    } else {
                        return motoRepository.getMotosByFilterAndIdAndVenta(id, color, modelo, marca, fechaI, fechaF, venta, pageable);
                    }
                } else {
                    if (venta == 0) {
                        return motoRepository.getMotosByFilterAndIdAndPrecioF(id, color, modelo, marca, fechaI, fechaF, precioF, pageable);
                    } else {
                        return motoRepository.getMotosByFilterAndIdAndPrecioFAndVenta(
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
                        return motoRepository.getMotosByFilterAndIdAndPrecioI(id, color, modelo, marca, fechaI, fechaF, precioI, pageable);
                    } else {
                        return motoRepository.getMotosByFilterAndIdAndPrecioIAndVenta(
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
                        return motoRepository.getMotosByFilterAndIdAndPrecios(
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
                        return motoRepository.getMotosByFilterAndIdAndPreciosAndVenta(
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
        log.debug("Request to delete Moto : {}", id);
        motoRepository.deleteById(id);
    }
}
