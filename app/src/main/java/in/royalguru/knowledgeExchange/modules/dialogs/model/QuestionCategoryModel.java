package in.royalguru.knowledgeExchange.modules.dialogs.model;

/**
 * Created by Kalmeshwar on 15 Oct 2019 at 16:00.
 */
public class QuestionCategoryModel {

    private String category;
    private String id;
    private boolean isSelected;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
