package com.agrupamento.jhonatanTeles.infraestructure;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transacoes_agrupadas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransacoesAgrupadasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String symbol;

    @OneToMany(
            mappedBy = "agrupamento",
            cascade = CascadeType.ALL
    )
    @Builder.Default
    private List<TransacaoSemSymbolEntity> transacoes = new ArrayList<>();
}