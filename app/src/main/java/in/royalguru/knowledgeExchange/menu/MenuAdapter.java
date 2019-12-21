package in.royalguru.knowledgeExchange.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.kalmesh.projectbase.Debug;
import in.royalguru.knowledgeExchange.R;
import in.royalguru.knowledgeExchange.enums.EnumClicks;
import in.royalguru.knowledgeExchange.listeners.OnItemClickListener;

/**
 * Created by Kalmeshwar on 01 Oct 2019 at 18:50.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder> {
    private ArrayList<MenuModel> menuList;
    private Context mContext;
    private OnItemClickListener itemClickListener;


    public MenuAdapter(Context mContext, ArrayList<MenuModel> menuList, OnItemClickListener itemClickListener) {
        this.menuList = menuList;
        this.mContext = mContext;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_row, parent, false);

        return new MenuHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder holder, int position) {

        MenuModel menuModel = menuList.get(position);
        Debug.printLogError(menuModel.getName(), "" + menuList.size());

        if (menuModel != null) {
            holder.icon.setImageResource(menuModel.getIcon());
            holder.txt_menu_name.setText(menuModel.getName());
        }


        holder.itemView.setOnClickListener(v ->
                itemClickListener.onItemClickListener(EnumClicks.CELL_CLICK, null, position, menuModel.getName(), menuModel.getId() != null ? menuModel.getId() : "ALL", null, false));

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    class MenuHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView txt_menu_name;


        MenuHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.img_icon);
            txt_menu_name = itemView.findViewById(R.id.txt_menu_name);
        }
    }
}
