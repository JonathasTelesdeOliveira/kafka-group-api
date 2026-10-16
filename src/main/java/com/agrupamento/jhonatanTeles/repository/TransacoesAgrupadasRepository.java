package com.agrupamento.jhonatanTeles.repository;

import com.agrupamento.jhonatanTeles.infraestructure.TransacoesAgrupadasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransacoesAgrupadasRepository extends JpaRepository<TransacoesAgrupadasEntity, Long> {

Optional<TransacoesAgrupadasEntity> findBySymbol(String symbol);

}









