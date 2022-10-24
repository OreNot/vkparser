public class ADPerson {

    private String cN;
    private String fName;
    private String lName;
    private String displayName;
    private String canonicalName;
    private String homePostalAddress;
    private String autoReplyMessage;
    private String department;
    private String title;
    private String city;
    private String streetAddress;
    private String userPrincipalName;
    private String sID;
    private String enabled;
    private String mobilePhone;
    private String mobile;
    private String telephoneNumber;
    private String lastLogonDate;
    private String birthdate;

    public ADPerson(String cN, String fName, String lName, String displayName, String canonicalName, String homePostalAddress, String autoReplyMessage, String department, String title, String city, String streetAddress, String userPrincipalName, String sID, String enabled, String mobilePhone, String mobile, String telephoneNumber, String lastLogonDate, String birthdate) {
        this.cN = cN;
        this.fName = fName;
        this.lName = lName;
        this.displayName = displayName;
        this.canonicalName = canonicalName;
        this.homePostalAddress = homePostalAddress;
        this.autoReplyMessage = autoReplyMessage;
        this.department = department;
        this.title = title;
        this.city = city;
        this.streetAddress = streetAddress;
        this.userPrincipalName = userPrincipalName;
        this.sID = sID;
        this.enabled = enabled;
        this.mobilePhone = mobilePhone;
        this.mobile = mobile;
        this.telephoneNumber = telephoneNumber;
        this.lastLogonDate = lastLogonDate;
        this.birthdate = birthdate;

    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCanonicalName() {
        return canonicalName;
    }

    public void setCanonicalName(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public String getHomePostalAddress() {
        return homePostalAddress;
    }

    public void setHomePostalAddress(String homePostalAddress) {
        this.homePostalAddress = homePostalAddress;
    }

    public String getAutoReplyMessage() {
        return autoReplyMessage;
    }

    public void setAutoReplyMessage(String autoReplyMessage) {
        this.autoReplyMessage = autoReplyMessage;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getUserPrincipalName() {
        return userPrincipalName;
    }

    public void setUserPrincipalName(String userPrincipalName) {
        this.userPrincipalName = userPrincipalName;
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getLastLogonDate() {
        return lastLogonDate;
    }

    public void setLastLogonDate(String lastLogonDate) {
        this.lastLogonDate = lastLogonDate;
    }

    public String getcN() {
        return cN;
    }

    public void setcN(String cN) {
        this.cN = cN;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "ADPerson{" +
                "CN='" + cN + '\'' +
                "fName='" + fName + '\'' +
                "lName='" + lName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", canonicalName='" + canonicalName + '\'' +
                ", homePostalAddress='" + homePostalAddress + '\'' +
                ", autoReplyMessage='" + autoReplyMessage + '\'' +
                ", department='" + department + '\'' +
                ", title='" + title + '\'' +
                ", city='" + city + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", userPrincipalName='" + userPrincipalName + '\'' +
                ", sID='" + sID + '\'' +
                ", enabled='" + enabled + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", mobile='" + mobile + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", lastLogonDate='" + lastLogonDate + '\'' +
                ", birthdate='" + birthdate + '\'' +
                '}';
    }
}
