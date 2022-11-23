package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Person> a = new ArrayList<>();


        while (scanner.hasNextLine()) {
            String asd = scanner.nextLine();
            String[] s = asd.split(" ");
            Person person = new Person(s[0], s[1]);
            for (int i = 2; i < s.length; i++) {
                person.addRole(new Role(s[i]));
            }
            a.add(person);
        }


        a.forEach(System.out::println);

    }
}


//NAME SECONDNAME ROLE1 ROLE2
////NAME2 SECONDNAME2 ROLE1 ROLE2
////NAME2 SECONDNAME2 ROLE1 ROLE2