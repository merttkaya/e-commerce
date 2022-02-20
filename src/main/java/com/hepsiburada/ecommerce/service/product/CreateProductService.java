package com.hepsiburada.ecommerce.service.product;

import com.hepsiburada.ecommerce.dto.input.CreateProductInputDto;
import com.hepsiburada.ecommerce.dto.output.CreateProductOutputDto;
import com.hepsiburada.ecommerce.exception.SameProductExistException;
import com.hepsiburada.ecommerce.model.Product;
import com.hepsiburada.ecommerce.repository.ProductRepository;
import com.hepsiburada.ecommerce.service.ServiceAbstract;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

@Service
@RequiredArgsConstructor
public class CreateProductService extends ServiceAbstract {

    private final ProductRepository productRepository;

    public CreateProductOutputDto createProduct(CreateProductInputDto inputDto) {
        CreateProductOutputDto outputDto = new CreateProductOutputDto();
        try {
            if(productRepository.existsById(inputDto.getProductCode())) {
                outputDto.setSuccess(false);
                outputDto.setMessage("ERROR - Product: " + inputDto.getProductCode() + " is already exist");
                throw new SameProductExistException();
            }
            Product product = new Product();
            product.setProductCode(inputDto.getProductCode());
            product.setPrice(inputDto.getPrice());
            product.setStock(inputDto.getStock());
            productRepository.save(product);
            outputDto.setSuccess(true);
            outputDto.setProduct(product);
            return outputDto;
        } catch (SameProductExistException sameProductExistException){
            logger.log(Level.WARNING,outputDto.getMessage());
        }
        return outputDto;
    }

}
