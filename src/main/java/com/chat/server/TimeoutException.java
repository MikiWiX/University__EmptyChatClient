package main.java.com.chat.server;


public class TimeoutException extends RuntimeException{

    TimeoutException(String msg, Throwable err){
        super(msg, err);
    }
}
