package com.agrupamento.jhonatanTeles.busines;

import com.agrupamento.jhonatanTeles.convert.Convert;
import com.agrupamento.jhonatanTeles.dto.out.TransacoesAgrupadasDTO_out;
import com.agrupamento.jhonatanTeles.exeptions.ConflictExeption;
import com.agrupamento.jhonatanTeles.infraestructure.TransacaoSemSymbolEntity;
import com.agrupamento.jhonatanTeles.infraestructure.TransacoesAgrupadasEntity;
import com.agrupamento.jhonatanTeles.dto.out.TransacaoKafkaDTO;
import com.agrupamento.jhonatanTeles.dto.in.TransacaoSemSymbolDTO_in;
import com.agrupamento.jhonatanTeles.dto.in.TransacoesAgrupadasDTO_in;
import com.agrupamento.jhonatanTeles.repository.TransacoesAgrupadasRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Component
public class AgrupamentoService {
    private final Convert convert;
    private final TransacoesAgrupadasRepository transrepository;
    private List<TransacoesAgrupadasDTO_in> transacoesAgrupadasDTOList = new ArrayList<>();
    private List<TransacoesAgrupadasEntity> transacoesAgrupadasEntityList = new ArrayList<>();
    private List<TransacoesAgrupadasDTO_out> dtoOutList= new ArrayList<>();



    public AgrupamentoService(Convert convert, TransacoesAgrupadasRepository transrepository) {
        this.convert = convert;
        this.transrepository = transrepository;
    }

    @KafkaListener(topics = "transacoes-processadas", groupId = "agrupamento-a1")
    public void testerecebendoDadosuploadBRL(List<TransacaoKafkaDTO> tList){
     log.info("Mensagem recebida do Kafka tamanho: {}", tList.size());
        for(TransacaoKafkaDTO list: tList) {log.info("Mensagem recebida do Kafka: {}", tList);}
        if(tList.isEmpty()) {return;}
        // Gera os DTOs agrupados a partir da lista que veio do Kafka
        try {
            Map<String, List<TransacaoSemSymbolDTO_in>> listmapAgrupadas = agruparPorSymbol(tList);
            List<TransacoesAgrupadasDTO_in> listaAgrupada = new ArrayList<>();
            for (Map.Entry<String, List<TransacaoSemSymbolDTO_in>> entry : listmapAgrupadas.entrySet()) {
                TransacoesAgrupadasDTO_in build = TransacoesAgrupadasDTO_in.builder()
                        .symbol(entry.getKey())
                        .transacaoAgrupadasList(entry.getValue())
                        .build();
                listaAgrupada.add(build);
            }
            // Atualiza/adiciona na lista principal
            for(TransacoesAgrupadasDTO_in novoAgrupados : listaAgrupada){
                adicionarOuAtualizar(novoAgrupados);
            }
            savetransactionsagrupadas(listaAgrupada);
        } catch (Exception e) {
            // Isso vai imprimir o erro exato no console com todas as letras vermelhas
            log.error("ERRO AO PROCESSAR MENSAGEM DO KAFKA: ", e);
            throw new ConflictExeption("Erro no metodo testerecebendoDadosuploadBRL: " + e);
        }
    }

    public Map<String, List<TransacaoSemSymbolDTO_in>> agruparPorSymbol(List<TransacaoKafkaDTO> transacoes) {
        return transacoes.stream()
                .collect(Collectors.groupingBy(
                        TransacaoKafkaDTO::getSymbol, // Chave do Map (String)
                Collectors.mapping(
                        t -> new TransacaoSemSymbolDTO_in( // Transforma cada item no DTO sem symbol
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
                        ),Collectors.toList())
                ));
    }

    public void adicionarOuAtualizar(TransacoesAgrupadasDTO_in novoAgrupado) {
        // Procura se o símbolo (ex: "BTCUSDT") já existe na nossa lista
        TransacoesAgrupadasDTO_in existente = transacoesAgrupadasDTOList
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

    public void savetransactionsagrupadas(List<TransacoesAgrupadasDTO_in> listaAgrupada) {
        if (listaAgrupada.isEmpty()) {
            throw new IllegalArgumentException("No método savetransactionsagrupadas() chamei o transacoesAgrupadasDTOList porem a lista está vazia ainda não recebeu o TransacoesAgrupadasDTO");
        }
        transacoesAgrupadasEntityList = convert.agrupamentoDTOinList_Para_EntitList(listaAgrupada);

        if (transacoesAgrupadasEntityList.isEmpty()){
            log.info("Erro ao converter para Enity");
        }
        for(TransacoesAgrupadasEntity novaEntity: transacoesAgrupadasEntityList) {
            TransacoesAgrupadasEntity existente = transrepository.findBySymbol(novaEntity.getSymbol()).orElse(null);
            if(existente != null) {
                for (TransacaoSemSymbolEntity novaTrancao : novaEntity.getTransacoes()) {
                    novaTrancao.setAgrupamento(existente);
                    existente.getTransacoes().add(novaTrancao);
                }
                transrepository.save(existente);
            } else {
                transrepository.save(novaEntity);
            }
        }
    }
    @Transactional(readOnly = true)
    public List<TransacoesAgrupadasDTO_out> listartransactionsagrupadas() {
      List<TransacoesAgrupadasEntity> entities = transrepository.findAll();
        if (entities.isEmpty()){
            throw new ConflictExeption("Nenhuma transações encontrada.");
        }
        dtoOutList = convert.agrupamentoEntitList_Para_DTO_outList(entities);
        if(dtoOutList.isEmpty()){
           throw new ConflictExeption("Não convertido lista entity para dto_ouLIst: ");
        }
        return dtoOutList;
    }
}


