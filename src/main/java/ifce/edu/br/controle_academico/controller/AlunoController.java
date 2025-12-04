package ifce.edu.br.controle_academico.controller;

import ifce.edu.br.controle_academico.model.entity.Aluno;
import ifce.edu.br.controle_academico.model.entity.Usuario;
import ifce.edu.br.controle_academico.model.enums.StatusAluno;
import ifce.edu.br.controle_academico.repository.UsuarioRepository;
import ifce.edu.br.controle_academico.service.AlunoService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService service;
    private final UsuarioRepository usuarioRepository;

    public AlunoController(AlunoService service, UsuarioRepository usuarioRepository) {
        this.service = service;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("alunos", service.listarTodos());
        return "alunos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("aluno", new Aluno());
        model.addAttribute("statusList", StatusAluno.values());
        return "alunos/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Aluno alunoForm,
                         Model model,
                         @AuthenticationPrincipal User user) {

        try {
            Usuario usuarioLogado = usuarioRepository.findByLogin(user.getUsername());

            Aluno aluno;

            if (alunoForm.getId() != null) {
                aluno = service.buscarPorId(alunoForm.getId()).orElseThrow();

                aluno.setNome(alunoForm.getNome());
                aluno.setMatricula(alunoForm.getMatricula());
                aluno.setEmail(alunoForm.getEmail());
                aluno.setStatus(alunoForm.getStatus());
                aluno.setDataNascimento(alunoForm.getDataNascimento());
            } else {
                aluno = alunoForm;
                aluno.setCreatedBy(usuarioLogado);
            }

            service.salvar(aluno);
            return "redirect:/alunos";

        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("statusList", StatusAluno.values());
            return "alunos/form";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Aluno a = service.buscarPorId(id).orElseThrow();
        model.addAttribute("aluno", a);
        model.addAttribute("statusList", StatusAluno.values());
        return "alunos/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/alunos";
    }
}