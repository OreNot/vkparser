import au.com.bytecode.opencsv.CSVReader;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;
import com.vk.api.sdk.client.Lang;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.ServiceClientCredentialsFlowResponse;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.friends.responses.GetResponse;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.users.UserField;
import com.vk.api.sdk.queries.users.UsersGetQuery;
import nu.pattern.OpenCV;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.presets.opencv_core;
import org.bytedeco.javacv.CanvasFrame;

import org.bytedeco.javacv.FrameGrabber;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;


import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;


public class Main {

    private static String APP_ID = "6502596";
    private static String CLIENT_SECRET = "yFOrMYfMCSUxEQzNJta8";
    private static String REDIRECT_URI = "";
    private static String token = "6edf50a46edf50a46edf50a4a06ebc686066edf6edf50a435c6777015dd6215b8066497";
    private static List<ADPerson> adPersonList = new ArrayList<>();
    private static Account account;
    private static String responseString;
    private static String comp = "";
    private static String pos = "";
    private static List<Thread> threads = new ArrayList<Thread>();
    private static TransportClient transportClient = HttpTransportClient.getInstance();
    private static VkApiClient vk = new VkApiClient(transportClient);

    public static void main (String... args) {

        int IDs = 70412701;

        List<UserXtrCounters> analyzeUserResp = new ArrayList<>();

        GetResponse friends = new GetResponse();
        ServiceActor actor = new ServiceActor(Integer.valueOf(APP_ID), CLIENT_SECRET, token);
        try {

            analyzeUserResp = vk.users().get(actor).userIds(String.valueOf(IDs)).fields(UserField.PHOTO_50, UserField.BDATE, UserField.CITY, UserField.CAREER, UserField.EDUCATION).lang(Lang.RU).execute();
            friends = vk.friends().get(actor).userId(IDs).execute();

        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

        String aNStr = analyzeUserResp.get(0).toString();
        aNStr = aNStr.replaceAll("UserXtrCounters", "");
        aNStr = aNStr.replaceAll("BaseObject", "");

        if (aNStr.contains("company")) {
            comp = aNStr.substring(aNStr.indexOf("company="), aNStr.length());
            comp = comp.substring(comp.indexOf("=") + 1, comp.indexOf(","));
            System.out.println(comp);
            aNStr = aNStr.replaceAll("company=" + comp + ",", "");
            //comp = "";
        }

        Gson g = new Gson();
        Account analyzeAccount = g.fromJson(aNStr, Account.class);
        analyzeAccount.setCount(friends.getCount());
        if (comp != "")
        {
            analyzeAccount.getCareer().put("company", comp);
            comp = "";
        }

        analyzeAccount.items.addAll(getFriendsSDK(friends, actor));

        for (Account acc : analyzeAccount.items)
        {
            friends = null;
            System.out.println(acc);
            try {
                friends = vk.friends().get(actor).userId(Integer.valueOf(acc.getId())).execute();
            } catch (ApiException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            }
            acc.items.addAll(getFriendsSDK(friends, actor));
        }








 //      account = getVKAccount("70412701");
 /*      // System.out.println(account.items.size());
        for (Account friend : account.items)
        {
            System.out.println("__________");
            System.out.println("Имя: " + friend.getFirst_name());
            System.out.println("Фамилия: " + friend.getLast_name());
            System.out.println("Д/Р: " + friend.getBdate());
            // System.out.println("Город: " + friend.getCityTitle());
            //System.out.println("Друзья: " + friend.getItems());
            System.out.println("__________");
        }
       List<Account> accIn  = new ArrayList<>();
       accIn.addAll(account.items);
       account.items.clear();

      int i = 0;
       for (Account acc : accIn)
       {
           //System.out.println(i++);
           Account account1 = getVKAccount(acc.getId());
           account.items.add(account1);
       }



*/


        //System.out.println("_______");
       // for (Account a : account.items) {
       //     System.out.println(a.items.size());
        //}

        adPersonList = getShtat();
        for (ADPerson person : adPersonList)
        {
            System.out.println(person.toString());
        }


    }

    public static List<Account> getFriendsSDK(GetResponse friends, ServiceActor actor)
    {
        Account friend;
        List<Account> friendsList = new ArrayList<>();
        String st = "";
        for (Integer id : friends.getItems()) {
            try {
                st = vk.users().get(actor).userIds(String.valueOf(id)).fields(UserField.PHOTO_50, UserField.BDATE, UserField.CAREER, UserField.CITY, UserField.EDUCATION).lang(Lang.RU).execute().get(0).toString();
            } catch (ApiException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            }

          //  try{
            String fN = st.substring(st.indexOf("lastName='") + 10, st.length());
            fN = fN.substring(0, fN.indexOf("',"));
            fN = fN.replaceAll("'", "");

            if (st.contains("Pan'kov")) {
                System.out.println();
            }
           // st.replaceAll("lastName='" + fN + "'", "lastName='null'");


            if (st.contains("career=[]")) {
                st = st.replaceAll("career=\\[], ", "");
            }
            st = st.replaceAll("UserXtrCounters", "");
            st = st.replaceAll("BaseObject", "");
            st = st.replaceAll("Career", "");
            st = st.replaceAll("\\[", "").replaceAll("\\]", "");
            st = st.replaceAll("\\(", "").replaceAll("\\)", "");
            System.out.println(st);

            Map<String, String> pP = new HashMap<>();
            if (st.contains("groupId")) {
                Pattern p = Pattern.compile("groupId");
                Matcher m = p.matcher(st);
                int counter = 0;
                while (m.find()) {
                    counter++;
                }
                if (counter > 1) {
                    String prace = st.substring(st.indexOf("career={") + 7, st.length());
                    String lastPrace = prace.substring(prace.lastIndexOf("groupId"), prace.length());
                    prace = prace.substring(prace.lastIndexOf("groupId=") + 8, prace.indexOf(", city="));
                    String[] pracePlace = prace.split("company=");

                    for (int i = 1; i < pracePlace.length; i++) {
                        pP.put("company " + i, pracePlace[i].substring(0, pracePlace[i].indexOf(",")));
                    }
                    //System.out.println(prace);
                    st = st.substring(0, st.indexOf("career={") + 8) + lastPrace;
                    //System.out.println(st.substring(275, st.length()));
                }
            }
            if (st.contains("company")) {
                comp = st.substring(st.indexOf("company="), st.length());
                comp = comp.substring(comp.indexOf("=") + 1, comp.indexOf(", countryId"));
                // System.out.println(comp);
                st = st.replaceAll("company=" + comp + ", ", "");
            }

            if(st.contains("барабаны"))
            {
                System.out.println();
            }
            if (st.contains("position"))
            {
                pos = st.substring(st.indexOf("position="), st.length());
                pos = pos.substring(pos.indexOf("=") + 1, pos.indexOf("},"));
                String posStr = ", position=" + pos;
               String posNew = null;
                if (posStr.contains("/") || posStr.contains("\\"))
                {
                    posNew = posStr.replaceAll(",", "").replaceAll("/", "").replaceAll("\\\\", " ").replaceAll("-", "").replaceAll(" ", "_");
                }

                System.out.println(st.contains(posStr));
                if (st.contains(posStr))
                {
                    String st2 = new String(st);
                    st = null;
                   // st = st.replaceAll(posStr, ", " + posNew);
                    st = st2.replaceAll(posStr, "");
                    System.out.println(st.substring(377, st.length()));

                }

            }

            if (st.contains("title"))
            {
                String cT = st.substring(st.indexOf("title='") + 7, st.length());
                cT = cT.substring(0, cT.indexOf("'}"));
                String cT1 = "title='" + cT.replaceAll("'", "") + "'";
                st = st.replaceAll("title='" + cT + "'",  cT1);
            }

            //System.out.println(st);
            System.out.println(st.substring(0, 351));

            friend = new Gson().fromJson(st, Account.class);

            //analyzeAccount.items.add(new Gson().fromJson(st, Account.class));
            if (comp != "" && friend.getCareer() != null)
            {
                friend.getCareer().put("company", comp);
                comp = "";
                for (Map.Entry<String, String > mp : pP.entrySet()) {
                    if (!friend.getCareer().containsValue(mp.getValue())) {
                        friend.getCareer().put(mp.getKey(), mp.getValue());
                    }
                }
            }
//            }
//            catch (StringIndexOutOfBoundsException e)
//            {
//                e.printStackTrace();
//                continue;
//
//            }
//            catch (PatternSyntaxException e)
//            {
//                e.printStackTrace();
//                continue;
//            }
//            catch (JsonSyntaxException e)
//            {
//            e.printStackTrace();
//            continue;
//            }

            st = "";
            friendsList.add(friend);
        }

        return friendsList;
    }
    public static Set <String> getRelationships(Account account, List<ADPerson> adPeople, String mainId, String fF)
    {
        if (threads.size() > 0)
        {
            for (Thread t : threads)
            {
                if (t.isAlive()) {
                    t.interrupt();
                }
            }
        }
        threads.clear();

        Set<String> resultOfRelationships = new HashSet<>();
        //StringBuilder sb = new StringBuilder();
        //int i = 0;
        synchronized (resultOfRelationships) {
            for (Account acc : account.items) {
                threads.add(new Thread(new Runnable() {
                    public void run() {

                        for (ADPerson person : adPeople) {
                            String str;

                             if ((acc.getFirst_name().toLowerCase().equals(person.getfName())) &&
                                            (acc.getLast_name().toLowerCase().equals(person.getlName()))) {

                                 str = "<tr><td>" + mainId + "---></td><td><img src=" + acc.getPhoto_50() + "></td><td></td><td>----</td><td>----</td><td>---></td><td>" + person.getDisplayName() + " " + person.getDepartment() + " " + person.getsID() + "</td>";

                             if (person.getBirthdate() != null && !person.getBirthdate().equals("") && !person.getBirthdate().equals("Не указано") && person.getBirthdate().length() > 1
                                     && acc.getBdate() != null && !acc.getBdate().equals("") && !acc.getBdate().equals("Не указано") && acc.getBdate().length() > 1
                                     && person.getBirthdate().contains(acc.getBdate()))
                             {
                                 str = str + "<td>День и месяц рождения совпадают</td>";
                             }
                             str = str + "</tr>";
                             resultOfRelationships.add(str);
                                 //resultOfRelationships.add("<tr><td>" + mainId + "---></td><td><img src=" + acc.getPhoto_50() + "></td><td></td><td>----</td><td>----</td><td>---></td><td>" + person.getDisplayName() + " " + person.getDepartment() + " " + person.getsID() + "</td></tr>");

                             }


                        }
                        if (fF != null) {
                            for (Account accFr : acc.items) {

                                for (ADPerson person : adPeople) {
                                    String str1;
                                    if ((accFr.getFirst_name().toLowerCase().equals(person.getfName())) &&
                                            (accFr.getLast_name().toLowerCase().equals(person.getlName()))) {


                                        str1 = "<tr><td>" + mainId + "---></td>" +
                                                "<td><img src=" + acc.getPhoto_50() + "></td><td>" + acc.getId() + "</td><td>" + acc.getFirst_name() + "</td><td>" + acc.getLast_name() + "-----></td>" +
                                                "<td>" + accFr.getId() + " " + accFr.getFirst_name() + " " + accFr.getLast_name() +
                                                "-----></td><td>" + person.getDisplayName() + " " + person.getDepartment() + " " + person.getsID() + "</td>";

                                        if (person.getBirthdate() != null && !person.getBirthdate().equals("") && !person.getBirthdate().equals("Не указано") && person.getBirthdate().length() > 1
                                                && accFr.getBdate() != null && !accFr.getBdate().equals("") && !accFr.getBdate().equals("Не указано") && accFr.getBdate().length() > 1
                                                && person.getBirthdate().contains(accFr.getBdate())) {
                                            str1 = str1 + "<td>День и месяц рождения совпадают</td>";
                                        }
                                        str1 = str1 + "</tr>";
                                        resultOfRelationships.add(str1);

                                    }

                                    // resultOfRelationships.add(str);
                                    //resultOfRelationships.add("<tr><td>" + mainId + "---></td><td><img src=" + acc.getPhoto_50() + "></td><td></td><td>----</td><td>----</td><td>---></td><td>" + person.getDisplayName() + " " + person.getDepartment() + " " + person.getsID() + "</td></tr>");

                                }
                            }
                        }
                        }

                }));

            }
        }

        for (Thread thread: threads)
        {
           // System.out.println(threads.size());
            thread.start();
        }
        for (Thread thread: threads)
        {
            try {
                thread.join();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        return resultOfRelationships;
      // return sb.toString();
    }

    public  Account getfriendsOfFriends (Account account) throws IOException {
        List<Account> accIn  = new ArrayList<>();
        accIn.addAll(account.items);
        account.items.clear();

        int i = 0;
        for (Account acc : accIn)
        {
           // System.out.println(i++);
            Account account1 = getVKAccount(acc.getId());
            account.items.add(account1);
        }

        return account;
    }

    public static  List<ADPerson> getShtat ()
    {
        BufferedReader file = null;

        List<ADPerson> shtatList = new ArrayList<>();

        try {
            file = new BufferedReader(new FileReader("D:\\vkparser\\CSV.csv"));

            String line;
            StringBuffer inputBuffer = new StringBuffer();

            while ((line = file.readLine()) != null) {
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            String inputStr = inputBuffer.toString();

            file.close();

            File newFile = new File("D:\\ADP\\CSV1.csv");
            newFile.createNewFile();
            FileOutputStream fileOut = new FileOutputStream(newFile, false);

            fileOut.write(inputStr.replaceAll(",", ".").getBytes());
            fileOut.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader("D:\\vkparser\\CSV1.csv"), '|' , '"' , 1);

            // CSVReader reader1 = new CSVReader(new FileReader("D:\\ADP\\CSV.csv"),)

            //Read CSV line by line and use the string array as you want
            List<String[]> allRowsFromCSV = reader.readAll();

            allRowsFromCSV.remove(0);

            //System.out.println(allRowsFromCSV.size());

            // System.out.println(Arrays.toString(allRowsFromCSV.get(3)));
            String st = Arrays.toString(allRowsFromCSV.get(4)).replaceAll("[\\[\\](){}]","");
            //      st.replaceAll("\\[" , "");
            //        System.out.println(st);
            //         String[] stS = st.split(",");
            //         System.out.println(stS[3]);
            //        stS[3] = stS[3].replaceAll("birthdate=", "%").trim();
            //System.out.println(stS[3]);
            // System.out.println(stS[3].lastIndexOf("%"));
            //System.out.println(stS[3].indexOf("\n"));
            //         String bd = stS[3].substring(stS[3].lastIndexOf("%") + 1, stS[3].indexOf("\n")).trim();
            //         System.out.println(bd);
            /*
            String[] splitPerson = st.split(",");
            for (int i = 0; i < splitPerson.length; i++) {
                System.out.println("splitPerson[" + i + "] = " + splitPerson[i]);

            } */

            for (String[] person : allRowsFromCSV)
            {
                String strPerson = Arrays.toString(person).replaceAll("[\\[\\](){}]","");
                String[] splitPerson = strPerson.split(",");

                String birthdate = "";

                if (splitPerson[3].contains("birthdate"))
                {
                    String stBd = splitPerson[3].replaceAll("birthdate=", "%").trim();
                    birthdate = stBd.substring(stBd.lastIndexOf("%") + 1, stBd.indexOf("\n")).trim();
                }
                else
                {
                    birthdate = null;
                }

                String[] fio = splitPerson[0].split(" ");
                String lName = fio[0].trim();
                String fName = fio.length < 2 ? null : fio[1];


                shtatList.add(new ADPerson(splitPerson[0].toString().trim(),
                        fName,
                        lName,
                        splitPerson[1].toString().trim(),
                        splitPerson[2].toString().trim(),
                        splitPerson[3].toString().trim(),
                        splitPerson[4].toString().trim(),
                        splitPerson[5].toString().trim(),
                        splitPerson[6].toString().trim(),
                        splitPerson[7].toString().trim(),
                        splitPerson[8].toString().trim(),
                        splitPerson[9].toString().trim(),
                        splitPerson[10].toString().trim(),
                        splitPerson[11].toString().trim(),
                        splitPerson[12].toString().trim(),
                        splitPerson[13].toString().trim(),
                        splitPerson[14].toString().trim(),
                        splitPerson[15].toString().trim(),
                        birthdate));

            }

            Collections.sort(shtatList, new Comparator<ADPerson>() {
                        @Override
                        public int compare(ADPerson o1, ADPerson o2) {
                            return o1.getsID().compareTo(o2.getsID());
                        }
                    }
            );



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return shtatList;
    }


    synchronized private static Account getVKAccount (String vkId) {
        System.out.println(vkId);
        Account vkAccount = null;

        try {

            int idVk = Integer.valueOf(vkId);
        }
        catch (NumberFormatException e)
        {
            try {
                HttpClient client1 = new DefaultHttpClient();
                HttpPost request1 = new HttpPost("https://api.vk.com/method/users.get?user_ids=" + vkId + "&fields=bdate&access_token=" + token + "&v=5.78");
                //  HttpGet request = new HttpGet("https://api.vk.com/method/users.get?user_ids=18033314&fields=nickname,domain,sex,bdate,city,country,timezone,photo_50,photo_100,photo_200_orig,has_mobile,contacts,education,online,relation,last_seen,status,can_write_private_message,can_see_all_posts,can_post,universities&access_token=55bbf49408fd294d35ab494350c47aecbb77c33c73b8fc4f49baa5ef094055548db0e1ecbefdc0caafb67&v=5.78");
                HttpResponse response1 = client1.execute(request1);
                BufferedReader rd1 = new BufferedReader(new InputStreamReader(response1.getEntity().getContent()));
                String responseString1 = rd1.readLine().trim();
                vkId = responseString1.substring(responseString1.lastIndexOf("{\"id\":") + 6, responseString1.indexOf(","));


            }
            catch (Exception e1)
            {
                e1.printStackTrace();
            }

        }
        System.out.println(vkId);
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost("https://api.vk.com/method/friends.get?lang=ru&user_id=" + vkId + "&fields=nickname,domain,sex,bdate,city,country,timezone,photo_50,photo_100,photo_200_orig,has_mobile,contacts,education,online,relation,last_seen,status,can_write_private_message,can_see_all_posts,can_post,universities&access_token=" + token + "&v=5.78");
            //  HttpGet request = new HttpGet("https://api.vk.com/method/users.get?user_ids=18033314&fields=nickname,domain,sex,bdate,city,country,timezone,photo_50,photo_100,photo_200_orig,has_mobile,contacts,education,online,relation,last_seen,status,can_write_private_message,can_see_all_posts,can_post,universities&access_token=55bbf49408fd294d35ab494350c47aecbb77c33c73b8fc4f49baa5ef094055548db0e1ecbefdc0caafb67&v=5.78");
            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            responseString = rd.readLine().trim();
            //   String line = "";
            //   while ((line = rd.readLine()) != null) {
            //       System.out.println(line);
            //  }
            //System.out.println("json- " + responseString);
            responseString = responseString.substring(responseString.indexOf(":") + 1, responseString.lastIndexOf("}"));
            // System.out.println(responseString);


            Gson g = new Gson();
            vkAccount = g.fromJson(responseString, Account.class);
            // System.out.println(vkAccount.items.size());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        for (Account friend : vkAccount.items)
        {
            String ctTit;
            if (friend.getCity().get("title") == null)
            {
                ctTit = "Не указано";
            }
            else
            {
                ctTit = friend.getCity().get("title");
            }
            friend.setCityTitle(ctTit);
        }



        return vkAccount;


    }

    public static Account getVK (String id){
        Account vK = getVKAccount(id);
       // Account vkF = getfriendsOfFriends(vK);
        return vK;

    }


    public static Account getVkAcc(String id){
        Account acc = getVK(id);
        //System.out.println(account);
        Account a1 = transliteToUTF(acc);
        acc = a1;
        return acc;
    }

    public static List<ADPerson> transliteShtatToUTF (List<ADPerson> adList)
    {
        List<ADPerson> workADList = new ArrayList<>();
        //workADList.addAll(adList);

        for (ADPerson person : adList)
        {
            workADList.add(new ADPerson(
                    person.getcN() != null ? toUTF(person.getcN()) : "Не указано",
                    person.getfName() != null ? toUTF(person.getfName()) : "Не указано",
                    person.getlName() != null ? toUTF(person.getlName()) : "Не указано",
                    person.getDisplayName() != null ? toUTF(person.getDisplayName()) : "Не указано",
                    person.getCanonicalName() != null ? toUTF(person.getCanonicalName()) : "Не указано",
                    person.getHomePostalAddress() != null ? toUTF(person.getHomePostalAddress()) : "Не указано",
                    person.getAutoReplyMessage() != null ? toUTF(person.getAutoReplyMessage()) : "Не указано",
                    person.getDepartment() != null ? toUTF(person.getDepartment()) : "Не указано",
                    person.getTitle() != null ? toUTF(person.getTitle()) : "Не указано",
                    person.getCity() != null ? toUTF(person.getCity()) : "Не указано",
                    person.getStreetAddress() != null ? toUTF(person.getStreetAddress()) : "Не указано",
                    person.getUserPrincipalName() != null ? toUTF(person.getUserPrincipalName()) : "Не указано",
                    person.getsID() != null ? toUTF(person.getsID()) : "Не указано",
                    person.getEnabled() != null ? toUTF(person.getEnabled()) : "Не указано",
                    person.getMobilePhone() != null ? toUTF(person.getMobilePhone()) : "Не указано",
                    person.getMobile() != null ? toUTF(person.getMobile()) : "Не указано",
                    person.getTelephoneNumber() != null ? toUTF(person.getTelephoneNumber()) : "Не указано",
                    person.getLastLogonDate() != null ? toUTF(person.getLastLogonDate()) : "Не указано",
                    person.getBirthdate() != null ? toUTF(person.getBirthdate()) : "Не указано")
            );
        }
        return workADList;
    }

    public static Account transliteToUTF(Account acc)
    {
        for (Account friend : acc.items) {
            String fName = friend.getFirst_name() != null ? toUTF(friend.getFirst_name()) : "Не указано";
            String lName = friend.getLast_name() != null ? toUTF(friend.getLast_name()) : "Не указано";
            String status = friend.getStatus() != null ? toUTF(friend.getStatus()) : "Не указано";

            friend.setFirst_name(fName);
            friend.setLast_name(lName);
            friend.setStatus(status);
            //friend.setCountry(toUTF(friend.getCountry()));
            if (friend.getCityTitle() == null)
            {
                friend.setCityTitle("Нет доступа");
            }
            if (!friend.getCityTitle().equals("Не указано")) {
                friend.setCityTitle(toUTF(friend.getCityTitle()));
            }


        }
        return acc;
    }

    private static String toUTF(String inStr)
    {
        String utf8String = null;
        String str = null;
        try
        {
            utf8String = new String(inStr.getBytes(), "windows-1251");
            str = new String(utf8String.getBytes("windows-1251"), "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return str;
    }
}
