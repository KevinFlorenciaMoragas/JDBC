package kevin.dao;

import kevin.model.Product;

public interface ProductDAO {
    public void insert(Product product);
    public void delete(Integer id);
    public void update(Product product);
    public Product read(Integer id);
}
