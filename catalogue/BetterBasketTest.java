package catalogue;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BetterBasketTest {

	@Test
	void testAddProduct() {
		BetterBasket b = new BetterBasket();
		Product p1 = new Product("0001", "Toaster", 10.00, 1);
		Product p2 = new Product("0001", "Toaster", 10.00, 1);
		Product P3 = new Product("0002", "Kettle", 15.00, 1);
		Product P4 = new Product("0002", "Kettle", 15.00, 2);
		
		// test that p1 and p2 get merged
		b.add(p1);
		b.add(p2);
		assertEquals(1, b.size(), "size incorrect after merge");
		assertEquals(2, b.get(0).getQuantity(), "quantity incorrect after merge");
		
		//test that p3 dosent get merged 
		b.add(P3);
		assertEquals(2, b.size(), "size incorrect after non-merge");
		
		//test that p4 merges two into 3 
		b.add(P4);
		assertEquals(3, b.get(1).getQuantity(), "quantity incorrect after revers merg");
		
		
	}
	
	@Test
	void testSortAddProduct() {
		BetterBasket b = new BetterBasket();
		Product p1 = new Product("0001", "Toaster", 10.00, 1);
		Product p2 = new Product("0001", "Microwave", 50.00, 1);
		Product p3 = new Product("0003", "Kettle", 15.00, 1);
		
		//test that p1 and p3 get sorted 
		b.add(p3);
		b.add(p1);
		assertEquals("0001", b.get(0).getProductNum(), "product missorted");
		assertEquals("0003", b.get(1).getProductNum(), "product missorted");
		
		//test that p2 gets inserted
		b.add(p2);
		assertEquals("0001", b.get(0).getProductNum(), "product incorrect after insert");
		assertEquals("0002", b.get(1).getProductNum(), "product incorect after insert");
		assertEquals("0003", b.get(2).getProductNum(), "product incorect after insert");
		
		
		
		
	}

}
