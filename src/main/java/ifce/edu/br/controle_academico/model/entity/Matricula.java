package ifce.edu.br.controle_academico.model.entity;

import ifce.edu.br.controle_academico.model.enums.SituacaoMatricula;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "matricula", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"aluno_id", "disciplina_id", "situacao"})
})
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne(optional = false)
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    private LocalDate dataMatricula = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private SituacaoMatricula situacao = SituacaoMatricula.CURSANDO;

    private Double notaFinal;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Usuario createdBy;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }

    public LocalDate getDataMatricula() { return dataMatricula; }
    public void setDataMatricula(LocalDate dataMatricula) { this.dataMatricula = dataMatricula; }

    public SituacaoMatricula getSituacao() { return situacao; }
    public void setSituacao(SituacaoMatricula situacao) { this.situacao = situacao; }

    public Double getNotaFinal() { return notaFinal; }
    public void setNotaFinal(Double notaFinal) { this.notaFinal = notaFinal; }

    public Usuario getCreatedBy() { return createdBy; }
    public void setCreatedBy(Usuario createdBy) { this.createdBy = createdBy; }
}