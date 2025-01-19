package org.imt.tournamentmaster.service.match;

import org.imt.tournamentmaster.model.equipe.Equipe;
import org.imt.tournamentmaster.model.match.Round;
import org.imt.tournamentmaster.model.match.ImportingReport;
import org.imt.tournamentmaster.model.match.Match;
import org.imt.tournamentmaster.repository.equipe.EquipeRepository;
import org.imt.tournamentmaster.repository.equipe.JoueurRepository;
import org.imt.tournamentmaster.repository.match.MatchRepository;
import org.imt.tournamentmaster.repository.match.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final EquipeRepository equipeRepository;
    private final RoundRepository roundRepository;
    private final JoueurRepository joueurRepository;

    @Autowired
    public MatchService(
        MatchRepository matchRepository,
        RoundRepository roundRepository,
        EquipeRepository equipeRepository,
        JoueurRepository joueurRepository
    ) {
        this.matchRepository = matchRepository;
        this.roundRepository = roundRepository;
        this.equipeRepository = equipeRepository;
        this.joueurRepository = joueurRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Match> getById(long id) {
        return matchRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(long id) {
        Optional<Match> match = matchRepository.findById(id);
        if (match.isPresent()) {
            matchRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<Match> getAll() {
        return StreamSupport.stream(matchRepository.findAll().spliterator(), false)
                .toList();
    }

    @Transactional(readOnly = true)
    public Integer getNumberOfRounds(long matchId) {
        return matchRepository.getNumberOfRounds(matchId);
    }

    @Transactional(readOnly = true)
    public List<Match> getMatchesOfATeam(long teamId) {
        return matchRepository.findByEquipe(teamId);
    }

    @Transactional(readOnly = true)
    public List<Match> getWonMatchesOfATeam(long teamId) {
        List<Match> matches = this.getMatchesOfATeam(teamId);
        if (matches.isEmpty())
            throw new Error("Pas de matches trouvés.");

        List<Match> wonMatches = new ArrayList<>();

        for (Match match : matches) {
            List<Round> rounds = roundRepository.findRoundsOfMatch(match.getId());

            /* Récupérer les équipes ayant joué dans le match */
            List<Equipe[]> equipes = this.equipeRepository.findTeamsFromAMatch(match.getId());
            Equipe equipeA = null;
            Equipe equipeB = null;
            for (Equipe[] row : equipes) {
                equipeA = (Equipe) row[0];
                equipeB = (Equipe) row[1];
            }

            /* Compteurs de victoires pour chaque équipe */
            int occurenceOfTeamAWinning = 0;
            int occurenceOfTeamBWinning = 0;

            /* Calcul des victoires pour chaque round */
            for (Round round : rounds) {
                long winningTeamId = equipeRepository.findWinnerTeamOfARound(round.getId());
                Optional<Equipe> winningTeam = equipeRepository.findById(winningTeamId);

                if (winningTeam.isEmpty())
                    ResponseEntity.notFound().build();

                if (winningTeam.isPresent()) {
                    if (winningTeam.get().equals(equipeA))
                        occurenceOfTeamAWinning++;
                    else if (winningTeam.get().equals(equipeB))
                        occurenceOfTeamBWinning++;
                }

            }

            // Vérification de l'équipe gagnante du match
            if (occurenceOfTeamAWinning > occurenceOfTeamBWinning && equipeA.getId() == teamId)
                wonMatches.add(match);
            else if (occurenceOfTeamBWinning > occurenceOfTeamAWinning && equipeB.getId() == teamId)
                wonMatches.add(match);
        }
        return wonMatches;
    }

    @Transactional
    public ArrayList<ImportingReport> createOne(Match[] matches){
        ArrayList<ImportingReport> reports = new ArrayList<>();
        for (Match match: matches){
            try {
                Match inserted = matchRepository.save(match);
                reports.add( new ImportingReport(ImportingReport.Status.OK, inserted.getId(), Timestamp.from(Instant.now())));
            }catch (Exception e){
                reports.add( new ImportingReport(ImportingReport.Status.FAILED, -1, Timestamp.from(Instant.now())) );
            }
        }
        return reports;
    }

    @Transactional
    public Match updateOne(long id, Match match){
        Optional<Match> potentialMatch = this.getById(id);
        if (potentialMatch.isPresent()){
            match.setId(potentialMatch.get().getId());
            return matchRepository.save(match);
        }else{
            throw new RuntimeException();
        }
    }
}
