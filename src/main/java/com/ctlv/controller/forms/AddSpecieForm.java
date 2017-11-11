package com.ctlv.controller.forms;

import com.ctlv.controller.AppUI;
import com.ctlv.controller.beans.Animal;
import com.ctlv.controller.beans.AnimalCategory;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class AddSpecieForm extends Panel{

	private static final long serialVersionUID = 1L;
	
	public AddSpecieForm(AppUI controller, Window window, AnimalCategory cat){
		super();
		setContent(this.createAddForm(controller, window, cat));
		addStyleName(ValoTheme.PANEL_BORDERLESS);

	}
	
	private Component createAddForm(AppUI controller, Window window, AnimalCategory cat){
		VerticalLayout vL = new VerticalLayout();
		
		TextField specieName = new TextField();
		specieName.setPlaceholder("Enter the animal's specie (cannot be empty)");
		specieName.setCaption("Animal's Specie");
		specieName.setRequiredIndicatorVisible(true);
		specieName.setSizeFull();
		
		TextArea specieDescr = new TextArea();
		specieDescr.setCaption("Specie's Description");
		specieDescr.setPlaceholder("Write the specie's description");
		specieDescr.setValue("");
		specieDescr.setSizeFull();
		
		Button validateBut = new Button("Validate");
		validateBut.addClickListener(event -> {
			if (specieName.getValue()!=null && !specieName.getValue().equals("")){
				Animal animal = new Animal();
				animal.setAnimalCategory(cat);
				animal.setSpecie(specieName.getValue());
				animal.setDescription(specieDescr.getValue());
				controller.saveAnimal(animal);
				window.close();

			}
			else {
				specieName.setValue("");
				specieName.setPlaceholder("CANNOT BE EMPTY");
			}
		});
		
		vL.addComponent(specieName);
		vL.addComponent(specieDescr);
		vL.addComponent(new Label(""));
		vL.addComponent(validateBut);

		return vL;
	}

}
