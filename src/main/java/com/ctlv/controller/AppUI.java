package com.ctlv.controller;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.ctlv.controller.beans.Animal;
import com.ctlv.controller.beans.AnimalCategory;
import com.ctlv.controller.forms.AddCategoryForm;
import com.ctlv.controller.forms.AddSpecieForm;
import com.ctlv.controller.repositories.AnimalCategoryRepository;
import com.ctlv.controller.repositories.AnimalRepository;
import com.gear.commons.util.GearStyles;
import com.gear.components.core.grid.Grid;
import com.gear.components.core.grid.Row;
import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SpringUI(path="/")
//@SpringViewDisplay
//@PreserveOnRefresh
@Theme("mytheme")
public class AppUI extends UI{

	@Autowired private AnimalCategoryRepository catAnimalRepo;
	@Autowired private AnimalRepository animalRepo;

	
	private static final long serialVersionUID = -1932159237650704685L;

	private HashMap<String, Panel> mapPanel;
	private Row rowGlob;
	
	/**
	 * View of our application
	 */
	@Override
	protected void init(VaadinRequest request) {

		VerticalLayout vL = new VerticalLayout();
		vL.setMargin(false);
		
		Component hearder = this.makeHeader();
		Component mainComponent = this.makeMainComponent();
		Component footer = this.makeFooter();
		
		vL.addComponent(hearder);
		vL.addComponent(mainComponent);
		vL.addComponent(footer);
		
	    setContent(vL);
		setLocale(Locale.FRANCE);
	}

	/**
	 * Create the Header Component
	 * @return Component
	 */
	private Component makeHeader(){
		
		CssLayout layout = new CssLayout();
		Label title = new Label("<h1>CTLV<h1>", ContentMode.HTML);

		VerticalLayout vL = new VerticalLayout();
		vL.setMargin(false);
		vL.addComponent(title);
		layout.addComponent(vL);
		return layout;
	}
	
	/**
	 * Create the Body Component
	 * @return Component
	 */
	private Component makeMainComponent(){
		
		this.rowGlob=new Row();
		
		//Map to avoid creating a new panel at each selection change
		this.mapPanel = new HashMap<String, Panel>();

		Grid grid = new Grid();
		rowGlob.addCell(this.createAnimalCatList());
		grid.addComponent(rowGlob);
	    return grid;
	}
	
	/**
	 * Create the Footer Component
	 * @return Component
	 */
	private Component makeFooter(){
		
		Panel pan = new Panel();
		pan.addStyleName(ValoTheme.PANEL_BORDERLESS);
		Label ref = new Label("<Footer>Pair: <b><I>DUFAU Laetitia & GRASA Guillaume</Footer>", ContentMode.HTML);
		
		VerticalLayout vL = new VerticalLayout();
		vL.setMargin(false);
		
		//Add a space between the components
		vL.addComponent(new Label());
		vL.addComponent(ref);
		
		pan.setContent(vL);
		return pan;
	}
	
	/**
	 * Create the Button list containing all the animal categories
	 * @return Component
	 */
	private Component createAnimalCatList(){
		
		Panel pan = new Panel();
		VerticalLayout vL = new VerticalLayout();
		vL.setMargin(false);
		
		HorizontalLayout categoryHeader = new HorizontalLayout();
		
		Label categoryLab = new Label("<h3>Animals</h3>", ContentMode.HTML);
		categoryLab.setWidth("100px");
		
		Button addCategory = this.createAddCategoryBut();
		
		categoryHeader.addComponent(categoryLab);
		categoryHeader.addComponent(addCategory);
		
		vL.addComponent(categoryHeader);
		
		vL.addComponent(new Label());
		
		this.catAnimalRepo.findAll().stream().forEach( c -> {
			Button butCat = new Button(c.getAnimalCat());
			butCat.addStyleName(ValoTheme.BUTTON_PRIMARY);
			butCat.setWidth("100px");
			butCat.addClickListener(event -> {
				
				//I the panel associated with the category is not yet created -> creation
				Panel panCat = this.mapPanel.get(c.getAnimalCat());
				if (panCat==null){
					
					panCat=this.createPanel(c);
					
				}
				this.rowGlob.removeAllComponents();
				this.rowGlob.addCell(this.createAnimalCatList(), 5);
				this.rowGlob.addCell(panCat, 7);

			});
			vL.addComponent(butCat);
		});
		
		pan.addStyleName(ValoTheme.PANEL_BORDERLESS);
		pan.setContent(vL);
		
		pan.setWidth("350px");
		return pan;
	}
		
	/**
	 * Create the Button enabling the add of a new animal category
	 * @return Button
	 */
	private Button createAddCategoryBut(){
		Button but = new Button("Add", VaadinIcons.PLUS);
		but.addClickListener(event -> {
			
			Window window= new Window();
			window.setCaption("Add Animal");
			window.center();
			window.setModal(true);
			window.setWidth("50%");
			window.setHeight("50%");
			
			AddCategoryForm addForm = new AddCategoryForm(this, window);
			
			window.setContent(addForm);
			window.addCloseListener(close -> {
				rowGlob.removeAllComponents();
				rowGlob.addCell(this.createAnimalCatList());
			});
			addWindow(window);
		});
		
		return but;
	}
	
	/**
	 * Create the Button enabling the add of a new animal specie
	 * @param AnimalCategory
	 * @return Button
	 */
	private Button createAddSpecieBut(AnimalCategory cat){
		Button but = new Button("Add", VaadinIcons.PLUS);
		but.addClickListener(event -> {
			
			Window window= new Window();
			window.setCaption("Add Specie");
			window.center();
			window.setModal(true);
			window.setWidth("50%");
			window.setHeight("50%");
			
			AddSpecieForm addForm = new AddSpecieForm(this, window, cat);
			
			window.setContent(addForm);
			window.addCloseListener(close -> {
				rowGlob.removeAllComponents();
				rowGlob.addCell(this.createAnimalCatList());
				mapPanel.remove(cat.getAnimalCat());
			});
			
			addWindow(window);
		});
		
		return but;
	}

	/**
	 * Create a panel associated to an animal Category
	 * @param AnimalCategory
	 * @return Panel
	 */
	private Panel createPanel(AnimalCategory c){
		Panel panCat=new Panel();
		panCat.addStyleName(ValoTheme.PANEL_BORDERLESS);
		VerticalLayout vLAnimals = new VerticalLayout();
		vLAnimals.setMargin(false);
		HorizontalLayout speciesHeader = new HorizontalLayout();
		
		Label specieLab = new Label("<h3>Species of "+c.getAnimalCat()+"s</h3>", ContentMode.HTML);
		specieLab.setWidth("300px");
		
		Button addSpecie = this.createAddSpecieBut(c);
		
		speciesHeader.addComponent(specieLab);
		speciesHeader.addComponent(addSpecie);
		
		vLAnimals.addComponent(speciesHeader);
		
		vLAnimals.addComponent(new Label());
		
		catAnimalRepo.getAnimalsForCategory(c.getId()).stream()
			.sorted((p1, p2) -> p1.getSpecie().toUpperCase().compareTo(p2.getSpecie().toUpperCase()))
			.forEach( animal -> {
				
				
				Label specie = new Label("<b>" + animal.getSpecie() + " :</b>", ContentMode.HTML);

				vLAnimals.addComponent(specie);

				RichTextArea descr = new RichTextArea();
				descr.setValue(animal.getDescription());
				descr.setReadOnly(true);
				descr.setWidth("600px");
				descr.setHeight("75px");
				
				vLAnimals.addComponent(descr);

				vLAnimals.addComponent(new Label());
			});
		panCat.setContent(vLAnimals);
		
		//Save the panel
		this.mapPanel.put(c.getAnimalCat(), panCat);
		
		return panCat;
	}
	
	/**
	 * Enable to add an animal into the database
	 * @param Animal
	 */
	public void saveAnimal(Animal animal){
		animalRepo.save(animal);
	}
	
	/**
	 * Enable to add a category of animal into the database
	 * @param cat
	 */
	public void saveAnimalCat(AnimalCategory cat){
		catAnimalRepo.save(cat);
	}
}


