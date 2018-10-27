package service;

import dao.ItemDAO;
import model.Item;

public class ItemService {
    private ItemDAO itemDAO = new ItemDAO();

    public Item save(Item item) throws Exception{
        return itemDAO.save(item);
    }

    public Item update(Item item) throws Exception{
        return itemDAO.update(item);
    }

    public Item delete(Long id) throws Exception{
        return itemDAO.delete(id);
    }

    public Item findById(Long id) throws Exception{
        return itemDAO.findById(id);
    }
}
