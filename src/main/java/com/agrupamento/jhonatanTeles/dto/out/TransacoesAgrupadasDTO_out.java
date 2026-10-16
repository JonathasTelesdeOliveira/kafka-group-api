package com.agrupamento.jhonatanTeles.dto.out;

import com.agrupamento.jhonatanTeles.dto.in.TransacaoSemSymbolDTO_in;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransacoesAgrupadasDTO_out {
    Long id;
    private String symbol;
    private List<TransacaoSemSymbolDTO_out> transacaoAgrupadasList;
}



