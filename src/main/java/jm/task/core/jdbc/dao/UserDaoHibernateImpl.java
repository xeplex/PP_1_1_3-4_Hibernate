package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        Session session = null;
        String sql = "CREATE TABLE IF NOT EXISTS kata_users" +
                "(Id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "Name VARCHAR(100), " +
                "LastName VARCHAR(100), " +
                "Age TINYINT );";
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            logger.warning("При попытке создания таблицы возникла ошибка");
            try {
                transaction.rollback();
            } catch (Exception ex) {
                logger.warning("При попытке отмены транзакции возникла ошибка");
            }
        } finally {
            try {
                session.close();
            } catch (Exception e) {
                logger.warning("При попытке закрытия сессии возникла ошибка");
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        Session session = null;
        String sql = "DROP TABLE IF EXISTS kata_users;";
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            logger.warning("При попытке удаления таблицы возникла ошибка");
            try {
                transaction.rollback();
            } catch (Exception ex) {
                logger.warning("При попытке отмены транзакции возникла ошибка");
            }
        } finally {
            try {
                session.close();
            } catch (Exception e) {
                logger.warning("При попытке закрытия сессии возникла ошибка");
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        Session session = null;
        User user = new User(name, lastName, age);
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.save(user);

            transaction.commit();
            System.out.printf("User с именем — %s добавлен в базу данных %n", name);
        } catch (Exception e) {
            logger.warning("При попытке добавления пользователя в таблицу возникла ошибка");
            try {
                transaction.rollback();
            } catch (Exception ex) {
                logger.warning("При попытке отмены транзакции возникла ошибка");
            }
        } finally {
            try {
                session.close();
            } catch (Exception e) {
                logger.warning("При попытке закрытия сессии возникла ошибка");
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);

            transaction.commit();
        } catch (Exception e) {
            logger.warning("При попытке удаления пользователя из таблицы возникла ошибка");
            try {
                transaction.rollback();
            } catch (Exception ex) {
                logger.warning("При попытке отмены транзакции возникла ошибка");
            }
        } finally {
            try {
                session.close();
            } catch (Exception e) {
                logger.warning("При попытке закрытия сессии возникла ошибка");
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        Session session = null;
        List<User> usersList = new ArrayList<>();
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            usersList = session.createQuery("FROM User", User.class).list();

            transaction.commit();
        } catch (Exception e) {
            logger.warning("При попытке получения всех пользователей из таблицы возникла ошибка");
            try {
                transaction.rollback();
            } catch (Exception ex) {
                logger.warning("При попытке отмены транзакции возникла ошибка");
            }
        } finally {
            try {
                session.close();
            } catch (Exception e) {
                logger.warning("При попытке закрытия сессии возникла ошибка");
            }
        }
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.createQuery("delete from User").executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            logger.warning("При попытке очистки таблицы возникла ошибка");
            try {
                transaction.rollback();
            } catch (Exception ex) {
                logger.warning("При попытке отмены транзакции возникла ошибка");
            }
        } finally {
            try {
                session.close();
            } catch (Exception e) {
                logger.warning("При попытке закрытия сессии возникла ошибка");
            }
        }
    }
}
