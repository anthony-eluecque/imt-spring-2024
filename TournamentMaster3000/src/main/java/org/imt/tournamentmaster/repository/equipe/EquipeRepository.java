package org.imt.tournamentmaster.repository.equipe;

import org.imt.tournamentmaster.model.equipe.Equipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipeRepository extends CrudRepository<Equipe, Long> {

    @Query("SELECT m.equipeA, m.equipeB FROM Match m WHERE m.id = :matchId")
    List<Equipe[]> findTeamsFromAMatch(long matchId);

    @Query("SELECT " +
            "    CASE " +
            "        WHEN r.scoreA > r.scoreB THEN r.equipeA.id" +
            "        WHEN r.scoreA < r.scoreB THEN r.equipeB.id" +
            "    END AS gagnant " +
            "FROM Round r " +
            "WHERE r.id = :roundId")
    long findWinnerTeamOfARound(long roundId);


}
