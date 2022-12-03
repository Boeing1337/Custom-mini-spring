package tech.wg.tools;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static tech.wg.dao.QuestionRepository.ANY_NEW_LINE;
import static tech.wg.dao.QuestionRepository.SIMPLE_NEW_LINE;


public class MockGrammar extends Grammar {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream printStream = new PrintStream(out);
    private Scanner scanner;

    @Override
    public void write(String value) {
        printStream.println(value);
    }

    @Override
    public void write(int value) {
        printStream.println(value);
    }

    @Override
    public void write(char value) {
        printStream.println(value);
    }

    @Override
    public int readInt() {
        return scanner.nextInt();
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public String readToken() {
        return scanner.next();
    }

    @Override
    public boolean hasNextInt() {
        return scanner.hasNextInt();
    }

    public String getOut() {
        return out.toString().replaceAll(ANY_NEW_LINE, SIMPLE_NEW_LINE).trim();
    }

    public void setInputContent(String inputStream) {
        this.scanner = new Scanner(inputStream);
    }
}
