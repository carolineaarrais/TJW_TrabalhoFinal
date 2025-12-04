package ifce.edu.br.controle_academico.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "disciplina")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String nome;

    private Integer cargaHoraria;

    private String semestre;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Usuario createdBy;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(Integer cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    public String getSemestre() { return semestre; }
    public void setSemestre(String semestre) { this.semestre = semestre; }

    public Usuario getCreatedBy() { return createdBy; }
    public void setCreatedBy(Usuario createdBy) { this.createdBy = createdBy; }
}