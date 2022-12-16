package tech.wg.tools;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static tech.wg.dao.QuestionRepository.ANY_NEW_LINE;
import static tech.wg.dao.QuestionRepository.SIMPLE_NEW_LINE;


public class MockGrammar extends Grammar {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    public String getOut() {
        return out.toString().replaceAll(ANY_NEW_LINE, SIMPLE_NEW_LINE).trim();
    }

    public void initWithInput(String inputStream) {
        super.printStream = new PrintStream(out);
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public void println(String value) {
        super.println(value);
    }

    @Override
    public void print(String value) {
        super.print(value);
    }

    @Override
    public void println(int value) {
        super.println(value);
    }

    @Override
    public void println(char value) {
        super.println(value);
    }

    @Override
    public int nextInt() {
        return super.nextInt();
    }

    @Override
    public String nextLine() {
        return super.nextLine();
    }

    @Override
    public String next() {
        return super.next();
    }

    @Override
    public boolean hasNextInt() {
        return super.hasNextInt();
    }
}
