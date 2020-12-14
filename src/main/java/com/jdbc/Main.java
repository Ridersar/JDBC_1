package com.jdbc;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        Сделать приложение, которое получает из базы данных:
//        Все товары
//        Товар по id
//        Добавляет товар в базу данных

        Class.forName("org.h2.Driver");
        try(Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "test", "еуые")) {
            Statement statement = connection.createStatement();

//            String sql = "CREATE TABLE PRODUCT " +
//                    "(id INTEGER not NULL, " +
//                    " name VARCHAR(255) not NULL, " +
//                    " PRIMARY KEY ( id ))";
//
//            statement.executeUpdate(sql);
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println();
                System.out.println("Функции:");
                System.out.println("1 - ввести данные");
                System.out.println("2 - вывести данные");
                System.out.println("3 - поиск по id");
                System.out.println("другое - завершить работу");
                int number = sc.nextInt();
                if (number == 1) {
                    System.out.println("Введите id");
                    Integer id = sc.nextInt();
                    System.out.println("Введите название товара");
                    String name = sc.next();
                    insert(connection, id, name);
                } else if (number == 2) {
                    printAll(connection);
                } else if (number == 3) {
                    System.out.println("Введите id");
                    Integer id = sc.nextInt();
                    searchId(connection, id);
                } else {
                    break;
                }
            }
        }
    }

    //вставка
    public static void insert(Connection connection, Integer id, String name) throws SQLException {
        try(PreparedStatement insertStatement = connection.prepareStatement("insert into PRODUCT(id, name) values (?, ?)")) {
            insertStatement.setInt(1, id);
            insertStatement.setString(2, name);
            insertStatement.executeUpdate();
        }
    }

    //вывод всей информации
    public static void printAll(Connection connection) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement("select * from PRODUCT")) {
            try(ResultSet resultSet = statement.executeQuery()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    System.out.print(resultSet.getMetaData().getColumnLabel(i) + " ");
                }
                System.out.println();
                while (resultSet.next()) {
                    System.out.print(resultSet.getInt(1) + " ");
                    System.out.println(resultSet.getString(2) + " ");
                }
            }
        }
    }

    //поиск по Id
    public static void searchId(Connection connection, Integer id) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement("select * from PRODUCT where id = " + id)) {
            try(ResultSet resultSet = statement.executeQuery()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    System.out.print(resultSet.getMetaData().getColumnLabel(i) + " ");
                }
                System.out.println();
                while (resultSet.next()) {
                    System.out.print(resultSet.getInt(1) + " ");
                    System.out.println(resultSet.getString(2) + " ");
                }
            }
        }
    }
}

