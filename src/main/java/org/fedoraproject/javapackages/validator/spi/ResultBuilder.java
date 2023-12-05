package org.fedoraproject.javapackages.validator.spi;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A class used to add log messages and merge result types producing a final instance of
 * {@link org.fedoraproject.javapackages.validator.spi.Result}.
 *
 * @author Marián Konček
 */
public class ResultBuilder {
    private TestResult result = TestResult.skip;
    private List<LogEntry> log = new ArrayList<>();

    private static class BuildResult implements Result {
        private final TestResult result;
        private final List<LogEntry> log;

        BuildResult(TestResult result, List<LogEntry> log) {
            this.result = result;
            this.log = log;
        }

        @Override
        public TestResult getResult() {
            return result;
        }

        @Override
        public Iterator<LogEntry> iterator() {
            return log.iterator();
        }
    }

    /**
     * Construct an empty builder with the initial result type set to
     * {@link org.fedoraproject.javapackages.validator.spi.TestResult#skip}
     */
    public ResultBuilder() {
    }

    /**
     * Get the final {@link org.fedoraproject.javapackages.validator.spi.Result}
     * from this builder.
     * @return The final Result from this builder.
     */
    public Result build() {
        return new BuildResult(result, new ArrayList<>(log));
    }

    /**
     * Get the current result type.
     * @return The current result type.
     */
    public TestResult getResult() {
        return result;
    }

    /**
     * Get the whole log of this builder.
     * @return The current log.
     */
    public List<LogEntry> getLog() {
        return Collections.unmodifiableList(log);
    }

    /**
     * Set the result type.
     * @param result The result to set.
     */
    public void setResult(TestResult result) {
        this.result = result;
    }

    /**
     * Merge the current result with the provided result.
     * @param result The value to merge with.
     */
    public void mergeResult(TestResult result) {
        if (result.compareTo(this.result) > 0) {
            setResult(result);
        }
    }

    /**
     * Add the log entry into the current log. This function does not change the
     * result type.
     * @param logEntry The log entry to add.
     */
    public void addLog(LogEntry logEntry) {
        log.add(logEntry);
    }

    /**
     * Add a log entry of type {@link org.fedoraproject.javapackages.validator.spi.LogEvent#debug}
     * @param pattern The pattern to build the log message from.
     * @param objects Objects to fill the pattern with.
     */
    public void debug(String pattern, Decorated... objects) {
        addLog(LogEntry.debug(pattern, objects));
    }

    /**
     *
     * Merge the current result with the result of type
     * {@link org.fedoraproject.javapackages.validator.spi.TestResult#skip} and add
     * a log entry of type {@link org.fedoraproject.javapackages.validator.spi.LogEvent#skip}
     * @param pattern The pattern to build the log message from.
     * @param objects Objects to fill the pattern with.
     */
    public void skip(String pattern, Decorated... objects) {
        mergeResult(TestResult.skip);
        addLog(LogEntry.skip(pattern, objects));
    }

    /**
     * Merge the current result with the result of type
     * {@link org.fedoraproject.javapackages.validator.spi.TestResult#pass} and add
     * a log entry of type {@link org.fedoraproject.javapackages.validator.spi.LogEvent#pass}
     * @param pattern The pattern to build the log message from.
     * @param objects Objects to fill the pattern with.
     */
    public void pass(String pattern, Decorated... objects) {
        mergeResult(TestResult.pass);
        addLog(LogEntry.pass(pattern, objects));
    }

    /**
     * Merge the current result with the result of type
     * {@link org.fedoraproject.javapackages.validator.spi.TestResult#info} and add
     * a log entry of type {@link org.fedoraproject.javapackages.validator.spi.LogEvent#info}
     * @param pattern The pattern to build the log message from.
     * @param objects Objects to fill the pattern with.
     */
    public void info(String pattern, Decorated... objects) {
        mergeResult(TestResult.info);
        addLog(LogEntry.info(pattern, objects));
    }

    /**
     * Merge the current result with the result of type
     * {@link org.fedoraproject.javapackages.validator.spi.TestResult#warn} and add
     * a log entry of type {@link org.fedoraproject.javapackages.validator.spi.LogEvent#warn}
     * @param pattern The pattern to build the log message from.
     * @param objects Objects to fill the pattern with.
     */
    public void warn(String pattern, Decorated... objects) {
        mergeResult(TestResult.warn);
        addLog(LogEntry.warn(pattern, objects));
    }

    /**
     * Merge the current result with the result of type
     * {@link org.fedoraproject.javapackages.validator.spi.TestResult#fail} and add
     * a log entry of type {@link org.fedoraproject.javapackages.validator.spi.LogEvent#fail}
     * @param pattern The pattern to build the log message from.
     * @param objects Objects to fill the pattern with.
     */
    public void fail(String pattern, Decorated... objects) {
        mergeResult(TestResult.fail);
        addLog(LogEntry.fail(pattern, objects));
    }

    /**
     * Merge the current result with the result of type
     * {@link org.fedoraproject.javapackages.validator.spi.TestResult#error} and add
     * a log entry of type {@link org.fedoraproject.javapackages.validator.spi.LogEvent#error}
     * @param pattern The pattern to build the log message from.
     * @param objects Objects to fill the pattern with.
     */
    public void error(String pattern, Decorated... objects) {
        mergeResult(TestResult.error);
        addLog(LogEntry.error(pattern, objects));
    }

    /**
     * Merge the current result with the result of type
     * {@link org.fedoraproject.javapackages.validator.spi.TestResult#error} and add
     * a log entry of type {@link org.fedoraproject.javapackages.validator.spi.LogEvent#error}
     * using the stack trace of the throwable as the log message.
     * @param ex The exception to log.
     */
    public void error(Throwable ex) {
        mergeResult(TestResult.error);
        var writer = new StringWriter();
        ex.printStackTrace(new PrintWriter(writer));
        addLog(LogEntry.error("An exception occured:{0}{1}",
                Decorated.plain(System.lineSeparator()),
                Decorated.plain(writer.toString())));
    }
}
