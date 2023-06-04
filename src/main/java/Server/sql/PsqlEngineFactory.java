package Server.sql;

public class PsqlEngineFactory {
    public static PsqlEngine engine() {
        PsqlEngine engine = new PsqlEngine("jdbc:postgresql://localhost:5432/ProjektUJ");
        engine.createDatabase();
        return engine;
    }
}
