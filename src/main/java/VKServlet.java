import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet(value = "/VKServlet", asyncSupported=true)
public class VKServlet extends HttpServlet
{
    private static String APP_ID = "6502596";
    private static List<ADPerson> adPersonList = new ArrayList<>();
    private static Account account;
    private static String responseString;
    private static String token = "6edf50a46edf50a46edf50a4a06ebc686066edf6edf50a435c6777015dd6215b8066497";
    private static String iD = "";
    private static Set<String> results = new HashSet<>();
    private String fF;


    String data = "";

    @Override
    public void init() throws ServletException {

            account = null;
            results = null;
            adPersonList.clear();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            final AsyncContext asyncContext = req.startAsync(req, resp);


            asyncContext.setTimeout(6000000);
            asyncContext.addListener(new AsyncListener() {

                @Override
                public void onComplete(AsyncEvent event) throws IOException {
                    AsyncContext asyncContext = event.getAsyncContext();
                    asyncContext.getRequest().setCharacterEncoding("UTF-8");
                    asyncContext.getResponse().setContentType("text/html");
                    asyncContext.getResponse().setCharacterEncoding("UTF-8");
                    asyncContext.getRequest().setAttribute("data", data);
                    try {
                        asyncContext.getRequest().getRequestDispatcher("Result.jsp").forward(req, resp);
                    } catch (ServletException e) {
                        e.printStackTrace();
                    }
                    // asyncContext.getResponse().getWriter().println(data);
                }

                @Override
                public void onTimeout(AsyncEvent event) throws IOException {

                    data = "Timeout";

                }

                @Override
                public void onError(AsyncEvent event) throws IOException {

                    data = "Error";

                }

                @Override
                public void onStartAsync(AsyncEvent event) {

                    data = req.getParameter("VkId");


                }
            });

            new Thread() {

                @Override
                public void run() {

                    iD = req.getParameter("vkId");

                    fF = req.getParameter("fF");

                    try {
                        account = Main.getVkAcc(iD);
                        // System.out.println(account.items.size());
                        // System.out.println(account.items.get(3).getFirst_name());
                    } catch (IllegalArgumentException e)
                    {
                        e.printStackTrace();
                        try {
                            asyncContext.getRequest().getRequestDispatcher("Error.jsp").forward(req, resp);
                        } catch (ServletException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }



                    if (fF != null) {
                        List<Account> accountList = new ArrayList<>();
                        accountList.addAll(account.items);

                        account.items.clear();
                        for (Account acc : accountList) {
                            acc.items.addAll(Main.getVkAcc(acc.getId()).items);
                        }
                        account.items.addAll(accountList);
                        accountList.clear();

                        for (Account acc : account.items) {
                            for (Account acc1 : acc.items) {
                                accountList.addAll(acc1.items);
                                acc1.items.clear();
                                for (Account acc2 : accountList) {
                                    acc1.items.add(Main.transliteToUTF(acc2));
                                }

                            }
                        }

                    }

                    adPersonList = Main.transliteShtatToUTF(Main.getShtat());

                    results = Main.getRelationships(account, adPersonList, iD, fF);

                    data = "<br><p align=\"center\">Количество друзей: " + String.valueOf(account.items.size() + "</p>");

                    StringBuilder sb = new StringBuilder(data);
                    sb.append("<br><table border=\"1\" align=\"center\">");
                    sb.append("<tr>");
                    sb.append("<td>Исомый Id</td><td>Фото</td><td>id Друга</td><td>Имя</td><td>Фамилия</td><td>Информация о друге друга</td><td>Информация из ШР</td>");
                    sb.append("</tr>");
                    for (String resStr : results)
                    {
                        sb.append(resStr);

                    }
                    sb.append("</table><br>");
                    data = sb.toString();

                    asyncContext.complete();

                }

            }.start();


            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("Results:");
            // Чтение данных из базы данных

            data = "Queried data...";

            // Переводим поток в режим ожидания на некоторое время...
        }



}
