/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package live.egg.estancia.web.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import live.egg.estancia.web.entidades.Estancias;
import live.egg.estancia.web.repositorios.CasasRepository;
import live.egg.estancia.web.repositorios.ClientesRepository;
import live.egg.estancia.web.repositorios.EstanciasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author pc
 */
@Service
public class EstaciasServicio {

    @Autowired
    ClientesRepository clienteRepositorio;

    @Autowired
    CasasRepository casasRepositorio;

    @Autowired
    EstanciasRepository estanciasRepositorio;

    @Transactional
    public void crearEstancia(String nombreHuesped, Date fechaDesde, Date fechaHasta) {

        Estancias estancia = new Estancias();

        estancia.setNombreHuesped(nombreHuesped);
        estancia.setFechaDesde(fechaDesde);
        estancia.setFechaHasta(fechaHasta);
        estancia.setActive(Boolean.TRUE);

        estanciasRepositorio.save(estancia);

    }

    @Transactional
    public List<Estancias> listarEstanciases() {
        return estanciasRepositorio.findAll();
    }

    @Transactional
    public void modificarEstancias(Long idEstancia, String nombreHuesped, Date fechaDesde, Date fechaHasta) {

        Optional<Estancias> estAcambiar = estanciasRepositorio.findById(idEstancia);

        Estancias estancia = new Estancias();

        if (estAcambiar.isPresent()) {

            estancia.setIdEstancia(estAcambiar.get().getIdEstancia());
            estancia.setNombreHuesped(nombreHuesped);
            estancia.setFechaDesde(fechaDesde);
            estancia.setFechaHasta(fechaHasta);
            estancia.setIdCasa(estAcambiar.get().getIdCasa());
            estancia.setIdCliente(estAcambiar.get().getIdCliente());

            if (estAcambiar.get().getActive()) {
                estanciasRepositorio.save(estancia);
            }

        }

    }

    @Transactional
    public Estancias getOne(Long id) {
        return estanciasRepositorio.getReferenceById(id);
    }

}
