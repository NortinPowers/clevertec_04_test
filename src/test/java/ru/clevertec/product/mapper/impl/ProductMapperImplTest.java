package ru.clevertec.product.mapper.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.clevertec.product.util.TestConstant.NEW_PRODUCT_DESCRIPTION;
import static ru.clevertec.product.util.TestConstant.NEW_PRODUCT_NAME;
import static ru.clevertec.product.util.TestConstant.NEW_PRODUCT_PRICE;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.util.InfoProductTestBuilder;
import ru.clevertec.product.util.ProductTestBuilder;

@ExtendWith(MockitoExtension.class)
class ProductMapperImplTest {

    @InjectMocks
    private ProductMapperImpl productMapper;

    @Nested
    class ToProductTest {

        @Test
        void toProductShouldReturnProduct_whenProductDtoPassed() {
            ProductDto productDto = ProductTestBuilder.builder().build()
                    .buildProductDto();
            Product expected = ProductTestBuilder.builder()
                    .withUuid(null).build()
                    .buildProduct();

            Product actual = productMapper.toProduct(productDto);
            assertThat(actual)
                    .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
                    .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                    .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                    .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                    .hasFieldOrProperty(Product.Fields.created);
        }

        @Test
        void toProductShouldReturnProduct_whenPassedProductDtoHasEmptyField() {
            ProductDto productDto = ProductTestBuilder.builder()
                    .withDescription(null).build()
                    .buildProductDto();
            Product expected = ProductTestBuilder.builder()
                    .withUuid(null)
                    .withDescription(null).build()
                    .buildProduct();

            Product actual = productMapper.toProduct(productDto);
            assertThat(actual)
                    .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
                    .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                    .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                    .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                    .hasFieldOrProperty(Product.Fields.created);
        }

        @Test
        void toProductShouldReturnNull_whenNullPassed() {
            ProductDto productDto = null;

            Product actual = productMapper.toProduct(productDto);
            assertThat(actual)
                    .isNull();
        }
    }

    @Nested
    class ToInfoProductDtoTest {

        @Test
        void toInfoProductDtoShouldReturnInfoProductDto_whenProductPassed() {
            Product product = ProductTestBuilder.builder().build()
                    .buildProduct();
            InfoProductDto expected = InfoProductTestBuilder.builder().build()
                    .buildInfoProductDto();

            InfoProductDto actual = productMapper.toInfoProductDto(product);
            assertThat(actual)
                    .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.uuid())
                    .hasFieldOrPropertyWithValue(Product.Fields.name, expected.name())
                    .hasFieldOrPropertyWithValue(Product.Fields.description, expected.description())
                    .hasFieldOrPropertyWithValue(Product.Fields.price, expected.price());
        }

        @Test
        void toInfoProductDtoShouldReturnInfoProductDto_whenPassedProductHasEmptyField() {
            Product product = ProductTestBuilder.builder()
                    .withName(null).build()
                    .buildProduct();
            InfoProductDto expected = InfoProductTestBuilder.builder()
                    .withName(null).build()
                    .buildInfoProductDto();

            InfoProductDto actual = productMapper.toInfoProductDto(product);
            assertThat(actual)
                    .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.uuid())
                    .hasFieldOrPropertyWithValue(Product.Fields.name, expected.name())
                    .hasFieldOrPropertyWithValue(Product.Fields.description, expected.description())
                    .hasFieldOrPropertyWithValue(Product.Fields.price, expected.price());
        }

        @Test
        void toInfoProductDtoShouldReturnNull_whenNullPassed() {
            Product product = null;

            InfoProductDto actual = productMapper.toInfoProductDto(product);
            assertThat(actual)
                    .isNull();
        }
    }

    @Nested
    class MergeTest {

        @Test
        void mergeShouldReturnUpdatedProduct_whenPassedProductDtoHasAllFields() {
            Product product = ProductTestBuilder.builder().build()
                    .buildProduct();
            ProductDto productDto = ProductTestBuilder.builder()
                    .withName(NEW_PRODUCT_NAME)
                    .withDescription(NEW_PRODUCT_DESCRIPTION)
                    .withPrice(NEW_PRODUCT_PRICE).build()
                    .buildProductDto();
            Product expected = ProductTestBuilder.builder()
                    .withName(NEW_PRODUCT_NAME)
                    .withDescription(NEW_PRODUCT_DESCRIPTION)
                    .withPrice(NEW_PRODUCT_PRICE).build()
                    .buildProduct();

            Product actual = productMapper.merge(product, productDto);
            assertThat(actual)
                    .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
                    .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                    .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                    .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                    .hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated());
        }

        @Test
        void mergeShouldReturnUpdatedProduct_whenPassedProductDtoHasEmptyField() {
            Product product = ProductTestBuilder.builder().build()
                    .buildProduct();
            ProductDto productDto = ProductTestBuilder.builder()
                    .withName(NEW_PRODUCT_NAME)
                    .withDescription(NEW_PRODUCT_DESCRIPTION)
                    .build()
                    .buildProductDto();
            Product expected = ProductTestBuilder.builder()
                    .withName(NEW_PRODUCT_NAME)
                    .withDescription(NEW_PRODUCT_DESCRIPTION)
                    .build()
                    .buildProduct();

            Product actual = productMapper.merge(product, productDto);
            assertThat(actual)
                    .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
                    .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                    .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                    .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                    .hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated());
        }

        @Test
        void mergeShouldReturnUpdatedProduct_whenPassedProductDtoHasAllEmptyFields() {
            Product product = ProductTestBuilder.builder().build()
                    .buildProduct();
            ProductDto productDto = ProductTestBuilder.builder().build()
                    .buildProductDto();
            Product expected = ProductTestBuilder.builder().build()
                    .buildProduct();

            Product actual = productMapper.merge(product, productDto);
            assertThat(actual)
                    .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
                    .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                    .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                    .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                    .hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated());
        }

        @Test
        void mergeShouldReturnNullPointerException_whenProductNullPassed() {
            Product product = null;
            ProductDto productDto = ProductTestBuilder.builder()
                    .withName(NEW_PRODUCT_NAME)
                    .withDescription(NEW_PRODUCT_DESCRIPTION)
                    .withPrice(NEW_PRODUCT_PRICE).build()
                    .buildProductDto();

            assertThrows(NullPointerException.class, () -> productMapper.merge(product, productDto));
        }

        @Test
        void mergeShouldReturnNonUpdatedProduct_whenProductDtoNullPassed() {
            Product product = ProductTestBuilder.builder().build()
                    .buildProduct();
            ProductDto productDto = null;
            Product expected = ProductTestBuilder.builder().build()
                    .buildProduct();

            Product actual = productMapper.merge(product, productDto);
            assertThat(actual)
                    .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
                    .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                    .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                    .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                    .hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated());
        }
    }
}
