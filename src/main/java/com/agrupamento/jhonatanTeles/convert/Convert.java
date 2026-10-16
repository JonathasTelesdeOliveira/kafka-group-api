package com.agrupamento.jhonatanTeles.convert;

import com.agrupamento.jhonatanTeles.dto.out.TransacaoSemSymbolDTO_out;
import com.agrupamento.jhonatanTeles.dto.out.TransacoesAgrupadasDTO_out;
import com.agrupamento.jhonatanTeles.infraestructure.TransacoesAgrupadasEntity;
import com.agrupamento.jhonatanTeles.infraestructure.TransacaoSemSymbolEntity;
import com.agrupamento.jhonatanTeles.dto.in.TransacaoSemSymbolDTO_in;
import com.agrupamento.jhonatanTeles.dto.in.TransacoesAgrupadasDTO_in;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class Convert {

    public List<TransacoesAgrupadasEntity> agrupamentoDTOinList_Para_EntitList(List<TransacoesAgrupadasDTO_in> dtoInList) {
        return  dtoInList.stream()
                .map(this::agrupamentoDTOParaEntity)
                .toList();
    }

    public TransacoesAgrupadasEntity agrupamentoDTOParaEntity(TransacoesAgrupadasDTO_in dtoIn) {
        TransacoesAgrupadasEntity agrupamentoEntity = TransacoesAgrupadasEntity.builder()
                .symbol(dtoIn.getSymbol())
                .build();
        List<TransacaoSemSymbolEntity> transactions = dtoIn
                .getTransacaoAgrupadasList()
                .stream()
                .map(this::transacaoDTO_para_TransacaoEntity)
                .toList();
        for(TransacaoSemSymbolEntity transaction: transactions){
            transaction.setAgrupamento(agrupamentoEntity);
        }
        agrupamentoEntity.setTransacoes(transactions);
        return agrupamentoEntity;
    }

    public TransacaoSemSymbolEntity transacaoDTO_para_TransacaoEntity(TransacaoSemSymbolDTO_in transSymbolDTO) {
            return TransacaoSemSymbolEntity.builder()
                    .tradeId(transSymbolDTO.getTradeId())
                    .dataSped(transSymbolDTO.getDataSped())
                    .lado(transSymbolDTO.getLado())
                    .quantycoinExe(transSymbolDTO.getQuantycoinExe())
                    .quantityDol(transSymbolDTO.getQuantityDol())
                    .symbolquantityDol(transSymbolDTO.getSymbol_quantity_Dol())
                    .price(transSymbolDTO.getPrice())
                    .taxa(transSymbolDTO.getTaxa())
                    .symboltaxa(transSymbolDTO.getSymbol_taxa())
                    .valorBrl(transSymbolDTO.getValorBrl())
                    .dataSpedUSDTBRL(transSymbolDTO.getDataSpedUSDTBRL())
                    .build();
    }




    public List<TransacoesAgrupadasDTO_out> agrupamentoEntitList_Para_DTO_outList(List<TransacoesAgrupadasEntity> tAgrupList) {
        return  tAgrupList.stream()
                .map(this::agrupamentoEntityParaDTO_out)
                .toList();
    }

    public TransacoesAgrupadasDTO_out agrupamentoEntityParaDTO_out(TransacoesAgrupadasEntity tgrupentity) {
        TransacoesAgrupadasDTO_out agrupamentoDTO_out = TransacoesAgrupadasDTO_out.builder()
                .id(tgrupentity.getId())
                .symbol(tgrupentity.getSymbol())
                .build();
        List<TransacaoSemSymbolDTO_out> transactionsDTO_out =
                tgrupentity.getTransacoes()
                .stream()
                .map(this::transacaoEntity_para_transacaoDTO_out)
                .toList();

        agrupamentoDTO_out.setTransacaoAgrupadasList(transactionsDTO_out);
        return agrupamentoDTO_out;
    }

    public TransacaoSemSymbolDTO_out transacaoEntity_para_transacaoDTO_out(TransacaoSemSymbolEntity tSymbolEntity) {
        return TransacaoSemSymbolDTO_out.builder()
                .id(tSymbolEntity.getId())
                .tradeId(tSymbolEntity.getTradeId())
                .dataSped(tSymbolEntity.getDataSped())
                .lado(tSymbolEntity.getLado())
                .quantycoinExe(tSymbolEntity.getQuantycoinExe())
                .quantityDol(tSymbolEntity.getQuantityDol())
                .symbol_quantity_Dol(tSymbolEntity.getSymbolquantityDol())
                .price(tSymbolEntity.getPrice())
                .taxa(tSymbolEntity.getTaxa())
                .symbol_taxa(tSymbolEntity.getSymboltaxa())
                .valorBrl(tSymbolEntity.getValorBrl())
                .dataSpedUSDTBRL(tSymbolEntity.getDataSpedUSDTBRL())
                .build();
    }
}