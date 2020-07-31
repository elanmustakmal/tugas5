package com.example.tugas5.service;

import com.example.tugas5.model.Item;
import com.example.tugas5.model.User;
import com.example.tugas5.repository.ItemRepository;
import com.example.tugas5.repository.UserDetailRepository;
import com.example.tugas5.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    ItemRepository repo;

    @Autowired
    MongoTemplate mongoTemplate;

    public List<Item> getAllItem() {
        return repo.findAll();
    }

    public Map<String, Object> updateItem(Item item) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Query query = new Query(new Criteria("id").is(item.getId()));
            Map<String, Object> objectMap = Utility.objectToMap(item);
            Update updateQuery = new Update();
            objectMap.forEach((key, value) -> {
                if (value != null) {
                    updateQuery.set(key, value);
                }
            });
            mongoTemplate.findAndModify(query, updateQuery, Item.class);
            resultMap.put("success", true);
            resultMap.put("message", "item updated");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("success", false);
            resultMap.put("message", "item update failed");
        }
        return resultMap;
    }

    public Map<String, Object> deleteItem(String id) {
        Optional itemResult = repo.findById(id);
        Map<String, Object> resultMap = new HashMap<>();
        if (itemResult.isPresent()) {
            try {
                repo.deleteById(id);
                resultMap.put("success", true);
                resultMap.put("message", "item deleted");
            } catch (Exception e) {
                resultMap.put("success", false);
                resultMap.put("message", "item delete failed");
            }
        } else {
            resultMap.put("success", false);
            resultMap.put("message", "no item data");
        }
        return resultMap;
    }

    public Page<Item> getAllItemPaginated(String search, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("nama").regex(".*" + search + ".*", "i"));
        query.with(pageable);
        List<Item> list = mongoTemplate.find(query, Item.class);
        return PageableExecutionUtils.getPage(list, pageable, () -> mongoTemplate.count(query, Item.class));
    }


}
