import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class MySQLDumper {
    private final String host;
    private final String user;
    private final String password;

    public MySQLDumper(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.password = password;
    }

    public void dumpDatabase(String dbName) throws IOException {
        // Crear carpeta si no existe
        File dumpDir = new File("dumps");
        if (!dumpDir.exists())
            dumpDir.mkdirs();

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String fileName = dbName + "_" + timestamp + "_dump.sql";
        File outputFile = new File(dumpDir, fileName);

        List<String> command = Arrays.asList(
                "mysqldump",
                "-h", host,
                "-u", user,
                "-p" + password,
                "--no-tablespaces", /*
                                     * This option suppresses all CREATE LOGFILE GROUP and CREATE TABLESPACE
                                     * statements in the output of mysqldump.
                                     */
                "--set-gtid-purged=OFF", /*
                                          * Add 'SET @@GLOBAL.GTID_PURGED' to the output. Possible
                                          * values for this option are ON, COMMENTED, OFF and AUTO.
                                          * If ON is used and GTIDs are not enabled on the server, an
                                          * error is generated. If COMMENTED is used, 'SET
                                          * 
                                          * @@GLOBAL.GTID_PURGED' is added as a comment. If OFF is
                                          * used, this option does nothing. If AUTO is used and GTIDs
                                          * are enabled on the server, 'SET @@GLOBAL.GTID_PURGED' is
                                          * added to the output. If GTIDs are disabled, AUTO does
                                          * nothing. If no value is supplied then the default (AUTO)
                                          * value will be considered.
                                          */
                "--single-transaction", /*
                                         * Creates a consistent snapshot by dumping all tables in a
                                         * single transaction. Works ONLY for tables stored in
                                         * storage engines which support multiversioning (currently
                                         * only InnoDB does); the dump is NOT guaranteed to be
                                         * consistent for other storage engines. While a
                                         * --single-transaction dump is in process, to ensure a
                                         * valid dump file (correct table contents and binary log
                                         * position), no other connection should use the following
                                         * statements: ALTER TABLE, DROP TABLE, RENAME TABLE,
                                         * TRUNCATE TABLE, as consistent snapshot is not isolated
                                         * from them. Option automatically turns off --lock-tables.
                                         */
                "--databases",
                dbName);

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectOutput(outputFile);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        Process process = pb.start();
        try {
            int exit = process.waitFor();
            if (exit == 0) {
                System.out.println("Dump exitoso: " + outputFile.getPath());
            } else {
                System.out.println("Error al ejecutar mysqldump (código: " + exit + ")");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
