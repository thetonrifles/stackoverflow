package com.thetonrifles.stackoverflow;

public class ListItem {

    private String label;
    private String imgUrl;

    public ListItem(String label, String imgUrl) {
        this.label = label;
        this.imgUrl = imgUrl;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
