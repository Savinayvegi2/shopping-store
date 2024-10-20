package com.example.shopping_store.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.shopping_store.entity.Cart;
import com.example.shopping_store.entity.OrderDetails;
import com.example.shopping_store.entity.Product;
import com.example.shopping_store.repository.CartRepository;
import com.example.shopping_store.repository.ProductRepository;
import com.example.shopping_store.util.CommonUtil;
import com.example.shopping_store.util.OrderStatus;


@Service
public class OrderService {

	@Autowired
	private ProductRepository orderRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CommonUtil commonUtil;
	
	public void saveOrder(Integer id, OrderDetails request) throws Exception {
		List<Cart> carts = cartRepository.findByUserId(id);

		for (Cart cart : carts) {

			Product order = new Product();

			order.setOrderId(UUID.randomUUID().toString());
			order.setOrderDate(LocalDate.now());

			order.setProduct(cart.getProduct());
			order.setPrice(cart.getProduct().getDiscountPrice());

			order.setQuantity(cart.getQuantity());
			order.setUser(cart.getUser());

			order.setStatus(OrderStatus.IN_PROGRESS.getName());
			order.setPaymentType(request.getPaymentType());

			OrderDetails address = new OrderDetails();
			address.setFirstName(request.getFirstName());
			address.setLastName(request.getLastName());
			address.setEmail(request.getEmail());
			address.setMobileNo(request.getMobileNo());
			address.setAddress(request.getAddress());
			address.setCity(request.getCity());
			address.setState(request.getState());
			address.setPincode(request.getPincode());

			order.setOrderDetails(address);

			Product saveOrder = orderRepository.save(order);
			commonUtil.sendMailForProductOrder(saveOrder, "success");
		}

	}

	public List<Product> getOrdersByUser(Integer id) {
		List<Product> orders = orderRepository.findByUserId(id);
		return orders;
	}

	public Product updateOrderStatus(Integer id, String status) {
		Optional<Product> findById = orderRepository.findById(id);
		if (findById.isPresent()) {
			Product productOrder = findById.get();
			productOrder.setStatus(status);
			Product updateOrder = orderRepository.save(productOrder);
			return updateOrder;
		}
		return null;
	}

	public Page<Product> getAllOrdersPagination(Integer pageNo, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return orderRepository.findAll(pageable);

	}

	public Product getOrdersByOrderId(String orderId) {
		return orderRepository.findByOrderId(orderId);
	}
	
	public List<Product> getAllOrders() {
		return orderRepository.findAll();
	}


}
