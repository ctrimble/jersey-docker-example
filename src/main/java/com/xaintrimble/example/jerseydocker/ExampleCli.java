package com.xiantrimble.example.jerseydocker;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;

public class ExampleCli
{
  public static void main( String[] argv ) {
    try {
      Runnable command = parse(argv, new CommandStart());
      command.run();
    }
    catch( Throwable t ) {
      System.err.println("error: "+t.getMessage());
    }
  }

  public static Runnable parse( String[] argv, Runnable... commands ) {

    final JCommander jc = new JCommander();
    jc.setProgramName("jersey-docker-example");
    for( Runnable command : commands ) {
      jc.addCommand(command);
    }

    try {
      jc.parse(argv);
    }
    catch( Throwable t ) {
      System.err.println("error: "+ t.getMessage());
      jc.usage();
      System.exit(1);
    }

    String parsedCommand = jc.getParsedCommand();
    for( Runnable command : commands ) {
      if( ArrayUtils.contains(command.getClass().getAnnotation(Parameters.class).commandNames(), parsedCommand) ) {
        return command;
      }
    }

    return new Runnable() {
      @Override
      public void run() {
        jc.usage();
      }
    };
  }
}
