import java.util.*;

public class Friend {

    private String id;
    private String first_name;
    private String last_name;
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
  //  private String last_seen;

    List<Friend> items = new ArrayList<>();

    public Friend(String first_name, String last_name) {
     //   this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
      //  this.city = city;
    }

    public String getCityTitle() {
        return cityTitle;
    }

    public void setCityTitle(String cityTitle) {
        this.cityTitle = cityTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getCity() {
        return city;
    }

    public void setCity(Map<String, String> city) {
        this.city = city;
    }

    public String getPhoto_50() {
        return photo_50;
    }

    public void setPhoto_50(String photo_50) {
        this.photo_50 = photo_50;
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
        return "Friend{" +
                ", id='" + id + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
               // ", country='" + country + '\'' +
                ", cityTitle='" + cityTitle + '\'' +
                ", nickname='" + nickname + '\'' +
                ", domain='" + domain + '\'' +
                ", bdate='" + bdate + '\'' +
                ", has_mobile='" + has_mobile + '\'' +
                ", online='" + online + '\'' +
                ", status='" + status + '\'' +
                ", photo='" + photo_50 + '\'' +
                '}';
    }
}
