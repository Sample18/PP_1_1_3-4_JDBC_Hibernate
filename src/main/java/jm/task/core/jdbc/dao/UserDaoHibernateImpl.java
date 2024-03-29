package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS users(id INT NOT NULL AUTO_INCREMENT, " +
                    "name VARCHAR(45) NOT NULL, " +
                    "lastName VARCHAR(45) NOT NULL, " +
                    "age INT(127) NOT NULL, " +
                    "PRIMARY KEY (id));").addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            System.err.println("Ошибка при создании таблицы. " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            System.err.println("Ошибка при удалении таблицы. " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            session.close();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            System.err.println("Ошибка при создании юзера. " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                session.getTransaction().commit();
            }
            session.close();
        } catch (HibernateException e) {
            System.err.println("Ошибка при удалении юзера. " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root);

            Query query = session.createQuery(criteriaQuery);
            userList.addAll(query.getResultList());
            session.close();
        } catch (HibernateException e) {
            System.err.println("Ошибка при получении таблицы. " + e.getMessage());
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            List<User> userList = getAllUsers();
            for (User user : userList) {
                session.delete(user);
            }
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            System.err.println("Ошибка при очистке таблицы. " + e.getMessage());
        }
    }
}
