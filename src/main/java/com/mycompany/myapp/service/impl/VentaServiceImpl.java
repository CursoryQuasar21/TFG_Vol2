package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Coche;
import com.mycompany.myapp.domain.Moto;
import com.mycompany.myapp.domain.Venta;
import com.mycompany.myapp.repository.CocheRepository;
import com.mycompany.myapp.repository.MotoRepository;
import com.mycompany.myapp.repository.VentaRepository;
import com.mycompany.myapp.service.VentaService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Venta}.
 */
@Service
@Transactional
public class VentaServiceImpl implements VentaService {

    private final Logger log = LoggerFactory.getLogger(VentaServiceImpl.class);

    private final VentaRepository ventaRepository;
    private final CocheRepository cocheRepository;
    private final MotoRepository motoRepository;

    public VentaServiceImpl(VentaRepository ventaRepository, CocheRepository cocheRepository, MotoRepository motoRepository) {
        this.ventaRepository = ventaRepository;
        this.cocheRepository = cocheRepository;
        this.motoRepository = motoRepository;
    }

    @Override
    public Venta save(Venta venta) {
        log.debug("Request to save Venta : {}", venta);
        log.debug("Request to save Coches : {}", venta.getCoches());
        log.debug("Request to save Motos : {}", venta.getMotos());

        Venta venta2 = venta;
        if (venta.getId() != null) {
            cocheRepository.updateCocheDeleteVentaByVentaId(venta.getId());
            motoRepository.updateMotoDeleteVentaByVentaId(venta.getId());
            if (venta.getCoches() != null) {
                venta.getCoches().forEach(i -> cocheRepository.updateCocheSaveVentaByVentaIdAndCocheId(venta.getId(), i.getId()));
            }
            if (venta.getMotos() != null) {
                venta.getMotos().forEach(i -> motoRepository.updateMotoSaveVentaByVentaIdAndMotoId(venta.getId(), i.getId()));
            }
        } else {
            venta2 = ventaRepository.save(venta);
            if (venta2.getCoches() != null) venta.getCoches().forEach(i -> cocheRepository.save(i));
            if (venta2.getMotos() != null) venta.getMotos().forEach(i -> motoRepository.save(i));
        }

        return ventaRepository.save(venta2);
    }

    @Override
    public Optional<Venta> partialUpdate(Venta venta) {
        log.debug("Request to partially update Venta : {}", venta);

        return ventaRepository
            .findById(venta.getId())
            .map(
                existingVenta -> {
                    if (venta.getTotal() != null) {
                        existingVenta.setTotal(venta.getTotal());
                    }
                    if (venta.getFecha() != null) {
                        existingVenta.setFecha(venta.getFecha());
                    }

                    return existingVenta;
                }
            )
            .map(ventaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Venta> findAll(Pageable pageable) {
        log.debug("Request to get all Ventas");
        return ventaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> findOne(Long id) {
        log.debug("Request to get Venta : {}", id);
        return ventaRepository.findById(id);
    }

    //Metodo Creado
    //Sirve para aplicar los filtros y obtener los resultados de la base de datos
    @Override
    @Transactional(readOnly = true)
    public Page<Venta> getSelesByFilter(
        Long id,
        Double totalI,
        Double totalF,
        Instant fechaI,
        Instant fechaF,
        Long idC,
        Long idE,
        Pageable pageable
    ) {
        log.debug("REST request to sales by filter: {}", id, totalI, totalF, fechaI, fechaF, idC, idE);
        if (id == 0) {
            if (totalI == 0) {
                if (totalF == 0) {
                    if (idC == 0) {
                        if (idE == 0) {
                            return ventaRepository.getSalesByFilter(fechaI, fechaF, pageable);
                        } else {
                            return ventaRepository.getSalesByFilterAndIdE(fechaI, fechaF, idE, pageable);
                        }
                    } else {
                        if (idE == 0) {
                            return ventaRepository.getSalesByFilterAndIdC(fechaI, fechaF, idC, pageable);
                        } else {
                            return ventaRepository.getSalesByFilterAndIds(fechaI, fechaF, idC, idE, pageable);
                        }
                    }
                } else {
                    if (idC == 0) {
                        if (idE == 0) {
                            return ventaRepository.getSalesByFilterAndTotalF(totalF, fechaI, fechaF, pageable);
                        } else {
                            return ventaRepository.getSalesByFilterAndTotalFAndIdE(totalF, fechaI, fechaF, idE, pageable);
                        }
                    } else {
                        if (idE == 0) {
                            return ventaRepository.getSalesByFilterAndTotalFAndIdC(totalF, fechaI, fechaF, idC, pageable);
                        } else {
                            return ventaRepository.getSalesByFilterAndTotalFAndIds(totalF, fechaI, fechaF, idC, idE, pageable);
                        }
                    }
                }
            } else {
                if (totalF == 0) {
                    if (idC == 0) {
                        if (idE == 0) {
                            return ventaRepository.getSalesByFilterAndTotalI(totalI, fechaI, fechaF, pageable);
                        } else {
                            return ventaRepository.getSalesByFilterAndTotalIAndIdE(totalI, fechaI, fechaF, idE, pageable);
                        }
                    } else {
                        if (idE == 0) {
                            return ventaRepository.getSalesByFilterAndTotalIAndIdC(totalI, fechaI, fechaF, idC, pageable);
                        } else {
                            return ventaRepository.getSalesByFilterAndTotalIAndIds(totalI, fechaI, fechaF, idC, idE, pageable);
                        }
                    }
                } else {
                    if (idC == 0) {
                        if (idE == 0) {
                            return ventaRepository.getSalesByFilterAndTotals(totalI, totalF, fechaI, fechaF, pageable);
                        } else {
                            return ventaRepository.getSalesByFilterAndTotalsAndIdE(totalI, totalF, fechaI, fechaF, idE, pageable);
                        }
                    } else {
                        if (idE == 0) {
                            return ventaRepository.getSalesByFilterAndTotalsAndIdC(totalI, totalF, fechaI, fechaF, idC, pageable);
                        } else {
                            return ventaRepository.getSalesByFilterAndTotalsAndIds(totalI, totalF, fechaI, fechaF, idC, idE, pageable);
                        }
                    }
                }
            }
        } else {
            if (totalI == 0) {
                if (totalF == 0) {
                    if (idC == 0) {
                        if (idE == 0) {
                            return ventaRepository.getSalesByFilterAndId(id, fechaI, fechaF, pageable);
                        } else {
                            return ventaRepository.getSalesByFilterAndIdAndIdE(id, fechaI, fechaF, idE, pageable);
                        }
                    } else {
                        if (idE == 0) {
                            return ventaRepository.getSalesByFilterAndIdAndIdC(id, fechaI, fechaF, idC, pageable);
                        } else {
                            return ventaRepository.getSalesByFilterAndIdAndIds(id, fechaI, fechaF, idC, idE, pageable);
                        }
                    }
                } else {
                    if (idC == 0) {
                        if (idE == 0) {
                            return ventaRepository.getSalesByFilterAndIdAndTotalF(id, totalF, fechaI, fechaF, pageable);
                        } else {
                            return ventaRepository.getSalesByFilterAndIdAndTotalFAndIdE(id, totalF, fechaI, fechaF, idE, pageable);
                        }
                    } else {
                        if (idE == 0) {
                            return ventaRepository.getSalesByFilterAndIdAndTotalFAndIdC(id, totalF, fechaI, fechaF, idC, pageable);
                        } else {
                            return ventaRepository.getSalesByFilterAndIdAndTotalFAndIds(id, totalF, fechaI, fechaF, idC, idE, pageable);
                        }
                    }
                }
            } else {
                if (totalF == 0) {
                    if (idC == 0) {
                        if (idE == 0) {
                            return ventaRepository.getSalesByFilterAndIdAndTotalI(id, totalI, fechaI, fechaF, pageable);
                        } else {
                            return ventaRepository.getSalesByFilterAndIdAndTotalIAndIdE(id, totalI, fechaI, fechaF, idE, pageable);
                        }
                    } else {
                        if (idE == 0) {
                            return ventaRepository.getSalesByFilterAndIdAndTotalIAndIdC(id, totalI, fechaI, fechaF, idC, pageable);
                        } else {
                            return ventaRepository.getSalesByFilterAndIdAndTotalIAndIds(id, totalI, fechaI, fechaF, idC, idE, pageable);
                        }
                    }
                } else {
                    if (idC == 0) {
                        if (idE == 0) {
                            return ventaRepository.getSalesByFilterAndIdAndTotals(id, totalI, totalF, fechaI, fechaF, pageable);
                        } else {
                            return ventaRepository.getSalesByFilterAndIdAndTotalsAndIdE(id, totalI, totalF, fechaI, fechaF, idE, pageable);
                        }
                    } else {
                        if (idE == 0) {
                            return ventaRepository.getSalesByFilterAndIdAndTotalsAndIdC(id, totalI, totalF, fechaI, fechaF, idC, pageable);
                        } else {
                            return ventaRepository.getSalesByFilterAndIdAndTotalsAndIds(
                                id,
                                totalI,
                                totalF,
                                fechaI,
                                fechaF,
                                idC,
                                idE,
                                pageable
                            );
                        }
                    }
                }
            }
        }
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Venta : {}", id);
        ventaRepository.deleteById(id);
    }
}
