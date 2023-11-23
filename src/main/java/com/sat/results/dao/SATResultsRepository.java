package com.sat.results.dao;

import com.sat.results.model.SATResultsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SATResultsRepository extends JpaRepository<SATResultsModel, String> {

    SATResultsModel findByName(String name);

    void deleteByName(String name);
}
