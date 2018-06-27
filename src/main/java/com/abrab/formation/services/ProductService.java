package com.abrab.formation.services;

import com.abrab.formation.entities.Product;
import com.abrab.formation.repository.ProductRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @RequestMapping(value = "/chercherProducts", method = RequestMethod.GET)
    public Page<Product> findPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "mc", defaultValue = "") String mc
    ) {
        return productRepository.findByLabel(mc, new PageRequest(page, size, Sort.Direction.DESC, "dateSys"));
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable Long id) {
        return productRepository.findOne(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Product save(@RequestBody Product product) {
        product.setDateSys(new Date());
//        product.setComment("comment");
        return productRepository.save(product);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
    public Product edit(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return productRepository.saveAndFlush(product);
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public boolean remove(@PathVariable Long id) {
        try {
            productRepository.delete(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
