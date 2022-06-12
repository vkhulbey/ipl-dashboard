package com.springboot.ms.ipldashboard.controllers;

import com.springboot.ms.ipldashboard.model.Match;
import com.springboot.ms.ipldashboard.model.Team;
import com.springboot.ms.ipldashboard.repositories.MatchRepository;
import com.springboot.ms.ipldashboard.repositories.TeamRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
public class TeamController {

    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;

    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    @GetMapping("/team")
    public Iterable<Team> getAllTeam() {
        return teamRepository.findAll();
    }

    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable("teamName") String teamName) {
        Team team = teamRepository.findByTeamName(teamName);

        team.setRecentMatches(matchRepository.findTop4ByTeam1OrTeam2OrderByDateDesc(teamName, teamName));
        return team;
    }

    @GetMapping("/team/{teamName}/matches")
    public List<Match> getMatchesForTeam(@PathVariable("teamName") String teamName, @RequestParam("year") int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year + 1, 1, 1);

        return matchRepository.getMatchesByTeamBetweenDates(teamName, startDate, endDate);
    }
}

