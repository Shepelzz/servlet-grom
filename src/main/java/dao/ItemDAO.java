package dao;

import model.Item;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.NoResultException;

public class ItemDAO {
    private SessionFactory sessionFactory;

    public Item save(Item item) throws Exception{
        Transaction transaction = null;
        try (Session session = createSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            session.save(item);

            session.getTransaction().commit();
            System.out.println(item.getClass().getName()+" saved with id:"+item.getId());
            return item;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            throw new Exception("Save "+item.getClass().getSimpleName()+": "+item.toString()+" failed"+e.getMessage());
        }
    }

    public Item update(Item item) throws Exception{
        Transaction transaction = null;
        try (Session session = createSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            session.update(item);

            session.getTransaction().commit();
            System.out.println(item.getClass().getName()+" updated");
            return item;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            throw new Exception("Update "+item.getClass().getSimpleName()+": "+item.toString()+" failed"+e.getMessage());
        }
    }

    public Item delete(Long id) throws Exception{
        Item item = findById(id);

        Transaction transaction = null;
        try (Session session = createSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            session.delete(item);

            session.getTransaction().commit();
            System.out.println(item.getClass().getName()+" id:"+item.getId()+" was deleted");
            return item;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            throw new Exception("Delete "+item.getClass().getSimpleName()+": "+item.toString()+" failed"+e.getMessage());
        }
    }

    public Item findById(Long id) throws Exception{
        try (Session session = createSessionFactory().openSession()) {

            return session.get(Item.class, id);

        } catch (HibernateException e) {
            throw new Exception(getClass().getSimpleName()+"-findById: "+id+" failed. "+e.getMessage());
        } catch (NoResultException noe){
            throw new Exception("There is not Item with id: "+id+". "+noe.getMessage());
        }
    }


    private SessionFactory createSessionFactory(){
        if(sessionFactory == null)
            sessionFactory = new Configuration().configure().buildSessionFactory();
        return sessionFactory;
    }
}
