// Robin Nandi - Spider Key 

package com.google.u2f.tools.httpserver.servlets;

import java.io.PrintStream;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.Status;

import com.google.u2f.db.DbConnectionManager;
import com.google.u2f.server.U2FServer;

public class LoginServlet extends HtmlServlet {

  private final U2FServer u2fServer;

  public LoginServlet(U2FServer u2fServer) {
    this.u2fServer = u2fServer;
  }

  @Override
  public void generateBody(Request req, Response resp, PrintStream body) throws Exception {
    body.println("Entered username is: " + req.getParameter("username"));
    body.println("Entered password is: " + req.getParameter("password"));
    DbConnectionManager dbConMan = new DbConnectionManager();
    dbConMan.setQuery("select password from user where username = '" + req.getParameter("username") + "';");
    dbConMan.executeQuery();
    String pwdhash = dbConMan.returnSingleResult();
    body.println("Database password is: " + pwdhash);
    if (pwdhash == req.getParameter("password")) {
    	// redirect to u2f page for second factor authentication
    	body.println("Success!");
    } else {
    	body.println("Wrong username or password");
    }
  }
}