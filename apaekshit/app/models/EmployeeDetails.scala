package models

case class ErrorDetail(statusCode: Int)
class EmployeeDetails {
  def fetchEmployeeDetailsById(id:Int): Any = {
    id match {
      case 101 => views.html.EmployeeDetails("Prakash","Delhi")
      case 102 => views.html.EmployeeDetails("anil","Dehradun")
      case _ => ErrorDetail(400)
    }
  }
}