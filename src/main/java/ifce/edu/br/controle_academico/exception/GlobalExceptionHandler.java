package ifce.edu.br.controle_academico.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MatriculaDuplicadaException.class)
    public String handleMatriculaDuplicada(MatriculaDuplicadaException ex, Model model) {
        model.addAttribute("erro", ex.getMessage());
        return "erro";
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public String handleDuplicate(DuplicateResourceException ex, Model model) {
        model.addAttribute("erro", ex.getMessage());
        return "erro";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeral(Exception ex, Model model) {
        model.addAttribute("erro", "Erro inesperado: " + ex.getMessage());
        return "erro";
    }
}