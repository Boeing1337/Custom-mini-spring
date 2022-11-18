package dao;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class KeywordsRepository {
    private final Set<String> cash = new LinkedHashSet<>();


    public List<String> readKeywords() {
        if (cash.isEmpty()) {
            File file = new File("slova");
            try (Scanner scanner = new Scanner(file, UTF_8)) {
                while (scanner.hasNextLine()) {
                    cash.add(scanner.nextLine());
                }
            } catch (Exception e) {
                cash.clear();
                System.out.println("нет файла со словами");
            }
        }
        return List.copyOf(cash);
    }

    public List<String> addKeywords(Collection<String> newKeywords) {
        readKeywords();
        cash.addAll(newKeywords);
        File file = new File("slova");
        try (PrintWriter writer = new PrintWriter(file, UTF_8)) {
            writer.write(String.join("\n", cash));
        } catch (Exception e) {
            System.out.println("не получилось добавить слова");
        }
        cash.clear();
        return readKeywords();
    }

    public List<String> editKeywords(String oldKeyword, String newKeyword) {
        File file = new File("slova");
        readKeywords();
        cash.remove(oldKeyword);
        cash.add(newKeyword);
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
            System.out.println("не удалось заменить слово");
        }
        cash.clear();
        return readKeywords();
    }

    public void deleteKeyword(String wrongWord){
        File file = new File("slova");
        try (PrintWriter writer = new PrintWriter(file, UTF_8)){
            cash.remove(wrongWord);
            writer.write(String.join("\n", cash));
        } catch (Exception e) {
            System.out.println("не удалось удалить слово");
        }
    }
}