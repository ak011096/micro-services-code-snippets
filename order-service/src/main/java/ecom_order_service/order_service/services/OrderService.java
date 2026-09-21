package ecom_order_service.order_service.services;

import ecom_order_service.order_service.entity.Inventory;
import ecom_order_service.order_service.feign.InventoryClientService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class OrderService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RestClient restClient;

    @Autowired
    InventoryClientService inventoryClientService;

   /* @Retryable(
            retryFor = RuntimeException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    @RateLimiter(
            name = "orderService",
            fallbackMethod = "rateLimitFallback"
    )*/
 /*   @CircuitBreaker(
            name = "inventoryService",
            fallbackMethod = "circuitBreakerFallback"
    )*/

    @TimeLimiter(
            name = "inventoryService",
            fallbackMethod = "inventoryTimeoutFallback"
    )
    public CompletableFuture<String> placeOrder(String productId){
    //    String response = restTemplate.getForObject("http://localhost:8081/inventory/"+productId, String.class);
     /*   ResponseEntity<Inventory> entity = restClient.get().uri("http://localhost:8081/inventory/"+productId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,((request, response) -> {
                    throw new MyCustomException(response.getStatusCode(),response.getHeaders());
                }))
                .toEntity(Inventory.class);*/
        return CompletableFuture.supplyAsync(() -> {

            System.out.println("Calling Inventory Service...");

            Inventory inventory =
                    inventoryClientService.getProductInventory(productId);
            //Update the inventory
            updateInventory(inventory);
            return inventory.getQuantity() > 0
                    ? productId + "::: Order Placed Successfully"
                    : "Order is out of Stock";
        });
    }
    public String rateLimitFallback(
            String productId,
            Throwable throwable) {

        System.out.println("Rate limit exceeded for product: " + productId);

        return "Too many requests. Please try again later.";
    }

    public String circuitBreakerFallback(
            String productId,
            Throwable throwable) {

        System.out.println(
                "Circuit Breaker fallback executed: "
                        + throwable.getMessage());

        return "Inventory Service is currently unavailable. Please try again later.";
    }

    public CompletableFuture<String> inventoryTimeoutFallback(
            String productId,
            Throwable throwable) {

        System.out.println(
                "Inventory Service timeout: "
                        + throwable.getMessage()
        );

        return CompletableFuture.completedFuture(
                "Inventory Service is taking too long. Please try again later."
        );
    }

    private void updateInventory(Inventory inventory) {
        inventory.setQuantity(inventory.getQuantity()-1);
        //restClient.post().uri("http://localhost:8081/inventory").body(inventory).retrieve().toBodilessEntity();
        inventoryClientService.updateInventory(inventory);
    }
}
