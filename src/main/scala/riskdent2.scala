/*


https://www.hackerrank.com/challenges/minimum-average-waiting-time/problem

TODO
Not all test cases passed


*/




import java.io.{PrintStream, PrintWriter}

import Solution.sortedList

import scala.collection.mutable
import scala.io.StdIn
import scala.language.implicitConversions




object Solution {

  /*
   * Complete the minimumAverage function below.
   */
  case class order(startTime : Int, processingTime:Int)

  var sortedList = new scala.collection.mutable.ArrayBuffer[order]()

  implicit def orderCompare(o:order):Ordered[order]=new Ordered[order]{
    def compare(other: order):Int =( o.startTime.compare(other.startTime) ) match {
      case 0 => o.processingTime.compareTo(other.processingTime)
      case c => c
    }
  }

  def process( out: PrintWriter): Unit = {

    var numInputs = scala.io.StdIn.readLine.trim.toInt
    //println(" total orders :- $numInputs")
    for (input <- 1 to numInputs ){
      var Stringinput = scala.io.StdIn.readLine().mkString

      Stringinput.split("\n").filter(x=> x.split(" ").length==2).map(initialise)
    }


    var curentTime = 0
    var finalList = new scala.collection.mutable.ArrayBuffer[order]()
   // println(sortedList)
    var pQ  = new mutable.PriorityQueue[order]()
    while(sortedList.nonEmpty || pQ.nonEmpty)
    {
      if (sortedList.nonEmpty){
        sortedList.filter( p=> p.startTime <= curentTime  ).map(pQ.enqueue(_))
         sortedList= sortedList.filter( p=> p.startTime > curentTime  )

      }




      if (pQ.nonEmpty){
       // println("----")
        //println(sortedList)
        //println(pQ)
        var currObj=  pQ.dequeue()
        finalList+=currObj
        //sortedList = sortedList.filter(p=> p.startTime != currObj.startTime && p.processingTime  != currObj.processingTime)
        curentTime=curentTime+ currObj.processingTime

      }
      else{
        curentTime=curentTime+1
      }


    }

    //print the final Result
   // println(finalList)
    var waitingTimeSum =0
    var currentTime=finalList(0).startTime
    for (item <- finalList){
      //println(waitingTimeSum)
      //println(currentTime)
      waitingTimeSum= waitingTimeSum+ currentTime   -item.startTime +item.processingTime
      currentTime=currentTime +item.processingTime
    }

    println((waitingTimeSum.toDouble / finalList.length).toInt)
   // out.print((waitingTimeSum.toDouble / finalList.length).toInt)
  }

  def initialise(str:String):Unit={
    //println(str)
    val obj = order(str.split("[\\s \\n]")(0).toInt , str.split("[\\s \\n]")(1).toInt)
    sortedList +=   obj
    //sortedList=sortedList.sorted

  }


  def main(args: Array[String]) {
    val stdin = scala.io.StdIn

    val printWriter = new PrintWriter(Console.out)

    Solution.process(  printWriter)


  }
}


