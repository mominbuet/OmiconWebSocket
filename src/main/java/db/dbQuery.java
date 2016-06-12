/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author user
 */
public class dbQuery {

    public static void insert_log(String user, String log) {
        try {
            LogRpc logrpc = new LogRpc();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("omicon_rpc");
            EntityManager em = emf.createEntityManager();
            EntityTransaction entr = em.getTransaction();

            entr.begin();
            
            logrpc.setUser(user);
            logrpc.setLog(log);
            logrpc.setInsertTime(new Date());
            em.persist(logrpc);
            entr.commit();

        } catch (Exception ex) {
            //System.out.println(ex.getMessage());
            Logger.getLogger(dbQuery.class.getName()).log(Level.SEVERE, "insert_log(String user,String log) exception " + ex.getMessage());
        }
    }

    public static Users get_user_by_name(String name) {
        try {
            //Users results = null;
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("omicon_rpc");
            EntityManager em = emf.createEntityManager();
            List tmpresults = em.createNamedQuery("Users.findByUser", Users.class).setParameter("user", name).getResultList();
            //System.out.println("present " + name);
            if (tmpresults != null && tmpresults.size() > 0) {
                //System.out.println("present " + results.getUser());
                return (Users) tmpresults.get(0);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(dbQuery.class.getName()).log(Level.SEVERE, "get_user_by_name(String name) exception " + ex.getMessage());
        }
        return null;
    }

    public static Users insert_users(Users user) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("omicon_rpc");
            EntityManager em = emf.createEntityManager();
            EntityTransaction entr = em.getTransaction();

            Users present = get_user_by_name(user.getUser());

            entr.begin();
            //user.setUpdatedTime(new Date());
            if (present == null) //if first time only
            {
                System.out.println("inserting new");
                em.persist(user);
            } else {
                System.out.println("present " + present);
                present.setSession(user.getSession());
                System.out.println("test executed "+((user.getExecuted() == null) ? present.getExecuted() : user.getExecuted()));
                present.setExecuted((user.getExecuted() == null) ? present.getExecuted() : user.getExecuted());
                em.merge(present);
            }
            entr.commit();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(dbQuery.class.getName()).log(Level.SEVERE, "insert_users(Users user) exception " + ex.getMessage());
        }
        return user;
    }
}
