import com.sun.nio.sctp.SctpStandardSocketOptions;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;
import java.util.Arrays;

public class WordApplication {
    static ArrayList<String[]> A = new ArrayList<>();
    static ArrayList<String> B = new ArrayList<>();
    static ArrayList<String> C = new ArrayList<>();

    public static void main(String[] args) {

        String poxuy = "А:\n" +
                "/Что за фрукт поспел в садочке? Кость внутри, в веснушках щечки.\n" +
                "Прилетел к нему рой ос — Сладок мягкий/\n" +
                "Абрикос\n" +
                "/Специальное помещение для стоянки и текущего ремонта самолетов, вертолетов/\n" +
                "Ангар\n" +
                "/Самая большая ягода/\n" +
                "Арбуз\n" +
                "/Какой город изображён на купюре в пятьсот рублей/\n" +
                "Архангельск;\n" +
                "\n" +
                "Б:\n" +
                "/Как раньше назывался небольшой магазин с базовыми потребительскими товарами/\n" +
                "Бакалея;\n" +
                "В:\n" +
                "/Столица самого маленького государства/\n" +
                "Ватикан\n" +
                "/Мельчайшая неклеточная частица, размножающаяся в живых клетках/\n" +
                "Вирус;\n" +
                "\n" +
                "Г:\n" +
                ";\n" +
                "\n" +
                "Д:\n" +
                "/Короткая черточка, употребляется как знак переноса/\n" +
                "Дефис\n" +
                "/Атмосферные осадки в виде водяных капель/\n" +
                "Дождь\n" +
                "/Название начала шахматных и шашечных партий/\n" +
                "Дебют\n" +
                "\n" +
                "Е:\n" +
                "/Профессия охотника/\n" +
                "Егерь\n" +
                "/Ягода чёрная — да не черника,Куст колючий — да не малина/\n" +
                "Ежевика;\n" +
                "/С хитрым носиком сестрица Счёт откроет/\n" +
                "Единица\n";

        String[][] bukvi = poxuy.split(";");
        for (int i = 0; i < bukvi.length; i++) {
            String[] a = (bukvi[i].split("/"));
            for (int j = 1; j < a.length; j+=2) {
                String[][] b = ;

            }
        }

        String[] voprosA = bukvi[0].split("/");
        for (int i = 1; i < voprosA.length; i+=2) {
            String[] voprosi = voprosA[i].split("/");
            A.addAll(Arrays.asList(voprosi));
        }

        String[] voprosB = bukvi[1].split("/");
        for (int i = 1; i < voprosB.length; i+=2) {
            String[] voprosi = voprosA[i].split("/");
            B.addAll(Arrays.asList(voprosi));
        }

        String[] voprosC = bukvi[2].split("/");
        for (int i = 1; i < voprosC.length; i+=2) {
            String[] voprosi = voprosA[i].split("/");
            C.addAll(Arrays.asList(voprosi));
        }








//        for (int i = 0; i < split.length; i++) {
//            A.add(split[i].split("/"));
//        }

//        for (int i = 1; i < voprosA.length; i+=2) {
//            System.out.println(voprosA[i]);
//        }
//        A.addAll(Arrays.asList(split).subList(0, split.length));

        print();

    }
    public  static void print(){
        for (String s : B) {
            System.out.println(s);
        }
    }
}
