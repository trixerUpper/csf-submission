package ibf2022.batch3.assessment.csf.orderbackend.respositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;
import ibf2022.batch3.assessment.csf.orderbackend.util.DeserUtils;

@Repository
public class PendingOrdersRepository {

	private static final String PENDING_ORDERS_KEY = "pending-orders";

	@Autowired
	@Qualifier("pending-orders")
	public RedisTemplate<String, String> redisTemplate;

	// TODO: Task 3
	// WARNING: Do not change the method's signature.
	public void add(PizzaOrder order) {
		redisTemplate.opsForHash().put(PENDING_ORDERS_KEY, order.getOrderId(), DeserUtils.formatPizzaOrder(order).toString());
	}

	// TODO: Task 7
	// WARNING: Do not change the method's signature.
	public boolean delete(String orderId) {
		Long deleted = redisTemplate.opsForHash().delete(PENDING_ORDERS_KEY, orderId);
        return deleted != null && deleted > 0;
	}
}
