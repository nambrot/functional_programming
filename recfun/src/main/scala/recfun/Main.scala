package recfun
import scala.annotation.tailrec

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
//      def nextRow(prevList: List[Int], remainingRows: Int): List[Int] = {
//        if (remainingRows == 0) prevList else nextRow
//      }
//      def row(rowNumber: Int): List[Int] = {
//        rowNumber match {
//          case 0 => List(1)
//          case 1 => List(1, 1)
//          case _ => {
//            val prevList = row(rowNumber - 1)
//            ((0 :: prevList) zip (prevList :+ 0)) map { case(a,b) => a + b }
//          }
//        }
//      } 
      def pascalHelper(frontier: List[(Int, Int)], acc: Int): Int = {
        if (frontier.isEmpty)
          return acc
        val (c, r) = frontier.head
        if (c == r || c == 0)
          pascalHelper(frontier.tail, acc + 1)
        else
          pascalHelper((c - 1, r - 1) :: (c, r - 1) :: frontier.tail, acc)
      }
      pascalHelper(List((c,r)), 0)
//      if (c == 0 || c == r) 1 else pascal(c - 1, r - 1) + pascal(c, r - 1)
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      @tailrec
      def balanceHelper(chars: List[Char], balance: Int): Boolean = {
        if (balance < 0) return false
        chars match {
          case List() => balance == 0
          case '(' :: as => balanceHelper(as, balance + 1)
          case ')' :: as => balanceHelper(as, balance - 1)
          case _ :: as => balanceHelper(as, balance)
        }
      }
      
      balanceHelper(chars, 0)
    }
  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
//      val sortedCoins = coins.sortBy(x => -x)
//      def countChangeHelper(money: Int, coins: List[Int]): Int = {
//        (money, coins) match {
//          case (0, _) => 0
//          case (_, List()) => 0
//          case (x, a :: as) => {
//            x match { 
//              case y if y == a => 1 + countChangeHelper(x, as)
//              case y if y > a => countChangeHelper(x-a, coins) + countChangeHelper(x, as)
//              case _ => countChangeHelper(x, as)
//            }
//          }
//        }
//      }
//
//      countChangeHelper(money, sortedCoins)
      @tailrec
      def countChangeHelper(frontiers: List[(Int, List[Int])], acc: Int): Int = {
        if (frontiers.isEmpty)
          return acc
        val (money, coins) = frontiers.head 
        if (money < 0 || coins.isEmpty) countChangeHelper(frontiers.tail, acc)
        else if (money == 0) countChangeHelper(frontiers.tail, acc + 1)
        else countChangeHelper((money, coins.tail) :: (money - coins.head, coins) :: frontiers.tail, acc)
      }
      
      countChangeHelper(List((money, coins)), 0)
    }
  }
