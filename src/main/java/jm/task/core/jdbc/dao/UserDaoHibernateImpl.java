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

        // Получаем SessionFactory в методе
        try (SessionFactory sessionFactory = Util.getConnectionHibernate()) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();  // Начинаем транзакцию
                Query query = session.createSQLQuery(sql);
//                query.executeUpdate();  // Выполняем SQL-запрос
                transaction.commit();  // Фиксируем транзакцию
            }
        } catch (Exception e) {
            e.printStackTrace();  // Логируем ошибку
        }
    }

    // Метод для удаления таблицы (через SQL)
    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        // Получаем SessionFactory в методе
        try (SessionFactory sessionFactory = Util.getConnectionHibernate()) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();  // Начинаем транзакцию
                Query query = session.createSQLQuery(sql);
                query.executeUpdate();  // Выполняем SQL-запрос
                transaction.commit();  // Фиксируем транзакцию
            }
        } catch (Exception e) {
            e.printStackTrace();  // Логируем ошибку
        }
    }

    // Метод для сохранения пользователя
    @Override
    public void saveUser(String name, String lastName, byte age) {
        // Получаем SessionFactory в методе
        try (SessionFactory sessionFactory = Util.getConnectionHibernate()) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();  // Начинаем транзакцию
                User user = new User(name, lastName, age);  // Создаем нового пользователя
                session.save(user);  // Сохраняем пользователя
                transaction.commit();  // Фиксируем транзакцию
                System.out.println("User с именем — " + name + " добавлен в базу данных");
            }
        } catch (Exception e) {
            e.printStackTrace();  // Логируем ошибку
        }
    }

    // Метод для удаления пользователя по ID
    @Override
    public void removeUserById(long id) {
        // Получаем SessionFactory в методе
        try (SessionFactory sessionFactory = Util.getConnectionHibernate()) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();  // Начинаем транзакцию
                User user = session.get(User.class, id);  // Получаем пользователя по ID
                if (user != null) {
                    session.delete(user);  // Удаляем пользователя
                    System.out.println("User с ID — " + id + " удалён из базы данных");
                }
                transaction.commit();  // Фиксируем транзакцию
            }
        } catch (Exception e) {
            e.printStackTrace();  // Логируем ошибку
        }
    }

    // Метод для получения всех пользователей
    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        // Получаем SessionFactory в методе
        try (SessionFactory sessionFactory = Util.getConnectionHibernate()) {
            try (Session session = sessionFactory.openSession()) {
                Query<User> query = session.createQuery("FROM User ", User.class);  // Запрос на выборку всех пользователей
                users = query.list();  // Получаем всех пользователей
            }
        } catch (Exception e) {
            e.printStackTrace();  // Логируем ошибку
        }
        return users;  // Возвращаем список пользователей
    }

    // Метод для очистки таблицы пользователей
    @Override
    public void cleanUsersTable() {
        // Получаем SessionFactory в методе
        try (SessionFactory sessionFactory = Util.getConnectionHibernate()) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();  // Начинаем транзакцию
                Query query = session.createSQLQuery("TRUNCATE TABLE users");  // Очищаем таблицу
                query.executeUpdate();  // Выполняем очистку
                transaction.commit();  // Фиксируем транзакцию
            }
        } catch (Exception e) {
            e.printStackTrace();  // Логируем ошибку
        }
    }
}