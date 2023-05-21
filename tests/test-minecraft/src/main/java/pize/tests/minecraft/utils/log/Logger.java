package pize.tests.minecraft.utils.log;

import java.io.PrintStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Logger{

    private final PrintStream out,err;
    private final DateTimeFormatter dateTimeFormatter;

    private Logger(PrintStream out,PrintStream err){
        this.out = out;
        this.err = err;
        dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    }

    private Logger(PrintStream out){
        this(out,out);
    }

    private Logger(){
        this(System.out,System.err);
    }


    public void trace(String trace){
        out.println( getLogPrefix(LogLevel.TRACE) + trace );
    }

    public void debug(String debug){
        out.println( getLogPrefix(LogLevel.DEBUG) + debug );
    }

    public void info(String info){
        out.println( getLogPrefix(LogLevel.INFO) + info );
    }

    public void warn(String warn){
        out.println( getLogPrefix(LogLevel.WARN) + warn );
    }

    public void error(String error){
        err.println( getLogPrefix(LogLevel.ERROR) + error );
    }


    public void trace(String trace,Object object){
        out.println( getLogPrefix(LogLevel.TRACE) + trace.replaceAll("\\{}",object.toString()) );
    }

    public void debug(String debug,Object object){
        out.println( getLogPrefix(LogLevel.DEBUG) + debug.replaceAll("\\{}",object.toString()) );
    }

    public void info(String info,Object object){
        out.println( getLogPrefix(LogLevel.INFO) + info.replaceAll("\\{}",object.toString()) );
    }

    public void warn(String warn,Object object){
        out.println( getLogPrefix(LogLevel.WARN) + warn.replaceAll("\\{}",object.toString()) );
    }

    public void error(String error,Object object){
        err.println( getLogPrefix(LogLevel.ERROR) + error.replaceAll("\\{}",object.toString()) );
    }


    private String getLogPrefix(LogLevel level){
        return "[" + ZonedDateTime.now().format(dateTimeFormatter) + "] [" + Thread.currentThread().getName() + "/" + level.name() + "]: ";
    }


    private static final Logger instance = new Logger();

    public static Logger instance(){
        return instance;
    }

}
