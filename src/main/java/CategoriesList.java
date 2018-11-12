import javax.swing.*;

public class CategoriesList extends MenuList {
    private static CategoriesList categoriesList;

    //TODO z properties
    private static String[] fields = new String[] {"C1", "Cat2", "Category3", "Categoryyy4"};

    public static CategoriesList initList(JPanel panel) {
        categoriesList = new CategoriesList(panel, fields);
        return categoriesList;
    }

    public static CategoriesList getList() {
        if(categoriesList == null) throw new NullPointerException("You need to init list first!");
        return categoriesList;
    }
    private CategoriesList(JPanel panel, String[] fields) {
        super(panel, fields);
        //initListFields();
    }
}
