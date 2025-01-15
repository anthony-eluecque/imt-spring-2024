package org.imt.tournamentmaster.controller.equipe;

import org.imt.tournamentmaster.model.equipe.Equipe;
import org.imt.tournamentmaster.service.equipe.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipe")
public class EquipeController {

    private final EquipeService equipeService;

    @Autowired
    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipe> getById(@PathVariable long id) {
        Optional<Equipe> equipe = equipeService.getById(id);

        return equipe.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Equipe> getAll() {
        return equipeService.getAll();
    }

    @GetMapping("/{matchId}")
    public List<Equipe[]> findEquipesFromAMatch(@PathVariable long matchId) {
        List<Equipe[]> equipes = equipeService.getTeamsFromAMatch(matchId);
        if (equipes.isEmpty())
            ResponseEntity.notFound().build();
        return equipes;
    }

    @GetMapping("/winner/{roundId}")
    public long findWinnerTeamFromARound(@PathVariable long roundId) {
        long equipe = equipeService.getWinnerTeamFromARound(roundId);
        return equipe;
    }

}
