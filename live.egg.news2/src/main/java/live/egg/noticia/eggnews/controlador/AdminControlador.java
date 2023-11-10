/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package live.egg.noticia.eggnews.controlador;

import java.util.List;
import live.egg.noticia.eggnews.entidades.Usuario;
import live.egg.noticia.eggnews.excepciones.MiException;
import live.egg.noticia.eggnews.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author pc
 */
@Controller
@PreAuthorize("hasAnyRole('ROLE_PERIODISTA', 'ROLE_ADMIN')")
@RequestMapping("/admin")

public class AdminControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @PreAuthorize("hasAnyRole('ROLE_PERIODISTA', 'ROLE_ADMIN')")
    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "panelAdmin.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PERIODISTA', 'ROLE_ADMIN')")
    @GetMapping("/usuarios")
    public String listarUsuarios(ModelMap modelo) {

        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);

        return "usuario_list.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PERIODISTA', 'ROLE_ADMIN')")
    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable String id) {
        usuarioServicio.cambiarRol(id);

        return "redirect:/admin/usuarios";
    }

    @PreAuthorize("hasAnyRole('ROLE_PERIODISTA', 'ROLE_ADMIN')")
    @PostMapping("/panelAdmin")
    public String registrar(@RequestParam String email, @RequestParam String nombre, @RequestParam String password, @RequestParam String password2, ModelMap modelo) {

        try {
            usuarioServicio.registrar(nombre, email, password, password2);

            modelo.put("exito", "Usuario registrado correctamente!");

            return "index.html";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "registro.html";
        }
    }

}
