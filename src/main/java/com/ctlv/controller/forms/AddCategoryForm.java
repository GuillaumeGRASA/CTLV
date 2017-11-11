package com.ctlv.controller.forms;

import com.ctlv.controller.AppUI;
import com.ctlv.controller.beans.AnimalCategory;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class AddCategoryForm extends Panel{
	
	private static final long serialVersionUID = 1L;
	
	public AddCategoryForm(AppUI controller, Window window){
		super();
		setContent(this.createAddForm(controller, window));
		addStyleName(ValoTheme.PANEL_BORDERLESS);
	}
	
	private Component createAddForm(AppUI controller, Window window){
		VerticalLayout vL = new VerticalLayout();
		
		TextField catName = new TextField();
		catName.setPlaceholder("Enter the animal's name (cannot be empty)");
		catName.setCaption("Animal's Name");
		catName.setRequiredIndicatorVisible(true);
		catName.setSizeFull();

		Button validateBut = new Button("Validate");
		validateBut.addClickListener(event -> {
			if (catName.getValue()!=null && !catName.getValue().equals("")){
				AnimalCategory cat = new AnimalCategory();
				cat.setAnimalCat(catName.getValue());
				controller.saveAnimalCat(cat);
				window.close();
			}
			else {
				catName.setValue("");
				catName.setPlaceholder("CANNOT BE EMPTY");
			}
		});
		
		vL.addComponent(catName);
		vL.addComponent(new Label(""));
		vL.addComponent(validateBut);

		return vL;
	}

}
