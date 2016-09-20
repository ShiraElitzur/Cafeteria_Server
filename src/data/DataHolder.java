package data;
import java.util.ArrayList;
import java.util.List;


public class DataHolder {
	
	private static DataHolder theData;
	private List<Category> categories;
	
	private DataHolder() {
		initCategories();
	}
	
	public static DataHolder getInstance() {
		if( theData == null ) {
			return new DataHolder();
		} else {
			return theData;
		}
	}
	
	public List<Category> getCategories(){
        return categories;
	}
	
	private void initCategories() {
		categories = new ArrayList<>();
        Category cat = new Category();
        cat.setTitle("����");
        cat.setDescription("������ ������ �������");
        // init items in category
        List<Item> items = new ArrayList<>();
        List<Meal> meals = new ArrayList<>();
        Meal meal = new Meal();
        Main main = new Main();
        Item item = new Item();
        List<Drink> drinks = new ArrayList<>();
        Drink drink = new Drink();
        drink.setTitle("���� ����");
        drink.setPrice(7.5);
        drinks.add(drink);
        drink = new Drink();
        drink.setTitle("�����");
        drink.setPrice(7.5);
        drinks.add(drink);
        drink = new Drink();
        drink.setTitle("�����");
        drink.setPrice(6.5);
        drinks.add(drink);
        drink = new Drink();
        drink.setTitle("������");
        drink.setPrice(7.5);
        drinks.add(drink);

        List<Item> extras = new ArrayList<Item>();
        Item extra = new Item();
        extra.setTitle("����");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("�'���");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("�����");
        extra.setPrice(5);
        extras.add(extra);

        item.setTitle("�����");
        item.setStandAlone(false);
        meal.setTitle("����� �����");
        meal.setExtraAmount(2);
        meal.setExtras(extras);
        meal.setDrinkOptions(drinks);
        meal.setPrice(35.66657);
        main.setTitle("�����");
        meal.setMain(main);
        meals.add(meal);
        
        extras = new ArrayList<Item>();
        extra = new Item();
        extra.setTitle("�����");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("����");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("�����");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("������");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("���� ����");
        extra.setPrice(5);
        extras.add(extra);
        
        meal = new Meal();
        meal.setTitle("����� �����");
        main = new Main();
        main.setTitle("�����");
        meal.setMain(main);
        meal.setPrice(35.66657);
        meal.setExtras(extras);
        meals.add(meal);
        
        meal = new Meal();
        meal.setTitle("����� �����");
        main = new Main();
        main.setTitle("�����");
        meal.setMain(main);
        meal.setPrice(15.5);
        meal.setExtras(extras);
        meals.add(meal);
        
        extras = new ArrayList<Item>();
        extra = new Item();
        extra.setTitle("����");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("�'���");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("�����");
        extra.setPrice(5);
        extras.add(extra);
        
        items.add(item);

        item = new Item();
        item.setTitle("�������");
        item.setStandAlone(false);
        meal = new Meal();
        meal.setTitle("������� �����");
        main = new Main();
        main.setTitle("�������");
        meal.setMain(main);
        meal.setPrice(32.5);
        meal.setExtras(extras);
        meals.add(meal);
        
        extras = new ArrayList<Item>();
        extra = new Item();
        extra.setTitle("�����");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("����");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("�����");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("������");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("���� ����");
        extra.setPrice(5);
        extras.add(extra);
        
        meal = new Meal();
        meal.setTitle("������� �����");
        main = new Main();
        main.setTitle("�������");
        meal.setMain(main);
        meal.setPrice(16);
        meal.setExtras(extras);
        meals.add(meal);
        
        extras = new ArrayList<Item>();
        extra = new Item();
        extra.setTitle("�����");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("����");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("�����");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("������");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("���� ����");
        extra.setPrice(5);
        extras.add(extra);
        
        meal = new Meal();
        meal.setTitle("������� �����");
        main = new Main();
        main.setTitle("�������");
        meal.setMain(main);
        meal.setPrice(17.6);
        meal.setExtras(extras);
        meals.add(meal);
        items.add(item);

        
        extras = new ArrayList<Item>();
        extra = new Item();
        extra.setTitle("�����");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("����� ����");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("������");
        extra.setPrice(5);
        extras.add(extra);
        
        
        item = new Item();
        item.setTitle("�����");
        item.setStandAlone(false);
        meal = new Meal();
        meal.setTitle("����� �����");
        main = new Main();
        main.setTitle("�����");
        meal.setMain(main);
        meal.setPrice(37);
        meal.setExtraAmount(2);
        meal.setExtras(extras);
        meals.add(meal);
        
        extras = new ArrayList<Item>();
        extra = new Item();
        extra.setTitle("�����");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("����");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("�����");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("������");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("���� ����");
        extra.setPrice(5);
        extras.add(extra);
        
        meal = new Meal();
        meal.setTitle("����� �����");
        main = new Main();
        main.setTitle("�����");
        meal.setMain(main);
        meal.setPrice(22);
        meal.setExtras(extras);
        meals.add(meal);
        
        meal = new Meal();
        meal.setTitle("����� �����");
        main = new Main();
        main.setTitle("�����");
        meal.setMain(main);
        meal.setPrice(18);
        meal.setExtras(extras);
        meals.add(meal);
        items.add(item);


        // set items and meals to category
        cat.setItems(items);
        cat.setMeals(meals);
        categories.add(cat);
        
        items = new ArrayList<>();
        
        cat = new Category();
        cat.setTitle("����");
        cat.setDescription("������ ������ �������");
        item = new Item();
        item.setTitle("����");
        item.setStandAlone(false);
        items.add(item);
        meals = new ArrayList<>();
        meal = new Meal();
        meal.setTitle("���� �����");
        main = new Main();
        main.setTitle("����");
        meal.setMain(main);
        meal.setPrice(15);
        
        extras = new ArrayList<Item>();
        extra = new Item();
        extra.setTitle("������");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("���� ����");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("���");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("�����");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("���� ���");
        extra.setPrice(5);
        extras.add(extra);
        meal.setExtras(extras);
        meals.add(meal);
        cat.setItems(items);
        cat.setMeals(meals);
        categories.add(cat);
        
        cat = new Category();
        cat.setTitle("�����");
        cat.setDescription("���� ����� ������");
        
        meals = new ArrayList<>();
        items = new ArrayList<>();
        item = new Item();
        item.setTitle("���");
        items.add(item);
        meal = new Meal();
        main = new Main();
        main.setTitle("���");
        meal.setTitle("��� ���");
        meal.setMain(main);
        meal.setPrice(15);
        
        extras = new ArrayList<Item>();
        extra = new Item();
        extra.setTitle("������");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("������");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("���");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("������");
        extra.setPrice(5);
        extras.add(extra);
        extra = new Item();
        extra.setTitle("����");
        extra.setPrice(5);
        extras.add(extra);
        meal.setExtras(extras);
        meals.add(meal);
        cat.setMeals(meals);
        
        cat.setItems(items);
        categories.add(cat);
        
        cat = new Category();
        cat.setTitle("���� ���");
        cat.setDescription("�� ���� ������ ����");
        List<Item> hotDrinks = new ArrayList<>();
        item = new Item();
        item.setTitle("��� ����");
        item.setPrice(7);
        item.setStandAlone(true);
        hotDrinks.add(item);
        item = new Item();
        item.setTitle("��� ���");
        item.setPrice(5);
        item.setStandAlone(true);
        hotDrinks.add(item);
        item = new Item();
        item.setTitle("��� ����");
        item.setPrice(4);
        item.setStandAlone(true);
        hotDrinks.add(item);
        item = new Item();
        item.setTitle("��");
        item.setPrice(2);
        item.setStandAlone(true);
        hotDrinks.add(item);
        cat.setItems(hotDrinks);
        categories.add(cat);
        
        cat = new Category();
        cat.setTitle("�����");
        cat.setDescription("�� ���� ������");
        
        List<Item> bakes = new ArrayList<>();
        item = new Item();
        item.setTitle("�������");
        item.setPrice(6);
        bakes.add(item);
        item = new Item();
        item.setTitle("�����");
        item.setPrice(4);
        bakes.add(item);
        item = new Item();
        item.setTitle("�����");
        item.setPrice(5);
        bakes.add(item);
        cat.setItems(bakes);
        categories.add(cat);
        cat = new Category();
        cat.setTitle("������");
        cat.setDescription("����,�����,��� ���...");
        List<Item> snacks = new ArrayList<>();
        item = new Item();
        item.setTitle("���� �����");
        item.setPrice(7);
        snacks.add(item);
        item = new Item();
        item.setTitle("���� ����");
        item.setPrice(5);
        snacks.add(item);
        item = new Item();
        item.setTitle("�����");
        item.setPrice(5);
        snacks.add(item);
        item = new Item();
        item.setTitle("���� ��� ���");
        item.setPrice(4.5);
        snacks.add(item);
        item = new Item();
        item.setTitle("�������");
        item.setPrice(6.6);
        snacks.add(item);
        item = new Item();
        item.setTitle("����� ������");
        item.setPrice(6);
        snacks.add(item);
        cat.setItems(snacks);
        categories.add(cat);

        cat = new Category();
        cat.setTitle("���� ���");
        cat.setDescription("�� ���� ����� ����");
        items = new ArrayList<Item>(drinks);
        cat.setItems(items);
        categories.add(cat);


        
	}

}
