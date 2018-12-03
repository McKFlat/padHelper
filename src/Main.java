import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;


class Main {
    public static void main(String[] args) {
        //System.out.println(searchTitle());
        List<String> monsterBook = new ArrayList<>();
        String completeMonsterBook;
        List<String> awoSkillList = new ArrayList<>();
        isSkill(readHTML("http://www.puzzledragonx.com/en/awokenskill-list.asp"),  awoSkillList);

        System.out.println(listToString(awoSkillList));


//       System.out.println(readHTML("http://www.puzzledragonx.com/en/awokenskill-list.asp?s=5"));
/*        for (int monsterID = 1; monsterID <= 3; monsterID++) {
            System.out.println(isSkill(readHTML("http://www.puzzledragonx.com/en/monster.asp?n=" + monsterID));
        }

        System.out.println(searchName(readHTML("http://www.puzzledragonx.com/en/monster.asp?n=" + monsterID)) +
                " " + monsterID);
        monsterBook.add(searchName(readHTML("http://www.puzzledragonx.com/en/monster.asp?n=" + monsterID)));
        System.out.println(monsterBook.get(monsterID - 1));
    }

*/


        //TODO~~~~~ENABLE ONLY WHEN completeMonsterBook is finished~~~~~
        //completeMonsterBook = listToString(monsterBook);
        // writeUsingFileWriter(completeMonsterBook);
    }
        /*TODO~~~~~ELEMENT, EVOLUTION TREE, MIN/MAX STATS/WITH +
          TODO~~~~~AWOKEN skill, SUPER AWOKEN, MAYBE DROP LOCATIONS

          */


    public static void isSkill(String html, List<String> skillList){
        boolean skillExist = true;
        String htmlText = html;
        List<String> workingID = new ArrayList<>();
        int amount = 0;
        String descStart = "</td><td style=\"padding-top: 8px;\">";
        String descEnd = "<hr class=\"awokenline\"></td><td style=\"width: 100%;\">";

        for(int i = 1; i <= 100; i++){
            String start = "awokenskill-list.asp?s=" + i + "\"";
            if (htmlText.indexOf(start) == -1) {
                skillExist = false;
            }
            else{
                workingID.add(start + i);
                amount++;
                skillList.add(Integer.toString(i) + "\n");
            }
        }

    }

    public static String searchName(String html) {
        String htmlText = html;
        String name;

        String start = "<div class=\"name\"><h1>";
        String end = "</h1></div>";//<div class=\"stars\">";


        int keyStart = htmlText.indexOf(start);
        int keyEnd = htmlText.indexOf(end);
        name = htmlText.substring(keyStart + start.length(), keyEnd);

        return name;
    }

    public static String searchSkill(String html) {
        String htmlText = html;
        String activeSkill = "";
        String start = "";
        String end = "";

        for (int i = 1; i <= 5; i++) {
            // skill name
            if (i == 1) {
                start = "<span class=\"blue\">";
                end = "</span></a></td></tr><tr><td class=\"title\">Effects";
                int keyStart = htmlText.indexOf(start);
                int keyEnd = htmlText.indexOf(end);
                activeSkill = htmlText.substring(keyStart + start.length(), keyEnd);
            }
            // skill description
            else if (i == 2) {
                start = "Effects:</td><td class=\"value-end\">";
                end = "</td></tr><tr><td class=\"title\">Cool Down";
                int keyStart = htmlText.indexOf(start);
                int keyEnd = htmlText.indexOf(end);
                activeSkill += ", " + htmlText.substring(keyStart + start.length(), keyEnd);
            }
            // cool down
            else if (i == 3) {
                start = "Cool Down:</td><td class=\"value-end\">";
                end = "</td></tr><tr><td class=\"title\"></td><td class=\"value-end\">This card <span class=\"red\">";
                int keyStart = htmlText.indexOf(start);
                int keyEnd = htmlText.indexOf(end);
                activeSkill += ", " + htmlText.substring(keyStart + start.length(), keyEnd);
            }
            // leader Skill name
            else if (i == 4){
                start ="<span class=\"green\">";
                end = "</span></a></td></tr><tr><td class=\"title\">Effects:</td><td class=\"value-end\">";
                int keyStart = htmlText.indexOf(start);
                int keyEnd = htmlText.lastIndexOf(end);
                activeSkill += ", " + htmlText.substring(keyStart + start.length(), keyEnd);
            }
            // leader skill effect
            else if (i == 5){
                start ="Effects:</td><td class=\"value-end\">";
                int keyStart = htmlText.lastIndexOf(start);
                int keyEnd = keyStart + start.length()+200;
                String temp = html.substring(keyStart + start.length(), keyEnd);

                activeSkill += ", " + temp.substring(0, temp.indexOf("<"));

            }

        }


        return activeSkill;
    }

    public static boolean isAssist(String html) {
        boolean assist = true;
        String htmlText = html;
        String start = "This card <span class=\"green\">";

        if (htmlText.indexOf(start) == -1) {
            assist = false;
        }

        return assist;
    }

    public static int findCost(String html){
        int cost = 0;

        String htmlText = html;


        String start = "monster which costs ";
        String end = " units";


        int keyStart = htmlText.indexOf(start);
        int keyEnd = htmlText.indexOf(end);
        cost = Integer.parseInt(htmlText.substring(keyStart + start.length(), keyEnd));



        return cost;
    }



    //TODO~~~~~SEPARATE MONSTERS BY NEW LINE, STATS BY | in listToString

    public static String readHTML(String websiteLink) {
        String content = null;
        URLConnection connection = null;

        try {
            connection = new URL(websiteLink).openConnection();
            Scanner webLink = new Scanner(connection.getInputStream());
            webLink.useDelimiter("\\Z");
            content = webLink.next();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return content;
    }

    public static void writeUsingFileWriter(String data) {
        File file = new File("/Users/Arctic/MonsterBook.txt"); //creates a new file called MonsterBook.txt
        FileWriter fr = null;

        try {
            fr = new FileWriter(file);
            fr.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String listToString(List<String> monsters) {
        String stat = "";

        for (int i = 0; i < monsters.size(); i++) {
            stat += monsters.get(i);
            if (i != monsters.size() - 1) {
                stat += "";
            }
        }

        return stat;
    }


}