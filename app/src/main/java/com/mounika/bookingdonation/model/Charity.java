package com.mounika.bookingdonation.model;

public class Charity {
    private int image_id;
    private int charityId;
    private String text;

    public int getImage_id()
    {
        return image_id;
    }

    public void setImage_id(int image_id)
    {
        this.image_id = image_id;
    }

    public String getText()
    {
        return text;
    }
    public int getItemId()
    {
        return charityId;
    }

    public void setText(String text)
    {
        this.text = text;
    }
    public void setItemId(int text)
    {
        this.charityId = charityId;
    }

    public Charity(int id, int img, String text)
    {
        image_id = img;
        charityId = id;
        this.text = text;
    }
}
