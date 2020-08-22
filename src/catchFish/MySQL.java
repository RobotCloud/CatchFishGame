package catchFish;

import java.sql.*;

public class MySQL {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/catchFish_data?useSSL=false&serverTimezone=UTC";

    static final String USER = "root";
    static final String PASS = "baoxu19990125";

    // 连接数据库
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    // 查询操作
    public boolean query() {
        Connection connection = getConnection();
        String sql = "SELECT id, name, grade, date FROM user_data";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int grade = resultSet.getInt("grade");
                Date date = resultSet.getDate("date");
                System.out.println("id:" + id);
                System.out.println("name:" + name);
                System.out.println("grade:" + grade);
                System.out.println("date:" + date);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    // 插入操作
    public boolean insert(User user) {
        Connection connection = getConnection();
        String sql = "INSERT INTO user_data (id, name, grade, date) VALUES (null, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setInt(2, user.getGrade());
            preparedStatement.setDate(3, user.getDate());
            int result = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
            return result > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void setUser(User user, String name, int grade, Date date) {
        user.setName(name);
        user.setGrade(grade);
        user.setDate(date);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        MySQL mySQL = new MySQL();
        User user = new User();
        mySQL.setUser(user, "zhang", 99, new Date(System.currentTimeMillis()));
//        mySQL.insert(user);
        mySQL.query();
    }
}
