alter table comments 
alter column date_create type timestamp; 

alter table comments 
alter column date_create 
SET DEFAULT now();