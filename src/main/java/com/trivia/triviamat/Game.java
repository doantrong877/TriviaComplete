package com.trivia.triviamat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class Game extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("game.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 650);
        stage.getIcons().add(new Image(getClass().getResource("/icon.png").toExternalForm()));
        stage.setTitle("Trivia Maze");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    /*   Connection con = Database.getConnection();
        Database.createNewTable();
        Database.createRecord("In 1768, Captain James Cook set out to explore which ocean?","txt","image/text.png","Atlantic Ocean","Indian Ocean","Pacific Ocean","Arctic Ocean",3);
        Database.createRecord("What is actually electricity?","txt","image/text.png","A flow of water","A flow of air","A flow of electrons","A flow of atoms",3);
        Database.createRecord("Which of the following is not an international organisation?","txt","image/text.png","FIFA","NATO","FBI","ASEAN",3);
        Database.createRecord("Which of the following is a song by the German heavy metal band “Scorpions”?","txt","image/text.png","Stairway to Heaven","Don’t Stop Me Now","Wind of Change","Hey Jude",3);
        Database.createRecord("What is the speed of sound?","txt","image/text.png","120 km/h","400 km/h","1,200 km/h","700 km/h",3);
        Database.createRecord("Which is the easiest way to tell the age of many trees?","txt","image/text.png","To measure the width of the tree","To count the number of leaves","To count the rings on the trunk","To measure the height of the tree",3);
        Database.createRecord("Which did Viking people use as money?","txt","image/text.png","Rune stones","Seal skins","Jewellery","Wool",3);
        Database.createRecord("What is the year that this picture was taken?","img","image/q1.png","1942","1940","1945","1962",3);
        Database.createRecord("What is this person’s name?","img","image/q2.png","Kim Jong Un","Nguyen Van Thieu","Ho Chi Minh","Song Hye Kyo",3);
        Database.createRecord("What is the meaning of the following code?","img","image/q3.png","OUR SECRET PLACE","MEET AT JOHN’S HOUSE","JAILBREAK AT 11 AM","POISON IN FOOD",3);
        Database.createRecord("Which of the following athletes make the most money in 2020?","img","image/text.png","Lionel Messi","Cristiano Ronaldo","Conor McGregor","Lebron James",3);
        Database.createRecord("What month of the year has 28 days?","img","image/text.png","February","December","February and December","None of The Above",3);
        Database.createRecord("I am a mother’s child and a father’s child but nobody’s son. What am I??","img","image/text.png","Grandson","Father","Daughter","Uncle",3);
        Database.createRecord("What is the name of this tower?","img","image/q4.png","The Burger Tower","The Donut Tower","The Pizza Tower","The Spaghetti Tower",3);
        Database.createRecord("What is the national language of Canada?","img","image/text.png","English","French","Dutch","Canadian",3);
        Database.createRecord("How Fast can an ostrich run?","img","image/text.png","60 mile/hour","20 mile/hour","40 mile/hour","100 mile/hour",3);
        Database.createRecord("How much of your vision do you lose if you go blind in one eye?","img","image/text.png","50%","40%","20%","70%",3);
        Database.createRecord("Name this country","img","image/q5.jpg","Vietnam","Russia","China","Canada",3);
        Database.createRecord("What is the name of this movie?","img","image/q6.png","The Dealer","The Big Short","The Wolf of Wallstreet","The High Castle",3);
        Database.createRecord("Name this country","img","image/p7.png","Lao","Korea","Mexico","South Africa",3);
        Database.createRecord("Which movie released in 1982 has the theme song “Eye of the Tiger” by the band Survivor?","img","image/text.png","Rocky II","Death Wish","Rocky III","Karate Kid",3);
        Database.createRecord("What number is five more than one-fifth of one-tenth of one-half of 5,000?","img","image/text.png","208","102","54","110",3);
        Database.createRecord("Where did Bruce Lee get buried?","img","image/text.png","Hong Kong","Beijing","Seattle","Los Angeles",3);
        Database.createRecord("Which of the following company was not founded by Elon Musk?","img","image/text.png","SpaceX","Hyperloop","Tesla","SolarCity",3);
        Database.createRecord("When I get multiplied by any number, the sum of the figures in the product is always me. What am I?","img","image/text.png","10","11","9","2",3);
        Database.createRecord("Where is this temple located?","img","image/q8.jpg","Vietnam","India","Cambodia","Thailand",3);
*/

    }
    public static void main(String[] args) {
        launch();
    }
}
