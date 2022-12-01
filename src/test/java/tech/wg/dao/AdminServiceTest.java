package tech.wg.dao;

import org.junit.jupiter.api.Test;
import tech.wg.Servise.AdminService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class AdminServiceTest {

    @Test
    void testMenuWords() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setIn(new ByteArrayInputStream("1\n1\n2\nФифА\n1\n3\nФифа\nФуфлО\n1\n4\nфуфло\n1\n0\n0".getBytes()));
        System.setOut(originalOut);
        AdminService adminService = new AdminService();
    }

    @Test
    void testMenuQuestsAnswers() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setIn(new ByteArrayInputStream("2\n1\nг\n2\nФифА\nГГГГ\n1\nг\n3\nг\n1\nФуфлО\n1\nг\n4\nг\n1\nгг\n1\nг\n5\nг\n1\n1\nг\n0\n0".getBytes()));
        System.setOut(originalOut);
        AdminService adminService = new AdminService();
    }
}