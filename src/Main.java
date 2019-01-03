/* Scrapping program created by Malik M. 2018/2019 for a Puzzle and Dragons team building application.
   http://www.puzzledragonx.com was used to scrape all information. Any inaccurate content
   may be due to japanese version of the game.
 */


import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.List;
import javax.imageio.*;
                                        //TODO SAVE IMAGES  monster, Awoken Skill, Elements
                                        //TODO DELIMIT MONSTER BY |. STRUCTURE.TXT
                                        //TODO MAPPING FOR AWOKEN SKILL AND NAME

                                        //TODO FILL BOOK?


class Main {
    public static void main(String[] args) {
        //System.out.println(searchTitle());
        List<String> monsterBook = new ArrayList<>();
        List<String> awoSkillBook = new ArrayList<>();
        String completeMonsterBook;
        List<String> awoSkillList = new ArrayList<>(); //skill IDs
        List<String> awoNameEffectList = new ArrayList<>(); //skill Effect
        List<String> awoNameList = new ArrayList<>(); //skill name






        //TODO~~~~~~~~~~~~~~~~~~READ HTML~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        //System.out.println(readHTML("http://www.puzzledragonx.com/en/monster.asp?n=4413"));

        //TODO~~~~~~~~~~~~~~~~~~TESTING GROUNDS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        isSkill(readHTML("http://www.puzzledragonx.com/en/awokenskill-list.asp"), awoSkillList);

        for(int i=0; i <= awoSkillList.size()-1; i++) {

            awoSkillName(readHTML("http://www.puzzledragonx.com/en/awokenskill-list.asp?s=" + awoSkillList.get(i)), awoNameList, awoSkillList);

        }
        System.out.println(listToString(awoNameList));
        writeUsingFileWriter(listToString(awoNameList), "awokenNameList.txt");
        /*
        //TODO~~~~~~~~~~~~~~~~~~AWOKEN SKILL LIST~~~~~~~~~~~~~~~~~~~~~~~~~~~
        isSkill(readHTML("http://www.puzzledragonx.com/en/awokenskill-list.asp"), awoSkillList);


       for(int i = 0; i < awoSkillList.size(); i++){

                awoSkillName(readHTML("http://www.puzzledragonx.com/en/awokenskill-list.asp?s=" + i), awoNameList, awoSkillList);
       }
        listConcatination(awoSkillBook,awoSkillList,awoNameEffectList);
        System.out.println(listToString(awoSkillBook));

*/

        //TODO~~~~~~~~~~~~~~~~~~~SAVE MONSTER IMAGES~~~~~~~~~~~~~~~~~~~~~~~~
/*

try {
    saveImage("http://www.puzzledragonx.com/en/img/book/1.png");
}
catch(IOException e){
    System.out.println(e);
}

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

     //     TODO~~~~~AWOKEN skill name

    //gets information of different skills
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

    //gets current awoken skill list
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
    //gets awoken skill names
    public static void awoSkillName(String html, List<String> skillName, List<String> skillList){
        String keyStart = "<td style=\"padding-top: 8px;\">";
        String keyEnd   = "<hr class=\"awokenline\">";

        if(html.indexOf(keyStart) > 0) {
            skillName.add(html.substring(html.indexOf(keyStart) + keyStart.length(), html.indexOf(keyEnd)) + "\n");
        }
        else{
            System.out.println("ERROR SKILL NOT FOUND");
        }
    }
    //gets awoken skills effect
    public static void skillNameEffect(String html, List<String> awoSkillEffect){
        String descStart = "</td><td style=\"padding-top: 8px;\">";
        String descEnd = "<hr class=\"awokenline\"></td><td style=\"width: 100%;\">";

        String htmlText = html;
        List<String> name = awoSkillEffect;

        name.add(htmlText.substring(htmlText.indexOf(descStart) + descStart.length(), htmlText.indexOf(descEnd)) + " ");

    }


    //gets monster names
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

    //gets monster awoken skills
    public static String searchAwoken(String html, List<String> skillList){



        for(int i = 0; i <= skillList.size()-1; i++) {
            String key = "awokenskill.asp?s=" + skillList.get(i) + "\">";
            key = key.substring(0, key.indexOf(" ")) + key.substring(key.indexOf(" ")+1);

            String tempHtml = html;
            int count = 0; //counts multiple occurrence of same awoken skill
            while (tempHtml.contains(key)) {

                tempHtml = tempHtml.substring(tempHtml.indexOf(key) + key.length());
                count++;
                System.out.println(count + ":" + skillList.get(i) + ":" + i);
            }


        }

        return "";
    }


    //checks to see if monster can assist
    public static boolean isAssist(String html) {
        boolean assist = true;
        String htmlText = html;
        String start = "This card <span class=\"green\">";

        if (htmlText.indexOf(start) == -1) {
            assist = false;
        }

        return assist;
    }

    //gets cost of monster
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

    //gets monster element
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

    //gets max atk,rcv,hp for monster
    public static int[] findMaxStat(String html){
        int[] maxStat = new int[4];
        String hpTag = "stathp\">HP</td><td>";
        String atkTag = "statatk\">ATK</td><td>";
        String rcvTag = "statrcv\">RCV</td><td>";
        String temp = "";

        temp = html.substring(html.indexOf(hpTag) + hpTag.length(),html.indexOf(hpTag) + hpTag.length()+25);
        temp = temp.substring(temp.indexOf("<td>") + 4, temp.lastIndexOf("</td>"));
        maxStat[0] = Integer.parseInt(temp);

        temp = html.substring(html.indexOf(atkTag) + atkTag.length(),html.indexOf(atkTag) + atkTag.length()+25);
        temp = temp.substring(temp.indexOf("<td>") + 4, temp.lastIndexOf("</td>"));
        maxStat[1] = Integer.parseInt(temp);

        temp = html.substring(html.indexOf(rcvTag) + rcvTag.length(),html.indexOf(rcvTag) + rcvTag.length()+25);
        temp = temp.substring(temp.indexOf("<td>") + 4, temp.lastIndexOf("</td>"));
        maxStat[2] = Integer.parseInt(temp);

        maxStat[3] = maxStat[0]/10 + maxStat[1]/5 + maxStat[2] / 3; //weighted stat number


        return maxStat;
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

    public static void writeUsingFileWriter(String data, String fileName) {
        File file = new File("C:\\Users\\Arctic\\Desktop\\Coding Projects\\readHTMLtoTxt\\" + fileName); //creates a new file
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

        for(int i = 0; i < second.size(); i++){
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
