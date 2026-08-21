package ecom_order_service.order_service.services;

import ecom_order_service.order_service.entity.Inventory;
import ecom_order_service.order_service.exception.MyCustomException;
import ecom_order_service.order_service.feign.InventoryClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RestClient restClient;

    @Autowired
    InventoryClientService inventoryClientService;
    public String placeOrder(String productId){
    //    String response = restTemplate.getForObject("http://localhost:8081/inventory/"+productId, String.class);
     /*   ResponseEntity<Inventory> entity = restClient.get().uri("http://localhost:8081/inventory/"+productId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,((request, response) -> {
                    throw new MyCustomException(response.getStatusCode(),response.getHeaders());
                }))
                .toEntity(Inventory.class);*/
       Inventory inventory = inventoryClientService.getProductInventory(productId);
        //Update the inventory
        updateInventory(inventory);
        return inventory.getQuantity()>0
                ? productId + "::: Order Placed Successfully"
                : "Order is out of Stock";
    }

    private void updateInventory(Inventory inventory) {
        inventory.setQuantity(inventory.getQuantity()-1);
        //restClient.post().uri("http://localhost:8081/inventory").body(inventory).retrieve().toBodilessEntity();
        inventoryClientService.updateInventory(inventory);
    }
}
