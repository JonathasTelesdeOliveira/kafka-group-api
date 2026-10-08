package com.agrupamento.jhonatanTeles.out;

import com.agrupamento.jhonatanTeles.enums.Lado;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoSemSymbolDTO(
        Long id,
        Integer tradeId,
        LocalDateTime dataSped,
        Lado lado,
        BigDecimal quantycoinExe,
        BigDecimal quantityDol,
        String symbol_quantity_Dol,
        BigDecimal price,
        BigDecimal taxa,
        String symbol_taxa,
        BigDecimal valorBrl,
        LocalDateTime dataSpedUSDTBRL
) {

}






