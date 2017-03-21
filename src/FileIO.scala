import java.io.File
import java.io._
import scala.collection.mutable
import scala.io.Source

case class Purchase(customer_id: Int, date: String, cc: String, cvv: String, category: String) {
  override def toString = s"$customer_id $date $cc $cvv $category"
}

object FileIO {
  def main(args: Array[String]): Unit = {
    val purch = mutable.MutableList[Purchase]()
    Source
      .fromFile("purchases.csv")
      .getLines().drop(1) //drops first line, such as a header.
      .foreach(line => {
        val Array(id, date, cc, cvv, category) = line.split(",").map(_.trim)
        purch += new Purchase(id.toInt, date, cc, cvv, category)
      })

    println("Which category would you like to see?")
    val userInput = io.StdIn.readLine().toLowerCase()
    val filtered = purch.filter(x => x.category.toLowerCase == userInput)
    val pw = new PrintWriter(new File("filtered_purchases.prn"))
    filtered.foreach(x =>
      pw.write(s"Customer: ${x.customer_id}, Date: ${x.date}\n")
    )
    pw.close
  }
}