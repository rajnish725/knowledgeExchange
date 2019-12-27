package in.royalguru.knowledgeExchange.menu;

import java.util.ArrayList;

import in.royalguru.knowledgeExchange.R;
import in.royalguru.knowledgeExchange.enums.EnumClicks;

/**
 * Created by Kalmeshwar on 01 Oct 2019 at 18:51.
 */
public class MenuModel {

    private String id;
    private int icon;
    private String name;
    private EnumClicks clicks;
    private MenuModel menuModel = null;
    private ArrayList<MenuModel> menuList = null;
    private String status;


    public MenuModel() {
    }

    public MenuModel(int icon, String name, EnumClicks clicks) {
        this.icon = icon;
        this.name = name;
        this.clicks = clicks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EnumClicks getClicks() {
        return clicks;
    }

    public void setClicks(EnumClicks clicks) {
        this.clicks = clicks;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<MenuModel> getMenuList() {
        return menuList;
    }

    public void setMenuList(ArrayList<MenuModel> menuList) {
        this.menuList = menuList;
    }

    public void setName(String name) {
        this.name = name;
    }


    public ArrayList<MenuModel> addMenuData() {
        menuList = new ArrayList<>();

        menuModel = new MenuModel(R.drawable.icon_home_vector, "Home", EnumClicks.HOME);
        menuList.add(menuModel);

        menuModel = new MenuModel(R.drawable.icon_discount_vector, "History", EnumClicks.HOME);
        menuList.add(menuModel);

        menuModel = new MenuModel(R.drawable.icon_close_remove_add_trn_vector, "Current Affairs", EnumClicks.HOME);
        menuList.add(menuModel);

        menuModel = new MenuModel(R.drawable.icon_barcode_img_vector, "Computer", EnumClicks.HOME);
        menuList.add(menuModel);

        menuModel = new MenuModel(R.drawable.icon_close_remove_add_trn_vector, "Hindi", EnumClicks.HOME);
        menuList.add(menuModel);

        menuModel = new MenuModel(R.drawable.icon_home_vector, "Share", EnumClicks.SHARE);
        menuList.add(menuModel);

       /* if (new SessionManager(mContext).isLoggedIn()) {
            menuModel = new MenuModel(R.drawable.icon_logout_vector, "Logout", LOGOUT);

        } else {
            menuModel = new MenuModel(R.drawable.icon_logout_vector, "Login", EnumClicks.LOGIN);
        }*/


        menuList.add(menuModel);

        return menuList;
    }


}
