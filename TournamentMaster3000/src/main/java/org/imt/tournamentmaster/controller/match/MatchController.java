package org.imt.tournamentmaster.controller.match;

import org.imt.tournamentmaster.model.match.Match;
import org.imt.tournamentmaster.service.match.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/match")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> getById(@PathVariable long id) {
        Optional<Match> match = matchService.getById(id);

        return match.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("")
    public List<Match> getAll() {
        return matchService.getAll();
    }

    @GetMapping("/{matchId}/roundsnumber")
    public Integer getRoundsNumber(@PathVariable long matchId) {
        Integer roundsNumber = matchService.getNumberOfRounds(matchId);
        if (roundsNumber == null)
            ResponseEntity.notFound().build();
        return roundsNumber;
    }

    @GetMapping("/equipe/{teamId}")
    public List<Match> getMatchsOfATeam(@PathVariable long teamId) {
        List<Match> matches = matchService.getMatchesOfATeam(teamId);
        if (matches.isEmpty())
            ResponseEntity.notFound().build();

        return matches;
    }

    @GetMapping("/equipe/{teamId}/matchs-won")
    public List<Match> getWonMatchsOfATeam(@PathVariable long teamId) {
        List<Match> matches = matchService.getWonMatchesOfATeam(teamId);
        if (matches.isEmpty())
            ResponseEntity.notFound().build();

        return matches;
    }
}
