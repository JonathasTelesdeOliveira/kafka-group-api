package com.agrupamento.jhonatanTeles.controller;

import com.agrupamento.jhonatanTeles.busines.AgrupamentoService;
import com.agrupamento.jhonatanTeles.out.TransacoesAgrupadasDTO;
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
    @ApiResponse(responseCode = "200", description = "busca com sucesso!")
    @ApiResponse(responseCode = "400", description = "erro de requisisão.")
    @ApiResponse(responseCode = "401", description = "Lista não cadastrado")
    public ResponseEntity<List<TransacoesAgrupadasDTO>> ListarTransactions(){
        return ResponseEntity.ok(service.listarTransactionsagrupadas());
    }
}




