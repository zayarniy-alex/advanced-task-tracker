package ru.geekbrains.events;


public class GenericEvent<T>
{

  private T data;
  private boolean success;


  public GenericEvent(T eventData)
  {
	data = eventData;
	success = false;
  }


  protected T getData()
  {
	return data;
  }

}