package ru.geekbrains.utils;


import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;


public class HttpRequestUtils
{

  public static URL getAppUrl(HttpServletRequest req)
  throws MalformedURLException
  {
	String scheme = req.getScheme();
	String host = req.getServerName();
	int port = req.getServerPort();
	String contextPath = req.getContextPath();

	return new URL(scheme, host, port, contextPath);
  }

}