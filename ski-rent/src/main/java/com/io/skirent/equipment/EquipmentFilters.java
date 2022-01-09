package com.io.skirent.equipment;

import java.util.ArrayList;
import java.util.List;

public class EquipmentFilters {

    private List<String> names;
    private List<String> manufacturers;
    private List<String> sizes;
    private List<String> categories;

    public List<String> getNames() {
        return names == null ? new ArrayList<>() : names.stream().map(String::toUpperCase).toList();
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getManufacturers() {
        return manufacturers == null ? new ArrayList<>() : manufacturers.stream().map(String::toUpperCase).toList();
    }

    public void setManufacturers(List<String> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public List<String> getSizes() {
        return sizes == null ? new ArrayList<>() : sizes.stream().map(String::toUpperCase).toList();
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    public List<Category> getCategories() {
        return categories == null ? new ArrayList<>() :  categories.stream().map(Category::valueOf).toList();
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
