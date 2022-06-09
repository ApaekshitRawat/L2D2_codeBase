package models

import org.joda.time.DateTime
import play.api.libs.json.{Format, Json}
import reactivemongo.play.json._
import reactivemongo.bson.BSONObjectID
import reactivemongo.bson._
import play.api.libs.json.JodaWrites._
import play.api.libs.json.JodaReads._
/* Case class has apply and unapply  methods in the companion object so we don’t need to use the new keyword to create
a new instance of the class and lets lets us use case classes in more ways in match expressions.  */
case class Movie(
                  _id:Option[BSONObjectID],
                  _creationDate: Option[DateTime],
                  _updateDate: Option[DateTime],
                  title:String,
                  description:String
                )
//A companion object and its class can access each other’s private members
object Movie{
  implicit val fmt : Format[Movie] = Json.format[Movie]
  implicit object MovieBSONReader extends BSONDocumentReader[Movie] {
    def read(doc: BSONDocument): Movie = {
      Movie(
        doc.getAs[BSONObjectID]("_id"),
        doc.getAs[BSONDateTime]("_creationDate").map(dt => new DateTime(dt.value)),
        doc.getAs[BSONDateTime]("_updateDate").map(dt => new DateTime(dt.value)),
        doc.getAs[String]("title").get,
        doc.getAs[String]("description").get)
    }
  }

  implicit object MovieBSONWriter extends BSONDocumentWriter[Movie] {
    def write(movie: Movie): BSONDocument = {
      BSONDocument(
        "_id" -> movie._id,
        "_creationDate" -> movie._creationDate.map(date => BSONDateTime(date.getMillis)),
        "_updateDate" -> movie._updateDate.map(date => BSONDateTime(date.getMillis)),
        "title" -> movie.title,
        "description" -> movie.description

      )
    }
  }
}
