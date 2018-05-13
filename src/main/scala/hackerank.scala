import java.io.{PrintStream, PrintWriter}
import scala.io.StdIn


case class order(startTime : Int, processingTime:Int)


object Solution {

  /*
   * Complete the minimumAverage function below.
   */
  var sortedList = new scala.collection.mutable.ArrayBuffer[order]()

  def process( out: PrintWriter): Unit = {

    var numInputs = scala.io.StdIn.readLine.trim.toInt
    //println(" total orders :- $numInputs")
    for (input <- 1 to numInputs ){
      var Stringinput = scala.io.StdIn.readLine().mkString

      Stringinput.split("\n").filter(x=> x.split(" ").length==2).map(initialise)
    }


    var curentTime = 0
    var finalList = new scala.collection.mutable.ArrayBuffer[order]()
    while(sortedList.length != 0)
    {
      var tmpList= sortedList.filter( p=> p.startTime <= curentTime  )
      tmpList=tmpList.sortWith(_.processingTime <= _.processingTime)
      if (tmpList.length >0){
        var currObj=  tmpList(0)
        finalList+=currObj
        sortedList = sortedList.filter(p=> p.startTime != currObj.startTime && p.processingTime  != currObj.processingTime)
        curentTime=curentTime+ currObj.processingTime
      }
      else{
        curentTime=curentTime+1
      }


    }

    //print the final Result

    var waitingTimeSum =0
    var currentTime=0
    for (item <- finalList){
      waitingTimeSum= waitingTimeSum+ currentTime +item.processingTime -item.startTime
      currentTime=currentTime +item.processingTime -item.startTime + item.startTime
    }

    //println(waitingTimeSum.toDouble / finalList.length)
    out.print((waitingTimeSum.toDouble / finalList.length).toInt)
  }

  def initialise(str:String):Unit={
    //println(str)
    val obj = order(str.split("[\\s \\n]")(0).toInt , str.split("[\\s \\n]")(1).toInt)
    sortedList +=   obj
    sortedList=sortedList.sortWith(_.startTime < _.startTime)

  }

  def main(args: Array[String]) {
    val stdin = scala.io.StdIn

    val printWriter = new PrintWriter(sys.env("OUTPUT_PATH"))

    Solution.process(  printWriter)


  }
}
