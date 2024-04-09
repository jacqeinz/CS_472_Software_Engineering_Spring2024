package com.business.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.business.entities.Cart;
import com.business.entities.repositories.CartRepository;
@Service
public class CartService {

	   @Autowired
	    private CartRepository cartRepository;
	    
	    
	    public CartService() {
	        super();
	    }
	    
	    
	    
	    public List<Cart> findAll() {
	        return this.cartRepository.findAll();
	    }

	    public void add(final Cart cart) {
	        this.cartRepository.add(cart);
	    }

}
