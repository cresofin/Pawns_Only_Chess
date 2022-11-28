package chess

import kotlin.math.abs

val table = mutableListOf(
    mutableListOf(" ", " "," "," ","a"," "," "," ","b"," "," "," ","c"," "," "," ","d"," ",
        " "," ","e"," "," "," ","f"," "," "," ","g"," "," "," ","h"," "," "),
    mutableListOf("1", " ","|"," "," "," ","|"," "," "," ","|"," "," "," ","|"," "," "," ",
        "|"," "," "," ","|"," "," "," ","|"," "," "," ","|"," "," "," ","|"),
    mutableListOf("2", " ","|"," ","W"," ","|"," ","W"," ","|"," ","W"," ","|"," ","W"," ",
        "|"," ","W"," ","|"," ","W"," ","|"," ","W"," ","|"," ","W"," ","|"),
    mutableListOf("3", " ","|"," "," "," ","|"," "," "," ","|"," "," "," ","|"," "," "," ",
        "|"," "," "," ","|"," "," "," ","|"," "," "," ","|"," "," "," ","|"),
    mutableListOf("4", " ","|"," "," "," ","|"," "," "," ","|"," "," "," ","|"," "," "," ",
        "|"," "," "," ","|"," "," "," ","|"," "," "," ","|"," "," "," ","|"),
    mutableListOf("5", " ","|"," "," "," ","|"," "," "," ","|"," "," "," ","|"," "," "," ",
        "|"," "," "," ","|"," "," "," ","|"," "," "," ","|"," "," "," ","|"),
    mutableListOf("6", " ","|"," "," "," ","|"," "," "," ","|"," "," "," ","|"," "," "," ",
        "|"," "," "," ","|"," "," "," ","|"," "," "," ","|"," "," "," ","|"),
    mutableListOf("7", " ","|"," ","B"," ","|"," ","B"," ","|"," ","B"," ","|"," ","B"," ",
        "|"," ","B"," ","|"," ","B"," ","|"," ","B"," ","|"," ","B"," ","|"),
    mutableListOf("8", " ","|"," "," "," ","|"," "," "," ","|"," "," "," ","|"," "," "," ",
        "|"," "," "," ","|"," "," "," ","|"," "," "," ","|"," "," "," ","|"))

val sepTab = mutableListOf(" "," ","+", "-","-","-","+", "-","-","-","+", "-","-","-","+", "-","-","-",
    "+", "-","-","-","+", "-","-","-","+", "-","-","-","+", "-","-","-","+")

var turn = 1
var antPosition = "a2a3"
var position = ""
var passant = 0
var whitePawn = 8
var blackPawn = 8
var winner = ""
var checkPosition = mutableListOf<Int>()


fun main() {
    println("Pawns-Only Chess\nFirst Player's name:")
    val firstPlayer = readln()
    println("Second Player's name:")
    val secondPlayer = readln()
    //val regex = "[a-h][1-8][a-h][1-8]".toRegex()
    for (i in 8 downTo 0) {
        println(sepTab.joinToString(""))
        println(table[i].joinToString(""))
    }
    loop@while (true) {
        if (turn % 2 != 0)println("$firstPlayer's turn:") else println("$secondPlayer's turn: ")
        position = readln()
        if (position == "exit"){
            println("Bye!")
            return
        }
        if (position.isEmpty()) {
            println("Invalid input")
            continue
        }
        checkPosition = mutableListOf()
        for (i in position) {
            val change = when(i){
                'a' -> 4
                'b' -> 8
                'c' -> 12
                'd' -> 16
                'e' -> 20
                'f' -> 24
                'g' -> 28
                'h' -> 32
                else -> if (i.isLetter()){
                    println("Invalid input")
                    continue@loop
                }else {
                    i.digitToInt()
                }
            }
            checkPosition.add(change)
        }
        passant = if (abs(antPosition[1].digitToInt() - antPosition[3].digitToInt()) == 2 &&
            (antPosition[3].digitToInt() == 5 ||antPosition[3].digitToInt() == 4) &&
            abs(position[0] - antPosition[2]) == 1 ) 1 else 0


        if (turn % 2 != 0 && position[1] > position[3] || turn % 2 == 0 && position[3] > position[1] ||
            (position[0] == position[2] && position[1] == position[3] && table[checkPosition[1]][checkPosition[0]] != " ") ||
            (turn % 2 != 0 && position[1].digitToInt() != 2 && position[3] - position[1] >= 2) ||
            (turn % 2 != 0 && position[0] == position[2] && table[checkPosition[3]][checkPosition[2]] == "B") ||
            (turn % 2 != 0 && table[checkPosition[3]][checkPosition[2]] == "W") ||
            (turn % 2 == 0 && table[checkPosition[3]][checkPosition[2]] == "B") ||
            (checkPosition[1] !in 1..8 || checkPosition[3] !in 1..8) ||
            (passant == 0 && abs(checkPosition[3] - checkPosition[1]) == 1 && abs(position[0] - position[2]) == 1) &&
            table[checkPosition[3]][checkPosition[2]] == " " || (abs(checkPosition[3] - checkPosition[1]) > 2) ||
            (abs(checkPosition[2] - checkPosition[0]) > 4)){
            println("Invalid input")
            continue
        }
        if (turn % 2 != 0 && (table[checkPosition[1]][checkPosition[0]] == "B" || table[checkPosition[1]][checkPosition[0]] == " ")) {
            println("No white pawn at ${position[0]}${position[1]}")
            continue
        }
        if (turn % 2 == 0 && (table[checkPosition[1]][checkPosition[0]] == "W" || table[checkPosition[1]][checkPosition[0]] == " ")) {
            println("No black pawn at ${position[0]}${position[1]}")
            continue
        }
        updatePosition(checkPosition)
        for (i in 8 downTo 0) {
            println(sepTab.joinToString(""))
            println(table[i].joinToString(""))
        }
        checkWin()
        if (winner == "whitePawns") {
            println("White Wins!\nBye!")
            return
        }
        if (winner == "blackPawns") {
            println("Black Wins!\nBye!")
            return
        }
        if (winner == "Stalemate!") {
            println("Stalemate!\nBye!")
            return
        }
    }
}

fun checkWin() {
    if (table[8].joinToString("").contains('W')) winner = "whitePawns"
    if (table[1].joinToString("").contains('B')) winner = "blackPawns"

    if (whitePawn == 0) winner = "blackPawns"

    if (blackPawn == 0) winner = "whitePawns"

    if (turn % 2 == 0 && blackPawn == 1 && table[checkPosition[3] + 1][checkPosition[2]] == "B"){
        if (checkPosition[2] - 4 >= 4 && table[checkPosition[3]][checkPosition[2] - 4] == " " &&
            checkPosition[2] + 4 <= 32 && table[checkPosition[3]][checkPosition[2] + 4] == " " ) {
            winner = "Stalemate!"
        }
        if(checkPosition[2] == 32 && table[checkPosition[3]][checkPosition[2] - 4] == " ") winner = "Stalemate!"
        if(checkPosition[2] == 4 && table[checkPosition[3]][checkPosition[2] + 4] == " ") winner = "Stalemate!"
    }

    if (turn % 2 != 0 && whitePawn == 1 && table[checkPosition[3] - 1][checkPosition[2]] == "W"){
        if (checkPosition[2] - 4 >= 4 && table[checkPosition[3]][checkPosition[2] - 4] == " " &&
            checkPosition[2] + 4 <= 32 && table[checkPosition[3]][checkPosition[2] + 4] == " " ) {
            winner = "Stalemate!"
        }
        if(checkPosition[2] == 32 && table[checkPosition[3]][checkPosition[2] - 4] == " ") winner = "Stalemate!"
        if(checkPosition[2] == 4 && table[checkPosition[3]][checkPosition[2] + 4] == " ") winner = "Stalemate!"
    }
}

fun updatePosition(checkPosition : MutableList<Int>) {
    if (turn % 2 != 0 && (checkPosition[0] + 4 <= 32 || checkPosition[0] == 32) && (checkPosition[0] - 4 >= 4 || checkPosition[0] == 4)) {
        if (passant == 1 && position[2] == antPosition[2] && checkPosition[3] - antPosition[3].digitToInt() == 1){
            table[checkPosition[3]][checkPosition[2]] = "W"
            table[checkPosition[3] - 1][checkPosition[2]] = " "
            table[checkPosition[1]][checkPosition[0]] = " "
            antPosition = position
            blackPawn--
            turn++
            passant = 0
            return
        }
        if (table[checkPosition[3]][checkPosition[2]] == "B" && checkPosition[1] + 1 <= 8) {
            table[checkPosition[3]][checkPosition[2]] = "W"
            table[checkPosition[1]][checkPosition[0]] = " "
            antPosition = position
            blackPawn--
            turn++
            return
        }

    }
    if (turn % 2 == 0 && (checkPosition[0] + 4 <= 32 || checkPosition[0] == 32) && (checkPosition[0] - 4 >= 4 || checkPosition[0] == 4)) {
        if (passant == 1 && position[2] == antPosition[2] && abs(checkPosition[3] - antPosition[3].digitToInt()) == 1){
                table[checkPosition[3]][checkPosition[2]] = "B"
                table[checkPosition[3] + 1][checkPosition[2]] = " "
                table[checkPosition[1]][checkPosition[0]] = " "
                antPosition = position
                whitePawn--
                turn++
                passant = 0
                return
            }
        }

        if ((table[checkPosition[3]][checkPosition[2]] == "W" && checkPosition[3] >= 1)) {
            table[checkPosition[3]][checkPosition[2]] = "B"
            table[checkPosition[1]][checkPosition[0]] = " "
            antPosition = position
            whitePawn--
            turn++
            return
        }

    if (table[checkPosition[3]][checkPosition[2]] == " ") {
        if (turn % 2 != 0) {
            table[checkPosition[3]][checkPosition[2]] = "W"
            table[checkPosition[1]][checkPosition[0]] = " "
            antPosition = position
            turn++
            return
        } else {
            table[checkPosition[3]][checkPosition[2]] = "B"
            table[checkPosition[1]][checkPosition[0]] = " "
            antPosition = position
            turn++
            return
        }
    }
}