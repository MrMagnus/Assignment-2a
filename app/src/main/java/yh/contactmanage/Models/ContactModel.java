package yh.contactmanage.Models;

/**
 * Created by Magnus on 2014-09-16.
 */
public class ContactModel {

    //Class variables
    private String url;
    private String name;
    private String age;
    private String description;

    public ContactModel(String url, String name, String age, String description){

        this.url = url;
        this.name = name;
        this.age = age;
        this.description = description;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName(){
        return name;
    }

    public String getAge(){
        return age;
    }

    public String getDescription(){
        return description;
    }
}
