package com.agrupamento.jhonatanTeles.dto.in;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacoesAgrupadasDTO_in {
    private String symbol;
    private List<TransacaoSemSymbolDTO_in> transacaoAgrupadasList;
}
