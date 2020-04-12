package ru.geekbrains.events;


import ru.geekbrains.entities.Task;


public class TaskCreatedEvent
		extends GenericEvent<Task>
{

  public TaskCreatedEvent(Task task)
  {
	super(task);
  }


  public Task getTask()
  {
	return super.getData();
  }

}