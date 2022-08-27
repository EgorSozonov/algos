package tech.sozonov.algos.FootballTournament

import java.lang.Integer.max

/**
 * Input: a list of lines like "TeamA - TeamB - 1:2
 * Output: a string of the football tournament results like:
 *       | TeamA | TeamB | TeamC | W | D | L | Pts
 * TeamA |       | W     | D     | 1 | 1 | 0 | 4
 */
object FootballTournament {
    private fun parseMatches(inp: String): HashMap<String, Team> {
        val lines = inp.split("\n")
        val result = HashMap<String, Team>()
        for (line in lines) {
            val splitByHyphen: List<String> = line.split("-").map(String::trim)
            if (splitByHyphen.size != 3) throw IllegalArgumentException("Incorrectly formatted input line: " + line)
            val name1 = splitByHyphen[0]
            val name2 = splitByHyphen[1]
            val scores = splitByHyphen[2].split(":").map(String::trim)
            if (scores.size != 2) throw IllegalArgumentException("Incorrectly formatted score in input line: " + scores)
            val goals1 = Integer.parseInt(scores[0])
            val goals2 = Integer.parseInt(scores[1])

            addTeam(name1, result)
            addTeam(name2, result)
            val team1 = result.get(name1)!!
            val team2 = result.get(name2)!!
            if (goals1 > goals2) {
                team1.wins += 1
                team2.losses += 1
                team1.matches.add(Match(name2, GameResult.win))
                team2.matches.add(Match(name1, GameResult.loss))
                team1.points += 3
            } else if (goals1 < goals2) {
                team1.losses += 1
                team2.wins += 1
                team1.matches.add(Match(name2, GameResult.loss))
                team2.matches.add(Match(name1, GameResult.win))
                team2.points += 3
            } else {
                team1.draws += 1
                team2.draws += 1
                team1.matches.add(Match(name2, GameResult.draw))
                team2.matches.add(Match(name1, GameResult.draw))
                team1.points += 1
                team2.points += 1
            }
        }
        return result
    }

    private fun sortByPoints(teamDict: HashMap<String, Team>): Array<Team> {
        val result: Array<Team> = teamDict.values.toTypedArray()
        result.sortByDescending { it.points }
        return result
    }

    fun tournamentRunner(): String {
        val inp = """
            Амкар - Спартак - 2:1
            Краснодар - ЦСКА - 2:2
            Амкар - ЦСКА - 1:0
            Краснодар - Спартак - 3:3
            ЦСКА - Спартак - 1:4
            Краснодар - Амкар - 1:1
        """.trimIndent()
        val teamDict = parseMatches(inp)
        val teamsStanding = sortByPoints(teamDict)
        return formattedTournamentTable(teamsStanding)
    }

    private fun formattedTournamentTable(sortedTeams: Array<Team>): String {
        val wr = StringBuilder()
        val colWidths: IntArray = determineWidths(sortedTeams)
        printHeader(sortedTeams, colWidths, wr)
        printRows(sortedTeams, colWidths, wr)
        return wr.toString()
    }

    private fun determineWidths(sortedTeams: Array<Team>): IntArray {
        val result = IntArray(sortedTeams.size + 5)
        val maxTeamLength: Int = sortedTeams.fold(0){acc: Int, team1: Team  -> max(acc, team1.name.length)} + 2
        for (i in 0 .. sortedTeams.size) {
            result[i] = maxTeamLength
        }
        for (i in (sortedTeams.size + 1) until result.size) {
            result[i] = 3
        }
        return result
    }

    private fun printHeader(sortedTeams: Array<Team>, colWidths: IntArray, wr: StringBuilder) {
        wr.append("|")
        wr.append(" ".repeat(colWidths[0]))
        wr.append("|")
        var i = 1
        for (team in sortedTeams) {
            wr.append(team.name)
            wr.append(" ".repeat(colWidths[i] - team.name.length))
            wr.append("|")
            i += 1
        }
        wr.append(" W |")
        wr.append(" D |")
        wr.append(" L |")
        wr.append("Pts|")
        wr.append("\n")

        wr.append(".")
        for (wid in colWidths) {
            wr.append("_".repeat(wid))
            wr.append(".")
        }
        wr.append("\n")
    }

    private fun printRows(sortedTeams: Array<Team>, colWidths: IntArray, wr: StringBuilder) {
        for (team in sortedTeams) {
            wr.append("|")
            wr.append(team.name)
            wr.append(" ".repeat(colWidths[0] - team.name.length))
            wr.append("|")
            var i = 1
            for (againstTeam in sortedTeams) {
                wr.append(" ".repeat(colWidths[i] - 2))
                val gameResult: Match? = team.matches.firstOrNull { it.againstTeam == againstTeam.name }
                if (gameResult == null) {
                    wr.append("-")
                } else {
                    when (gameResult.result) {
                        GameResult.win-> wr.append("W")
                        GameResult.draw-> wr.append("D")
                        GameResult.loss-> wr.append("L")
                    }
                }

                wr.append(" |")
                i += 1
            }
            wr.append(" ${team.wins} |")
            wr.append(" ${team.draws} |")
            wr.append(" ${team.losses} |")
            wr.append(" ${team.points} |")

            wr.append("\n")
        }

    }


    private fun addTeam(name: String, teamDict: HashMap<String, Team>) {
        if (teamDict.containsKey(name)) {
            return
        } else {
            val newTeam = Team(name, 0, 0, 0, 0, mutableListOf())
            teamDict.put(name, newTeam)
        }
    }


}

enum class GameResult {
    win, loss, draw
}

data class Match(val againstTeam: String, val result: GameResult)
data class Team(val name: String, var wins: Int, var losses: Int, var draws: Int, var points: Int, val matches: MutableList<Match>)