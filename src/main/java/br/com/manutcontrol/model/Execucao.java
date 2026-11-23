package br.com.manutcontrol.model;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity @Table(name="execucoes") public class Execucao {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @ManyToOne(optional=false) @JoinColumn(name="plano_id",nullable=false) public Plano plano;
 @ManyToOne(optional=false) @JoinColumn(name="tecnico_id",nullable=false) public Tecnico tecnico;
 @Column(nullable=false) public LocalDate dataExecucao;
 @Column(length=2000) public String observacoes;
}
