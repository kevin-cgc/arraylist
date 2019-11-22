package arraylist;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * PokemonBackpack
 */
public class PokemonBackpack {
    private ArrayList<Pokemon> backpackArrayList;

    public PokemonBackpack() {
        //simply initialize backpackArrayList
        this.backpackArrayList = new ArrayList<Pokemon>();
    }
    //GETTERS
    /**
     * @return the backpackArrayList
     */
    public ArrayList<Pokemon> getBackpackArrayList() {
        return backpackArrayList;
    }


    
    public int getBackpackSize() {
        //return backpack size

        return this.backpackArrayList.size();
    }

    public void appendPokemonToBackpack(Pokemon pokemonToAppend) {
        //add the pokemon to the end of the backpack

        backpackArrayList.add(pokemonToAppend);
    }

    public void removeLastPokemonFromBackpack() {
        //remove the last pokemon in the backpack

        backpackArrayList.remove(backpackArrayList.size()-1);
    }



    


    public void sortedInsertPokemonIntoBackpack(Pokemon pokemonToInsert) {
        int insertIndex = this.getInsertIndexForNameSortedArrayList(pokemonToInsert);

        backpackArrayList.add(insertIndex, pokemonToInsert);
    }

    public void removeAllPokemonWithName(String pokemonName) {
        //iterate through backpackArrayList and all the pokemon with pokemonName

        for (int i = 0; i < backpackArrayList.size(); i++) { //iterate through the ArrayList
            Pokemon pokemonAtIndexI = backpackArrayList.get(i);
            if (pokemonAtIndexI.getName() == pokemonName) backpackArrayList.remove(i);
        }
    }

    public void removeAllPokemonWithType(String pokemonType) {
        //iterate through backpackArrayList and all the pokemon with pokemonType

        for (int i = 0; i < backpackArrayList.size(); i++) { //iterate through the ArrayList
            Pokemon pokemonAtIndexI = backpackArrayList.get(i);
            if (pokemonAtIndexI.getType() == pokemonType) backpackArrayList.remove(i);
        }
    }

    public void removeAllPokemonWithNameAndType(String pokemonName, String pokemonType) {
        //iterate through backpackArrayList and all the pokemon with pokemonName and pokemonType

        for (int i = 0; i < backpackArrayList.size(); i++) { //iterate through the ArrayList
            Pokemon pokemonAtIndexI = backpackArrayList.get(i);
            if (pokemonAtIndexI.getName() == pokemonName && pokemonAtIndexI.getType() == pokemonType) backpackArrayList.remove(i);
        }
    }





    

    //ignore everything beyond this until challenge

    public int getInsertIndexForNameSortedArrayList(Pokemon pokemonToInsert) {
        String pokemonToInsertName = pokemonToInsert.getName(); //get the name of the pokemon you want to insert

        for (int i = 0; i < backpackArrayList.size(); i++) { //iterate through the ArrayList
            Pokemon pokemonAtIndexI = backpackArrayList.get(i); //get the next pokemon to compare names with
            String pokemonAtIndexIName = pokemonAtIndexI.getName(); //get the name of that pokemon
            
            int compare = pokemonToInsertName.compareToIgnoreCase(pokemonAtIndexIName); //Compare the strings lexicographically
            //compare < 0 then the String calling the method (pokemonToInsertName) is lexicographically first (comes first in a dictionary)
            //compare == 0 then the two strings are lexicographically equivalent
            //compare > 0 then the parameter passed to the compareToIgnoreCase method (pokemonAtIndexIName) is lexicographically first.
            if (compare < 0) return i; //if the string pokemonToInsertName lexicographically precedes pokemonAtIndexIName, this is the index to insert at
        }

        return backpackArrayList.size(); //return the end of the array if pokemonToInsertName lexicographically precedes nothing
    }

    public int getInsertIndexForTypeSortedArrayList(Pokemon pokemonToInsert) {
        //just getInsertIndexForNameSortedArrayList, but instead of comparing names it is comparing types



        return backpackArrayList.size();
    }



    //you can ignore everything beyond this point
    public void sortByName() {
        this.backpackArrayList.sort(Comparator.comparing(Pokemon::getName));
    }

    public void sortByType() {
        this.backpackArrayList.sort(Comparator.comparing(Pokemon::getType));
    }

}