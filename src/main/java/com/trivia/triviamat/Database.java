package com.trivia.triviamat;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:sqlite:trivia.db";

    public Database() {
    }

    public static Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception var4) {
        }

        String databaseUsername = "root";
        String databasePassword = "123456";

        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:trivia.db", databaseUsername, databasePassword);
            return con;
        } catch (SQLException var5) {
            var5.printStackTrace();
            if (var5.getErrorCode() == 0) {
            }

            return null;
        }
    }

    public static void createNewTable() {
        String url = "jdbc:sqlite:trivia.db";
        String sql_question = "CREATE TABLE IF NOT EXISTS questions (\n qID integer PRIMARY KEY AUTOINCREMENT,\n qContent text,\n qContentType text, \nqContentLink text,\n qAns1 text,\n qAns2 text,\n qAns3 text,\n qAns4 text, \n qTrueAns integer\n);".formatted();
        String sql_data = "CREATE TABLE IF NOT EXISTS data(\n dataID integer PRIMARY KEY,\n dataCon text);".formatted();
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute(sql_question);
            stmt.execute(sql_data);
        } catch (SQLException var7) {
            System.out.println(var7.getMessage());
        }

    }

    public static void createRecord(String qContent, String qContentType, String qContentLink, String qAns1, String qAns2, String qAns3, String qAns4, int qTrueAns) {
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = null;
            assert conn != null;
            stmt = conn.prepareStatement("INSERT INTO questions (qContent, qContentType, qContentLink, qAns1, qAns2, qAns3, qAns4, qTrueAns) VALUES(?, ?, ?, ?, ?, ?, ?, ?);");
            if ((qContent.length() > 0) && (qContentType == "txt" || (qContentType == "img" || qContentType == "mp3") && (qContentLink.length() > 0) && (1 <= qTrueAns && qTrueAns <= 4))) {
                stmt.setString(1, qContent);
                stmt.setString(2, qContentType);
                stmt.setString(3, qContentLink);
                stmt.setString(4, qAns1);
                stmt.setString(5, qAns2);
                stmt.setString(6, qAns3);
                stmt.setString(7, qAns4);
                stmt.setInt(8, qTrueAns);
                stmt.execute();
            }
        } catch (SQLException var7) {
            System.out.println(var7.getMessage());
        }

    }

    public static void setData(String data) {
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = null;
            assert conn != null;
            stmt = conn.prepareStatement("UPDATE data SET dataCon = ? WHERE dataID = 1;");
            stmt.setString(1, data);
            stmt.execute();
        } catch (SQLException var7) {
            System.out.println(var7.getMessage());
        }
    }

    public static String getData() {
        String sql = "SELECT * FROM data ";
        String data = "";
        Connection conn = getConnection();
        PreparedStatement ps;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                data = resultSet.getString(2);
            }
        } catch (SQLException throwables) {
            data = "";
            throwables.printStackTrace();
        }
        return data;
    }

    public static Question getQuestion(int qID) {
        String sql = "SELECT * FROM questions WHERE qID = ? ";
        Connection conn = getConnection();
        PreparedStatement ps;
        Question question = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, qID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                question = new Question(qID, resultSet.getString("qContent"), resultSet.getString("qContentType"), resultSet.getString("qContentLink"), resultSet.getString("qAns1"), resultSet.getString("qAns2"), resultSet.getString("qAns3"), resultSet.getString("qAns4"), resultSet.getInt("qTrueAns"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return question;
    }
}
