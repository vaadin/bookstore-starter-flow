package com.vaadin.samples.crud;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.icon.VaadinIcons;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.Route;
import com.vaadin.samples.MainScreen;
import com.vaadin.samples.backend.DataService;
import com.vaadin.samples.backend.data.Product;
import com.vaadin.ui.themes.ValoTheme;

@Route(value = "Inventory", layout = MainScreen.class)
@StyleSheet("/css/shared-styles.css")
public class SampleCrudView extends HorizontalLayout implements BeforeEnterListener {

    public static final String VIEW_NAME = "Inventory";
    private ProductGrid grid;
    private ProductForm form;
    private TextField filter;

    private SampleCrudLogic viewLogic = new SampleCrudLogic(this);
    private Button newProduct;

    private ProductDataProvider dataProvider = new ProductDataProvider();

    public SampleCrudView() {
        setSizeFull();
        addClassName("crud-view");
        HorizontalLayout topLayout = createTopBar();

        grid = new ProductGrid();
        grid.setDataProvider(dataProvider);
        grid.asSingleSelect().addValueChangeListener(
                event -> viewLogic.rowSelected(event.getValue()));

        form = new ProductForm(viewLogic);
        form.setCategories(DataService.get().getAllCategories());

        VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.add(topLayout);
        barAndGridLayout.add(grid);
        barAndGridLayout.setFlexGrow(1, grid);
        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.expand(grid);
        barAndGridLayout.setClassName("crud-main-layout");

        add(barAndGridLayout);
        add(form);

        viewLogic.init();
    }

    public HorizontalLayout createTopBar() {
        filter = new TextField();
        filter.setClassName("filter-textfield");
        filter.setPlaceholder("Filter name, availability or category");

        //TODO Vaadin Directory?
        //ResetButtonForTextField.extend(filter);

        // Apply the filter to grid's data provider. TextField value is never null
        filter.addValueChangeListener(event -> dataProvider.setFilter(event.getValue()));

        newProduct = new Button("New product");
        newProduct.addClassName(ValoTheme.BUTTON_PRIMARY);
        newProduct.setIcon(VaadinIcons.PLUS_CIRCLE.create());
        newProduct.addClickListener(click -> viewLogic.newProduct());

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(filter);
        topLayout.add(newProduct);
        topLayout.setVerticalComponentAlignment(Alignment.START, filter);
        topLayout.expand(filter);
        topLayout.setClassName("top-bar");
        return topLayout;
    }


    public void showError(String msg) {
        Notification.show(msg);
    }

    public void showSaveNotification(String msg) {
        Notification.show(msg);
    }

    public void setNewProductEnabled(boolean enabled) {
        newProduct.setEnabled(enabled);
    }

    public void clearSelection() {
        grid.getSelectionModel().deselectAll();
    }

    public void selectRow(Product row) {
        grid.getSelectionModel().select(row);
    }

    public Product getSelectedRow() {
        return grid.getSelectedRow();
    }

    public void updateProduct(Product product) {
        dataProvider.save(product);
        // FIXME: Grid used to scroll to the updated item
    }

    public void removeProduct(Product product) {
        dataProvider.delete(product);
    }

    public void editProduct(Product product) {
        //TODO
        if (product != null) {
            form.getElement().getClassList().add("visible");
            form.getElement().setEnabled(true);
        } else {
            form.getElement().getClassList().remove("visible");
            form.getElement().setEnabled(false);
        }
        form.editProduct(product);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        //TODO must be tested
        viewLogic.enter(event.getLocation().getQueryParameters().getQueryString());

    }
}
