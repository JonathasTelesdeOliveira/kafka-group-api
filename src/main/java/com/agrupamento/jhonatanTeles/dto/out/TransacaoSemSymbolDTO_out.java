package com.agrupamento.jhonatanTeles.dto.out;

import com.agrupamento.jhonatanTeles.enums.Lado;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoSemSymbolDTO_out {
    private Long id;
    private Integer tradeId;
    private LocalDateTime dataSped;
    private Lado lado;
    private BigDecimal quantycoinExe;
    private BigDecimal quantityDol;
    private String symbol_quantity_Dol;
    private BigDecimal price;
    private BigDecimal taxa;
    private String symbol_taxa;
    private BigDecimal valorBrl;
    private LocalDateTime dataSpedUSDTBRL;
}







