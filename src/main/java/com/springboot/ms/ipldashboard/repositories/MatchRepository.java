package com.springboot.ms.ipldashboard.repositories;

import com.springboot.ms.ipldashboard.model.Match;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MatchRepository extends CrudRepository<Match, Long> {

    List<Match> findTop4ByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2);

    @Query("select m from Match m where (m.team1 = :teamName or m.team2 = :teamName)" +
            "and (m.date between :startDate and :endDate) order by date desc"
    )
    List<Match> getMatchesByTeamBetweenDates(@Param("teamName") String teamName,
                                             @Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);
}
