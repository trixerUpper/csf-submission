package ibf2022.batch3.assessment.csf.orderbackend.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import ibf2022.batch3.assessment.csf.orderbackend.respositories.OrdersRepository;
import ibf2022.batch3.assessment.csf.orderbackend.respositories.PendingOrdersRepository;
import ibf2022.batch3.assessment.csf.orderbackend.services.OrderingService;
import ibf2022.batch3.assessment.csf.orderbackend.util.DeserUtils;

@RestController
@CrossOrigin(origins="*")
public class OrderController {

	@Autowired
	private OrdersRepository ordersRepository;

	@Autowired
	private PendingOrdersRepository pendingOrdersRepository;

	@Autowired
	private OrderingService orderingService;


	// TODO: Task 3 - POST /api/order
	@PostMapping(path="/api/order", consumes="application/json", produces="application/json")
	public ResponseEntity<String> submitOrder(@RequestBody PizzaOrder order) {
		System.out.println("Set Order");
		System.out.println("Debug: " + order.toString());
		
		try {
			PizzaOrder newOrder = orderingService.placeOrder(order);
			String formattedOrder = DeserUtils.formatPizzaOrder(newOrder).toString();			
			return ResponseEntity.ok(formattedOrder);
		} catch	(Exception e) {
			String formattedError = DeserUtils.formatError(e.getMessage()).toString();
			return ResponseEntity.status(400).body(formattedError);
		}
		
	}


	// TODO: Task 6 - GET /api/orders/<email>
	@GetMapping(path="/api/orders/{email}", produces="application/json")
	public ResponseEntity<String> getOrders(@PathVariable String email) {
		 try {
			List<PizzaOrder> orders = orderingService.getPendingOrdersByEmail(email);
			String serializedOrders = DeserUtils.listOfPizzaOrdersToString(orders).toString();
			return ResponseEntity.ok(serializedOrders);
		} catch (Exception e) {
			String formattedError = DeserUtils.formatError(e.getMessage()).toString();
			return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(formattedError);
		}
	}

	// TODO: Task 7 - DELETE /api/order/<orderId>

}
