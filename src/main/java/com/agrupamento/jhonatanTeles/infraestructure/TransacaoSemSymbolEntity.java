package com.agrupamento.jhonatanTeles.infraestructure;

import com.agrupamento.jhonatanTeles.enums.Lado;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transacoes_agrupadas_itens")
public class TransacaoSemSymbolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TradeId")
    private Integer tradeId;

    @PastOrPresent(message = "Insira uma data válida.")
    @Column(name = "DATA_COMPRA")
    private LocalDateTime dataSped;

    @Column(name = "BUY_SELL")
    private Lado lado;

    @Column(name = "PRICE", precision = 18, scale = 8)
    private BigDecimal price;

    @Column(name = "TOTAL_SPENT", precision = 18, scale = 8)
    private BigDecimal quantycoinExe;

    @Column(name = "QUANTIDADE", precision = 18, scale = 8)
    private BigDecimal quantityDol;

    @Column(name = "SYMBOL_QUANTIDADE")
    private String symbolquantityDol;

    @Column(name = "FREE", precision = 18, scale = 8)
    private BigDecimal taxa;

    @Column(name = "Simbolo_TAXA")
    private String symboltaxa;

    @Column(name = "VALOR_BRL", precision = 18, scale = 8)
    private BigDecimal valorBrl;

    @Column(name = "DATA_COMPRA_USDTBRL")
    private LocalDateTime dataSpedUSDTBRL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agrupamento_id", nullable = false)
    private TransacoesAgrupadasEntity agrupamento;
}