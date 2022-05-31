package controllers

import models.{EmployeeDetails, ErrorDetail}
import javax.inject._
import play.api._
import play.api.mvc._
import play.twirl.api.HtmlFormat

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class Application @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) with Logging {

  val employee = new EmployeeDetails

  def ping(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] => Ok("pong")
  }

  def getEmployeeDetailsByEmployeeId(id: Int): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      employee.fetchEmployeeDetailsById(id) match {
        case details: HtmlFormat.Appendable => Ok(details)
        case errorCode: ErrorDetail if errorCode.statusCode == 400 => NotFound("No employee exist from this")
        case _ => InternalServerError("something went wrong")
      }
  }

}