package org.imt.tournamentmaster.service.match;

import org.imt.tournamentmaster.model.match.ImportingReport;
import org.imt.tournamentmaster.model.match.Match;
import org.imt.tournamentmaster.repository.equipe.EquipeRepository;
import org.imt.tournamentmaster.repository.equipe.JoueurRepository;
import org.imt.tournamentmaster.repository.match.MatchRepository;
import org.imt.tournamentmaster.repository.match.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final RoundRepository roundRepository;
    private final EquipeRepository equipeRepository;
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

    @Transactional
    public ArrayList<ImportingReport> createOne(Match[] matches){
        ArrayList<ImportingReport> reports = new ArrayList<>();
        for (Match match: matches){
            try {
                Match inserted = matchRepository.save(match);
                reports.add( new ImportingReport(ImportingReport.Status.OK, inserted.getId()) );
            }catch (Exception e){
                reports.add( new ImportingReport(ImportingReport.Status.FAILED, -1) );
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
