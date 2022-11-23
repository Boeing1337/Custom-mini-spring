package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Person {

    private final String name;
    private final String secondName;

    private final List<Role> roles = new ArrayList<>();

    public Person(String name, String secondName) {
        this.name = name;
        this.secondName = secondName;
    }


    public String getName() {
        return name;
    }


    public String getSecondName() {
        return secondName;
    }


    public List<Role> getRoles() {
        return roles;
    }

    void addRole(Role role) {
        List<Role> asd = new ArrayList<>();
        for (int i = 0; i < roles.size(); i++) {
            Role r = roles.get(i);
            if (r.getName().equals(role.getName())) {
                return;
            }
        }
        roles.add(role);
    }

    @Override
    public String toString() {
        return String.format("Name: %s, SecName: %s, Roles %s", name, secondName, Arrays.toString(roles.toArray()));
    }
}
