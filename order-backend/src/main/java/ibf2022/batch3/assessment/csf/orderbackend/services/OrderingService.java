package ibf2022.batch3.assessment.csf.orderbackend.services;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import ibf2022.batch3.assessment.csf.orderbackend.respositories.OrdersRepository;
import ibf2022.batch3.assessment.csf.orderbackend.respositories.PendingOrdersRepository;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@Service
public class OrderingService {

	private static final String PRICING_SERVICE_URL = "https://pizza-pricing-production.up.railway.app/order";


	@Autowired
	private OrdersRepository ordersRepo;

	@Autowired
	private PendingOrdersRepository pendingOrdersRepo;
	
	// TODO: Task 5
	// WARNING: DO NOT CHANGE THE METHOD'S SIGNATURE
	public PizzaOrder placeOrder(PizzaOrder order) throws OrderException {
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		String header = "Accept";
		String value = "text/plain";
		headers.set(header, value);

		String payload = UriComponentsBuilder.newInstance()
                .queryParam("name", order.getName())
                .queryParam("email", order.getEmail())
                .queryParam("sauce", order.getSauce())
                .queryParam("size", order.getSize())
                .queryParam("thickCrust", order.getThickCrust())
                .queryParam("toppings", String.join(",", order.getToppings()))
                .queryParam("comments", order.getComments())
                .build()
                .encode()
                .toUriString();

		HttpEntity<String> entity = new HttpEntity<>(payload, headers);
		String quotationUrl = UriComponentsBuilder.fromUriString(PRICING_SERVICE_URL).toUriString();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(quotationUrl, HttpMethod.POST, entity, String.class);
        if (resp.getStatusCode() == HttpStatus.OK) {
            String result = resp.getBody();
			String[] split = result.split(",");
			order.setOrderId(split[0]);
			order.setDate(new Date(Long.parseLong(split[1])));
			order.setTotal(Float.parseFloat(split[2]));

			ordersRepo.add(order);

			pendingOrdersRepo.add(order);

			return order;
		} else {
            JsonObject errorObject = Json.createReader(new ByteArrayInputStream(resp.getBody().getBytes()))
                    .readObject();
            String errorMessage = errorObject.getString("error");
            throw new OrderException(errorMessage);
        }
	}

	// For Task 6
	// WARNING: Do not change the method's signature or its implemenation
	public List<PizzaOrder> getPendingOrdersByEmail(String email) {
		return ordersRepo.getPendingOrdersByEmail(email);
	}

	// For Task 7
	// WARNING: Do not change the method's signature or its implemenation
	public boolean markOrderDelivered(String orderId) {
		boolean ordersRepoOkay = ordersRepo.markOrderDelivered(orderId);
		boolean pendingOrdersRepoOkay = pendingOrdersRepo.delete(orderId);
		System.out.println("ordersRepoOkay" + ordersRepoOkay);
		System.out.println("pendingOrdersRepoOkay" + pendingOrdersRepoOkay);
		return ordersRepoOkay && pendingOrdersRepoOkay;
	}

}
