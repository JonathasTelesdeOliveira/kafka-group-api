package com.agrupamento.jhonatanTeles.busines;

import com.agrupamento.jhonatanTeles.out.TransacaoKafkaDTO;
import com.agrupamento.jhonatanTeles.out.TransacaoSemSymbolDTO;
import com.agrupamento.jhonatanTeles.out.TransacoesAgrupadasDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@AllArgsConstructor
@Component
public class AgrupamentoService {
    private List<TransacoesAgrupadasDTO> transacoesAgrupadasDTOList = new ArrayList<>();

    @KafkaListener(topics = "transacoes-processadas", groupId = "agrupamento-a1")
    public void testerecebendoDadosuploadBRL(List<TransacaoKafkaDTO> tList){
        log.info("Mensagem recebida do Kafka tamanho: {}", tList.size());
        for(TransacaoKafkaDTO list: tList) {
            log.info("Mensagem recebida do Kafka: {}", tList);
        }
        if(tList.isEmpty()) {
            return;
        }
        // Gera os DTOs agrupados a partir da lista que veio do Kafka
        Map<String, List<TransacaoSemSymbolDTO>> listmapAgrupadas = agruparPorSymbol(tList);

        List<TransacoesAgrupadasDTO> listaAgrupada = new ArrayList<>();
        for (Map.Entry<String, List<TransacaoSemSymbolDTO>> entry : listmapAgrupadas.entrySet()) {
            TransacoesAgrupadasDTO build = TransacoesAgrupadasDTO.builder()
                    .symbol(entry.getKey())
                    .transacaoAgrupadasList(entry.getValue())
                    .build();
            listaAgrupada.add(build);
        }
        // Atualiza/adiciona na lista principal
        for(TransacoesAgrupadasDTO novoAgrupados : listaAgrupada){
            adicionarOuAtualizar(novoAgrupados);
        }
    }

    public Map<String, List<TransacaoSemSymbolDTO>> agruparPorSymbol(List<TransacaoKafkaDTO> transacoes) {
        return transacoes.stream()
                .collect(Collectors.groupingBy(
                        TransacaoKafkaDTO::getSymbol, // Chave do Map (String)
                Collectors.mapping(
                        t -> new TransacaoSemSymbolDTO( // Transforma cada item no DTO sem symbol
                                            t.getId(),
                                            t.getTradeId(),
                                            t.getDataSped(),
                                            t.getLado(),
                                            t.getQuantycoinExe(),
                                            t.getQuantityDol(),
                                            t.getSymbol_quantity_Dol(),
                                            t.getPrice(),
                                            t.getTaxa(),
                                            t.getSymbol_taxa(),
                                            t.getValorBrl(),
                                            t.getDataSpedUSDTBRL()
                                    ),
                        Collectors.toList()
                    )
                ));
    }

    public void adicionarOuAtualizar(TransacoesAgrupadasDTO novoAgrupado) {
        // Procura se o símbolo (ex: "BTCUSDT") já existe na nossa lista
        TransacoesAgrupadasDTO existente = transacoesAgrupadasDTOList
                .stream()
                .filter(item -> item.getSymbol() != null && item.getSymbol().equalsIgnoreCase(novoAgrupado.getSymbol()))
                .findFirst()
                .orElse(null);
        if(existente != null) {
            existente.getTransacaoAgrupadasList().addAll(novoAgrupado.getTransacaoAgrupadasList());
        }else {
            transacoesAgrupadasDTOList.add(novoAgrupado);
        }
    }

    public List<TransacoesAgrupadasDTO> listarTransactionsagrupadas() {
        return transacoesAgrupadasDTOList;
    }
}




