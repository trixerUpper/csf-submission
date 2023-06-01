package ibf2022.batch3.assessment.csf.orderbackend.respositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;

@Repository
public class OrdersRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	// TODO: Task 3
	// WARNING: Do not change the method's signature.
	// Write the native MongoDB query in the comment below
	//   Native MongoDB query here for add()
	public void add(PizzaOrder order) {
		mongoTemplate.insert(order, "orders");

	}
	
	// TODO: Task 6
	// WARNING: Do not change the method's signature.
	// Write the native MongoDB query in the comment below
	//   Native MongoDB query here for getPendingOrdersByEmail()
	public List<PizzaOrder> getPendingOrdersByEmail(String email) {
		Criteria criteria = Criteria.where("email").is(email).and("delivered").ne(true);
		Query query = Query.query(criteria);

		query.with(Sort.by(Sort.Direction.DESC, "date"));
		query.fields().include("orderId").include("total").include("date");

	 	return mongoTemplate.find(query, PizzaOrder.class, "orders");	
	}

	// TODO: Task 7
	// WARNING: Do not change the method's signature.
	// Write the native MongoDB query in the comment below
	//   Native MongoDB query here for markOrderDelivered()
	public boolean markOrderDelivered(String orderId) {
		Query query = new Query(Criteria.where("orderId").is(orderId));
        Update update = new Update().set("delivered", true);
        UpdateResult result = mongoTemplate.updateFirst(query, update, PizzaOrder.class, "orders");
        return result.getModifiedCount() > 0;
	}

}
