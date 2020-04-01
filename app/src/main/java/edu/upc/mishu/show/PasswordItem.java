package edu.upc.mishu.show;

public class PasswordItem {
    private int imageId;
    private String website;
    private String username;
    public PasswordItem (){};

    public PasswordItem(int imageId,String website , String username){
        this.imageId = imageId;
        this.website = website;
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getUsername() {
        return username;
    }

    public String getWebsite() {
        return website;
    }

    public int getImageId() {
        return imageId;
    }

    @Override
    public String toString() {
        return "PasswordItem{" +
                "imageId=" + imageId +
                ", website='" + website + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
