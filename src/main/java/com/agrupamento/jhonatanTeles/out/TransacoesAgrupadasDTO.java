package com.agrupamento.jhonatanTeles.out;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacoesAgrupadasDTO {
    private String symbol;
    private List<TransacaoSemSymbolDTO> transacaoAgrupadasList;
}
