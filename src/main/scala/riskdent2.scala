/*


https://www.hackerrank.com/challenges/minimum-average-waiting-time/problem

TODO
Not all test cases passed


*/




import java.io.{PrintStream, PrintWriter}

import scala.collection.mutable
import scala.io.StdIn
import scala.language.implicitConversions




object Solution {

  /*
   * Complete the minimumAverage function below.
   */
  case class order(startTime : Int, processingTime:Int)



  val startTimeOrd = Ordering.by { t: order => t.startTime }
  val processingTimeOrd = Ordering.by { t: order => t.processingTime }

  var customerList = new scala.collection.mutable.PriorityQueue[order]()(startTimeOrd).reverse


  /*
  implicit def orderCompare(o:order):Ordered[order]=new Ordered[order]{
    def compare(other: order):Int =( o.startTime.compare(other.startTime) ) match {
      case 0 => o.processingTime.compareTo(other.processingTime)
      case c => c
    }
  }
*/

  def process( out: PrintWriter): Unit = {

    var numInputs = scala.io.StdIn.readLine.trim.toInt
    //println(" total orders :- $numInputs")
    for (input <- 1 to numInputs ){
      var Stringinput = scala.io.StdIn.readLine().mkString

      Stringinput.split("\n").filter(x=> x.split(" ").length==2).map(initialise)
    }


    var currentTime:Long = 0
    var waitingTimeSum:Double  =0
    var finalList = new scala.collection.mutable.ArrayBuffer[order]()
   // println(sortedList)
    var pQ  = new mutable.PriorityQueue[order]()(processingTimeOrd).reverse
    while(customerList.nonEmpty || pQ.nonEmpty)
    {
      if (customerList.nonEmpty){
       // customerList.filter( p=> p.startTime <= currentTime  ).map(pQ.enqueue(_))
        // sortedList= sortedList.filter( p=> p.startTime > currentTime  )

        while (customerList.nonEmpty  && customerList.head.startTime <= currentTime){
        //  println(customerList)
          pQ.enqueue(customerList.dequeue())
        }

      }



      if (pQ.nonEmpty){
       // println("----")
       // println(customerList)
        //println(pQ)
        var currObj=  pQ.dequeue()
        finalList+=currObj
       //println(currentTime)
        //println(waitingTimeSum)
        waitingTimeSum= waitingTimeSum+ currentTime-currObj.startTime +currObj.processingTime

        //sortedList = sortedList.filter(p=> p.startTime != currObj.startTime && p.processingTime  != currObj.processingTime)
        currentTime=currentTime+ currObj.processingTime

      }
      else{
        currentTime=currentTime+1
      }


    }

    //print the final Result
   // println(finalList)
      /*
       waitingTimeSum =0
      var currentTime=finalList(0).startTime
      for (item <- finalList){
        //println(waitingTimeSum)
        //println(currentTime)
        waitingTimeSum= waitingTimeSum+ currentTime   -item.startTime +item.processingTime
        currentTime=currentTime +item.processingTime
      }
      */
      println((waitingTimeSum.toDouble / finalList.length).toLong)
     // out.print((waitingTimeSum.toDouble / finalList.length).toInt)
    }

    def initialise(str:String):Unit={
      //println(str)
      val obj = order(str.split("[\\s \\n]")(0).toInt , str.split("[\\s \\n]")(1).toInt)
      //sortedList +=   obj
      customerList.enqueue(obj)
      //sortedList=sortedList.sorted

    }


    def main(args: Array[String]) {
      val stdin = scala.io.StdIn

      val printWriter = new PrintWriter(Console.out)

      Solution.process(  printWriter)


    }
  }


