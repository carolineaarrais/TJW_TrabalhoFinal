package ifce.edu.br.controle_academico.controller;

import ifce.edu.br.controle_academico.model.entity.Matricula;
import ifce.edu.br.controle_academico.model.entity.Usuario;
import ifce.edu.br.controle_academico.model.enums.SituacaoMatricula;
import ifce.edu.br.controle_academico.service.MatriculaService;
import ifce.edu.br.controle_academico.service.AlunoService;
import ifce.edu.br.controle_academico.service.DisciplinaService;
import ifce.edu.br.controle_academico.repository.UsuarioRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/matriculas")
public class MatriculaController {

    private final MatriculaService service;
    private final AlunoService alunoService;
    private final DisciplinaService disciplinaService;
    private final UsuarioRepository usuarioRepository;

    public MatriculaController(MatriculaService service,
                               AlunoService alunoService,
                               DisciplinaService disciplinaService,
                               UsuarioRepository usuarioRepository){
        this.service = service;
        this.alunoService = alunoService;
        this.disciplinaService = disciplinaService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listar(Model model){
        model.addAttribute("matriculas", service.listarTodas());
        return "matriculas/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model){
        model.addAttribute("matricula", new Matricula());
        model.addAttribute("alunos", alunoService.listarTodos());
        model.addAttribute("disciplinas", disciplinaService.listarTodas());
        model.addAttribute("situacoes", SituacaoMatricula.values());
        return "matriculas/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Matricula m,
                         Model model,
                         @AuthenticationPrincipal User user){ // usuário logado
        try {
            Usuario usuarioLogado = usuarioRepository.findByLogin(user.getUsername());
            m.setCreatedBy(usuarioLogado);

            service.salvar(m);
            return "redirect:/matriculas";
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("alunos", alunoService.listarTodos());
            model.addAttribute("disciplinas", disciplinaService.listarTodas());
            model.addAttribute("situacoes", SituacaoMatricula.values());
            return "matriculas/form";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model){
        Matricula m = service.buscarPorId(id).orElseThrow();
        model.addAttribute("matricula", m);
        model.addAttribute("alunos", alunoService.listarTodos());
        model.addAttribute("disciplinas", disciplinaService.listarTodas());
        model.addAttribute("situacoes", SituacaoMatricula.values());
        return "matriculas/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id){
        service.excluir(id);
        return "redirect:/matriculas";
    }
}