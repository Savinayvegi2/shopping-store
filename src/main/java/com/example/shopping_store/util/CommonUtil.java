package com.example.shopping_store.util;

import java.io.UnsupportedEncodingException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.example.shopping_store.entity.Product;
import com.example.shopping_store.entity.User;
import com.example.shopping_store.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CommonUtil {
	
	@Autowired(required = true)
	private JavaMailSender mailSender;
	
	@Autowired
	private UserService userService;

	public boolean sendMailForProductOrder(Product updateOrder, String status) throws Exception{
		msg="<p>Hello [[name]],</p>"
				+ "<p>Thank you order <b>[[orderStatus]]</b>.</p>"
				+ "<p><b>Product Details:</b></p>"
				+ "<p>Name : [[productName]]</p>"
				+ "<p>Category : [[category]]</p>"
				+ "<p>Quantity : [[quantity]]</p>"
				+ "<p>Price : [[price]]</p>"
				+ "<p>Payment Type : [[paymentType]]</p>";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("vegisavinay9@gmail.com", "Shooping Cart");
		helper.setTo(updateOrder.getOrderDetails().getEmail());

		msg=msg.replace("[[name]]",updateOrder.getOrderDetails().getFirstName());
		msg=msg.replace("[[orderStatus]]",status);
		msg=msg.replace("[[productName]]", updateOrder.getProduct().getTitle());
		msg=msg.replace("[[category]]", updateOrder.getProduct().getCategory());
		msg=msg.replace("[[quantity]]", updateOrder.getQuantity().toString());
		msg=msg.replace("[[price]]", updateOrder.getPrice().toString());
		msg=msg.replace("[[paymentType]]", updateOrder.getPaymentType());
		
		helper.setSubject("Product Order Status");
		helper.setText(msg, true);
		mailSender.send(message);
		return true;

	}

	public Boolean sendMail(String url, String reciepentEmail) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("vegisavinay9@gmail.com", "Shooping Cart");
		helper.setTo(reciepentEmail);

		String content = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>"
				+ "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + url
				+ "\">Change my password</a></p>";
		helper.setSubject("Password Reset");
		helper.setText(content, true);
		mailSender.send(message);
		return true;
	}

	public static String generateUrl(HttpServletRequest request) {
		// http://localhost:8080/forgot-password
				String siteUrl = request.getRequestURL().toString();

				return siteUrl.replace(request.getServletPath(), "");
	}

	String msg=null;
	
	public User getLoggedInUserDetails(Principal p) {
		String email = p.getName();
		User userDtls = userService.getUserByEmail(email);
		return userDtls;
	}

}
