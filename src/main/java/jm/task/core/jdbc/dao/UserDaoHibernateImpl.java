package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    // Метод для создания таблицы (через SQL)
    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (id BIGINT AUTO_INCREMENT, " +
                "name VARCHAR(50), " +
                "lastName VARCHAR(50), " +
                "age TINYINT, " +
                "PRIMARY KEY (id))";

        Transaction transaction = null;

        try (SessionFactory sessionFactory = Util.getConnectionHibernate();
             Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();  // Начинаем транзакцию
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // Откатываем транзакцию в случае ошибки
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        Transaction transaction = null;

        try (SessionFactory sessionFactory = Util.getConnectionHibernate();
             Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();  // Начинаем транзакцию
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // Откатываем транзакцию в случае ошибки
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (SessionFactory sessionFactory = Util.getConnectionHibernate();
             Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();  // Начинаем транзакцию
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // Откатываем транзакцию в случае ошибки
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (SessionFactory sessionFactory = Util.getConnectionHibernate();
             Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();  // Начинаем транзакцию
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                System.out.println("User с ID — " + id + " удалён из базы данных");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // Откатываем транзакцию в случае ошибки
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;

        try (SessionFactory sessionFactory = Util.getConnectionHibernate();
             Session session = sessionFactory.openSession()) {

            Query<User> query = session.createQuery("FROM User", User.class);
            users = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;

        try (SessionFactory sessionFactory = Util.getConnectionHibernate();
             Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();  // Начинаем транзакцию
            Query query = session.createSQLQuery("TRUNCATE TABLE users");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // Откатываем транзакцию в случае ошибки
            }
            e.printStackTrace();
        }
    }
}
