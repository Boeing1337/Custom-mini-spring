package dao;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class KeywordsRepository {
    private final Set<String> cash = new LinkedHashSet<>();

    public KeywordsRepository() {
        readKeywords();
    }


    public List<String> readKeywords() {
        if (cash.isEmpty()) {
            File file = new File("slova");
            try (Scanner scanner = new Scanner(file, UTF_8)) {
                while (scanner.hasNextLine()) {
                    cash.add(scanner.nextLine());
                }
            } catch (Exception e) {
                cash.clear();
                System.out.println("Нет файла со словами");
            }
        }
        return List.copyOf(cash);
    }

    public List<String> addKeywords(Collection<String> newKeywords) {
        cash.addAll(newKeywords);
        File file = new File("slova");
        try (PrintWriter writer = new PrintWriter(file, UTF_8)) {
            writer.write(String.join("\n", cash));
        } catch (Exception e) {
            System.out.println("Не получилось добавить слова");
        }
        cash.clear();
        readKeywords();
        return readKeywords();
    }

    public List<String> editKeywords(String oldKeyword, String newKeyword) {
        File file = new File("slova");
        if (cash.contains(oldKeyword)) {
            cash.remove(oldKeyword);
            cash.add(newKeyword);
        } else {
            System.out.println("Нет такого слова");
        }
        String perenosStroki = "\n";
        StringBuilder sb = new StringBuilder();
        for (String element : cash) {
            sb.append(element);
            sb.append(perenosStroki);
        }
        String res = sb.toString();
        try (PrintWriter writer = new PrintWriter(file, UTF_8)) {
            writer.write(res);
        } catch (Exception e) {
            System.out.println("Не удалось заменить слово");
        }
        cash.clear();
        readKeywords();
        return readKeywords();
    }

    public void deleteKeyword(String wrongWord){
        File file = new File("slova");
        try (PrintWriter writer = new PrintWriter(file, UTF_8)) {
            cash.remove(wrongWord);
            writer.write(String.join("\n", cash));
            cash.clear();
            readKeywords();
        } catch (Exception e) {
            System.out.println("Не удалось удалить слово");
        }
    }
}