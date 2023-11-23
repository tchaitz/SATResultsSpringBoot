package com.sat.results.dao;

import com.sat.results.model.SATScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SATScoreRepository extends JpaRepository<SATScore,Long> {
    @Query("SELECT dense_rank()  OVER (ORDER BY score DESC) rank FROM SATScore s")
    List<Integer> getScoresWithRanks();
}
