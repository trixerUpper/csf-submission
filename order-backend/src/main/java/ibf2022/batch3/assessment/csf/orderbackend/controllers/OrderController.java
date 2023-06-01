package ibf2022.batch3.assessment.csf.orderbackend.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import ibf2022.batch3.assessment.csf.orderbackend.respositories.OrdersRepository;
import ibf2022.batch3.assessment.csf.orderbackend.respositories.PendingOrdersRepository;
import ibf2022.util.DeserUtils;

@RestController
@CrossOrigin(origins="*")
public class OrderController {

	@Autowired
	private OrdersRepository ordersRepository;

	@Autowired
	private PendingOrdersRepository pendingOrdersRepository;

	// TODO: Task 3 - POST /api/order
	@PostMapping(path="/api/order", consumes="application/json", produces="application/json")
	public ResponseEntity<String> submitOrder(@RequestBody PizzaOrder order) {
		order.setOrderId("1234");
		order.setDate(new Date());
		order.setTotal(0.0f);

		System.out.println("Set Order");
		System.out.println("Debug: " + order.toString());
		
		try {
			ordersRepository.add(order);
			pendingOrdersRepository.add(order);
			String formattedOrder = DeserUtils.formatPizzaOrder(order).toString();
			return ResponseEntity.ok(formattedOrder);

		} catch	(Exception e) {
			String formattedError = DeserUtils.formatError(e.getMessage()).toString();
			return ResponseEntity.status(400).body(formattedError);
		}
		
	}


	// TODO: Task 6 - GET /api/orders/<email>


	// TODO: Task 7 - DELETE /api/order/<orderId>

}
