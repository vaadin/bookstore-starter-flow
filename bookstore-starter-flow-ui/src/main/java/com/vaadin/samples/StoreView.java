package com.vaadin.samples;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.samples.backend.DataService;
import com.vaadin.samples.backend.data.Product;

public class StoreView extends Div {

    public StoreView() {
        Grid<Product> grid = new Grid<>(Product.class);
        grid.setItems(DataService.get().getAvailableProducts());
        grid.setSizeFull();

        setSizeFull();
        add(new H4("  BUY BOOKS OR DIE"), grid);
    }
}
