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

    public void emptyBackpack() { //in GUI
        //clear the backpack

        this.backpackArrayList.clear();
    }

    public int getBackpackSize() { //in GUI
        //return backpack size

        return this.backpackArrayList.size();
    }

    public void appendPokemonToBackpack(Pokemon pokemonToAppend) { //in GUI
        //add the pokemon to the end of the backpack

        backpackArrayList.add(pokemonToAppend);
    }

    public void removeLastPokemonFromBackpack() { //in GUI
        //remove the last pokemon in the backpack if there are still pokemon in the backpack

        if (backpackArrayList.size() != 0) backpackArrayList.remove(backpackArrayList.size()-1);
    }

    public Pokemon getPokemonAtIndex(int index) { //in GUI
    	//get pokemon from index

    	return this.backpackArrayList.get(index);
    }


    public void sortedInsertByNameIntoBackpack(Pokemon pokemonToInsert) { //in GUI
    	//insert a pokemon into the index returned by getInsertIndexForNameSortedArrayList(pokemonToInsert)

        int insertIndex = this.getInsertIndexForNameSortedArrayList(pokemonToInsert);
        backpackArrayList.add(insertIndex, pokemonToInsert);
    }
    public void sortedInsertByTypeIntoBackpack(Pokemon pokemonToInsert) { //in GUI
    	//insert a pokemon into the index returned by getInsertIndexForNameSortedArrayList(pokemonToInsert)

        int insertIndex = this.getInsertIndexForTypeSortedArrayList(pokemonToInsert);
        backpackArrayList.add(insertIndex, pokemonToInsert);
    }

    public ArrayList<String> createNameList() { //in GUI
    	//return a list of all the pokemon names
    	ArrayList<String> stringlist = new ArrayList<String>();

        for (int i = 0; i < backpackArrayList.size(); i++) {
            stringlist.add(backpackArrayList.get(i).getName());
        }

    	return stringlist;
    }


    public int getInsertIndexForNameSortedArrayList(Pokemon pokemonToInsert) { //in GUI
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

    public int getInsertIndexForTypeSortedArrayList(Pokemon pokemonToInsert) { //in GUI
        //just getInsertIndexForNameSortedArrayList, but instead of comparing names it is comparing types



        return backpackArrayList.size();
    }









// 8888888 .d8888b. 888b    888 .d88888b. 8888888b. 8888888888   8888888b.     d8888 .d8888b.88888888888   888    88888888888888888888b. 8888888888
//   888  d88P  Y88b8888b   888d88P" "Y88b888   Y88b888          888   Y88b   d88888d88P  Y88b   888       888    888888       888   Y88b888
//   888  888    88888888b  888888     888888    888888          888    888  d88P888Y88b.        888       888    888888       888    888888
//   888  888       888Y88b 888888     888888   d88P8888888      888   d88P d88P 888 "Y888b.     888       88888888888888888   888   d88P8888888
//   888  888  88888888 Y88b888888     8888888888P" 888          8888888P" d88P  888    "Y88b.   888       888    888888       8888888P" 888
//   888  888    888888  Y88888888     888888 T88b  888          888      d88P   888      "888   888       888    888888       888 T88b  888
//   888  Y88b  d88P888   Y8888Y88b. .d88P888  T88b 888          888     d8888888888Y88b  d88P   888       888    888888       888  T88b 888
// 8888888 "Y8888P88888    Y888 "Y88888P" 888   T88b8888888888   888    d88P     888 "Y8888P"    888       888    8888888888888888   T88b8888888888
//this stuff is just provided as reference


    public void removeAllPokemonWithName(String pokemonName) { //in GUI
        //iterate through backpackArrayList and all the pokemon with pokemonName

    	backpackArrayList.removeIf(p -> p.getName().equalsIgnoreCase(pokemonName));

//      you must iterate backwards (i--) because removing elements would cause you to skip forward one
//        for (int i = backpackArrayList.size(); i > 0; i--) { //iterate through the ArrayList
//            Pokemon pokemonAtIndexI = backpackArrayList.get(i);
//            if (pokemonAtIndexI.getName().equalsIgnoreCase(pokemonName)) backpackArrayList.remove(i);
//        }
    }

    public void removeAllPokemonWithType(String pokemonType) { //in GUI
        //iterate through backpackArrayList and all the pokemon with pokemonType

    	backpackArrayList.removeIf(p -> p.getType().equalsIgnoreCase(pokemonType));

//      you must iterate backwards (i--) because removing elements would cause you to skip forward one
//    	for (int i = backpackArrayList.size(); i > 0; i--) { //iterate through the ArrayList
//            Pokemon pokemonAtIndexI = backpackArrayList.get(i);
//            if (pokemonAtIndexI.getType() == pokemonType) backpackArrayList.remove(i);
//        }
    }

    public void removeAllPokemonWithNameAndType(String pokemonName, String pokemonType) { //in GUI
        //iterate through backpackArrayList and all the pokemon with pokemonName and pokemonType

    	backpackArrayList.removeIf(p -> p.getType().equalsIgnoreCase(pokemonType) && p.getName().equalsIgnoreCase(pokemonName));

//      you must iterate backwards (i--) because removing elements would cause you to skip forward one
//    	for (int i = backpackArrayList.size(); i > 0; i--) { //iterate through the ArrayList
//            Pokemon pokemonAtIndexI = backpackArrayList.get(i);
//            if (pokemonAtIndexI.getName() == pokemonName && pokemonAtIndexI.getType() == pokemonType) backpackArrayList.remove(i);
//        }
    }



    //you can ignore everything beyond this point

    public void sortByName() { //in GUI
        this.backpackArrayList.sort(Comparator.comparing(Pokemon::getName));
    }

    public void sortByType() { //in GUI
        this.backpackArrayList.sort(Comparator.comparing(Pokemon::getType));
    }

}
