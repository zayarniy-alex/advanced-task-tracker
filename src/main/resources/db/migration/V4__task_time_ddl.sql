alter table task_time 
alter column date_start type timestamp; 

alter table task_time  
alter column date_finish type timestamp;

alter table task_time
add column comment varchar(2000);