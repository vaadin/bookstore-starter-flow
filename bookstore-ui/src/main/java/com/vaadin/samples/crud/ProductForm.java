package com.vaadin.samples.crud;

import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.samples.backend.data.Availability;
import com.vaadin.samples.backend.data.Category;
import com.vaadin.samples.backend.data.Product;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Locale;

/**
 * A form for editing a single product.
 * <p>
 * Using responsive layouts, the form can be displayed either sliding out on the
 * side of the view or filling the whole screen - see the theme for the related
 * CSS rules.
 */
public class ProductForm extends ProductFormDesign {

    private SampleCrudLogic viewLogic;
    private Binder<Product> binder;
    private Product currentProduct;

    private static class StockPriceConverter extends StringToIntegerConverter {

        public StockPriceConverter() {
            super("Could not convert value to " + Integer.class.getName());
        }

        @Override
        protected NumberFormat getFormat(Locale locale) {
            // do not use a thousands separator, as HTML5 input type
            // number expects a fixed wire/DOM number format regardless
            // of how the browser presents it to the user (which could
            // depend on the browser locale)
            DecimalFormat format = new DecimalFormat();
            format.setMaximumFractionDigits(0);
            format.setDecimalSeparatorAlwaysShown(false);
            format.setParseIntegerOnly(true);
            format.setGroupingUsed(false);
            return format;
        }

        @Override
        public Result<Integer> convertToModel(String value,
                                              ValueContext context) {
            Result<Integer> result = super.convertToModel(value, context);
            return result.map(stock -> stock == null ? 0 : stock);
        }

    }

    public ProductForm(SampleCrudLogic sampleCrudLogic) {
        super();

        //TODO
        //addClassName("product-form");

        viewLogic = sampleCrudLogic;

        availability.setItems(Availability.values());
        availability.setAllowCustomValue(false);

        binder = new BeanValidationBinder<>(Product.class);
        binder.forField(price).withConverter(new EuroConverter())
                .bind("price");
        binder.forField(stockCount).withConverter(new StockPriceConverter())
                .bind("stockCount");

        //TODO
        //category.setItemCaptionGenerator(Category::getName);

//        binder.forField(category).bind("category");

        binder.bindInstanceFields(this);

        // enable/disable save button while editing
        binder.addStatusChangeListener(event -> {
            boolean isValid = !event.hasValidationErrors();
            boolean hasChanges = binder.hasChanges();
            save.setEnabled(hasChanges && isValid);
            discard.setEnabled(hasChanges);
        });

        save.addClickListener(event -> {
            if (currentProduct != null
                    && binder.writeBeanIfValid(currentProduct)) {
                viewLogic.saveProduct(currentProduct);
            }
        });

        discard.addClickListener(
                event -> viewLogic.editProduct(currentProduct));

        cancel.addClickListener(event -> viewLogic.cancelProduct());

        delete.addClickListener(event -> {
            if (currentProduct != null) {
                viewLogic.deleteProduct(currentProduct);
            }
        });

        delete.setClassName("v-button-danger");
    }

    public void setCategories(Collection<Category> categories) {
        //TODO
        //category.setItems(categories);
    }

    public void editProduct(Product product) {
        if (product == null) {
            product = new Product();
        }
        currentProduct = product;
        binder.readBean(product);

        // Scroll to the top
        // As this is not a Panel, using JavaScript
        //TODO getId() is null
//        String scrollScript = "window.document.getElementById('" + getId()
//                + "').scrollTop = 0;";
//        UI.getCurrent().getPage().executeJavaScript(scrollScript);
    }
}
