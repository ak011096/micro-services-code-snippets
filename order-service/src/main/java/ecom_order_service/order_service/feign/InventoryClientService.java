package ecom_order_service.order_service.feign;

import ecom_order_service.order_service.entity.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="inventory-service" , url = "http://localhost:8081/inventory/")
public interface InventoryClientService {

    @GetMapping("/{productId}")
    public Inventory getProductInventory(@PathVariable String productId);

    @PostMapping
    public String updateInventory(@RequestBody Inventory inventory);

}
