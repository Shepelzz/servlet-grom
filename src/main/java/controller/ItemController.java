package controller;

import model.Item;
import service.ItemService;

public class ItemController {
    private ItemService itemService = new ItemService();

    public Item save(Item item) throws Exception{
        return itemService.save(item);
    }

    public Item update(Item item) throws Exception{
        return itemService.update(item);
    }

    public Item delete(Long id) throws Exception{
        return itemService.delete(id);
    }

    public Item findById(Long id) throws Exception{
        return itemService.findById(id);
    }

}
