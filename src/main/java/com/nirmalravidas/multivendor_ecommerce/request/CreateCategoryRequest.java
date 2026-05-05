package com.nirmalravidas.multivendor_ecommerce.request;

import lombok.Data;

@Data
public class CreateCategoryRequest {
    private String parentCategory;
    private int level;
    private String name;
    private String categoryId;
}
