// Robin Nandi - Spider Key 

package com.google.u2f.tools.httpserver.servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.Status;

import com.google.u2f.server.U2FServer;
import com.google.u2f.U2FException;

public class LoginServlet extends HtmlServlet {

  private final U2FServer u2fServer;

  public LoginServlet(U2FServer u2fServer) {
    this.u2fServer = u2fServer;
  }

  @Override
  public void generateBody(Request req, Response resp, PrintStream body) throws Exception {
	try {
		if (u2fServer.verifyCredentials(req.getParameter("username"), req.getParameter("password"))) {
			BufferedReader br = new BufferedReader(new FileReader("html/sign.html"));
		    try {
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();

		        while (line != null) {
		            sb.append(line);
		            sb.append(System.lineSeparator());
		            line = br.readLine();
		        }
		        String everything = sb.toString();
		        body.print(everything);
		    } finally {
		        br.close();
		    }
		} else {
			body.println("Wrong username or password.");
		}
	} catch (U2FException e) {
		body.println("Failure: " + e.toString());
	}
  }
}