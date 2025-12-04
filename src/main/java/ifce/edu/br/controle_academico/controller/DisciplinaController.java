package ifce.edu.br.controle_academico.controller;

import ifce.edu.br.controle_academico.model.entity.Disciplina;
import ifce.edu.br.controle_academico.model.entity.Usuario;
import ifce.edu.br.controle_academico.repository.UsuarioRepository;
import ifce.edu.br.controle_academico.service.DisciplinaService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/disciplinas")
public class DisciplinaController {

    private final DisciplinaService service;
    private final UsuarioRepository usuarioRepository;

    public DisciplinaController(DisciplinaService service, UsuarioRepository usuarioRepository) {
        this.service = service;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listar(Model model){
        model.addAttribute("disciplinas", service.listarTodas());
        return "disciplinas/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model){
        model.addAttribute("disciplina", new Disciplina());
        return "disciplinas/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Disciplina disciplina,
                         Model model,
                         @AuthenticationPrincipal User user){
        try {
            Usuario usuarioLogado = usuarioRepository.findByLogin(user.getUsername());
            disciplina.setCreatedBy(usuarioLogado);

            service.salvar(disciplina);
            return "redirect:/disciplinas";
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            return "disciplinas/form";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model){
        Disciplina d = service.buscarPorId(id).orElseThrow();
        model.addAttribute("disciplina", d);
        return "disciplinas/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id){
        service.excluir(id);
        return "redirect:/disciplinas";
    }
}
