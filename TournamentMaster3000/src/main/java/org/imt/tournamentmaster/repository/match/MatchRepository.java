package org.imt.tournamentmaster.repository.match;

import org.imt.tournamentmaster.model.match.Match;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {

    @Query("SELECT m FROM Match m WHERE m.equipeA.id = :teamId OR m.equipeB.id = :teamId ")
    List<Match> findByEquipe(long teamId);

    @Query("SELECT COUNT(DISTINCT (m.rounds)) FROM Match m")
    Integer getNumberOfRounds(long matchId);

    /*
    @Query("SELECT m FROM Match m WHERE (m.equipeA.id = :teamId OR m.equipeB.id = :teamId) AND m.status != 'TERMINE'")
    List<Match> findByEquipeNotEnd(long teamId);
     */



}
