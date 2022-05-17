fun main() {

    val game = UserGame()
    do {
        game.prepareGame()
        game.playGame()
        game.endGame()
        if (game.javaClass.name == "UserGame") {
            ComputerGame()
        } else {
            UserGame()
        }

        val continueGame:String = try {
            print("Press Enter to continue: ")
            readln() + " "
        }catch (e:Exception){
            ""
        }

    }while (continueGame.isNotEmpty())
}

class UserGame: Game{

    override var number = -1
    override var score = 0

    private var lowerEdge = 0
    private var topEdge = 100

    override fun prepareGame() {
        number = (Game.LOWER_EDGE..Game.TOP_EDGE).random()
        println("\nYou are playing a game with a computer. He guessed a number from 0 to 100 and you need to guess it.")
    }

    override fun playGame(){
        do {
            val input = getUserInput()
            score++
        }while(!isWin(input))
    }

    override fun endGame() {
        println("You won from $score attempt")
    }

    private fun getUserInput(): Int {
        return try {
            print("Enter the number: ")
            readln().toInt()
        }catch (e: java.lang.NumberFormatException){
            getUserInput()
        }catch (e: java.lang.NullPointerException){
            getUserInput()
        }catch (e:java.lang.Exception){
            with((lowerEdge..topEdge).random()){
                println(this)
                return this
            }
        }
    }

    private fun isWin(input: Int):Boolean{
        if (input < number){
            println("This is less than computer wish. Try more.")
            lowerEdge = input + 1
            return false
        }else if (input > number){
            println("This is more than computer wish. Try less.")
            topEdge = input - 1
            return false
        }
        return true
    }
}

class ComputerGame: Game{
    override var number = -1
    override var score = 0


    override fun prepareGame() {
        do {
            print("\nYou are playing a game with a computer. You think of a number from 0 to 100 and the computer has to guess it.\nYour number: ")
            number = getUserInput()
        }while (number !in Game.LOWER_EDGE..Game.TOP_EDGE)
    }

    private fun getUserInput(): Int {
        return  try {
            readln().toInt()
        }catch (e:java.lang.NumberFormatException){
            getUserInput()
        }
        catch (e:java.lang.Exception){
            (Game.LOWER_EDGE..Game.TOP_EDGE).random()
        }
    }

    override fun playGame() {
        var lowerEdge = Game.LOWER_EDGE
        var topEdge = Game.TOP_EDGE

        do {
            val computerAnswer = (lowerEdge..topEdge).random()
            score++
            println("Computer have this answer: $computerAnswer\nThis answer is less than, greater than or same to your number [L/G/S]?")
            var input: String
            do {
                print("Your decision: ")
                input = try {
                    readln().uppercase()
                }catch (e: java.lang.Exception){
                    if (computerAnswer > number)
                        "G"
                    else if (computerAnswer < number)
                        "L"
                    else
                        "S"
                }
            }while (input !in arrayOf("L", "G", "S"))
            if (input == "G")
                if (computerAnswer <= number)
                    println("Your Lie!")
                else {
                    topEdge = computerAnswer - 1
                }
            else if (input == "L")
                if (computerAnswer >= number)
                    println("Your Lie!")
                else
                    lowerEdge = computerAnswer + 1
            else if (input == "S"){
                if (computerAnswer != number) {
                    println("Your Lie!")
                }
                break
            }
        }while (true)
    }

    override fun endGame() {
        println("Computer won from $score attempt")
    }

}

interface Game{
    var number: Int
    var score: Int
    fun prepareGame()
    fun playGame()
    fun endGame(){}

    companion object {
        const val LOWER_EDGE = 0
        const val TOP_EDGE = 100
    }
}