package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Batman", "Bat", (byte) 30);
        userService.saveUser("Joker", "Jok", (byte) 31);
        userService.saveUser("Superman", "Sup", (byte) 32);
        userService.saveUser("Robin", "Rob", (byte) 33);
        System.out.println(userService.getAllUsers().toString());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
