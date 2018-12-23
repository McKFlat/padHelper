
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.List;
import javax.imageio.*;


class Main {
    public static void main(String[] args) {
        //System.out.println(searchTitle());
        List<String> monsterBook = new ArrayList<>();
        List<String> skillBook = new ArrayList<>();
        String completeMonsterBook;
        List<String> awoSkillList = new ArrayList<>();
        List<String> awoNameEffectList = new ArrayList<>();






/*
        System.out.println(readHTML("http://www.puzzledragonx.com/en/monster.asp?n=4"));


try {
    saveImage("http://www.puzzledragonx.com/en/img/book/1.png");
}
catch(IOException e){
    System.out.println(e);
}


        isSkill(readHTML("http://www.puzzledragonx.com/en/awokenskill-list.asp"), awoSkillList);

       // skillNameEffect(readHTML("http://www.puzzledragonx.com/en/awokenskill-list.asp"), awoSkillList, skillBook);


//       System.out.println(readHTML("http://www.puzzledragonx.com/en/awokenskill-list.asp?s=5"));
    for (int monsterID = 3; monsterID <= awoSkillList.size()-1; monsterID++) {
          skillNameEffect(readHTML("http://www.puzzledragonx.com/en/awokenskill-list.asp?s=" + awoSkillList.get(monsterID)), awoNameEffectList);
         }

        System.out.println( awoSkillList.size() + " | | " + awoNameEffectList.size());
        listConcatination(skillBook, awoSkillList, awoNameEffectList);
        System.out.println(listToString(skillBook));





        //TODO~~~~~SAVE ALL IMAGES~~~~~
        try {
            saveImage("http://www.puzzledragonx.com/en/img/book/1.png");
        }
        catch(IOException e){
            System.out.println(e);
        }

        //TODO~~~~~ENABLE ONLY WHEN completeMonsterBook is finished~~~~~
        //completeMonsterBook = listToString(monsterBook);
        // writeUsingFileWriter(completeMonsterBook); */
    }
     //     TODO~~~~~EVOLUTION TREE, MAX STATS
     //     TODO~~~~~AWOKEN skill, SUPER AWOKEN, MAYBE DROP LOCATIONS, Type




    public static void isSkill(String html, List<String> skillList){
        boolean skillExist = true;
        String htmlText = html;
        List<String> workingID = new ArrayList<>();

        for(int i = 1; i <= 80; i++){
            String start = "awokenskill-list.asp?s=" + i + "\"";
            if (htmlText.indexOf(start) == -1) {
                skillExist = false;
            }
            else{
                workingID.add(start + i);
                skillList.add(Integer.toString(i) + " ");
            }
        }

        // **** Adding skill name next to skill ID number
        //TODO add name next to skill id

        //System.out.println(listToString(skillList));

    }

    public static void skillNameEffect(String html, List<String> skillList){
        String descStart = "</td><td style=\"padding-top: 8px;\">";
        String descEnd = "<hr class=\"awokenline\"></td><td style=\"width: 100%;\">";

        String htmlText = html;
        List<String> name = skillList;

        name.add(htmlText.substring(htmlText.indexOf(descStart) + descStart.length(), htmlText.indexOf(descEnd)) + " ");

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

    public static String findElement(String html){
        String monElement = "";
        String water = "nofollow\">Water";
        String fire = "nofollow\">Fire";
        String wood ="nofollow\">Wood";
        String light = "nofollow\">Light";
        String dark = "nofollow\">Dark";

        if(monElement.length() < 1 && html.indexOf(water) > 0 ) {
                monElement += "Water";
        }else if (monElement.length() > 1 && html.indexOf(water) > 0) {
                monElement += "subWater"; //Sub Element
            }


        if(monElement.length() < 1 && html.indexOf(fire) > 0) {
                monElement += "Fire";
        }else if (monElement.length() > 1 && html.indexOf(fire) > 0) {
            monElement += "subFire"; //Sub Element
        }

        if(monElement.length() < 1 && html.indexOf(wood) > 0) {
                monElement += "Wood";
        }else if (monElement.length() > 1 && html.indexOf(wood) > 0) {
            monElement += "subWood"; //Sub Element
        }

        if(monElement.length() < 1 && html.indexOf(light) > 0){
                monElement += "Light";
        }else if (monElement.length() > 1 && html.indexOf(light) > 0) {
            monElement += "subLight"; //Sub Element
        }

        if(monElement.length() < 1 && html.indexOf(dark) > 0) {
                monElement += "Dark";
        }else if (monElement.length() > 1 && html.indexOf(dark) > 0) {
            monElement += "subDark"; //Sub Element
        }

        return monElement;
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
        File file = new File("C:\\Users\\Arctic\\Desktop\\Coding Projects\\readHTMLtoTxt\\MonsterBook.txt"); //creates a new file called MonsterBook.txt
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

    public static void listConcatination(List<String> finished, List<String> first, List<String> second){

        for(int i = 0; i < second.size()-1; i++){
            finished.add(first.get(i) + second.get(i) + "\n");
        }
    }

    public static void saveImage(String imageUrl) throws IOException {

        BufferedImage image = null;
        String MonsterID = imageUrl.substring(imageUrl.indexOf("book/" )+5);
        URL url = new URL(imageUrl);
        image = ImageIO.read(url);

        File file = new File("C:\\Users\\Arctic\\Desktop\\Coding Projects\\readHTMLtoTxt\\MonsterBookImages\\" + MonsterID );
        System.out.println(MonsterID);

        ImageIO.write(image, "png", file);

    }

}
