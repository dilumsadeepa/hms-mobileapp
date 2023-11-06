package com.example.hms.model;

public class Users {

    private static String name;
    private static int id = 0;

    private static int role;

    private static String index_no;

    public static String getIndex_no() {
        return index_no;
    }

    public static void setIndex_no(String index_no) {
        Users.index_no = index_no;
    }

    public static int getRole() {
        return role;
    }

    public static void setRole(int role) {
        Users.role = role;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Users.name = name;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Users.id = id;
    }
}
