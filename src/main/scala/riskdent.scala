

import java.io.{BufferedInputStream, InputStream, PrintStream}

import scala.collection.mutable._

case class order(startTime : Int, processingTime:Int)

object OrderProcessor {

  var sortedList = new scala.collection.mutable.ArrayBuffer[order]()

  def process(in: InputStream, out: PrintStream): Unit = {

    var numInputs=scala.io.Source.fromInputStream(in).bufferedReader().readLine().toInt
    //println(" total orders :- $numInputs")
    for (input <- 1 to numInputs ){
      var Stringinput = scala.io.Source.fromInputStream(in).bufferedReader().readLine().mkString
      //print(Stringinput)
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
}

object riskdent extends  App{

    //scala.io.Source.fromInputStream(Console.in).getLines()
    OrderProcessor.process( System.in , Console.out)

}
