use apaekshit
go
create procedure longRunning
As
Begin
Waitfor delay '00:01:15'
select * from EMP
End

