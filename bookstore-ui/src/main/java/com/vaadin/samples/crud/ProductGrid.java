package com.vaadin.samples.crud;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.samples.backend.data.Availability;
import com.vaadin.samples.backend.data.Category;
import com.vaadin.samples.backend.data.Product;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Grid of products, handling the visual presentation and filtering of a set of
 * items. This version uses an in-memory data source that is suitable for small
 * data sets.
 */
public class ProductGrid extends Grid<Product> {

    public ProductGrid() {
        setSizeFull();

        addColumn(Product::getId).setHeader("Id");
        addColumn(Product::getProductName).setHeader("Product Name");

        // Format and add " €" to price
        final DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);
        addColumn(product -> decimalFormat.format(product.getPrice()) + " €")
                .setHeader("Price").setComparator((p1, p2) -> {
            return p1.getPrice().compareTo(p2.getPrice());
        });

        //TODO How to set css class?
        // .setStyleGenerator(product -> "align-right");


        //TODO replacement for HtmlRenderer? maybe TemplateRenderer.
        // Add an traffic light icon in front of availability
        addColumn(Product::getAvailability)
                .setHeader("Availability")
                .setComparator(Product::getAvailability);

        //TODO add icon by template or ...
//        addColumn(this::htmlFormatAvailability)
//                .setHeader("Availability").setComparator((p1, p2) -> {
//            return p1.getAvailability().toString()
//                    .compareTo(p2.getAvailability().toString());
//        });

        // Show empty stock as "-"
        addColumn(product -> {
            if (product.getStockCount() == 0) {
                return "-";
            }
            return Integer.toString(product.getStockCount());
        }).setHeader("Stock Count").setComparator((p1, p2) -> {
            return Integer.compare(p1.getStockCount(), p2.getStockCount());
        });

        //TODO How to set css class?
        //.setStyleGenerator(product -> "align-right");

        // Show all categories the product is in, separated by commas
        addColumn(this::formatCategories).setHeader("Category").setSortable(false);
    }

    public Product getSelectedRow() {
        return asSingleSelect().getValue();
    }

    public void refresh(Product product) {
        getDataCommunicator().refresh(product);
    }

    private String htmlFormatAvailability(Product product) {
        Availability availability = product.getAvailability();
        String text = availability.toString();

        String color = "";
        switch (availability) {
            case AVAILABLE:
                color = "#2dd085";
                break;
            case COMING:
                color = "#ffc66e";
                break;
            case DISCONTINUED:
                color = "#f54993";
                break;
            default:
                break;
        }

        String iconCode = "<span class=\"v-icon\" style=\"font-family: "
                + VaadinIcons.CIRCLE.getFontFamily() + ";color:" + color
                + "\">&#x"
                + Integer.toHexString(VaadinIcons.CIRCLE.getCodepoint())
                + ";</span>";

        return iconCode + " " + text;
    }

    private String formatCategories(Product product) {
        if (product.getCategory() == null || product.getCategory().isEmpty()) {
            return "";
        }
        return product.getCategory().stream()
                .sorted(Comparator.comparing(Category::getId))
                .map(Category::getName).collect(Collectors.joining(", "));
    }
}
