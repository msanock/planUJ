package Server.sql;

public class PsqlException extends RuntimeException{
    PsqlException(Exception cause) { super(cause); }
}
