select t.text 'Query Name',
stat.execution_count As 'Execution count',
stat.max_elapsed_time As 'Max Execution time',
ISNULL(stat.total_elapsed_time / stat.execution_count, 0) As 'Avg Execution Time',
stat.creation_time As CreatedData,
ISNULL(stat.execution_count / DATEDIFF(s,stat.creation_time, GETDATE()),0) As 'Frequency per Sec',
t.*
From sys.dm_exec_query_stats stat 
Cross Apply sys.dm_exec_sql_text(stat.sql_handle) t
order by stat.max_elapsed_time DESC 

select * from sys.dm_exec_query_stats