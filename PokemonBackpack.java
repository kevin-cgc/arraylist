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

    /**
     * @return the backpackArrayList
     */
    public ArrayList<Pokemon> getBackpackArrayList() {
        return backpackArrayList;
    }


    public void emptyBackpack() {
        //clear the backpack

        this.backpackArrayList.clear();
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
        //remove the last pokemon in the backpack if there are still pokemon in the backpack

        if (backpackArrayList.size() != 0) backpackArrayList.remove(backpackArrayList.size()-1);
    }

    public Pokemon getPokemonAtIndex(int index) {
    	//get pokemon from index

    	return this.backpackArrayList.get(index);
    }

    public void sortedInsertPokemonIntoBackpack(Pokemon pokemonToInsert) {
    	//insert a pokemon into the index returned by getInsertIndexForNameSortedArrayList(pokemonToInsert)

        int insertIndex = this.getInsertIndexForNameSortedArrayList(pokemonToInsert);
        backpackArrayList.add(insertIndex, pokemonToInsert);
    }

    public ArrayList<String> createNameList() {
    	//return a list of all the pokemon names
    	ArrayList<String> stringlist = new ArrayList<String>();

        for (int i = 0; i < backpackArrayList.size(); i++) {
            stringlist.add(backpackArrayList.get(i).getName());
        }

    	return stringlist;
    }


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




    public void removeAllPokemonWithName(String pokemonName) {
        //iterate through backpackArrayList and all the pokemon with pokemonName

    	backpackArrayList.removeIf(p -> p.getName().equalsIgnoreCase(pokemonName));

//        for (int i = backpackArrayList.size(); i > 0; i--) { //iterate through the ArrayList
//            Pokemon pokemonAtIndexI = backpackArrayList.get(i);
//            if (pokemonAtIndexI.getName().equalsIgnoreCase(pokemonName)) backpackArrayList.remove(i);
//        }
    }

    public void removeAllPokemonWithType(String pokemonType) {
        //iterate through backpackArrayList and all the pokemon with pokemonType

    	backpackArrayList.removeIf(p -> p.getType().equalsIgnoreCase(pokemonType));

//    	for (int i = backpackArrayList.size(); i > 0; i--) { //iterate through the ArrayList
//            Pokemon pokemonAtIndexI = backpackArrayList.get(i);
//            if (pokemonAtIndexI.getType() == pokemonType) backpackArrayList.remove(i);
//        }
    }

    public void removeAllPokemonWithNameAndType(String pokemonName, String pokemonType) {
        //iterate through backpackArrayList and all the pokemon with pokemonName and pokemonType

    	backpackArrayList.removeIf(p -> p.getType().equalsIgnoreCase(pokemonType) && p.getName().equalsIgnoreCase(pokemonName));

//    	for (int i = backpackArrayList.size(); i > 0; i--) { //iterate through the ArrayList
//            Pokemon pokemonAtIndexI = backpackArrayList.get(i);
//            if (pokemonAtIndexI.getName() == pokemonName && pokemonAtIndexI.getType() == pokemonType) backpackArrayList.remove(i);
//        }
    }



    //you can ignore everything beyond this point
    public void sortByName() {
        this.backpackArrayList.sort(Comparator.comparing(Pokemon::getName));
    }

    public void sortByType() {
        this.backpackArrayList.sort(Comparator.comparing(Pokemon::getType));
    }

}
