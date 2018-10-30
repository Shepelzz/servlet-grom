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
import java.util.Date;

public class ItemDAO {
    private SessionFactory sessionFactory;

    private static final String SQL_FIND_ITEM_BY_NAME = "SELECT * FROM ITEM WHERE NAME = :name";

    public Item save(Item item) throws InternalServerError {
        Transaction transaction = null;
        try (Session session = createSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            item.setDateCreated(new Date());
            item.setLastUpdatedDate(new Date());
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

            item.setLastUpdatedDate(new Date());
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

    public Item delete(Item item) throws InternalServerError{
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

    public Item findById(Long id) throws InternalServerError, BadRequestException{
        try (Session session = createSessionFactory().openSession()) {

            return session.get(Item.class, id);

        } catch (HibernateException e) {
            throw new InternalServerError(getClass().getSimpleName()+"-findById: "+id+" failed. "+e.getMessage());
        } catch (NoResultException noe){
            throw new BadRequestException("There is not Item with id: "+id+". "+noe.getMessage());
        }
    }

    public Item findByName(String name) throws InternalServerError{
        try (Session session = createSessionFactory().openSession()) {

            return (Item) session.createSQLQuery(SQL_FIND_ITEM_BY_NAME)
                    .setParameter("name", name)
                    .addEntity(Item.class).getSingleResult();

        } catch (HibernateException e) {
            throw new InternalServerError(getClass().getSimpleName()+"-findByName: "+name+" failed. "+e.getMessage());
        } catch (NoResultException e){
            return null;
        }
    }


    private SessionFactory createSessionFactory(){
        if(sessionFactory == null)
            sessionFactory = new Configuration().configure().buildSessionFactory();
        return sessionFactory;
    }
}
