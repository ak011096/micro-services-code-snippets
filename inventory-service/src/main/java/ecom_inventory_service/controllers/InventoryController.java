package ecom_inventory_service.controllers;

import ecom_inventory_service.entity.Inventory;
import ecom_inventory_service.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    InventoryRepository inventoryRepository;

    @GetMapping("/{productId}")
    public Inventory checkInventory(@PathVariable String productId) throws InterruptedException {
        Thread.sleep(2000);
        return  inventoryRepository.findById(productId).get();
    }

    @PostMapping
    public Inventory addProduct(@RequestBody Inventory inventory){
        return inventoryRepository.save(inventory);
    }

}
