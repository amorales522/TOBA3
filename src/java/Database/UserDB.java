/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import JavaBeans.User;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Morales
 */
public class UserDB {

    @Resource(name = "jdbc/toba")
    private DataSource ds;

    public boolean insert(User user) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("TOBAPU");

        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        entitymanager.persist(user);
        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();
        return true;
    }

    public boolean update(User user) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("TOBAPU");

        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        User user1 = entitymanager.find(User.class, user.getId());

        //before update
        user1.setPassword(user.getPassword());
        entitymanager.getTransaction().commit();

        //after update
        //    System.out.println( employee );
        entitymanager.close();
        emfactory.close();
        return true;
    }

    @SuppressWarnings("unchecked")
    public User check(String username, String password) {
        boolean isLogin = false;
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("TOBAPU");

        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        //User user1 = entitymanager.
        Query q = entitymanager.createQuery("Select a FROM User a WHERE a.username=:user and a.password=:pass");
        q.setParameter("user", username);
        q.setParameter("pass", password);

        List<User> todoList = q.getResultList();
        User a = new User();
        for (User rs : todoList) {

            a.setId(rs.getId());
            a.setUsername(username);
            a.setPassword(password);
            a.setFirstName(rs.getFirstName());
            a.setLastName(rs.getLastName());
            a.setAddress(rs.getAddress());
            a.setPhoneNumber(rs.getPhoneNumber());
            a.setCity(rs.getCity());
            a.setState(rs.getState());
            a.setEmail(rs.getEmail());
            isLogin = true;
        }

        entitymanager.getTransaction().commit();

        entitymanager.close();
        emfactory.close();
        if (isLogin) {
            return a;
        } else {
            return null;
        }
    }

}
