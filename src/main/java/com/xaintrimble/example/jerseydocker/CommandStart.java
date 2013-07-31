package com.xiantrimble.example.jerseydocker;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

@Parameters(commandNames = "start", commandDescription = "Starts the REST API")
public class CommandStart
  implements Runnable
{
  @Parameter(names="-p", description="Public Port")
  public int port = 8080;

@Override
public void run() {
   HttpServer server = null;

  try {
    System.out.println("Start");
    String baseUrl = "http://localhost:"+port;
    //ResourceConfig rc = new ResourceConfig().packages("com.xiantrimble.example.jerseydocker");
    ResourceConfig rc = new ResourceConfig(MyResource.class);

    System.out.println("Found "+rc.getClasses().size()+" classes.");
    for( Class<?> clazz : rc.getClasses() ) {
      System.out.println("Class found "+clazz.getName());
    }

    server = GrizzlyHttpServerFactory.createHttpServer(URI.create(baseUrl), rc);

    System.in.read();
  }
  catch( Exception e ) {
    System.out.println(e.getMessage());
  }
  finally {
    if( server != null ) {
      server.stop();
    }
  }
}
}
