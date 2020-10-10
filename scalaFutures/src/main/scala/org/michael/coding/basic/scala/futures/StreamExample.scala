import scala.collection.generic.CanBuildFrom
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.language.higherKinds
import scala.util.Random

object StreamExample extends App {

  def traverseSequentially[A, B, M[X] <: TraversableOnce[X]](in: M[A])(fn: A => Future[B])(implicit cbf: CanBuildFrom[M[A], B, M[B]], executor: ExecutionContext): Future[M[B]] =
    in.foldLeft(Future.successful(cbf(in))) { (fr, a) =>
      for (r <- fr; b <- fn(a)) yield r += b
    }.map(_.result())

  import scala.concurrent.ExecutionContext.Implicits.global

  val alphabet = 'a' to 'z'

  val text = "The class Stream implements lazy lists where elements are only evaluated when they are needed."
  // Quick and dirty tokenization
  val words = text.toLowerCase.split("[^a-z]+").toList.filter(_.nonEmpty)

  def loadValues(): Future[Map[Char, Int]] = {
    // We use a Future to represent that these values might be obtained from a database or another external service.
    // For demonstration purposes, we just generate the values randomly.
    Future {
      Thread.sleep(1000)
      val values = alphabet.map(_ -> Random.nextInt(10)).toMap
      println(s"Generated new values: $values.")
      values
    }
  }

  val refreshInterval = 5

  def valuesStream: Stream[Future[Map[Char, Int]]] = {
    Stream.continually(loadValues()) flatMap { valuesFuture =>
      Stream.fill(refreshInterval)(valuesFuture)
    }
  }

  val valuesIterator = valuesStream.iterator

  val result = traverseSequentially(words) { word =>
    valuesIterator.next() map { values =>
      val value = word.map(char => values.getOrElse(char, 0)).sum
      println(s"The word '$word' got value $value.")
    }
  }

  // Blocking to ensure that the application does not exit before the Future has completed.
  // Don't block in a real app!
  val stream = Await.result(result, Duration.Inf)
}