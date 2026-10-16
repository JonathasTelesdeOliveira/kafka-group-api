package com.agrupamento.jhonatanTeles.controller;

import com.agrupamento.jhonatanTeles.busines.AgrupamentoService;
import com.agrupamento.jhonatanTeles.dto.in.TransacoesAgrupadasDTO_in;
import com.agrupamento.jhonatanTeles.dto.out.TransacoesAgrupadasDTO_out;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class AgrupamentoController {
    private final AgrupamentoService service;

    public AgrupamentoController(AgrupamentoService service) {
        this.service = service;

    }

    @GetMapping("Transactions")
    @Operation(summary = "buscar", description = "buscar Lista de códogo de Cripto")
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!")
    @ApiResponse(responseCode = "400", description = "Erro de requisição.")
    @ApiResponse(responseCode = "409", description = "Conflito: Nenhuma transação encontrada.")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    public ResponseEntity<List<TransacoesAgrupadasDTO_out>> ListarTransactions(){
        return ResponseEntity.ok(service.listartransactionsagrupadas());
    }
}




