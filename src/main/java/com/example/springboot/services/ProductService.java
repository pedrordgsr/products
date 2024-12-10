package com.example.springboot.services;

import com.example.springboot.dtos.ProductRecordDTO;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<ProductModel> saveProductService(@RequestBody @Valid ProductRecordDTO productRecordDto){
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    public ResponseEntity<List<ProductModel>> getAllProductsService(){
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    public ResponseEntity<Object> getOneProductService(@PathVariable(value="id") UUID id){
        Optional<ProductModel> product0 = productRepository.findById(id);
        if(product0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found. ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(product0.get());
    }

    public ResponseEntity<Object> updateProductService(@PathVariable(value="id" )UUID id,
                                                @RequestBody @Valid ProductRecordDTO productRecordDto){
        Optional<ProductModel> product0 = productRepository.findById(id);
        if(product0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found. ");
        }
        var productModel = product0.get();
        BeanUtils.copyProperties(productRecordDto,productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    public ResponseEntity<Object> deleteProductService(@PathVariable(value="id") UUID id){
        Optional<ProductModel> product0 = productRepository.findById(id);
        if(product0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        productRepository.delete(product0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
    }

}
