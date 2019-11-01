package Resources;/* Scrapping program created by Malik M. 2018/2019 for a Puzzle and Dragons team building application.
   http://www.puzzledragonx.com was used to scrape all information. Any inaccurate content
   may be due to japanese version of the game having a different version than NA.
 */

import netscape.javascript.JSException;
import netscape.javascript.JSObject;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.List;
import javax.imageio.*;

                                        //TODO MAPPING FOR AWOKEN SKILL AND NAME
                                        //TODO FILL BOOK?

class Main {
    public static void main(String[] args) throws Exception{
        //System.out.println(searchTitle());
        List<String> awoSkillBook = new ArrayList<>();
        List<String> awoSkillList = new ArrayList<>(); //skill IDs
        List<String> awoNameEffectList = new ArrayList<>(); //skill Effect
        List<String> awoNameList = new ArrayList<>(); //skill name
        //String monsterData = readFromFile("monsterData");

        JSObject name = new JSObject() {
            @Override
            public Object call(String methodName, Object... args) throws JSException {
                return null;
            }

            @Override
            public Object eval(String s) throws JSException {
                return null;
            }

            @Override
            public Object getMember(String name) throws JSException {
                return null;
            }

            @Override
            public void setMember(String name, Object value) throws JSException {

            }

            @Override
            public void removeMember(String name) throws JSException {

            }

            @Override
            public Object getSlot(int index) throws JSException {
                return null;
            }

            @Override
            public void setSlot(int index, Object value) throws JSException {

            }
        };

        name.setMember("Lightning", 0);
        System.out.println(name.getSlot(0));
        //System.out.println(monsterData);
/*
       //TODO~~~~~~~~~~~~~~~~~~READ HTML~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        isAwoSkill(readHTML("http://www.puzzledragonx.com/en/awokenskill-list.asp"), awoSkillList);
        for(int i = 0; i <= 1338; i++) {
            String htmlText = readHTML("http://www.puzzledragonx.com/en/monster.asp?n=" + i);


            monsterData += "#" + i + " monName: " + searchName(htmlText) + " element: " + findElement(htmlText) + " " +
                isAssist(htmlText) + " cost:" + findCost(htmlText) + " stat:" + findMaxStat(htmlText) + " awoSkill: " +
                searchAwoken(htmlText, awoSkillList) + " sSkill: " + searchSkill(htmlText) + "\n";

            System.out.printf("\n %s", monsterData);
        }
        writeUsingFileWriter(monsterData);
        //System.out.println(readHTML("http://www.puzzledragonx.com/en/awokenskill-list.asp"));
 /*

        //TODO~~~~~~~~~~~~~~~~~~AWOKEN SKILL LIST~~~~~~~~~~~~~~~~~~~~~~~~~~~
        isSkill(readHTML("http://www.puzzledragonx.com/en/awokenskill-list.asp"), awoSkillList);


        for(int i = 0; i < awoSkillList.size(); i++){
            awoSkillName(readHTML("http://www.puzzledragonx.com/en/awokenskill-list.asp?s=" + i), awoNameList, awoSkillList);
       }


*/

 /*       //TODO~~~~~SAVE ALL IMAGES~~~~~
        for (int i = 4001; i <= 5044; i++) {
            try {
                saveImage("http://www.puzzledragonx.com/en/img/book/" + i + ".png", i + ".png");
            } catch (IOException e) {
                System.out.println(e);
            }
   */
    }
/*
        //TODO~~~~~ENABLE ONLY WHEN completeMonsterBook is finished~~~~~
        completeMonsterBook = listToString(monsterBook);
        writeUsingFileWriter(completeMonsterBook);

 */


    //     TODO~~~~~AWOKEN skill name

    //gets information of different skills
    public static String searchSkill(String html) {
        String htmlText = html;
        String activeSkill = "";
        String start = "";
        String end = "";
        boolean isActive = htmlText.contains("<span class=\"blue\">None</span>");
        boolean isLeader = htmlText.contains("<span class=\"green\">None</span>");

        if (!isActive) {
            //skill name
            start = "<span class=\"blue\">";
            end = "</span></a></td></tr><tr><td class=\"title\">Effects";
            int keyStart = htmlText.indexOf(start);
            int keyEnd = htmlText.indexOf(end);
            activeSkill = " sName: " + htmlText.substring(keyStart + start.length(), keyEnd);

            //skill description
            start = "Effects:</td><td class=\"value-end\">";

            if(htmlText.contains("<td class=\"orbdisplay\">")) {
                end = "</td></tr><tr><td>&nbsp;</td><td class=\"orbdisplay\">";//"</td></tr><tr><td class=\"title\">Cool Down";
            }else{end = "</td></tr><tr><td class=\"title\">Cool Down";}

            keyStart = htmlText.indexOf(start);
            keyEnd = htmlText.indexOf(end);
            activeSkill += " sDesc: " + htmlText.substring(keyStart + start.length(), keyEnd);

            //skill cooldown
            start = "Cool Down:</td><td class=\"value-end\">";
            end = "</td></tr><tr><td class=\"title\"></td><td class=\"value-end\">This card <span class=";
            keyStart = htmlText.indexOf(start);
            keyEnd = htmlText.indexOf(end);
            activeSkill += " CoolD: " + htmlText.substring(keyStart + start.length(), keyEnd);

        }else{ activeSkill +=" sName: None sDesc: None CoolD: N/a";}

        if(!isLeader){
            //leader skill name
            start = "\"><span class=\"green\">";
            end = "</span></a></td></tr><tr><td class=\"title\">Effects:</td><td class=\"value-end\">";
            int keyStart = htmlText.indexOf(start);
            int keyEnd = htmlText.lastIndexOf(end);
            activeSkill += " sLeaderName: " + htmlText.substring(keyStart + start.length(), keyEnd);

            //leader desc
            start = "Effects:</td><td class=\"value-end\">";
            keyStart = htmlText.lastIndexOf(start);
            keyEnd = keyStart + start.length() + 800;
            String temp = html.substring(keyStart + start.length(), keyEnd);
            activeSkill += " sLeaderEffect: " + temp.substring(0, temp.indexOf("<"));


        }else{ activeSkill += " sLeaderName: None sLeaderEffect: None";}



        return activeSkill;
    }

    //gets current awoken skill list
    public static void isAwoSkill(String html, List<String> skillList){

        String htmlText = html;

        for(int i = 1; i <= 70; i++){
            String start = "awokenskill-list.asp?s=" + i + "\"";

            if (htmlText.contains(start)) {
                skillList.add(Integer.toString(i) + " ");
            }else{}

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
        String awoken = "";


        for(int i = 0; i <= skillList.size()-1; i++) {
            String key = "awokenskill.asp?s=" + skillList.get(i) + "\">";
            key = key.substring(0, key.indexOf(" ")) + key.substring(key.indexOf(" ")+1);

            String tempHtml = html;
            int count = 0; //counts multiple occurrence of same awoken skill
            while (tempHtml.contains(key)) {

                tempHtml = tempHtml.substring(tempHtml.indexOf(key) + key.length());
                count++;
                awoken += "a$"  + skillList.get(i) ;
            }


        }

        return awoken;
    }

    //checks to see if monster can assist
    public static String isAssist(String html) {
        String assist = "true*";
        String htmlText = html;
        String start = "This card <span class=\"green\">";

        if (htmlText.indexOf(start) == -1) {
            assist = "false*";
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
    public static String findMaxStat(String html){
        String maxStat = "";
        String hpTag = "stathp\">HP</td><td>";
        String atkTag = "statatk\">ATK</td><td>";
        String rcvTag = "statrcv\">RCV</td><td>";
        String temp = "";
        int hp;
        int atk;
        int rcv;


        temp = html.substring(html.indexOf(hpTag) + hpTag.length(),html.indexOf(hpTag) + hpTag.length()+25);
        temp = temp.substring(temp.indexOf("<td>") + 4, temp.lastIndexOf("</td>"));
        maxStat += " Hp: " + Integer.parseInt(temp);
        hp = Integer.parseInt(temp);

        temp = html.substring(html.indexOf(atkTag) + atkTag.length(),html.indexOf(atkTag) + atkTag.length()+25);
        temp = temp.substring(temp.indexOf("<td>") + 4, temp.lastIndexOf("</td>"));
        maxStat += " Atk: " + Integer.parseInt(temp);
        atk = Integer.parseInt(temp);

        temp = html.substring(html.indexOf(rcvTag) + rcvTag.length(),html.indexOf(rcvTag) + rcvTag.length()+25);
        temp = temp.substring(temp.indexOf("<td>") + 4, temp.lastIndexOf("</td>"));
        maxStat += " Rcv: " + Integer.parseInt(temp);
        rcv = Integer.parseInt(temp);

        maxStat += " Weighted: " + (hp/10 + atk/5 + rcv/3); //weighted stat number


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

    public static void writeUsingFileWriter(String data) {
        File file = new File("C:\\Users\\Arctic\\Desktop\\Coding Projects\\readHTMLtoTxt\\monsterData"); //creates a new file
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

    public static void saveImage(String imageUrl, String i) throws IOException {

        BufferedImage image = null;
        String pictureName = i;
        URL url = new URL(imageUrl);
        image = ImageIO.read(url);

        File file = new File("C:\\Users\\Arctic\\Desktop\\Coding Projects\\readHTMLtoTxt\\MonsterBookImages\\" + pictureName );
        System.out.println(pictureName);

        ImageIO.write(image, "png", file);

    }

    public static String readFromFile(String fileName) {
        String updatedText = "";

        try{
            Scanner input = new Scanner(new File(fileName));
            while (input.hasNext() ){
                updatedText += input.nextLine()+"\n";
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.exit(1);
        }


        return updatedText;
    }


}
