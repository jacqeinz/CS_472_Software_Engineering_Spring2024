package com.business.entities.repositories;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.business.entities.Cart;

@Repository
public class CartRepository {
	private final List<Cart> carts = new ArrayList<Cart>();
	public CartRepository() {
		
	}
    public List<Cart> findAll() {
        return new ArrayList<Cart>(this.carts);
    }
    
    public void add(final Cart cart) {
        this.carts.add(cart);
    }

}
