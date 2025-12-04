package ifce.edu.br.controle_academico.controller;

import ifce.edu.br.controle_academico.model.entity.Usuario;
import ifce.edu.br.controle_academico.model.enums.Role;
import ifce.edu.br.controle_academico.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", Role.values());
        return "register";
    }

    @PostMapping("/register")
    public String registrar(@ModelAttribute Usuario usuario, Model model) {

        if (usuarioRepository.existsByLogin(usuario.getLogin())) {
            model.addAttribute("erro", "Login já existe!");
            model.addAttribute("roles", Role.values());
            return "register";
        }

        // If user submitted role ADMIN, allow only if currently logged user is ADMIN
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean creatingAdmin = usuario.getRole() == Role.ROLE_ADMIN;
        if (creatingAdmin) {
            if (auth == null || !auth.isAuthenticated() ||
                    auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                model.addAttribute("erro", "Somente ADMIN pode criar usuários ADMIN.");
                model.addAttribute("roles", Role.values());
                return "register";
            }
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        if (usuario.getRole() == null) usuario.setRole(Role.ROLE_SECRETARIA);

        usuarioRepository.save(usuario);
        return "redirect:/login?registered";
    }
}