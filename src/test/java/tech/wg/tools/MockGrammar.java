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
    public void write(String value) {
        super.write(value);
    }

    @Override
    public void print(String value) {
        super.print(value);
    }

    @Override
    public void write(int value) {
        super.write(value);
    }

    @Override
    public void write(char value) {
        super.write(value);
    }

    @Override
    public int readInt() {
        return super.readInt();
    }

    @Override
    public String readLine() {
        return super.readLine();
    }

    @Override
    public String readToken() {
        return super.readToken();
    }

    @Override
    public boolean hasNextInt() {
        return super.hasNextInt();
    }
}
