package tech.wg.dao;

import lombok.extern.log4j.Log4j2;
import tech.ioc.annotations.Component;
import tech.ioc.annotations.InjectProperty;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@Log4j2
public class KeywordsRepository {
    private final Set<String> cash = new LinkedHashSet<>();
    @InjectProperty
    private String keywordsFileName;

    public List<String> readKeywords() {
        if (cash.isEmpty()) {
            File file = new File(keywordsFileName);
            try (Scanner scanner = new Scanner(file, UTF_8)) {
                while (scanner.hasNextLine()) {
                    cash.add(scanner.nextLine());
                }
            } catch (Exception e) {
                cash.clear();
                log.error("Нет файла со словами", e);
            }
        }
        return List.copyOf(cash);
    }

    public List<String> addKeywords(Collection<String> newKeywords) {
        readKeywords();
        cash.addAll(newKeywords);
        File file = new File(keywordsFileName);
        try (PrintWriter writer = new PrintWriter(file, UTF_8)) {
            writer.write(String.join("\n", cash));
        } catch (Exception e) {
            log.error("Не получилось добавить слова", e);
        }
        cash.clear();
        return readKeywords();
    }

    public List<String> editKeywords(String oldKeyword, String newKeyword) {
        readKeywords();
        readKeywords();
        if (cash.contains(oldKeyword)) {
            cash.remove(oldKeyword);
            cash.add(newKeyword);
        } else {
            log.warn("Нет такого слова");
            return List.of();
        }
        String perenosStroki = "\n";
        StringBuilder sb = new StringBuilder();
        for (String element : cash) {
            sb.append(element);
            sb.append(perenosStroki);
        }
        String res = sb.toString();
        try (PrintWriter writer = new PrintWriter(keywordsFileName, UTF_8)) {
            writer.write(res);
        } catch (Exception e) {
            log.error("Не удалось заменить слово", e);
        }
        cash.clear();
        return readKeywords();
    }

    public void deleteKeyword(String wrongWord) {
        try (PrintWriter writer = new PrintWriter(keywordsFileName, UTF_8)) {
            if(cash.contains(wrongWord)) {
                cash.remove(wrongWord);
            } else {
                log.error("Нет такого слова в списке слов.");
            }
            writer.write(String.join("\n", cash));
            cash.clear();
            readKeywords();
        } catch (Exception e) {
            log.error("Не удалось удалить слово.", e);
        }
    }
}