import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account {

    private static String APP_ID = "6502596";
    private static List<ADPerson> adPersonList = new ArrayList<>();
    private static Account account;
    private static String responseString;
    private String id;
    private String first_name;
    private String firstName;
    private String last_name;
    private String lastName;
    // private String sex;
    private String nickname;
    private String domain;
    private String bdate;
    private Map<String, String> city = new HashMap<>();
    private String cityTitle;
    // private String country;
    private String photo_50;
    //private String photo_100;
    //private String photo_200_orig;
    private String has_mobile;
    private String online;
    private String status;
    private Map<String, String> career = new HashMap<>();
    private String educationForm;
    private String educationStatus;
    private String facultyName;
    //  private String last_seen;

    private int count;
    List<Account> items = new ArrayList<>();


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public Map<String, String> getCareer() {
        return career;
    }

    public void setCareer(Map<String, String> career) {
        this.career = career;
    }

    public String getEducationForm() {
        return educationForm;
    }

    public void setEducationForm(String educationForm) {
        this.educationForm = educationForm;
    }

    public String getEducationStatus() {
        return educationStatus;
    }

    public void setEducationStatus(String educationStatus) {
        this.educationStatus = educationStatus;
    }

    public List<Account> getItems() {
        return items;
    }

    public void setItems(List<Account> items) {
        this.items = items;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public Map<String, String> getCity() {
        return city;
    }

    public void setCity(Map<String, String> city) {
        this.city = city;
    }

    public String getCityTitle() {
        return cityTitle;
    }

    public void setCityTitle(String cityTitle) {
        this.cityTitle = cityTitle;
    }

    public String getPhoto_50() {
        return photo_50;
    }

    public void setPhoto_50(String photo_50) {
        this.photo_50 = photo_50;
    }

    public String getHas_mobile() {
        return has_mobile;
    }

    public void setHas_mobile(String has_mobile) {
        this.has_mobile = has_mobile;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", first_name='" + firstName + '\'' +
                ", last_name='" + lastName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", domain='" + domain + '\'' +
                ", bdate='" + bdate + '\'' +
                ", city=" + city +
                ", cityTitle='" + cityTitle + '\'' +
                ", photo_50='" + photo_50 + '\'' +
                ", has_mobile='" + has_mobile + '\'' +
                ", online='" + online + '\'' +
                ", status='" + status + '\'' +
                ", count=" + count +
                ", items=" + items +
                '}';
    }
}
