package catalogue;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;

public class BetterBasket extends Basket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public boolean add(Product pr) {
        boolean found = false;

        for (Product existingProduct : this) {
            if (existingProduct.getProductNum().equals(pr.getProductNum())) {
                existingProduct.setQuantity(existingProduct.getQuantity() + pr.getQuantity());
                found = true;
                break;
            }
        }

        if (!found) {
            super.add(pr);
        }

        Collections.sort(this, Comparator.comparing(Product::getProductNum));

        return true;
    }
    
    public void test() {
    	
    }
}