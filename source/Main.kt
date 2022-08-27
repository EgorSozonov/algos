package tech.sozonov.algos;
import tech.sozonov.algos.Knapsack.knapsack
import tech.sozonov.algos.Knapsack.knapsackRunner
import tech.sozonov.algos.LinkedList.LinkedList
import tech.sozonov.algos.FootballTournament.FootballTournament

fun main(args: Array<String>) {
    //knapsackRunner()

    //LinkedList.linkedListRunner()
    val tournamentTable = FootballTournament.tournamentRunner()
    println(tournamentTable)
}
