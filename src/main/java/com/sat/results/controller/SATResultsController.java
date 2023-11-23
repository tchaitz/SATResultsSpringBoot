package com.sat.results.controller;

import com.sat.results.dao.SATResultsRepository;
import com.sat.results.dao.SATScoreRepository;
import com.sat.results.model.SATResultsModel;
import com.sat.results.model.SATScore;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SATResultsController {
    @Autowired
    private SATResultsRepository satResultsRepository;
    @Autowired
    private SATScoreRepository satScoreRepository;

    @PostMapping("/save-results")
    public ResponseEntity<String> saveResults(@RequestBody SATResultsModel satResultsModel) {
        if (satResultsModel.getSatScore().getScore() > 30) {
            satResultsModel.getSatScore().setPassed("Pass");
        } else {
            satResultsModel.getSatScore().setPassed("Fail");
        }
        satResultsRepository.save(satResultsModel);
        return ResponseEntity.ok("Results saved");
    }

    @GetMapping("/results")
    public ResponseEntity<List<SATResultsModel>> getAllResults() {
        List<SATResultsModel> results = satResultsRepository.findAll();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/results/{name}")
    public int getRank(@PathVariable String name) {
        SATResultsModel satResultsModel = satResultsRepository.findByName(name);
        Long id = satResultsModel.getSatScore().getId();
        int rank;
        try {
            rank = satScoreRepository.getScoresWithRanks().get(Math.toIntExact(id) - 1);
        } catch (
                IndexOutOfBoundsException exception
        ) {
            return 1;
        }
        return rank;
    }

    @PutMapping("/results/{name}")
    public SATResultsModel updateResult(@PathVariable String name, @RequestBody SATScore rxSatScore) {
        SATResultsModel satResultsModel = satResultsRepository.findByName(name);
        Long id = satResultsModel.getSatScore().getId();
        Optional<SATScore> optionalSATScore = satScoreRepository.findById(id);
        if (optionalSATScore.isPresent()) {
            SATScore satScore = optionalSATScore.get();
            satScore.setScore(rxSatScore.getScore());
            satScoreRepository.save(satScore);
            satResultsModel.setSatScore(satScore);
            satResultsRepository.save(satResultsModel);
        } else {
            return null;
        }
        return satResultsModel;
    }

    @DeleteMapping("/results/{name}")
    @Transactional
    public String deleteResult(@PathVariable String name) {
        satResultsRepository.deleteByName(name);
        return "deleted " + name;
    }
}
