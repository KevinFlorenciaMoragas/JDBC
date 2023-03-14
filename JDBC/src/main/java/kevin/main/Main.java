package kevin.main;

import kevin.dao.ProductDAOImpl;
import kevin.model.Product;

public class Main {
    public static void main(String[] args) {

        ProductDAOImpl product = new ProductDAOImpl();

        // agregar nuevo producto
        product.insert(new Product(1081, "Arroz", 1.50));
        // obtener el producto con ID = 100
        Product p = product.read(1081);
        System.out.println(p);
        product.update(new Product(1081, "AAA", 1.50));
        System.out.println(p);
        // eliminar el producto con ID = 100
        product.delete(1081);
    }
}