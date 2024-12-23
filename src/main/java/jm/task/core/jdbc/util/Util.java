package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;


public class Util {

    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/my_database";
    private static final String USER = "jpauser";
    private static final String PASSWORD = "jpapwd";


    public static Connection getConnection() {
        Connection connection = null;
        try {

           connection = DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static SessionFactory sessionFactory;

    static {
        try {
            // Настройка Hibernate без использования XML
//            Properties properties = new Properties();
//            properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//            properties.put("hibernate.hbm2ddl.auto", "update"); // Не удалять и не пересоздавать таблицу
//            properties.put("hibernate.show_sql", "true");
//            properties.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
//            properties.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/my_database");
//            properties.put("hibernate.connection.username", "jpauser");
//            properties.put("hibernate.connection.password", "jpapwd");
//
//            Configuration configuration = new Configuration();
//            configuration.setProperties(properties);
//
//            // Добавляем класс сущности
//            configuration.addAnnotatedClass(jm.task.core.jdbc.model.User.class);
//
//            // Создаем SessionFactory
//            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                    .applySettings(configuration.getProperties()).build();
//            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                    .setProperty("hibernate.connection.url", URL)
                    .setProperty("hibernate.connection.username", USER)
                    .setProperty("hibernate.connection.password", PASSWORD)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .addAnnotatedClass(User.class);

            StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(standardServiceRegistry);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {

        return sessionFactory;
    }

    // Метод для закрытия SessionFactory
    public static void shutdown() {
        getSessionFactory().close();
    }
    public static SessionFactory getConnectionHibernate() {
        try {
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                    .setProperty("hibernate.connection.url", URL)
                    .setProperty("hibernate.connection.username", USER)
                    .setProperty("hibernate.connection.password", PASSWORD)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .addAnnotatedClass(User.class);

            StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(standardServiceRegistry);
            System.out.println("Connection OK");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return sessionFactory;
    }

}
