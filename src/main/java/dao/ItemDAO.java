package dao;

import exception.BadRequestException;
import exception.InternalServerError;
import model.Item;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.NoResultException;

public class ItemDAO {
    private SessionFactory sessionFactory;

    public Item save(Item item) throws InternalServerError {
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
            throw new InternalServerError("Save "+item.getClass().getSimpleName()+": "+item.toString()+" failed"+e.getMessage());
        }
    }

    public Item update(Item item) throws InternalServerError{
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
            throw new InternalServerError("Update "+item.getClass().getSimpleName()+": "+item.toString()+" failed"+e.getMessage());
        }
    }

    public Item delete(Long id) throws InternalServerError{
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
            throw new InternalServerError("Delete "+item.getClass().getSimpleName()+": "+item.toString()+" failed"+e.getMessage());
        }
    }

    public Item findById(Long id) throws InternalServerError{
        try (Session session = createSessionFactory().openSession()) {

            return session.get(Item.class, id);

        } catch (HibernateException e) {
            throw new InternalServerError(getClass().getSimpleName()+"-findById: "+id+" failed. "+e.getMessage());
        } catch (NoResultException noe){
            throw new BadRequestException("There is not Item with id: "+id+". "+noe.getMessage());
        }
    }


    private SessionFactory createSessionFactory(){
        if(sessionFactory == null)
            sessionFactory = new Configuration().configure().buildSessionFactory();
        return sessionFactory;
    }
}
