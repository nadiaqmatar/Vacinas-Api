package com.github.nadia.vacinasapi.domain.repository;

import com.github.nadia.vacinasapi.domain.entity.Vacina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacinaRepository extends JpaRepository<Vacina, Long> {

}
